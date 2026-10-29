import { readFileSync, readdirSync, statSync } from 'node:fs';
import { join } from 'node:path';

const root = new URL('../..', import.meta.url).pathname;
const forbidden = [/from\s+['"]lit(?:\/|['"])/, /from\s+['"]lit-html['"]/, /LitElement/];
const ignored = new Set([
  'node_modules',
  'dist',
  '.git',
  '.stencil',
  'www',
  'loader',
  '.turbo',
  '.cache',
  'storybook-static',
  'playwright-report',
  'blob-report',
  'coverage',
  'test-results',
]);

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

// --- Rule: no hardcoded px border-radius in packages/ source CSS ---
// Allowed: 50% (circles), calc(var(...) ± Npx) structural offsets, 999px (pill), var(--pm-radius-*), 0
const tokenFiles = new Set(tokenSources);
const hexColorPattern = /#[0-9a-fA-F]{3,8}(?=[\s;,)!]|$)/gm;

for (const file of walkCss(join(root, 'packages'))) {
  // Token definition files are allowed to contain hex values and raw px
  if (tokenFiles.has(file)) continue;
  const text = readFileSync(file, 'utf8');

  // No hardcoded standalone px border-radius (calc() offsets with var() are OK)
  for (const match of text.matchAll(/border-radius\s*:[^;]+;/g)) {
    const value = match[0];
    // Skip if the only px usage is inside a calc() that already references a token var
    if (/calc\(\s*var\(--pm-/.test(value)) continue;
    // Skip 999px (pill definition), 50% (circles), 0 values
    if (/\b999px\b/.test(value)) continue;
    // Flag bare px values not wrapped in calc+var
    if (/\b\d+px\b/.test(value)) {
      violations.push(`${file}: hardcoded px border-radius – use var(--pm-radius-*) instead`);
    }
  }

  // No hardcoded hex colors in component CSS (must use tokens or CSS vars)
  for (const match of text.matchAll(hexColorPattern)) {
    violations.push(`${file}: hardcoded hex color ${match[0]} – use a --pm-color-* token instead`);
  }
}

if (violations.length) {
  console.error(violations.join('\n'));
  process.exit(1);
}
console.log(`PALM architecture rules passed (${files.length} source files checked).`);
