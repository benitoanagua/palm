import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [type, setType] = useFixtureSelect('type', {
    options: ['text', 'email', 'password', 'number'],
    defaultValue: 'text',
  });
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [readonly, setReadonly] = useFixtureInput('readonly', false);
  const [required, setRequired] = useFixtureInput('required', false);
  const [error, setError] = useFixtureInput('error', '');
  const [placeholder, setPlaceholder] = useFixtureInput('placeholder', 'you@example.com');
  const [value, setValue] = useFixtureInput('value', '');
  return (
    <pm-input
      label="Correo electrónico"
      type={type}
      value={value}
      placeholder={placeholder}
      disabled={disabled}
      readonly={readonly}
      required={required}
      error={error}
    />
  );
};

export const Default = () => <pm-input label="Correo electrónico" placeholder="tú@ejemplo.com" />;
export const WithValue = () => <pm-input label="Correo electrónico" value="usuario@ejemplo.com" />;
export const Disabled = () => <pm-input label="Correo electrónico" disabled />;
export const Error = () => <pm-input label="Correo electrónico" error="Correo inválido" />;
export const ReadOnly = () => (
  <pm-input label="Correo electrónico" value="usuario@ejemplo.com" readonly />
);
export const Required = () => (
  <pm-input label="Correo electrónico" required placeholder="Requerido" />
);

export default { Playground, Default, WithValue, Disabled, Error, ReadOnly, Required };
