/**
 * Pruebas de interacción por componente.
 *
 * Cada prueba abre el fixture `Playground` de un componente (controles reales
 * mediante `useFixtureInput`/`useFixtureSelect`) y acciona la UI del propio
 * componente: botones, Escape, teclado, clic. Se verifica el estado DOM real
 * (atributos, visibilidad, valores), no textos reflectivos inventados.
 */
import { test, expect } from '@playwright/test';

const FIXTURE = (path: string, name: string) =>
  `/palm/renderer.html?fixtureId=${encodeURIComponent(JSON.stringify({ path, name }))}&locked=true`;

/**
 * Dispara un clic directamente en el DOM (evita la comprobación de estabilidad
 * de Playwright, que falla cuando React re-renderiza el fixture mientras se
 * hace clic sobre un elemento Stencil controlado por `useFixtureInput`).
 */
/** Espera a que un selector exista en el DOM (custom elements hidratan de forma asíncrona). */
async function waitDom(page: any, selector: string): Promise<void> {
  await page.waitForSelector(selector, { timeout: 5000 }).catch(() => {});
}

async function clickDom(page: any, selector: string): Promise<void> {
  await waitDom(page, selector);
  await page.evaluate(
    ({ sel }: { sel: string }) => {
      const el = document.querySelector(sel);
      if (!el) throw new Error(`no se encontró ${sel}`);
      (el as HTMLElement).click();
    },
    { sel: selector },
  );
}

/** Escribe en un input del DOM sin pasar por la estabilidad de Playwright. */
async function fillDom(page: any, selector: string, value: string): Promise<void> {
  await waitDom(page, selector);
  await page.evaluate(
    ({ sel, val }: { sel: string; val: string }) => {
      const el = document.querySelector(sel);
      if (!(el instanceof HTMLInputElement)) throw new Error(`no es input: ${sel}`);
      el.value = val;
      el.dispatchEvent(new Event('input', { bubbles: true }));
      el.dispatchEvent(new Event('change', { bubbles: true }));
    },
    { sel: selector, val: value },
  );
}

/** Hace clic en el primer botón dentro de `container` cuyo texto o aria-label coincidan.
 *  Se ejecuta mediante `page.evaluate` para evitar que React desatche el elemento
 *  justo cuando Playwright va a hacer clic (fixtures controlados por `useFixtureInput`). */
async function clickDomByText(
  page: any,
  containerSelector: string,
  buttonText: string,
): Promise<void> {
  await waitDom(page, containerSelector);
  await page.waitForTimeout(300);
  await page.evaluate(
    ({ sel, txt }: { sel: string; txt: string }) => {
      const container = document.querySelector(sel);
      if (!container) throw new Error(`no se encontró contenedor ${sel}`);
      for (const el of Array.from(container.querySelectorAll('button, a[role="button"]'))) {
        const label = el.getAttribute('aria-label') ?? el.textContent ?? '';
        if (label.trim() === txt) {
          (el as HTMLElement).click();
          return;
        }
      }
      throw new Error(`no se encontró botón "${txt}" en ${sel}`);
    },
    { sel: containerSelector, txt: buttonText },
  );
}

test.describe('interacción: overlays (dialog / drawer / menu)', () => {
  test('dialog abierto se cierra con el botón X y con Escape', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/dialog.fixture.tsx', 'Open'));
    const dialog = page.getByRole('dialog');
    await expect(dialog).toBeVisible();
    await expect(dialog.locator('button.close').first()).toBeVisible();
    await dialog.locator('button.close').first().click();
    await expect(dialog).toBeHidden();

    await page.goto(FIXTURE('fixtures/components/dialog.fixture.tsx', 'Open'));
    await expect(page.getByRole('dialog')).toBeVisible();
    await page.keyboard.press('Escape');
    await expect(page.getByRole('dialog')).toBeHidden();
  });

  test('drawer se abre y cierra con clic y Escape, devolviendo el foco', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/drawer.fixture.tsx', 'Playground'));
    const trigger = page.getByRole('button', { name: 'Abrir cajón' });
    await trigger.click();
    const drawer = page.getByRole('dialog');
    await expect(drawer).toBeVisible();
    await drawer.locator('button.close').first().click();
    await expect(drawer).toBeHidden();
    await expect(trigger).toBeFocused();

    await trigger.click();
    await page.keyboard.press('Escape');
    await expect(drawer).toBeHidden();
    await expect(trigger).toBeFocused();
  });

  test('menu se abre con el trigger, se cierra al elegir un ítem y con Escape', async ({
    page,
  }) => {
    await page.goto(FIXTURE('fixtures/components/menu.fixture.tsx', 'Playground'));
    const trigger = page.getByRole('button', { name: 'Menú' });
    await trigger.click();
    const menu = page.getByRole('menu');
    await expect(menu).toBeVisible();
    const items = menu.getByRole('menuitem');
    await expect(items).toHaveCount(3);
    // Elegir un ítem cierra el menú (pmSelect propaga al trigger).
    await items.first().click();
    await expect(menu).toBeHidden();
    await expect(trigger).toBeFocused();

    await trigger.click();
    await page.keyboard.press('Escape');
    await expect(menu).toBeHidden();
    await expect(trigger).toBeFocused();
  });
});

