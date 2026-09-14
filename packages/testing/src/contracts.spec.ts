import { test, expect } from '@playwright/test';

test.describe('PALM public contracts', () => {
  test('button exposes a native button', async ({ page }) => {
    await page.setContent('<pm-button>Continue</pm-button>');
    const button = page.locator('pm-button button');
    await expect(button).toHaveRole('button', { name: 'Continue' });
  });
});
