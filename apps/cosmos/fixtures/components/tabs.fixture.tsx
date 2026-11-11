import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [value, setValue] = useFixtureInput('value', 'uno');
  return (
    <pm-tabs value={value}>
      <pm-tab value="uno">Primera</pm-tab>
      <pm-tab value="dos">Segunda</pm-tab>
      <pm-tab value="tres" disabled>
        Tercera (deshabilitada)
      </pm-tab>
      <pm-tab-panel value="uno">Contenido de la primera pestaña.</pm-tab-panel>
      <pm-tab-panel value="dos">Contenido de la segunda pestaña.</pm-tab-panel>
      <pm-tab-panel value="tres">Contenido de la tercera pestaña.</pm-tab-panel>
    </pm-tabs>
  );
};

export const Default = () => (
  <pm-tabs value="uno">
    <pm-tab value="uno">Primera</pm-tab>
    <pm-tab value="dos">Segunda</pm-tab>
    <pm-tab-panel value="uno">Contenido de la primera pestaña.</pm-tab-panel>
    <pm-tab-panel value="dos">Contenido de la segunda pestaña.</pm-tab-panel>
  </pm-tabs>
);
export const SecondTab = () => (
  <pm-tabs value="dos">
    <pm-tab value="uno">Primera</pm-tab>
    <pm-tab value="dos">Segunda</pm-tab>
    <pm-tab-panel value="uno">Contenido de la primera pestaña.</pm-tab-panel>
    <pm-tab-panel value="dos">Contenido de la segunda pestaña.</pm-tab-panel>
  </pm-tabs>
);

export default { Playground, Default, SecondTab };
