import { test, expect } from '@playwright/test';

test.describe('PALM Cosmos smoke', () => {
  test('Cosmos UI opens', async ({ page }) => {
    await page.goto('/palm/');
    await expect(page.locator('body')).toBeVisible();
  });

  test('renderer loads a page', async ({ page }) => {
    await page.goto('/palm/renderer.html');
    await expect(page.locator('#root')).toBeVisible();
  });
});
