import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmButton } from '@palm/components';

const meta = {
  title: 'PALM/Components/Button',
  component: PmButton,
} satisfies Meta<PmButton>;

export default meta;

export const Primary = {
  render: () => h('pm-button', null, 'Continue'),
};

export const Secondary = {
  render: () => h('pm-button', { variant: 'secondary' }, 'Cancel'),
};

export const WithIcons = {
  render: () => h('pm-button', null, h('pm-icon', { slot: 'start', name: 'plus' }), 'Create'),
};
