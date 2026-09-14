import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmDialog } from '@palm/components';
import { stencilContent } from './story-helpers';

const meta = {
  title: 'PALM/Components/Overlays/Dialog',
  component: PmDialog,
} satisfies Meta<PmDialog>;

export default meta;

export const Default = {
  render: () => stencilContent(`
    <pm-button onclick="document.querySelector('pm-dialog').show()">Open dialog</pm-button>
    <pm-dialog label="Confirm action">
      <p>This dialog uses the native dialog element and restores focus on close.</p>
      <span slot="footer">
        <pm-button variant="secondary" onclick="document.querySelector('pm-dialog').close()">Cancel</pm-button>
        <pm-button onclick="document.querySelector('pm-dialog').close()">Confirm</pm-button>
      </span>
    </pm-dialog>`),
};
