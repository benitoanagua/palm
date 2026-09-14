import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmSpinner } from '@palm/components';

const meta = {
  title: 'PALM/Components/Feedback/Spinner',
  component: PmSpinner,
} satisfies Meta<PmSpinner>;

export default meta;

export const Default = { render: () => h('pm-spinner') };
export const CustomLabel = { render: () => h('pm-spinner', { label: 'Fetching results' }) };
