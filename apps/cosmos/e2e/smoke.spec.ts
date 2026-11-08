import { test, expect } from '@playwright/test';

test.describe('PALM Cosmos smoke', () => {
  test('Cosmos UI opens and lists fixture categories', async ({ page }) => {
    await page.goto('/palm/');
    // The fixture tree must render its category entries (components, patterns, primitives).
    await expect(page.getByRole('button', { name: 'components' })).toBeVisible({ timeout: 10000 });
    await expect(page.getByRole('button', { name: 'patterns' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'primitives' })).toBeVisible();
  });

  test('renderer loads a fixture', async ({ page }) => {
    await page.goto(
      '/palm/renderer.html?fixtureId=' +
        encodeURIComponent(
          JSON.stringify({
            path: 'fixtures/components/button.fixture.tsx',
            name: 'Primary',
          }),
        ) +
        '&locked=true',
    );
    await expect(page.locator('pm-button')).toBeVisible();
  });
});
