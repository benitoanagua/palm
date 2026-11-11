import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [name, setName] = useFixtureInput('name', 'plus');
  const [size, setSize] = useFixtureSelect('size', {
    options: ['small', 'medium', 'large'],
    defaultValue: 'medium',
  });
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [loading, setLoading] = useFixtureInput('loading', false);
  return (
    <pm-icon-button name={name} size={size} disabled={disabled} loading={loading} label="Acción" />
  );
};

export const Default = () => <pm-icon-button name="plus" label="Agregar" />;
export const WithName = () => <pm-icon-button name="settings" label="Configuración" />;
export const Disabled = () => <pm-icon-button name="trash" label="Eliminar" disabled />;
export const Loading = () => <pm-icon-button name="save" label="Guardando" loading />;

export default { Playground, Default, WithName, Disabled, Loading };
