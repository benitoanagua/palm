import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmTabs } from '@palm/components';

const meta = {
  title: 'PALM/Components/Navigation/Tabs',
  component: PmTabs,
} satisfies Meta<PmTabs>;

export default meta;

export const Default = {
  render: () => h('pm-tabs', { value: 'overview' },
    h('div', { slot: 'tablist', style: { display: 'flex', gap: '4px' } },
      h('pm-tab', { value: 'overview', active: true }, 'Overview'),
      h('pm-tab', { value: 'activity' }, 'Activity'),
      h('pm-tab', { value: 'settings', disabled: true }, 'Settings'),
    ),
    h('pm-tab-panel', { value: 'overview' }, 'Overview content.'),
    h('pm-tab-panel', { value: 'activity' }, 'Activity content.'),
    h('pm-tab-panel', { value: 'settings' }, 'Settings content.'),
  ),
};
