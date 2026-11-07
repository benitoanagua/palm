import * as React from 'react';

export const Primary = () => <pm-button>Continue</pm-button>;

export const Secondary = () => <pm-button variant="secondary">Cancel</pm-button>;

export const WithIcon = () => (
  <pm-button>
    <pm-icon slot="start" name="plus" /> Create
  </pm-button>
);

export const Disabled = () => <pm-button disabled>Disabled</pm-button>;

export const Loading = () => <pm-button loading>Loading…</pm-button>;
