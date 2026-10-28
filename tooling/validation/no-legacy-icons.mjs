import { readFileSync, readdirSync, statSync } from 'node:fs';
import { join, resolve } from 'node:path';

const root = resolve(new URL('../..', import.meta.url).pathname);
const forbidden = [
  new RegExp(String.fromCharCode(99, 97, 114, 98, 111, 110), 'i'),
  new RegExp(String.fromCharCode(105, 99, 111, 110, 105, 102, 121), 'i'),
];
const ignored = new Set(['node_modules', 'dist', '.git', 'coverage', 'test-results']);
const violations = [];

function walk(dir) {
  for (const entry of readdirSync(dir)) {
    if (ignored.has(entry)) continue;
    const file = join(dir, entry);
    const stat = statSync(file);
    if (stat.isDirectory()) {
      walk(file);
      continue;
    }
    if (!/\.(ts|tsx|js|mjs|json|md|css|html|yaml|yml)$/.test(entry)) continue;
    const text = readFileSync(file, 'utf8');
    if (forbidden.some((pattern) => pattern.test(text))) violations.push(file);
  }
}

walk(root);
if (violations.length) {
  console.error('PALM legacy icon reference check failed:');
  console.error(violations.join('\n'));
  process.exit(1);
}
console.log('PALM legacy icon reference check passed.');
