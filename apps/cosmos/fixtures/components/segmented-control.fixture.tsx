import * as React from 'react';

export const Default = () => (
  <pm-segmented-control value="week" label="Rango">
    <pm-segmented-item value="day">Día</pm-segmented-item>
    <pm-segmented-item value="week">Semana</pm-segmented-item>
    <pm-segmented-item value="month">Mes</pm-segmented-item>
  </pm-segmented-control>
);
export const DisabledItem = () => (
  <pm-segmented-control value="day" label="Rango">
    <pm-segmented-item value="day">Día</pm-segmented-item>
    <pm-segmented-item value="week" disabled>
      Semana
    </pm-segmented-item>
  </pm-segmented-control>
);

export default { Default, DisabledItem };
