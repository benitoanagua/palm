import { existsSync } from 'node:fs';
import { resolve } from 'node:path';
import { spawnSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';

const root = resolve(fileURLToPath(new URL('..', import.meta.url)));
const testingRoot = resolve(root, 'packages/testing');
const localConfig = resolve(testingRoot, 'playwright.local.config.ts');
const config = existsSync(localConfig) ? 'playwright.local.config.ts' : 'playwright.config.ts';

const result = spawnSync(
  'pnpm',
  [
    '--filter',
    '@palm/testing',
    'exec',
    'playwright',
    'test',
    '--config',
    config,
    'src/contracts.spec.ts',
    'visual/a11y.spec.ts',
  ],
  { cwd: root, stdio: 'inherit' },
);

if (result.error) {
  console.error(`Could not start Playwright: ${result.error.message}`);
  process.exit(1);
}
process.exit(result.status ?? 1);
