import { readFileSync, readdirSync, statSync } from 'node:fs';
import { join, resolve } from 'node:path';

const packageName = process.argv[2];
const root = resolve(new URL(`../../packages/${packageName}/`, import.meta.url).pathname);
const violations = [];

function walk(dir) {
  for (const entry of readdirSync(dir)) {
    if (entry === 'node_modules' || entry === 'dist') continue;
    const file = join(dir, entry);
    if (statSync(file).isDirectory()) walk(file);
    else if (/\.(ts|tsx|js|mjs)$/.test(entry)) {
      const text = readFileSync(file, 'utf8');
      const banned = [
        /from ['"]lit['"]/,
        /from ['"]lit\//,
        /LitElement/,
        /lit-html/,
        /@lit\//,
        new RegExp(
          String.fromCharCode(105, 99, 111, 110, 105, 102, 121, 45, 105, 99, 111, 110),
          'i',
        ),
        new RegExp(
          String.fromCharCode(105, 99, 111, 110, 105, 102, 121, 45, 106, 115, 111, 110),
          'i',
        ),
        new RegExp(['c', 'a', 'r', 'b', 'o', 'n'].join(''), 'i'),
      ];
      for (const pattern of banned)
        if (pattern.test(text))
          violations.push(`${file}: forbidden dependency or legacy icon reference`);
    }
  }
}

walk(root);
if (violations.length) {
  console.error(
    'PALM architecture violation: forbidden framework or icon-provider dependency detected.',
  );
  console.error(violations.join('\n'));
  process.exit(1);
}
