import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmTooltip } from '@palm/components';

const meta = {
  title: 'PALM/Components/Overlays/Tooltip',
  component: PmTooltip,
} satisfies Meta<PmTooltip>;

export default meta;

export const Top = {
  render: () =>
    h(
      'pm-tooltip',
      { text: 'Helpful context', placement: 'top' },
      h('pm-icon', { name: 'info-circle', 'aria-label': 'Information' }),
    ),
};
export const Bottom = {
  render: () =>
    h(
      'pm-tooltip',
      { text: 'Helpful context', placement: 'bottom' },
      h('pm-icon', { name: 'info-circle', 'aria-label': 'Information' }),
    ),
};
