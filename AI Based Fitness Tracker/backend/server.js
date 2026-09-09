import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { hashPassword, requireAuth, signToken, verifyPassword } from './auth.js';
import { id, readDatabase, writeDatabase } from './database.js';
import { createRecommendation } from './recommendations.js';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const frontend = path.join(root, 'frontend');
const activityTypes = new Set(['RUNNING', 'WALKING', 'CYCLING', 'SWIMMING', 'WEIGHT_TRAINING', 'YOGA', 'HIIT', 'CARDIO', 'STRETCHING', 'OTHER']);

function json(response, status, body) {
  response.writeHead(status, { 'Content-Type': 'application/json; charset=utf-8', 'Cache-Control': 'no-store' });
  response.end(JSON.stringify(body));
}

function publicUser(user) { return { id: user.id, email: user.email, firstName: user.firstName, lastName: user.lastName, role: user.role, createdAt: user.createdAt }; }

async function body(request) {
  let raw = '';
  for await (const chunk of request) { raw += chunk; if (raw.length > 1_000_000) throw Object.assign(new Error('Request body is too large'), { status: 413 }); }
  try { return raw ? JSON.parse(raw) : {}; } catch { throw Object.assign(new Error('Request body must be valid JSON'), { status: 400 }); }
}

function validateRegistration(input) {
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(input.email || ''))) throw Object.assign(new Error('A valid email is required'), { status: 400 });
  if (String(input.password || '').length < 8) throw Object.assign(new Error('Password must be at least 8 characters'), { status: 400 });
  if (!String(input.firstName || '').trim()) throw Object.assign(new Error('First name is required'), { status: 400 });
}

function validateActivity(input) {
  if (!activityTypes.has(input.type)) throw Object.assign(new Error('Activity type is invalid'), { status: 400 });
  if (!Number.isInteger(input.duration) || input.duration < 1 || input.duration > 1440) throw Object.assign(new Error('Duration must be between 1 and 1440 minutes'), { status: 400 });
  if (!Number.isInteger(input.caloriesBurned) || input.caloriesBurned < 0 || input.caloriesBurned > 20000) throw Object.assign(new Error('Calories burned is invalid'), { status: 400 });
  if (Number.isNaN(Date.parse(input.startTime))) throw Object.assign(new Error('Start time is invalid'), { status: 400 });
}

function serveStatic(request, response, pathname) {
  const requested = pathname === '/' ? 'index.html' : pathname.replace(/^\/+/, '');
  const resolved = path.resolve(frontend, requested);
  if (!resolved.startsWith(frontend) || !fs.existsSync(resolved) || fs.statSync(resolved).isDirectory()) return false;
  const types = { '.html': 'text/html; charset=utf-8', '.js': 'text/javascript; charset=utf-8', '.css': 'text/css; charset=utf-8' };
  response.writeHead(200, { 'Content-Type': types[path.extname(resolved)] || 'application/octet-stream' });
  fs.createReadStream(resolved).pipe(response);
  return true;
}

export function createServer() {
  return http.createServer(async (request, response) => {
    response.setHeader('Access-Control-Allow-Origin', process.env.CORS_ORIGIN || 'http://localhost:3000');
    response.setHeader('Access-Control-Allow-Headers', 'Authorization, Content-Type');
    response.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
    if (request.method === 'OPTIONS') return response.writeHead(204).end();
    const url = new URL(request.url, `http://${request.headers.host || 'localhost'}`);
    try {
      if (!url.pathname.startsWith('/api/')) { if (!serveStatic(request, response, url.pathname)) json(response, 404, { error: 'Not found' }); return; }
      const database = readDatabase();
      if (request.method === 'POST' && url.pathname === '/api/auth/register') {
        const input = await body(request); validateRegistration(input);
        const email = input.email.trim().toLowerCase();
        if (database.users.some((user) => user.email === email)) throw Object.assign(new Error('An account with this email already exists'), { status: 409 });
        const user = { id: id(), email, passwordHash: hashPassword(input.password), firstName: input.firstName.trim(), lastName: String(input.lastName || '').trim(), role: database.users.length ? 'USER' : 'ADMIN', createdAt: new Date().toISOString() };
        database.users.push(user); writeDatabase(database); return json(response, 201, { user: publicUser(user), accessToken: signToken(user) });
      }
      if (request.method === 'POST' && url.pathname === '/api/auth/login') {
        const input = await body(request); const user = database.users.find((candidate) => candidate.email === String(input.email || '').trim().toLowerCase());
        if (!user || !verifyPassword(String(input.password || ''), user.passwordHash)) throw Object.assign(new Error('Email or password is incorrect'), { status: 401 });
        return json(response, 200, { user: publicUser(user), accessToken: signToken(user) });
      }
      const claims = requireAuth(request);
      const user = database.users.find((candidate) => candidate.id === claims.sub);
      if (!user) throw Object.assign(new Error('Account no longer exists'), { status: 401 });
      if (request.method === 'GET' && url.pathname === '/api/auth/me') return json(response, 200, { user: publicUser(user) });
      if (request.method === 'POST' && url.pathname === '/api/auth/logout') return json(response, 204, null);
      if (request.method === 'POST' && url.pathname === '/api/activities') {
        const input = await body(request); validateActivity(input);
        const now = new Date().toISOString();
        const activity = { id: id(), userId: user.id, type: input.type, duration: input.duration, caloriesBurned: input.caloriesBurned, startTime: new Date(input.startTime).toISOString(), additionalMetrics: input.additionalMetrics && typeof input.additionalMetrics === 'object' ? input.additionalMetrics : {}, createdAt: now, updatedAt: now };
        database.activities.push(activity); const recommendation = { id: id(), ...(await createRecommendation(activity)) }; database.recommendations.push(recommendation); writeDatabase(database);
        return json(response, 201, { activity, recommendationStatus: 'ready' });
      }
      if (request.method === 'GET' && url.pathname === '/api/activities') {
        const activities = database.activities.filter((activity) => activity.userId === user.id).sort((a, b) => b.startTime.localeCompare(a.startTime));
        return json(response, 200, { activities });
      }
      if (request.method === 'GET' && url.pathname === '/api/recommendations') {
        const recommendations = database.recommendations.filter((recommendation) => recommendation.userId === user.id).sort((a, b) => b.createdAt.localeCompare(a.createdAt));
        return json(response, 200, { recommendations });
      }
      if (request.method === 'GET' && url.pathname === '/api/admin/users') {
        if (user.role !== 'ADMIN') throw Object.assign(new Error('Administrator access is required'), { status: 403 });
        return json(response, 200, { users: database.users.map(publicUser) });
      }
      json(response, 404, { error: 'API route not found' });
    } catch (error) {
      json(response, error.status || 500, { error: error.message || 'Unexpected server error' });
    }
  });
}

if (process.argv[1] === fileURLToPath(import.meta.url)) {
  const port = Number(process.env.PORT || 3000);
  createServer().listen(port, () => console.log(`AI Based Fitness Tracker running at http://localhost:${port}`));
}

