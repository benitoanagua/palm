import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [tone, setTone] = useFixtureSelect('tone', {
    options: ['neutral', 'success', 'warning', 'danger', 'info'],
    defaultValue: 'neutral',
  });
  const [label, setLabel] = useFixtureInput('label', '');
  return (
    <pm-status tone={tone} label={label}>
      Estado
    </pm-status>
  );
};

export const Default = () => <pm-status tone="success">Listo</pm-status>;
export const Warning = () => <pm-status tone="warning">Degradado</pm-status>;
export const Danger = () => <pm-status tone="danger">Caído</pm-status>;
export const Info = () => <pm-status tone="info" label="Inactivo" />;

export default { Playground, Default, Warning, Danger, Info };
