import * as React from 'react';
import { useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [orientation, setOrientation] = useFixtureSelect('orientation', {
    options: ['horizontal', 'vertical'],
    defaultValue: 'vertical',
  });
  return (
    <pm-nav orientation={orientation}>
      <pm-nav-item href="/inicio" active>
        Inicio
      </pm-nav-item>
      <pm-nav-item href="/componentes">Componentes</pm-nav-item>
      <pm-nav-item href="/patrones">Patrones</pm-nav-item>
      <pm-nav-item href="/ajustes" disabled>
        Ajustes
      </pm-nav-item>
    </pm-nav>
  );
};

export const Default = () => (
  <pm-nav orientation="vertical">
    <pm-nav-item href="/inicio" active>
      Inicio
    </pm-nav-item>
    <pm-nav-item href="/componentes">Componentes</pm-nav-item>
    <pm-nav-item href="/patrones">Patrones</pm-nav-item>
  </pm-nav>
);
export const Horizontal = () => (
  <pm-nav orientation="horizontal">
    <pm-nav-item href="/inicio" active>
      Inicio
    </pm-nav-item>
    <pm-nav-item href="/componentes">Componentes</pm-nav-item>
    <pm-nav-item href="/patrones">Patrones</pm-nav-item>
  </pm-nav>
);

export default { Playground, Default, Horizontal };
