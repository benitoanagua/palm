import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [label, setLabel] = useFixtureInput('label', 'Hora');
  const [placeholder, setPlaceholder] = useFixtureInput('placeholder', 'HH:mm');
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [required, setRequired] = useFixtureInput('required', false);
  const [error, setError] = useFixtureInput('error', '');
  return (
    <pm-time-picker
      label={label}
      name="hora"
      placeholder={placeholder}
      disabled={disabled}
      required={required}
      error={error}
    />
  );
};

export const Default = () => <pm-time-picker label="Hora" name="hora" placeholder="HH:mm" />;
export const Disabled = () => <pm-time-picker label="Hora" name="hora" value="" disabled />;

export default { Playground, Default, Disabled };
