import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [label, setLabel] = useFixtureInput('label', 'Nombre completo');
  const [hint, setHint] = useFixtureInput('hint', 'Introduce tu nombre y apellidos');
  const [error, setError] = useFixtureInput('error', '');
  const [required, setRequired] = useFixtureInput('required', false);
  return (
    <pm-form-field label={label} hint={hint} error={error} required={required}>
      <pm-input name="nombre" />
    </pm-form-field>
  );
};

export const Default = () => (
  <pm-form-field label="Nombre completo" hint="Introduce tu nombre y apellidos">
    <pm-input name="nombre" />
  </pm-form-field>
);
export const Required = () => (
  <pm-form-field label="Nombre completo" required>
    <pm-input name="nombre" />
  </pm-form-field>
);
export const WithError = () => (
  <pm-form-field label="Nombre completo" error="Este campo es obligatorio">
    <pm-input name="nombre" />
  </pm-form-field>
);

export default { Playground, Default, Required, WithError };
