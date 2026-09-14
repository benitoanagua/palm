import { newSpecPage } from '@stencil/core/testing';
import { PmToastRegion } from './toast';

describe('pm-toast-region', () => {
  it('renders a shown toast', async () => {
    const page = await newSpecPage({ components: [PmToastRegion], html: '<pm-toast-region></pm-toast-region>' });
    const region = page.rootInstance as PmToastRegion;
    await region.show({ message: 'Saved.' });
    await page.waitForChanges();
    expect(page.root?.textContent).toContain('Saved.');
  });

  it('queues toasts beyond maxVisible and promotes on dismiss', async () => {
    const page = await newSpecPage({ components: [PmToastRegion], html: '<pm-toast-region max-visible="1"></pm-toast-region>' });
    const region = page.rootInstance as PmToastRegion;
    const firstId = await region.show({ message: 'First' });
    await region.show({ message: 'Second' });
    await page.waitForChanges();
    expect(page.root?.textContent).toContain('First');
    expect(page.root?.textContent).not.toContain('Second');

    await region.dismiss(firstId);
    await page.waitForChanges();
    expect(page.root?.textContent).toContain('Second');
  });
});
