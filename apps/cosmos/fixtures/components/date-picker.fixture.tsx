import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [label, setLabel] = useFixtureInput('label', 'Fecha');
  const [placeholder, setPlaceholder] = useFixtureInput('placeholder', 'YYYY-MM-DD');
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [required, setRequired] = useFixtureInput('required', false);
  const [error, setError] = useFixtureInput('error', '');
  const [locale, setLocale] = useFixtureSelect('locale', {
    options: ['es', 'en'],
    defaultValue: 'es',
  });
  return (
    <pm-date-picker
      label={label}
      name="fecha"
      placeholder={placeholder}
      disabled={disabled}
      required={required}
      error={error}
      locale={locale}
    />
  );
};

export const Default = () => <pm-date-picker label="Fecha" name="fecha" placeholder="YYYY-MM-DD" />;
export const WithLocale = () => (
  <pm-date-picker label="Fecha" name="fecha" locale="es" placeholder="YYYY-MM-DD" />
);
export const Disabled = () => <pm-date-picker label="Fecha" name="fecha" value="" disabled />;

export default { Playground, Default, WithLocale, Disabled };
