import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmProgress } from '@palm/components';

const meta = {
  title: 'PALM/Components/Feedback/Progress',
  component: PmProgress,
} satisfies Meta<PmProgress>;

export default meta;

export const Default = { render: () => h('pm-progress', { value: 60, label: 'Uploading' }) };
export const Indeterminate = { render: () => h('pm-progress', { indeterminate: true, label: 'Loading' }) };
export const Tones = {
  render: () => h('div', { style: { display: 'flex', flexDirection: 'column', gap: '12px', maxWidth: '280px' } },
    h('pm-progress', { value: 80, tone: 'success', label: 'Success' }),
    h('pm-progress', { value: 40, tone: 'warning', label: 'Warning' }),
    h('pm-progress', { value: 20, tone: 'danger', label: 'Danger' }),
  ),
};
