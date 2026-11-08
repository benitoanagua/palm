import { test, expect } from '@playwright/test';

const FIXTURE = (path: string, name: string) =>
  `/palm/renderer.html?fixtureId=${encodeURIComponent(JSON.stringify({ path, name }))}&locked=true`;

test.describe('PALM public contracts', () => {
  test('button exposes a native button with its accessible name', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/button.fixture.tsx', 'Primary'));
    await expect(page.getByRole('button', { name: 'Continue' })).toBeVisible();
  });

  test('input exposes an accessible name from its label', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/input.fixture.tsx', 'Default'));
    await expect(page.locator('pm-input')).toBeVisible();
  });
});
