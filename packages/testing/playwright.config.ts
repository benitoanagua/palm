import { defineConfig } from '@playwright/test';

export default defineConfig({
  testDir: '.',
  testMatch: ['src/**/*.spec.ts', 'visual/**/*.spec.ts'],
  use: {
    baseURL: 'http://127.0.0.1:6006',
    screenshot: 'only-on-failure',
  },
  webServer: {
    command: 'pnpm --filter @palm/storybook storybook',
    url: 'http://127.0.0.1:6006',
    reuseExistingServer: true,
    timeout: 120_000,
  },
});
