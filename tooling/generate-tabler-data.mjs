import { copyFileSync, existsSync, mkdirSync, readFileSync, statSync } from 'node:fs';
import { dirname, resolve } from 'node:path';

const root = resolve(new URL('..', import.meta.url).pathname);
const source = resolve(root, 'packages/icons/node_modules/@tabler/icons/tabler-nodes-outline.json');
const destination = resolve(root, 'packages/icons/src/data/tabler-outline.json');

if (!existsSync(source)) {
  throw new Error(
    `Tabler outline dataset not found at ${source}. Install workspace dependencies before generating icon data.`,
  );
}

const parsed = JSON.parse(readFileSync(source, 'utf8'));
if (!parsed || typeof parsed !== 'object' || Array.isArray(parsed)) {
  throw new Error('The Tabler outline dataset must be a JSON object keyed by icon name.');
}

const iconCount = Object.keys(parsed).length;
if (iconCount < 4000) {
  throw new Error(`Expected at least 4,000 Tabler outline icons; found ${iconCount}.`);
}

mkdirSync(dirname(destination), { recursive: true });
copyFileSync(source, destination);

const bytes = statSync(destination).size;
console.log(`Generated ${iconCount} Tabler outline icons (${bytes} bytes): packages/icons/src/data/tabler-outline.json`);
