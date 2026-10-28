import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmToastRegion } from '@palm/components';
import { stencilContent } from './story-helpers';

const meta = {
  title: 'PALM/Components/Overlays/Toast',
  component: PmToastRegion,
} satisfies Meta<PmToastRegion>;

export default meta;

export const Default = {
  render: () =>
    stencilContent(`
    <pm-button onclick="document.querySelector('pm-toast-region').show({ heading: 'Saved', message: 'Your changes were saved.', tone: 'success' })">Show toast</pm-button>
    <pm-toast-region placement="bottom"></pm-toast-region>`),
};
export const TopPlacement = {
  render: () =>
    stencilContent(`
    <pm-button onclick="document.querySelector('pm-toast-region').show({ heading: 'Heads up', message: 'A new update is available.', tone: 'info' })">Show toast</pm-button>
    <pm-toast-region placement="top"></pm-toast-region>`),
};
export const Persistent = {
  render: () =>
    stencilContent(`
    <pm-button onclick="document.querySelector('pm-toast-region').show({ heading: 'Action required', message: 'Confirm your email address.', tone: 'warning', duration: 0 })">Show persistent toast</pm-button>
    <pm-toast-region placement="bottom"></pm-toast-region>`),
};
