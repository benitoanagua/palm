import { test, expect } from '@playwright/test';

test('showcase renders the system surface', async ({ page }) => {
  await page.goto('/');
  await expect(page.getByRole('heading', { name: 'Overview' })).toBeVisible();
  await expect(page.locator('pm-metric-card')).toHaveCount(4);
  await expect(
    page.getByText(
      'Every element below is composed from PALM Web Components, tokens and Tabler Icons.',
    ),
  ).toBeVisible();
});

test('navigation switches product surfaces', async ({ page }) => {
  await page.goto('/');
  await page.locator('.sidebar-nav').getByRole('button', { name: 'Activity' }).click();
  await expect(page.locator('#page-title')).toHaveText('Activity');
  await expect(page.locator('[data-panel="activity"]')).toBeVisible();
  await expect(page.getByText('Recent events')).toBeVisible();
  await expect(
    page.getByText('Responsive tables, semantic status and toolbar composition.'),
  ).toBeVisible();
});

test('create dialog opens and closes', async ({ page }) => {
  await page.goto('/');
  await page.locator('#hero-create').click();
  const dialog = page.locator('#create-dialog dialog[open]');
  await expect(dialog).toBeVisible();
  await page.keyboard.press('Escape');
  await expect(dialog).not.toBeVisible();
});

test('mobile navigation is available', async ({ page }) => {
  await page.setViewportSize({ width: 375, height: 812 });
  await page.goto('/');
  await page.getByRole('button', { name: 'Open navigation' }).click();
  await expect(page.locator('pm-drawer')).toBeVisible();
});
