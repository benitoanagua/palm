import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmAvatar } from '@palm/components';

const meta = {
  title: 'PALM/Components/Surface/Avatar',
  component: PmAvatar,
} satisfies Meta<PmAvatar>;

export default meta;

export const Initials = { render: () => h('pm-avatar', { name: 'Ada Lovelace' }) };
export const WithImage = {
  render: () => h('pm-avatar', { name: 'Ada Lovelace', src: 'https://i.pravatar.cc/64?img=5' }),
};
export const Sizes = {
  render: () =>
    h(
      'div',
      { style: { display: 'flex', gap: '8px', alignItems: 'center' } },
      h('pm-avatar', { name: 'Ada Lovelace', size: 'small' }),
      h('pm-avatar', { name: 'Ada Lovelace', size: 'medium' }),
      h('pm-avatar', { name: 'Ada Lovelace', size: 'large' }),
    ),
};
export const Status = {
  render: () =>
    h(
      'div',
      { style: { display: 'flex', gap: '8px', alignItems: 'center' } },
      h('pm-avatar', { name: 'Ada Lovelace', status: 'online' }),
      h('pm-avatar', { name: 'Ada Lovelace', status: 'busy' }),
      h('pm-avatar', { name: 'Ada Lovelace', status: 'away' }),
      h('pm-avatar', { name: 'Ada Lovelace', status: 'offline' }),
    ),
};
export const Square = { render: () => h('pm-avatar', { name: 'Ada Lovelace', shape: 'square' }) };
