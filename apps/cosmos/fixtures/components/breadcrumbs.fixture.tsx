import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [label, setLabel] = useFixtureInput('label', 'Migas de pan');
  const [maxItems, setMaxItems] = useFixtureInput('maxItems', 0);
  return (
    <pm-breadcrumbs label={label} maxItems={maxItems}>
      <pm-breadcrumb-item href="/inicio">Inicio</pm-breadcrumb-item>
      <pm-breadcrumb-item href="/componentes">Componentes</pm-breadcrumb-item>
      <pm-breadcrumb-item href="/botones">Botones</pm-breadcrumb-item>
    </pm-breadcrumbs>
  );
};

export const Default = () => (
  <pm-breadcrumbs>
    <pm-breadcrumb-item href="/inicio">Inicio</pm-breadcrumb-item>
    <pm-breadcrumb-item href="/componentes">Componentes</pm-breadcrumb-item>
    <pm-breadcrumb-item href="/botones">Botones</pm-breadcrumb-item>
  </pm-breadcrumbs>
);
export const WithCurrent = () => (
  <pm-breadcrumbs label="Ruta actual" maxItems={3}>
    <pm-breadcrumb-item href="/inicio">Inicio</pm-breadcrumb-item>
    <pm-breadcrumb-item href="/componentes">Componentes</pm-breadcrumb-item>
    <pm-breadcrumb-item href="/botones">Botones</pm-breadcrumb-item>
    <pm-breadcrumb-item href="/botones/primario" aria-current="page">
      Primario
    </pm-breadcrumb-item>
  </pm-breadcrumbs>
);

export default { Playground, Default, WithCurrent };
