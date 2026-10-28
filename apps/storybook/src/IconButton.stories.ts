import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmIconButton } from '@palm/components';

const meta = {
  title: 'PALM/Components/Actions/IconButton',
  component: PmIconButton,
} satisfies Meta<PmIconButton>;

export default meta;

export const Default = { render: () => h('pm-icon-button', { name: 'plus', label: 'Add item' }) };
export const Loading = {
  render: () => h('pm-icon-button', { name: 'plus', label: 'Add item', loading: true }),
};
export const Disabled = {
  render: () => h('pm-icon-button', { name: 'trash', label: 'Delete item', disabled: true }),
};
export const Sizes = {
  render: () =>
    h(
      'div',
      { style: { display: 'flex', gap: '8px', alignItems: 'center' } },
      h('pm-icon-button', { name: 'plus', label: 'Small', size: 'small' }),
      h('pm-icon-button', { name: 'plus', label: 'Medium', size: 'medium' }),
      h('pm-icon-button', { name: 'plus', label: 'Large', size: 'large' }),
    ),
};
