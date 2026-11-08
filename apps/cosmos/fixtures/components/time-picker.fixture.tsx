import * as React from 'react';

export const Default = () => (
  <pm-time-picker label="Hora de inicio" name="start-time" value="09:00" />
);
export const Disabled = () => (
  <pm-time-picker label="Hora de inicio" name="start-time" value="" disabled />
);

export default { Default, Disabled };
