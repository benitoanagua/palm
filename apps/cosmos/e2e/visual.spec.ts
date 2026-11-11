/**
 * Regresión visual del catálogo PALM.
 *
 * Una captura por fixture en el tema claro y oscuro (viewport desktop
 * 1280×800, el preset clave de la UI de Cosmos). Las bases se generan con:
 *   pnpm test:e2e:update
 * y se commitean bajo apps/cosmos/e2e/__screenshots__/.
 * Las capturas se comparan con maxDiffPixelRatio bajo y animaciones
 * desactivadas para evitar flakiness.
 */
import { test, expect } from '@playwright/test';
import { discoverFixtures, fixtureRendererUrl, type DiscoveredFixture } from './helpers/fixtures';

const ALL: DiscoveredFixture[] = discoverFixtures();

const SCREENSHOT_OPTS = { animations: 'disabled' as const, maxDiffPixelRatio: 0.02 };

test.describe('regresión visual del catálogo', () => {
  // Las capturas se toman en serie: cada prueba monta su propia página y el
  // entorno tiene memoria limitada (~7.5 GiB).
  for (const fixture of ALL) {
    for (const theme of ['light', 'dark'] as const) {
      test(`${fixture.label} × ${theme} × desktop`, async ({ page }) => {
        await page.goto(fixtureRendererUrl(fixture), { waitUntil: 'networkidle' });
        if (theme === 'dark') {
          await page.evaluate(() => {
            document.documentElement.dataset.pmTheme = 'dark';
          });
          await expect(page.locator('html')).toHaveAttribute('data-pm-theme', 'dark');
        }
        await expect(page).toHaveScreenshot(SCREENSHOT_OPTS);
      });
    }
  }
});
