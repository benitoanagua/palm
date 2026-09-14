import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmRadio } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/Radio',
  component: PmRadio,
} satisfies Meta<PmRadio>;

export default meta;

export const Group = {
  render: () => h('div', { style: { display: 'flex', flexDirection: 'column', gap: '8px' } },
    h('pm-radio', { name: 'plan', value: 'basic', label: 'Basic', checked: true }),
    h('pm-radio', { name: 'plan', value: 'pro', label: 'Pro' }),
    h('pm-radio', { name: 'plan', value: 'enterprise', label: 'Enterprise' }),
  ),
};
export const Disabled = { render: () => h('pm-radio', { name: 'plan', value: 'basic', label: 'Basic', disabled: true }) };
