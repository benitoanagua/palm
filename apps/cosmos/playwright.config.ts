import { defineConfig } from '@playwright/test';
import { existsSync } from 'node:fs';

const chromiumPath = existsSync(
  '/home/mamaya/.cache/ms-playwright/chromium-1234/chrome-linux64/chrome',
)
  ? '/home/mamaya/.cache/ms-playwright/chromium-1234/chrome-linux64/chrome'
  : undefined;

export default defineConfig({
  testDir: './e2e',
  use: {
    baseURL: 'http://127.0.0.1:8123',
    screenshot: 'only-on-failure',
    launchOptions: chromiumPath
      ? { executablePath: chromiumPath, args: ['--no-sandbox'] }
      : undefined,
  },
  webServer: {
    command: 'node ../../tooling/serve-palm.mjs',
    url: 'http://127.0.0.1:8123/palm/',
    reuseExistingServer: true,
    timeout: 120_000,
  },
});
