import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmBreadcrumbs } from '@palm/components';

const meta = {
  title: 'PALM/Components/Navigation/Breadcrumbs',
  component: PmBreadcrumbs,
} satisfies Meta<PmBreadcrumbs>;

export default meta;

export const Default = {
  render: () => h('pm-breadcrumbs', null,
    h('pm-breadcrumb-item', { href: '#' }, 'Home'),
    h('pm-breadcrumb-item', { href: '#' }, 'Settings'),
    h('pm-breadcrumb-item', { current: true }, 'Profile'),
  ),
};
export const Collapsed = {
  render: () => h('pm-breadcrumbs', { maxItems: 3 },
    h('pm-breadcrumb-item', { href: '#' }, 'Home'),
    h('pm-breadcrumb-item', { href: '#' }, 'Products'),
    h('pm-breadcrumb-item', { href: '#' }, 'Category'),
    h('pm-breadcrumb-item', { href: '#' }, 'Subcategory'),
    h('pm-breadcrumb-item', { current: true }, 'Item'),
  ),
};
