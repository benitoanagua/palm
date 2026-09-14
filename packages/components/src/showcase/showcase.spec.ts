import { newSpecPage } from '@stencil/core/testing';
import { PmMetricCard } from './metric-card';
import { PmStatus } from './status';
import { PmAlert } from './alert';
import { PmEmptyState } from './empty-state';
import { PmDataTable } from './data-table';
import { PmToolbar } from './toolbar';

describe('showcase components', () => {
  it('renders the compositional set', async () => {
    const page = await newSpecPage({ components: [PmMetricCard, PmStatus, PmAlert, PmEmptyState, PmDataTable, PmToolbar], html: '<pm-metric-card label=Users value=42></pm-metric-card>' });
    expect(page.root).toBeTruthy();
  });
});
