import test from 'node:test';
import assert from 'node:assert/strict';
import { createServer } from './server.js';
import fs from 'node:fs';
import path from 'node:path';

const data = path.resolve(process.cwd(), 'data');
test('registration, protected activity creation, and recommendations work together', async () => {
  fs.rmSync(data, { recursive: true, force: true });
  const server = createServer(); await new Promise((resolve) => server.listen(0, resolve));
  const base = `http://127.0.0.1:${server.address().port}`;
  try {
    const registration = await fetch(`${base}/api/auth/register`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ email: 'test@example.com', password: 'secure-pass-123', firstName: 'Test' }) });
    assert.equal(registration.status, 201); const account = await registration.json(); assert.ok(account.accessToken); assert.equal(account.user.password, undefined);
    const auth = { Authorization: `Bearer ${account.accessToken}`, 'Content-Type': 'application/json' };
    const activity = await fetch(`${base}/api/activities`, { method: 'POST', headers: auth, body: JSON.stringify({ type: 'RUNNING', duration: 30, caloriesBurned: 270, startTime: '2026-09-09T08:00:00.000Z' }) });
    assert.equal(activity.status, 201);
    const recommendations = await fetch(`${base}/api/recommendations`, { headers: auth }); assert.equal(recommendations.status, 200); assert.equal((await recommendations.json()).recommendations.length, 1);
    const unauthenticated = await fetch(`${base}/api/activities`); assert.equal(unauthenticated.status, 401);
  } finally { await new Promise((resolve) => server.close(resolve)); fs.rmSync(data, { recursive: true, force: true }); }
});

