import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmPagination } from '@palm/components';

const meta = {
  title: 'PALM/Components/Navigation/Pagination',
  component: PmPagination,
} satisfies Meta<PmPagination>;

export default meta;

export const Default = { render: () => h('pm-pagination', { page: 1, pageCount: 10 }) };
export const MiddlePage = { render: () => h('pm-pagination', { page: 5, pageCount: 10 }) };
export const WideSiblings = {
  render: () => h('pm-pagination', { page: 5, pageCount: 20, siblingCount: 2 }),
};
