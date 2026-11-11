import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [interactive, setInteractive] = useFixtureInput('interactive', false);
  return (
    <pm-card interactive={interactive}>
      <span slot="header">Tarjeta</span>
      <p style={{ margin: 0 }}>Contenido de la tarjeta.</p>
      <span slot="footer">
        <pm-status tone="success">Listo</pm-status>
      </span>
    </pm-card>
  );
};

export const Default = () => (
  <pm-card>
    <span slot="header">Tarjeta</span>
    <p style={{ margin: 0 }}>Contenido de la tarjeta.</p>
  </pm-card>
);
export const WithFooter = () => (
  <pm-card>
    <span slot="header">Tarjeta con pie</span>
    <p style={{ margin: 0 }}>Contenido.</p>
    <span slot="footer">
      <pm-status tone="success">Listo</pm-status>
    </span>
  </pm-card>
);
export const Interactive = () => <pm-card interactive>Tarjeta interactiva</pm-card>;

export default { Playground, Default, WithFooter, Interactive };
