import fs from 'node:fs';
import path from 'node:path';

const root = process.cwd();
const readJson = (relative) => JSON.parse(fs.readFileSync(path.join(root, relative), 'utf8'));
const cosmos = readJson('apps/cosmos/package.json');
const react = readJson('packages/react/package.json');

const cosmosVersion = cosmos.dependencies['react-cosmos'] ?? cosmos.devDependencies['react-cosmos'];
const pluginVersion = cosmos.devDependencies['react-cosmos-plugin-vite'];

const major = (range) => Number(String(range).match(/\d+/)?.[0]);
const errors = [];
if (major(cosmosVersion) !== 7)
  errors.push(
    `react-cosmos must be major 7 for react-cosmos-plugin-vite ${pluginVersion}; found ${cosmosVersion}`,
  );
if (
  major(react.devDependencies['@types/react']) !== 18 ||
  major(react.devDependencies['@types/react-dom']) !== 18
) {
  errors.push(
    'React adapter development types must remain on React 18 to match the package peer contract.',
  );
}

if (errors.length) {
  console.error('PALM dependency consistency check failed:');
  for (const error of errors) console.error(`- ${error}`);
  process.exit(1);
}

console.log('PALM dependency consistency check passed.');
