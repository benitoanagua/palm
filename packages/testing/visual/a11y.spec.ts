import { expect, test, type FrameLocator } from '@playwright/test';

async function assertAccessibleBasics(frame: FrameLocator) {
  const violations = await frame.locator('body').evaluate(() => {
    const problems: string[] = [];
    document.querySelectorAll('img').forEach((el) => {
      if (!el.hasAttribute('alt')) problems.push('img without alt');
    });
    document.querySelectorAll('button').forEach((el) => {
      if (
        !el.textContent?.trim() &&
        !el.getAttribute('aria-label') &&
        !el.getAttribute('aria-labelledby')
      ) {
        problems.push('button without accessible name');
      }
    });
    document.querySelectorAll('input, textarea, select').forEach((el) => {
      const labelled =
        el.getAttribute('aria-label') ||
        el.getAttribute('aria-labelledby') ||
        (el.id && document.querySelector(`label[for="${CSS.escape(el.id)}"]`));
      if (!labelled) problems.push(`${el.tagName.toLowerCase()} without accessible name`);
    });
    document.querySelectorAll('dialog, [role="dialog"]').forEach((el) => {
      if (
        !el.getAttribute('aria-label') &&
        !el.getAttribute('aria-labelledby') &&
        !el.querySelector('h1, h2, h3, h4, h5, h6')
      ) {
        problems.push('dialog without accessible name');
      }
    });
    document.querySelectorAll('[aria-invalid="true"]').forEach((el) => {
      const described = el.getAttribute('aria-describedby');
      if (!described || !described.split(/\\s+/).some((id) => document.getElementById(id))) {
        problems.push('invalid control without described error/hint');
      }
    });
    return problems;
  });
  expect(violations, violations.join('\\n')).toEqual([]);
}

test.describe('PALM accessibility audit', () => {
  test('component gallery has accessible names and semantics', async ({ page }) => {
    await page.goto('/?path=/story/palm-components-gallery--core');
    const frame = page.frameLocator('#storybook-preview-iframe');
    await assertAccessibleBasics(frame);
    await expect(frame.locator('pm-button button').first()).toBeVisible();
  });

  test('keyboard can reach primary controls', async ({ page }) => {
    await page.goto('/?path=/story/palm-components-gallery--core');
    const frame = page.frameLocator('#storybook-preview-iframe');
    const button = frame.locator('pm-button button').first();
    await button.focus();
    await expect(button).toBeFocused();
    await page.keyboard.press('Tab');
    await expect(frame.locator(':focus')).toBeVisible();
  });

  test('dialog restores focus after Escape', async ({ page }) => {
    await page.goto('/?path=/story/palm-components-overlays--dialog-and-drawer');
    const frame = page.frameLocator('#storybook-preview-iframe');
    const openButton = frame.locator('pm-button button').first();
    await openButton.focus();
    await openButton.click();
    await expect(frame.locator('pm-dialog dialog')).toBeVisible();
    await page.keyboard.press('Escape');
    await expect(frame.locator('pm-dialog dialog')).toBeHidden();
    await expect(openButton).toBeFocused();
  });

  test('reduced motion preference is applied', async ({ page }) => {
    await page.emulateMedia({ reducedMotion: 'reduce' });
    await page.goto('/?path=/story/palm-components-gallery--core');
    const frame = page.frameLocator('#storybook-preview-iframe');
    await expect(frame.locator('pm-button button').first()).toBeVisible();
    const value = await frame
      .locator('html')
      .evaluate((el) => getComputedStyle(el).getPropertyValue('--pm-motion-normal').trim());
    expect(value).toBeTruthy();
  });
});
