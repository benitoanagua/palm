import { existsSync } from 'node:fs';
import { resolve } from 'node:path';
import { spawnSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';

const root = resolve(fileURLToPath(new URL('..', import.meta.url)));
const testingRoot = resolve(root, 'packages/testing');
const localConfig = resolve(testingRoot, 'playwright.local.config.ts');
const config = existsSync(localConfig) ? 'playwright.local.config.ts' : 'playwright.config.ts';

const commands = [
  {
    label: 'component contracts and accessibility',
    args: [
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
  },
  {
    label: 'showcase browser tests',
    args: ['--filter', '@palm/showcase', 'test:e2e'],
  },
];

for (const command of commands) {
  console.log(`\nRunning ${command.label}…`);
  const result = spawnSync('pnpm', command.args, { cwd: root, stdio: 'inherit' });
  if (result.error) {
    console.error(`Could not start ${command.label}: ${result.error.message}`);
    process.exit(1);
  }
  if (result.status !== 0) process.exit(result.status ?? 1);
}
