import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmDrawer } from '@palm/components';
import { stencilContent } from './story-helpers';

const meta = {
  title: 'PALM/Components/Overlays/Drawer',
  component: PmDrawer,
} satisfies Meta<PmDrawer>;

export default meta;

export const Default = {
  render: () =>
    stencilContent(`
    <pm-button onclick="document.querySelector('pm-drawer').show()">Open drawer</pm-button>
    <pm-drawer label="Navigation">
      <pm-nav>
        <pm-nav-item active href="#home">Home</pm-nav-item>
        <pm-nav-item href="#settings">Settings</pm-nav-item>
      </pm-nav>
    </pm-drawer>`),
};
export const LeftSide = {
  render: () =>
    stencilContent(`
    <pm-button onclick="document.querySelector('pm-drawer').show()">Open drawer</pm-button>
    <pm-drawer label="Navigation" side="left">
      <pm-nav>
        <pm-nav-item active href="#home">Home</pm-nav-item>
        <pm-nav-item href="#settings">Settings</pm-nav-item>
      </pm-nav>
    </pm-drawer>`),
};
