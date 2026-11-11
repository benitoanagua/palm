import { defineConfig } from '@playwright/test';

// El e2e se ejecuta sobre el export estático (apps/cosmos/cosmos-export) servido
// bajo /palm/ por tooling/serve-palm.mjs. Para apuntar al servidor de desarrollo
// de Cosmos (puerto 5000) ejecuta `pnpm cosmos` en otra pestaña y luego:
//   COSMOS_BASE_URL=http://127.0.0.1:5000 pnpm cosmos:e2e
export default defineConfig({
  testDir: './e2e',
  use: {
    baseURL: process.env.COSMOS_BASE_URL ?? 'http://127.0.0.1:8123',
    headless: true,
    launchOptions: {
      executablePath: process.env.CHROMIUM_PATH ?? '/usr/bin/chromium',
      args: ['--no-sandbox', '--disable-dev-shm-usage', '--disable-gpu'],
    },
    screenshot: 'only-on-failure',
    viewport: { width: 1280, height: 800 },
  },
  workers: 2,
  webServer: process.env.COSMOS_BASE_URL
    ? undefined
    : {
        command: 'node ../../tooling/serve-palm.mjs cosmos-export /palm 8123',
        url: 'http://127.0.0.1:8123/palm/',
        reuseExistingServer: false,
        timeout: 120_000,
      },
});
