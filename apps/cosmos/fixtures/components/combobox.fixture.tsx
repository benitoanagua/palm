import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

const OPTIONS = (
  <>
    <pm-combobox-option value="es">España</pm-combobox-option>
    <pm-combobox-option value="mx">México</pm-combobox-option>
    <pm-combobox-option value="ar">Argentina</pm-combobox-option>
    <pm-combobox-option value="co">Colombia</pm-combobox-option>
    <pm-combobox-option value="pe">Perú</pm-combobox-option>
  </>
);

export const Playground = () => {
  const [value, setValue] = useFixtureInput('value', 'es');
  const [placeholder, setPlaceholder] = useFixtureInput('placeholder', 'Selecciona un país');
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [error, setError] = useFixtureInput('error', '');
  const [allowCustom, setAllowCustom] = useFixtureInput('allowCustomValue', false);
  return (
    <pm-combobox
      label="País"
      name="pais"
      value={value}
      placeholder={placeholder}
      disabled={disabled}
      error={error}
      allowCustomValue={allowCustom}
      noResultsText="Sin resultados"
      onPmChange={(e) => setValue(e.detail)}
    >
      {OPTIONS}
    </pm-combobox>
  );
};

export const Default = () => (
  <pm-combobox label="País" name="pais" value="es" placeholder="Selecciona un país">
    {OPTIONS}
  </pm-combobox>
);
export const Disabled = () => (
  <pm-combobox label="País" name="pais" value="es" disabled>
    {OPTIONS}
  </pm-combobox>
);
export const WithError = () => (
  <pm-combobox label="País" name="pais" value="" error="Selecciona un país válido">
    {OPTIONS}
  </pm-combobox>
);
export const CustomValue = () => (
  <pm-combobox
    label="Etiquetas"
    name="etiquetas"
    allowCustomValue
    placeholder="Escribe una etiqueta"
  >
    <pm-combobox-option value="urgente">Urgente</pm-combobox-option>
    <pm-combobox-option value="baja">Baja</pm-combobox-option>
  </pm-combobox>
);

export default { Playground, Default, Disabled, WithError, CustomValue };
