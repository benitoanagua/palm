import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmSwitch } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/Switch',
  component: PmSwitch,
} satisfies Meta<PmSwitch>;

export default meta;

export const Default = {
  render: () => h('pm-switch', { label: 'Enable notifications', name: 'notifications' }),
};
export const Checked = {
  render: () =>
    h('pm-switch', { label: 'Enable notifications', name: 'notifications', checked: true }),
};
export const Disabled = {
  render: () =>
    h('pm-switch', { label: 'Enable notifications', name: 'notifications', disabled: true }),
};
