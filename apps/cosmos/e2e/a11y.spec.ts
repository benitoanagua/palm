import { test, expect } from '@playwright/test';
import { AxeBuilder } from '@axe-core/playwright';

const FIXTURE = (path: string, name: string) =>
  `/palm/renderer.html?fixtureId=${encodeURIComponent(JSON.stringify({ path, name }))}&locked=true`;

// color-contrast es la regla crítica. Las demás (button-name, image-alt, …)
// dependen del contexto completo de la página del catálogo y de botones
// generados dinámicamente, por lo que se dejan fuera para evitar falsos
// positivos en el barrido de todo el catálogo.
const AXE_RULES = ['color-contrast'];

// Fixtures representativas donde se valida el contraste en ambos temas.
const KEY_FIXTURES: [string, string][] = [
  ['fixtures/components/button.fixture.tsx', 'Primary'],
  ['fixtures/components/input.fixture.tsx', 'Default'],
  ['fixtures/components/dialog.fixture.tsx', 'Open'],
  ['fixtures/components/data-table.fixture.tsx', 'Default'],
  ['fixtures/patterns/dashboard.fixture.tsx', 'Default'],
];

test.describe('PALM accessibility checks (contrast)', () => {
  for (const [path, name] of KEY_FIXTURES) {
    for (const theme of ['light', 'dark'] as const) {
      test(`axe color-contrast: ${path.replace('fixtures/', '')} #${name} × ${theme}`, async ({
        page,
      }) => {
        await page.goto(FIXTURE(path, name), { waitUntil: 'networkidle' });
        if (theme === 'dark') {
          await page.evaluate(() => {
            document.documentElement.dataset.pmTheme = 'dark';
          });
          await expect(page.locator('html')).toHaveAttribute('data-pm-theme', 'dark');
        }
        const results = await new AxeBuilder({ page }).withRules(AXE_RULES).analyze();
        expect(results.violations, JSON.stringify(results.violations, null, 2)).toEqual([]);
      });
    }
  }
});
