import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmFormField } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/FormField',
  component: PmFormField,
} satisfies Meta<PmFormField>;

export default meta;

export const WrappingCustomControl = {
  render: () =>
    h(
      'pm-form-field',
      { label: 'Search radius', hint: 'Distance in kilometers' },
      h('pm-slider', { min: 0, max: 50, value: 10 }),
    ),
};
export const WithError = {
  render: () =>
    h(
      'pm-form-field',
      { label: 'API key', error: 'This field is required', required: true },
      h('input', { type: 'text', style: { width: '100%' } }),
    ),
};
