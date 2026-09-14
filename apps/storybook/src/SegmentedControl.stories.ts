import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmSegmentedControl } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/SegmentedControl',
  component: PmSegmentedControl,
} satisfies Meta<PmSegmentedControl>;

export default meta;

export const Default = {
  render: () => h('pm-segmented-control', { label: 'View', value: 'list' },
    h('pm-segmented-item', { value: 'list', selected: true }, 'List'),
    h('pm-segmented-item', { value: 'grid' }, 'Grid'),
    h('pm-segmented-item', { value: 'map' }, 'Map'),
  ),
};
export const WithDisabledOption = {
  render: () => h('pm-segmented-control', { label: 'View', value: 'list' },
    h('pm-segmented-item', { value: 'list', selected: true }, 'List'),
    h('pm-segmented-item', { value: 'grid' }, 'Grid'),
    h('pm-segmented-item', { value: 'map', disabled: true }, 'Map'),
  ),
};
