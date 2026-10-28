import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmIcon } from '@palm/components';

const meta = {
  title: 'PALM/Components/Actions/Icon',
  component: PmIcon,
} satisfies Meta<PmIcon>;

export default meta;

export const Default = { render: () => h('pm-icon', { name: 'star' }) };
export const WithAriaLabel = {
  render: () => h('pm-icon', { name: 'info-circle', 'aria-label': 'Information' }),
};
export const Gallery = {
  render: () =>
    h(
      'div',
      { style: { display: 'flex', gap: '12px' } },
      ['home', 'search', 'settings', 'bell', 'user', 'calendar'].map((n) =>
        h('pm-icon', { name: n }),
      ),
    ),
};
