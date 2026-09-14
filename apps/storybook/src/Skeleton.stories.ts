import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmSkeleton } from '@palm/components';

const meta = {
  title: 'PALM/Components/Feedback/Skeleton',
  component: PmSkeleton,
} satisfies Meta<PmSkeleton>;

export default meta;

export const Text = { render: () => h('pm-skeleton', { variant: 'text', width: '220px' }) };
export const Circle = { render: () => h('pm-skeleton', { variant: 'circle', width: '48px', height: '48px' }) };
export const Rect = { render: () => h('pm-skeleton', { variant: 'rect', width: '280px', height: '140px' }) };
export const CardPlaceholder = {
  render: () => h('div', { style: { display: 'flex', gap: '12px', alignItems: 'center' } },
    h('pm-skeleton', { variant: 'circle', width: '40px', height: '40px' }),
    h('div', { style: { display: 'flex', flexDirection: 'column', gap: '6px' } },
      h('pm-skeleton', { variant: 'text', width: '160px' }),
      h('pm-skeleton', { variant: 'text', width: '100px' }),
    ),
  ),
};