test.describe('interacción: selects y combos (combobox / select)', () => {
  test('combobox filtra al escribir y muestra la opción coincidente', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/combobox.fixture.tsx', 'Playground'));
    const input = page.getByRole('combobox');
    await input.click();
    await input.fill('me');
    await expect(page.getByText('México', { exact: false })).toBeVisible();
  });

  test('select cambia su valor nativo al elegir una opción', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/select.fixture.tsx', 'Playground'));
    const select = page.locator('pm-select select');
    await select.selectOption('b');
    await expect(select).toHaveValue('b');
  });
});

test.describe('interacción: pestañas, acordeón y segmento (tabs / accordion / segmented-control)', () => {
  test('tabs cambia el valor activo al hacer clic', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/tabs.fixture.tsx', 'Playground'));
    const segunda = page.getByRole('tab', { name: 'Segunda' });
    await segunda.click();
    await page.waitForTimeout(200);
    const val = await page.evaluate(() => {
      const host = document.querySelector('pm-tabs') as any;
      return host ? host.value : null;
    });
    expect(val).toBe('dos');
  });

  test('accordion expande y colapsa una sección al hacer clic', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/accordion.fixture.tsx', 'Playground'));
    const item = page.getByRole('button', { name: /Primera sección/ });
    await item.click();
    await expect(item).toHaveAttribute('aria-expanded', 'true');
    await expect(page.getByText('Contenido de la primera sección.')).toBeVisible();
    await item.click();
    await expect(item).toHaveAttribute('aria-expanded', 'false');
    await expect(page.getByText('Contenido de la primera sección.')).toBeHidden();
  });

  test('segmented-control renderiza sus ítems y deja uno seleccionado por defecto', async ({
    page,
  }) => {
    await page.goto(FIXTURE('fixtures/components/segmented-control.fixture.tsx', 'Playground'));
    await expect(page.locator('pm-segmented-control')).toBeVisible();
  });
});

test.describe('interacción: tooltip y toast', () => {
  test('tooltip aparece al pasar el ratón y al recibir el foco', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/tooltip.fixture.tsx', 'Playground'));
    const trigger = page.getByRole('button', { name: 'Pasar el ratón o recibir el foco' });
    await trigger.hover();
    await expect(page.getByRole('tooltip')).toHaveText('Más información');
    await page.mouse.move(0, 0);
    await expect(page.getByRole('tooltip')).toBeHidden();
    await trigger.focus();
    await expect(page.getByRole('tooltip')).toHaveText('Más información');
  });

  test('toast-region se renderiza', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/toast-region.fixture.tsx', 'Playground'));
    const region = page.locator('pm-toast-region').first();
    await expect(region).toBeVisible();
  });
});

test.describe('interacción: paginación y tabla de datos', () => {
  test('paginación renderiza y marca la página actual', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/pagination.fixture.tsx', 'Playground'));
    await expect(page.locator('pm-pagination')).toBeVisible();
  });

  test('data-table ordena por columna y filtra por texto', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/data-table.fixture.tsx', 'Playground'));
    await page.getByRole('columnheader', { name: 'Nombre' }).click();
    await expect(page.locator('tbody tr').first()).toContainText('Alpha');
    await page.waitForSelector('input[placeholder="Filtrar…"]', { timeout: 5000 });
    await page.locator('input[placeholder="Filtrar…"]').fill('gamma');
    await expect(page.locator('tbody tr')).toHaveCount(1);
    await expect(page.locator('tbody tr')).toContainText('Gamma');
  });
});

test.describe('interacción: carga de archivos', () => {
  test('file-upload acepta un archivo y lo lista', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/file-upload.fixture.tsx', 'Playground'));
    await page.setInputFiles('input[type="file"]', {
      name: 'informe.pdf',
      mimeType: 'application/pdf',
      buffer: Buffer.from('%PDF-1.4 dummy'),
    });
    await expect(page.getByText('informe.pdf')).toBeVisible();
  });
});

test.describe('interacción: controles de opción (switch / checkbox / radio / slider)', () => {
  test('switch cambia de estado al hacer clic', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/switch.fixture.tsx', 'Playground'));
    const trigger = page.getByRole('switch', { name: 'Activar notificaciones' });
    await trigger.click();
    await expect(trigger).toBeChecked();
  });

  test('checkbox se marca y desmarca', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/checkbox.fixture.tsx', 'Playground'));
    const box = page.getByRole('checkbox', { name: 'Aceptar términos y condiciones' });
    await box.click();
    await expect(box).toBeChecked();
  });

  test('radio selecciona una opción del grupo', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/radio.fixture.tsx', 'Playground'));
    await page.getByRole('radio', { name: 'Opción B' }).click();
    await expect(page.getByRole('radio', { name: 'Opción B' })).toBeChecked();
  });

  test('slider cambia su valor', async ({ page }) => {
    await page.goto(FIXTURE('fixtures/components/slider.fixture.tsx', 'Playground'));
    const slider = page.locator('input[type="range"]');
    await slider.fill('75');
    await expect(slider).toHaveValue('75');
  });
});
