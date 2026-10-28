import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmCombobox } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/ComboBox',
  component: PmCombobox,
} satisfies Meta<PmCombobox>;

export default meta;

const options = () => [
  h('pm-combobox-option', { value: 'bolivia' }, 'Bolivia'),
  h('pm-combobox-option', { value: 'argentina' }, 'Argentina'),
  h('pm-combobox-option', { value: 'peru' }, 'Peru'),
  h('pm-combobox-option', { value: 'chile' }, 'Chile'),
];

export const Default = { render: () => h('pm-combobox', { label: 'Country' }, ...options()) };
export const WithHint = {
  render: () =>
    h('pm-combobox', { label: 'Country', hint: 'Start typing to filter' }, ...options()),
};
export const AllowCustomValue = {
  render: () =>
    h(
      'pm-combobox',
      { label: 'Tag', allowCustomValue: true, noResultsText: 'Press Enter to add' },
      ...options(),
    ),
};
export const Disabled = {
  render: () => h('pm-combobox', { label: 'Country', disabled: true }, ...options()),
};
