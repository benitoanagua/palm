import * as React from 'react';

export const Default = () => (
  <pm-combobox label="País" name="country" value="us">
    <pm-combobox-option value="us">Estados Unidos</pm-combobox-option>
    <pm-combobox-option value="ca">Canadá</pm-combobox-option>
    <pm-combobox-option value="mx">México</pm-combobox-option>
  </pm-combobox>
);
export const Disabled = () => (
  <pm-combobox label="País" name="country" value="" disabled>
    <pm-combobox-option value="us">Estados Unidos</pm-combobox-option>
  </pm-combobox>
);
export const WithError = () => (
  <pm-combobox label="País" name="country" value="" error="Selecciona un país válido">
    <pm-combobox-option value="us">Estados Unidos</pm-combobox-option>
  </pm-combobox>
);

export default { Default, Disabled, WithError };
