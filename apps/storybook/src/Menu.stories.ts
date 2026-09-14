import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmMenu } from '@palm/components';

const meta = {
  title: 'PALM/Components/Navigation/Menu',
  component: PmMenu,
} satisfies Meta<PmMenu>;

export default meta;

export const Default = {
  render: () => h('pm-menu', { label: 'Actions' },
    h('pm-button', { slot: 'trigger' }, 'Open menu'),
    h('pm-menu-item', { value: 'edit' }, 'Edit'),
    h('pm-menu-item', { value: 'duplicate' }, 'Duplicate'),
    h('pm-menu-item', { value: 'delete', tone: 'danger' }, 'Delete'),
  ),
};
