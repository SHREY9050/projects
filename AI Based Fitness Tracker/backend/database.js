import fs from 'node:fs';
import path from 'node:path';
import crypto from 'node:crypto';

const directory = path.resolve(process.cwd(), 'data');
const filename = path.join(directory, 'fitness-tracker.json');
const empty = () => ({ users: [], activities: [], recommendations: [] });

export function readDatabase() {
  if (!fs.existsSync(filename)) return empty();
  return JSON.parse(fs.readFileSync(filename, 'utf8'));
}

export function writeDatabase(database) {
  fs.mkdirSync(directory, { recursive: true });
  const temp = `${filename}.tmp`;
  fs.writeFileSync(temp, JSON.stringify(database, null, 2));
  fs.renameSync(temp, filename);
}

export function id() { return crypto.randomUUID(); }

