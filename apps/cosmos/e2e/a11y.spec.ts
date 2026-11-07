import { expect, test } from '@playwright/test';

const FIXTURE = (path: string, name: string) =>
  `/palm/renderer.html?fixtureId=${encodeURIComponent(JSON.stringify({ path, name }))}&locked=true`;

async function assertAccessibleBasics(page: any) {
  const problems = await page.evaluate(() => {
    const issues: string[] = [];
    document.querySelectorAll('img').forEach((el) => {
      if (!el.hasAttribute('alt')) issues.push('img without alt');
    });
    document.querySelectorAll('button').forEach((el) => {
      if (
        !el.textContent?.trim() &&
        !el.getAttribute('aria-label') &&
        !el.getAttribute('aria-labelledby')
      ) {
        issues.push('button without accessible name');
      }
    });
    return issues;
  });
  expect(problems, problems.join('\n')).toEqual([]);
}

test.describe('PALM accessibility checks', () => {
  test('button fixture has accessible basics', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/button.fixture.tsx', 'Primary'));
    await assertAccessibleBasics(page);
    await expect(page.locator('pm-button button').first()).toBeVisible();
  });

  test('dashboard fixture renders', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/patterns/dashboard.fixture.tsx', 'Default'));
    await expect(page.locator('pm-alert')).toBeVisible({ timeout: 10000 });
    await assertAccessibleBasics(page);
  });
});
