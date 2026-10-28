import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmNav } from '@palm/components';

const meta = {
  title: 'PALM/Components/Navigation/Nav',
  component: PmNav,
} satisfies Meta<PmNav>;

export default meta;

export const Vertical = {
  render: () =>
    h(
      'pm-nav',
      { orientation: 'vertical' },
      h('pm-nav-item', { href: '#home', active: true }, 'Home'),
      h('pm-nav-item', { href: '#settings' }, 'Settings'),
      h('pm-nav-item', { href: '#billing', disabled: true }, 'Billing'),
    ),
};
export const Horizontal = {
  render: () =>
    h(
      'pm-nav',
      { orientation: 'horizontal' },
      h('pm-nav-item', { href: '#home', active: true }, 'Home'),
      h('pm-nav-item', { href: '#settings' }, 'Settings'),
    ),
};
