import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmSlider } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/Slider',
  component: PmSlider,
} satisfies Meta<PmSlider>;

export default meta;

export const Default = { render: () => h('pm-slider', { label: 'Volume', value: 40 }) };
export const WithValueLabel = {
  render: () => h('pm-slider', { label: 'Volume', value: 65, showValue: true }),
};
export const Range = {
  render: () =>
    h('pm-slider', { label: 'Brightness', min: 0, max: 200, step: 5, value: 100, showValue: true }),
};
export const Disabled = {
  render: () => h('pm-slider', { label: 'Volume', value: 40, disabled: true }),
};
