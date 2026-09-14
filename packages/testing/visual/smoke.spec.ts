import { test, expect } from '@playwright/test';

test('PALM Storybook is reachable', async ({ page }) => {
  await page.goto('/');
  await expect(page.locator('body')).toContainText('PALM');
});
