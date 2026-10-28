import fs from 'node:fs';
import path from 'node:path';

const root = process.cwd();
const packagesRoot = path.join(root, 'packages');
const matrixPath = path.join(root, 'docs/components/component-matrix.md');
const componentTags = new Set();
const matrixTags = new Set();

function walk(directory) {
  for (const entry of fs.readdirSync(directory, { withFileTypes: true })) {
    const absolute = path.join(directory, entry.name);
    if (entry.isDirectory() && !['node_modules', 'dist', '.stencil'].includes(entry.name)) walk(absolute);
    else if (/\.(ts|tsx|js|jsx)$/.test(entry.name)) {
      const source = fs.readFileSync(absolute, 'utf8');
      for (const match of source.matchAll(/\btag\s*:\s*['"](pm-[a-z0-9-]+)['"]/g)) {
        componentTags.add(match[1]);
      }
    }
  }
}

walk(packagesRoot);
const matrix = fs.readFileSync(matrixPath, 'utf8');
for (const line of matrix.split('\n')) {
  if (!line.startsWith('|')) continue;
  const firstCell = line.split('|')[1] ?? '';
  for (const match of firstCell.matchAll(/\b(pm-[a-z0-9-]+)\b/g)) matrixTags.add(match[1]);
}

const missing = [...componentTags].filter((tag) => !matrixTags.has(tag)).sort();
const stale = [...matrixTags].filter((tag) => !componentTags.has(tag)).sort();
if (missing.length || stale.length) {
  console.error('PALM component matrix does not match @Component tags.');
  if (missing.length) console.error('Missing matrix rows: ' + missing.join(', '));
  if (stale.length) console.error('Rows without @Component source: ' + stale.join(', '));
  process.exit(1);
}
console.log(`PALM component matrix covers all ${componentTags.size} @Component tags.`);
