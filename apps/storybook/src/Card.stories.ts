import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmCard } from '@palm/components';

const meta = {
  title: 'PALM/Components/Surface/Card',
  component: PmCard,
} satisfies Meta<PmCard>;

export default meta;

export const Default = {
  render: () => h('pm-card', null,
    h('span', { slot: 'header' }, 'Account'),
    h('p', null, 'Reusable content surface.'),
  ),
};
export const WithFooter = {
  render: () => h('pm-card', null,
    h('span', { slot: 'header' }, 'Account'),
    h('p', null, 'Reusable content surface.'),
    h('span', { slot: 'footer' }, h('pm-badge', { tone: 'success' }, 'Active')),
  ),
};
export const Interactive = {
  render: () => h('pm-card', { interactive: true, onHhPress: () => console.log('card pressed') },
    h('span', { slot: 'header' }, 'Clickable card'),
    h('p', null, 'This card responds to click and keyboard activation.'),
  ),
};
