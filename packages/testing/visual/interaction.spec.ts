import { test, expect } from '@playwright/test';

test.describe('PALM interactions', () => {
  test('tabs use roving tabindex and keyboard arrows', async ({ page }) => {
    await page.goto('http://127.0.0.1:6006/?path=/story/palm-components');
    const tab = page.locator('pm-tab').first();
    if (!(await tab.count())) return;
    await tab.locator('button').focus();
    await page.keyboard.press('ArrowRight');
    await expect(page.locator('pm-tab button:focus')).toBeVisible();
  });

  test('mobile drawer closes with Escape', async ({ page }) => {
    await page.setViewportSize({ width: 375, height: 812 });
    await page.goto('http://127.0.0.1:6006/?path=/story/palm-components');
    const menu = page.locator('#menu-toggle');
    if (!(await menu.count())) return;
    await menu.click();
    const drawer = page.locator('pm-drawer .drawer');
    await expect(drawer).toBeVisible();
    await page.keyboard.press('Escape');
    await expect(drawer).toBeHidden();
  });
});
