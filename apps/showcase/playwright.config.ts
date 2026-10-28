import { defineConfig, devices } from '@playwright/test';
export default defineConfig({
  testDir: './tests',
  use: { baseURL: 'http://127.0.0.1:4173', trace: 'retain-on-failure' },
  webServer: {
    command:
      'pnpm --filter @palm/components build && pnpm --filter @palm/showcase build && pnpm --filter @palm/showcase preview',
    port: 4173,
    reuseExistingServer: true,
  },
  projects: [{ name: 'chromium', use: { ...devices['Desktop Chrome'] } }],
});
