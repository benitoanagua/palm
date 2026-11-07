import * as React from 'react';

export const Default = () => (
  <pm-stack gap="var(--pm-space-4)">
    <pm-surface>Surface content</pm-surface>
  </pm-stack>
);

export const WithGap = () => (
  <pm-stack gap="var(--pm-space-8)">
    <pm-surface>Raised surface</pm-surface>
    <pm-surface>Another surface</pm-surface>
  </pm-stack>
);
