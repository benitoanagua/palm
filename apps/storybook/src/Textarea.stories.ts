import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmTextarea } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/Textarea',
  component: PmTextarea,
} satisfies Meta<PmTextarea>;

export default meta;

export const Default = { render: () => h('pm-textarea', { label: 'Message' }) };
export const WithHint = {
  render: () => h('pm-textarea', { label: 'Message', hint: 'Max 500 characters' }),
};
export const WithError = {
  render: () => h('pm-textarea', { label: 'Message', error: 'Message is required' }),
};
export const Disabled = {
  render: () => h('pm-textarea', { label: 'Message', disabled: true, value: 'Locked content' }),
};
export const Rows = { render: () => h('pm-textarea', { label: 'Long message', rows: 8 }) };
