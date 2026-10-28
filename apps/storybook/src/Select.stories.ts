import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmSelect } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/Select',
  component: PmSelect,
} satisfies Meta<PmSelect>;

export default meta;

export const Default = {
  render: () =>
    h(
      'pm-select',
      { label: 'Country', name: 'country' },
      h('option', { value: 'bo' }, 'Bolivia'),
      h('option', { value: 'ar' }, 'Argentina'),
      h('option', { value: 'pe' }, 'Peru'),
    ),
};
export const WithHint = {
  render: () =>
    h(
      'pm-select',
      { label: 'Country', name: 'country', hint: 'Used for shipping estimates' },
      h('option', { value: 'bo' }, 'Bolivia'),
      h('option', { value: 'ar' }, 'Argentina'),
    ),
};
export const WithError = {
  render: () =>
    h(
      'pm-select',
      { label: 'Country', name: 'country', error: 'Please select a country' },
      h('option', { value: '' }, 'Choose...'),
      h('option', { value: 'bo' }, 'Bolivia'),
    ),
};
export const Disabled = {
  render: () =>
    h(
      'pm-select',
      { label: 'Country', name: 'country', disabled: true },
      h('option', { value: 'bo' }, 'Bolivia'),
    ),
};
