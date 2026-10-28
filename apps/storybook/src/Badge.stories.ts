import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmBadge } from '@palm/components';

const meta = {
  title: 'PALM/Components/Surface/Badge',
  component: PmBadge,
} satisfies Meta<PmBadge>;

export default meta;

export const Default = { render: () => h('pm-badge', null, 'Neutral') };
export const Tones = {
  render: () =>
    h(
      'div',
      { style: { display: 'flex', gap: '8px' } },
      h('pm-badge', { tone: 'neutral' }, 'Neutral'),
      h('pm-badge', { tone: 'info' }, 'Info'),
      h('pm-badge', { tone: 'success' }, 'Active'),
      h('pm-badge', { tone: 'warning' }, 'Pending'),
      h('pm-badge', { tone: 'danger' }, 'Failed'),
    ),
};
