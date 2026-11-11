import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [variant, setVariant] = useFixtureSelect('variant', {
    options: ['primary', 'secondary'],
    defaultValue: 'primary',
  });
  const [size, setSize] = useFixtureSelect('size', {
    options: ['small', 'medium', 'large'],
    defaultValue: 'medium',
  });
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [loading, setLoading] = useFixtureInput('loading', false);
  return (
    <pm-button variant={variant} size={size} disabled={disabled} loading={loading}>
      {disabled ? 'Deshabilitado' : loading ? 'Cargando…' : 'Continuar'}
    </pm-button>
  );
};

export const Default = () => <pm-button>Continuar</pm-button>;
export const Secondary = () => <pm-button variant="secondary">Cancelar</pm-button>;
export const WithIcon = () => (
  <pm-button>
    <pm-icon slot="start" name="plus" /> Crear
  </pm-button>
);
export const Disabled = () => <pm-button disabled>Deshabilitado</pm-button>;
export const Loading = () => <pm-button loading>Cargando…</pm-button>;
export const LongContent = () => (
  <pm-button>
    Este es un texto de botón muy largo que prueba el ajuste y el desbordamiento del contenido
  </pm-button>
);

export default {
  Playground,
  Default,
  Secondary,
  WithIcon,
  Disabled,
  Loading,
  LongContent,
};
