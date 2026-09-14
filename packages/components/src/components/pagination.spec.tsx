import { newSpecPage } from '@stencil/core/testing';
import { PmPagination } from './pagination';

describe('pm-pagination', () => {
  it('shows all pages when the total fits without collapsing', async () => {
    const page = await newSpecPage({ components: [PmPagination], html: '<pm-pagination page="1" page-count="5"></pm-pagination>' });
    const buttons = page.root?.querySelectorAll('.pm-pagination__page');
    expect(buttons?.length).toBe(5);
  });

  it('collapses distant pages behind an ellipsis', async () => {
    const page = await newSpecPage({ components: [PmPagination], html: '<pm-pagination page="10" page-count="20"></pm-pagination>' });
    const ellipses = page.root?.querySelectorAll('.pm-pagination__ellipsis');
    expect(ellipses?.length).toBeGreaterThan(0);
    const current = page.root?.querySelector('[aria-current="page"]');
    expect(current?.textContent?.trim()).toBe('10');
  });

  it('emits pmChange with the clamped next page', async () => {
    const page = await newSpecPage({ components: [PmPagination], html: '<pm-pagination page="1" page-count="3"></pm-pagination>' });
    const spy = jest.fn();
    page.root?.addEventListener('pmChange', (event: Event) => spy((event as CustomEvent<number>).detail));
    const next = page.root?.querySelectorAll('.pm-pagination__page')[1] as HTMLButtonElement;
    next.click();
    expect(spy).toHaveBeenCalledWith(2);
  });
});
