import { existsSync, readdirSync, readFileSync, statSync } from 'node:fs';
import { dirname, extname, relative, resolve, sep } from 'node:path';
import { fileURLToPath } from 'node:url';

const root = resolve(dirname(fileURLToPath(import.meta.url)), '../..');
const ignoredDirectories = new Set([
  '.git',
  '.turbo',
  'coverage',
  'dist',
  'node_modules',
  'storybook-static',
  'test-results',
]);
const markdownExtensions = new Set(['.md', '.mdx']);
const failures = [];

function walk(directory) {
  for (const entry of readdirSync(directory, { withFileTypes: true })) {
    if (entry.isDirectory() && ignoredDirectories.has(entry.name)) continue;
    const absolute = resolve(directory, entry.name);
    if (entry.isDirectory()) {
      walk(absolute);
    } else if (entry.isFile() && markdownExtensions.has(extname(entry.name))) {
      checkMarkdown(absolute);
    }
  }
}

function extractTarget(rawTarget) {
  const trimmed = rawTarget.trim();
  const angleTarget = trimmed.match(/^<([^>]+)>/);
  if (angleTarget) return angleTarget[1];
  return trimmed.split(/\s+/)[0].replace(/\s+["'][^"']*["']$/, '');
}

function checkMarkdown(file) {
  const source = readFileSync(file, 'utf8')
    .replace(/\x60{3}[\s\S]*?\x60{3}/g, '')
    .replace(/~~~[\s\S]*?~~~/g, '')
    .replace(/\x60[^\x60]*\x60/g, '');
  const links = /!?\[[^\]]*\]\(([^)]+)\)/g;
  let match;

  while ((match = links.exec(source)) !== null) {
    const target = extractTarget(match[1]);
    if (
      !target ||
      /^(?:https?:|mailto:|tel:|data:|javascript:)/i.test(target) ||
      target.startsWith('#')
    ) {
      continue;
    }

    const pathOnly = decodeURIComponent(target.split('#')[0].split('?')[0]);
    if (!pathOnly) continue;

    const resolved = pathOnly.startsWith('/')
      ? resolve(root, pathOnly.slice(1))
      : resolve(dirname(file), pathOnly);

    if (!existsSync(resolved)) {
      const line = source.slice(0, match.index).split('\n').length;
      failures.push(
        `${relative(root, file).split(sep).join('/') }:${line} -> ${target}`,
      );
    }
  }
}

walk(root);

if (failures.length > 0) {
  console.error('Broken internal Markdown links:');
  for (const failure of failures) console.error(`- ${failure}`);
  process.exitCode = 1;
} else {
  console.log('Internal Markdown links are valid.');
}
