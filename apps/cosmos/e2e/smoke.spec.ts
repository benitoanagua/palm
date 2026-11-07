import { test, expect } from '@playwright/test';

const FIXTURE = (path: string, name: string) =>
  `/palm/renderer.html?fixtureId=${encodeURIComponent(JSON.stringify({ path, name }))}&locked=true`;

test.describe('PALM Cosmos smoke', () => {
  test('Cosmos UI opens', async ({ page }) => {
    await page.goto('/palm/');
    // Wait for the React app to hydrate (check for fixture tree text)
    await expect(page.locator('text=Fixtures')).toBeVisible({ timeout: 10000 });
  });

  test('renderer loads a fixture', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/button.fixture.tsx', 'Primary'));
    await expect(page.locator('pm-button')).toBeVisible();
  });
});
