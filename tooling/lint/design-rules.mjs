import { readFileSync, readdirSync, statSync } from 'node:fs';
import { join } from 'node:path';

const root = new URL('../..', import.meta.url).pathname;
const forbidden = [/from\s+['"]lit(?:\/|['"])/, /from\s+['"]lit-html['"]/, /LitElement/];
const ignored = new Set(['node_modules', 'dist', '.git']);

function walk(dir) {
  const files = [];
  for (const name of readdirSync(dir)) {
    if (ignored.has(name)) continue;
    const path = join(dir, name);
    const stat = statSync(path);
    if (stat.isDirectory()) files.push(...walk(path));
    else if (/\.(ts|tsx|js|mjs|jsx)$/.test(name)) files.push(path);
  }
  return files;
}

const files = walk(join(root, 'packages'));
const violations = [];
for (const file of files) {
  const text = readFileSync(file, 'utf8');
  for (const pattern of forbidden)
    if (pattern.test(text)) violations.push(`${file}: forbidden Lit dependency`);
}

const tokenSources = [
  join(root, 'packages/tokens/src/tokens.css'),
  join(root, 'packages/tokens/src/themes.css'),
];
const declaredTokens = new Set(
  tokenSources.flatMap((file) =>
    [...readFileSync(file, 'utf8').matchAll(/(--pm-[\w-]+)\s*:/g)].map((match) => match[1]),
  ),
);
const tokenPrefixes = [
  '--pm-color-',
  '--pm-space-',
  '--pm-size-control-',
  '--pm-radius-',
  '--pm-font-',
  '--pm-line-height-',
  '--pm-letter-spacing-',
  '--pm-type-',
  '--pm-motion-',
  '--pm-ease-',
  '--pm-focus-',
  '--pm-shadow-',
  '--pm-breakpoint-',
  '--pm-grid-',
  '--pm-container-',
  '--pm-z-',
];

function walkCss(dir) {
  const files = [];
  for (const name of readdirSync(dir)) {
    if (ignored.has(name)) continue;
    const path = join(dir, name);
    const stat = statSync(path);
    if (stat.isDirectory()) files.push(...walkCss(path));
    else if (name.endsWith('.css')) files.push(path);
  }
  return files;
}

for (const file of [...walkCss(join(root, 'packages')), ...walkCss(join(root, 'apps'))]) {
  const text = readFileSync(file, 'utf8');
  for (const match of text.matchAll(/var\(\s*(--pm-[\w-]+)/g)) {
    const token = match[1];
    if (tokenPrefixes.some((prefix) => token.startsWith(prefix)) && !declaredTokens.has(token)) {
      violations.push(`${file}: undefined design token ${token}`);
    }
  }
}

if (violations.length) {
  console.error(violations.join('\n'));
  process.exit(1);
}
console.log(`PALM architecture rules passed (${files.length} source files checked).`);
