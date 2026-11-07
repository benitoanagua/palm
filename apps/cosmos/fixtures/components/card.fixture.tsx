import * as React from 'react';

export const Default = () => (
  <pm-card>
    <span slot="header">Account</span>
    <p>Reusable content surface.</p>
  </pm-card>
);
export const WithFooter = () => (
  <pm-card>
    <span slot="header">Account</span>
    <p>Reusable content surface.</p>
    <span slot="footer">
      <pm-badge tone="success">Active</pm-badge>
    </span>
  </pm-card>
);
export const Interactive = () => (
  <pm-card interactive>
    <span slot="header">Clickable card</span>
    <p>This card responds to click and keyboard activation.</p>
  </pm-card>
);

export default { Default, WithFooter, Interactive };
