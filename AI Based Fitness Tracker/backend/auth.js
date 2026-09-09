import crypto from 'node:crypto';

const secret = process.env.JWT_SECRET || 'development-only-change-me';
const encode = (value) => Buffer.from(value).toString('base64url');
const decode = (value) => Buffer.from(value, 'base64url').toString('utf8');

export function hashPassword(password, salt = crypto.randomBytes(16).toString('hex')) {
  const hash = crypto.pbkdf2Sync(password, salt, 210000, 32, 'sha256').toString('hex');
  return `${salt}:${hash}`;
}

export function verifyPassword(password, stored) {
  const [salt, expected] = stored.split(':');
  const actual = hashPassword(password, salt).split(':')[1];
  return crypto.timingSafeEqual(Buffer.from(actual, 'hex'), Buffer.from(expected, 'hex'));
}

export function signToken(user) {
  const header = encode(JSON.stringify({ alg: 'HS256', typ: 'JWT' }));
  const payload = encode(JSON.stringify({ sub: user.id, email: user.email, role: user.role, exp: Math.floor(Date.now() / 1000) + 60 * 60 * 8 }));
  const signature = crypto.createHmac('sha256', secret).update(`${header}.${payload}`).digest('base64url');
  return `${header}.${payload}.${signature}`;
}

export function verifyToken(token) {
  const [header, payload, signature] = String(token || '').split('.');
  if (!header || !payload || !signature) throw new Error('Missing or malformed access token');
  const expected = crypto.createHmac('sha256', secret).update(`${header}.${payload}`).digest('base64url');
  if (!crypto.timingSafeEqual(Buffer.from(signature), Buffer.from(expected))) throw new Error('Invalid access token');
  const claims = JSON.parse(decode(payload));
  if (!claims.exp || claims.exp * 1000 <= Date.now()) throw new Error('Access token has expired');
  return claims;
}

export function requireAuth(request) {
  const match = request.headers.authorization?.match(/^Bearer\s+(.+)$/i);
  if (!match) throw Object.assign(new Error('Authentication is required'), { status: 401 });
  try { return verifyToken(match[1]); } catch (error) { throw Object.assign(error, { status: 401 }); }
}

