import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

const OPTIONS = (
  <>
    <option value="a">Opción A</option>
    <option value="b">Opción B</option>
    <option value="c">Opción C</option>
  </>
);

export const Playground = () => {
  const [value, setValue] = useFixtureInput('value', 'a');
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [required, setRequired] = useFixtureInput('required', false);
  const [error, setError] = useFixtureInput('error', '');
  return (
    <pm-select
      label="Opción"
      name="opcion"
      value={value}
      disabled={disabled}
      required={required}
      error={error}
      onPmChange={(e) => setValue(e.detail)}
    >
      {OPTIONS}
    </pm-select>
  );
};

export const Default = () => (
  <pm-select label="Opción" name="opcion" value="a">
    {OPTIONS}
  </pm-select>
);
export const Disabled = () => (
  <pm-select label="Opción" name="opcion" value="a" disabled>
    {OPTIONS}
  </pm-select>
);
export const Required = () => (
  <pm-select label="Opción" name="opcion" value="" required>
    {OPTIONS}
  </pm-select>
);
export const WithError = () => (
  <pm-select label="Opción" name="opcion" value="" error="Selecciona una opción">
    {OPTIONS}
  </pm-select>
);

export default { Playground, Default, Disabled, Required, WithError };
