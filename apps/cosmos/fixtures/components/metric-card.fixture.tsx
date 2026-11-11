import * as React from 'react';
import { useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [tone, setTone] = useFixtureSelect('tone', {
    options: ['neutral', 'positive', 'negative'],
    defaultValue: 'neutral',
  });
  return (
    <pm-metric-card label="Componentes" value="20+" trend="Creciente" tone={tone}>
      <pm-icon slot="icon" name="settings" />
    </pm-metric-card>
  );
};

export const Default = () => (
  <pm-metric-card label="Componentes" value="20+" trend="Creciente" tone="neutral">
    <pm-icon slot="icon" name="settings" />
  </pm-metric-card>
);
export const Neutral = () => (
  <pm-metric-card label="A11y" value="AA" trend="Integrada" tone="neutral" />
);
export const Negative = () => (
  <pm-metric-card label="Errores" value="4" trend="En aumento" tone="negative" />
);

export default { Playground, Default, Neutral, Negative };
