import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [value, setValue] = useFixtureInput('value', 'day');
  const [label, setLabel] = useFixtureInput('label', 'Agrupar por');
  return (
    <pm-segmented-control label={label} value={value} onPmChange={(e) => setValue(e.detail)}>
      <pm-segmented-item value="day">Día</pm-segmented-item>
      <pm-segmented-item value="week">Semana</pm-segmented-item>
      <pm-segmented-item value="month">Mes</pm-segmented-item>
    </pm-segmented-control>
  );
};

export const Default = () => (
  <pm-segmented-control label="Agrupar por" value="week">
    <pm-segmented-item value="day">Día</pm-segmented-item>
    <pm-segmented-item value="week">Semana</pm-segmented-item>
    <pm-segmented-item value="month">Mes</pm-segmented-item>
  </pm-segmented-control>
);
export const DisabledItem = () => (
  <pm-segmented-control label="Agrupar por" value="day">
    <pm-segmented-item value="day">Día</pm-segmented-item>
    <pm-segmented-item value="week" disabled>
      Semana
    </pm-segmented-item>
    <pm-segmented-item value="month">Mes</pm-segmented-item>
  </pm-segmented-control>
);

export default { Playground, Default, DisabledItem };
