import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [placeholder, setPlaceholder] = useFixtureInput('placeholder', 'Escribe un comentario…');
  const [value, setValue] = useFixtureInput('value', '');
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [readonly, setReadonly] = useFixtureInput('readonly', false);
  const [required, setRequired] = useFixtureInput('required', false);
  const [error, setError] = useFixtureInput('error', '');
  const [rows, setRows] = useFixtureInput('rows', 4);
  return (
    <pm-textarea
      label="Comentarios"
      name="comentario"
      value={value}
      placeholder={placeholder}
      rows={rows}
      disabled={disabled}
      readonly={readonly}
      required={required}
      error={error}
    />
  );
};

export const Default = () => (
  <pm-textarea label="Comentarios" name="comentario" placeholder="Escribe un comentario…" />
);
export const WithValue = () => (
  <pm-textarea label="Comentarios" name="comentario" value="Este es un comentario de ejemplo." />
);
export const Disabled = () => (
  <pm-textarea label="Comentarios" name="comentario" value="" disabled />
);
export const Required = () => <pm-textarea label="Comentarios" name="comentario" required />;

export default { Playground, Default, WithValue, Disabled, Required };
