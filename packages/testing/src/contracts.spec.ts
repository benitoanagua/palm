import { test, expect } from '@playwright/test';

test.describe('PALM public contracts', () => {
  test('button exposes a native button with its accessible name', async ({ page }) => {
    await page.goto('/?path=/story/palm-components-button--primary');
    const button = page.frameLocator('#storybook-preview-iframe').locator('pm-button button');
    await expect(button).toHaveRole('button', { name: 'Continue' });
  });
});
