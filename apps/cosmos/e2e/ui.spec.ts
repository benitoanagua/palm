import { test, expect } from '@playwright/test';

test.describe('PALM Cosmos UI', () => {
  test('Cosmos UI loads', async ({ page }) => {
    await page.goto('/palm/');
    await expect(page.locator('body')).toBeVisible();
  });

  test('renderer iframe loads a fixture', async ({ page }) => {
    await page.goto(
      '/?fixture=' +
        encodeURIComponent(
          JSON.stringify({ path: 'fixtures/components/button.fixture.tsx', name: 'Primary' }),
        ),
    );
    const renderer = page.frameLocator('iframe');
    await expect(renderer.locator('pm-button')).toBeVisible({ timeout: 15000 });
  });
});
