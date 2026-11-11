import * as React from 'react';
import { useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [tone, setTone] = useFixtureSelect('tone', {
    options: ['neutral', 'info', 'success', 'warning', 'danger'],
    defaultValue: 'neutral',
  });
  return <pm-badge tone={tone}>Activo</pm-badge>;
};

export const Default = () => <pm-badge tone="success">Activo</pm-badge>;
export const Info = () => <pm-badge tone="info">Nuevo</pm-badge>;
export const Warning = () => <pm-badge tone="warning">Pendiente</pm-badge>;
export const Danger = () => <pm-badge tone="danger">Bloqueado</pm-badge>;

export default { Playground, Default, Info, Warning, Danger };
