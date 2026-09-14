import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmCheckbox } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/Checkbox',
  component: PmCheckbox,
} satisfies Meta<PmCheckbox>;

export default meta;

export const Default = { render: () => h('pm-checkbox', { label: 'Accept terms', name: 'terms' }) };
export const Checked = { render: () => h('pm-checkbox', { label: 'Accept terms', name: 'terms', checked: true }) };
export const Disabled = { render: () => h('pm-checkbox', { label: 'Accept terms', name: 'terms', disabled: true }) };
export const Required = { render: () => h('pm-checkbox', { label: 'Accept terms', name: 'terms', required: true }) };
