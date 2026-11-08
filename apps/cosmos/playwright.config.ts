import { defineConfig } from '@playwright/test';

export default defineConfig({
  testDir: './e2e',
  use: {
    baseURL: 'http://127.0.0.1:8123',
    headless: true,
    launchOptions: {
      executablePath: '/usr/sbin/chromium',
      args: ['--no-sandbox', '--disable-dev-shm-usage'],
    },
    screenshot: 'only-on-failure',
  },
  webServer: {
    command: 'node ../../tooling/serve-palm.mjs cosmos-export /palm 8123',
    url: 'http://127.0.0.1:8123/palm/',
    reuseExistingServer: true,
    timeout: 120_000,
  },
});
