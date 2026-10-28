import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmTimePicker } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/TimePicker',
  component: PmTimePicker,
} satisfies Meta<PmTimePicker>;

export default meta;

export const Default = { render: () => h('pm-time-picker', { label: 'Meeting time' }) };
export const WithValue = {
  render: () => h('pm-time-picker', { label: 'Meeting time', value: '14:30' }),
};
export const WithStep = { render: () => h('pm-time-picker', { label: 'Meeting time', step: 900 }) };
export const Disabled = {
  render: () => h('pm-time-picker', { label: 'Meeting time', disabled: true, value: '09:00' }),
};
