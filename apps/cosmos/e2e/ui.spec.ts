import { test, expect } from '@playwright/test';

// Abre la UI de Cosmos por la ruta que ella misma genera (?fixture=…) y comprueba
// dentro del iframe del renderer que el componente se renderiza de verdad.
const FIXTURE_URL = (path: string, name: string) =>
  `/palm/?fixture=${encodeURIComponent(JSON.stringify({ path, name }))}`;

test.describe('PALM Cosmos UI -> renderer iframe', () => {
  test('button fixture renders inside the renderer iframe', async ({ page }) => {
    await page.goto(FIXTURE_URL('fixtures/components/button.fixture.tsx', 'Primary'));
    const renderer = page.frameLocator('iframe');
    await expect(renderer.locator('pm-button')).toBeVisible({ timeout: 15000 });
    await expect(renderer.locator('pm-button button').first()).toHaveAttribute('type', 'button');
  });

  test('dashboard fixture renders inside the renderer iframe', async ({ page }) => {
    await page.goto(FIXTURE_URL('fixtures/patterns/dashboard.fixture.tsx', 'Default'));
    const renderer = page.frameLocator('iframe');
    await expect(renderer.locator('pm-alert')).toBeVisible({ timeout: 15000 });
    await expect(renderer.locator('pm-data-table')).toBeVisible({ timeout: 15000 });
    await expect(renderer.locator('pm-button').filter({ hasText: 'Create' }).first()).toBeVisible({
      timeout: 15000,
    });
  });
});
