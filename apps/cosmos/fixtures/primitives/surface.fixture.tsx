import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [elevated, setElevated] = useFixtureInput('elevated', true);
  return <pm-surface elevated={elevated}>Contenido de superficie</pm-surface>;
};

export const Default = () => <pm-surface elevated>Contenido elevado</pm-surface>;
export const Flat = () => <pm-surface>Contenido plano</pm-surface>;

export default { Playground, Default, Flat };
