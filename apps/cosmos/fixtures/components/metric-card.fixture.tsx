import * as React from 'react';

export const Default = () => (
  <pm-metric-card label="Components" value="20+" trend="Growing" tone="positive">
    <pm-icon slot="icon" name="settings" aria-label="Components" />
  </pm-metric-card>
);
export const Neutral = () => (
  <pm-metric-card label="A11y baseline" value="AA" trend="Built in" tone="neutral" />
);
export const Negative = () => (
  <pm-metric-card label="Errors" value="0" trend="Down" tone="negative" />
);

export default { Default, Neutral, Negative };
