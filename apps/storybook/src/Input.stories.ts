import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmInput } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/Input',
  component: PmInput,
} satisfies Meta<PmInput>;

export default meta;

export const Default = {
  render: () => h('pm-input', { label: 'Email', placeholder: 'you@example.com' }),
};
export const WithHint = {
  render: () => h('pm-input', { label: 'Email', hint: "We'll never share your email." }),
};
export const WithError = {
  render: () =>
    h('pm-input', { label: 'Email', error: 'Enter a valid email address', value: 'not-an-email' }),
};
export const Password = { render: () => h('pm-input', { label: 'Password', type: 'password' }) };
export const Disabled = {
  render: () => h('pm-input', { label: 'Email', disabled: true, value: 'locked@example.com' }),
};
export const Readonly = {
  render: () => h('pm-input', { label: 'Reference code', readonly: true, value: 'REF-2026-0831' }),
};
