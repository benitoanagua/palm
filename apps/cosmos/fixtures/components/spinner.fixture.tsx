import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [label, setLabel] = useFixtureInput('label', 'Cargando');
  return <pm-spinner label={label} />;
};

export const Default = () => <pm-spinner label="Cargando" />;
export const SinLabel = () => <pm-spinner />;

export default { Playground, Default, SinLabel };
