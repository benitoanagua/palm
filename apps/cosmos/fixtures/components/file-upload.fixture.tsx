import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [label, setLabel] = useFixtureInput('label', 'Adjuntar archivo');
  const [accept, setAccept] = useFixtureInput('accept', '');
  const [multiple, setMultiple] = useFixtureInput('multiple', false);
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [error, setError] = useFixtureInput('error', '');
  return (
    <pm-file-upload
      label={label}
      name="adjunto"
      accept={accept}
      multiple={multiple}
      disabled={disabled}
      error={error}
    />
  );
};

export const Default = () => <pm-file-upload label="Adjuntar archivo" name="adjunto" />;
export const Disabled = () => <pm-file-upload label="Adjuntar archivo" name="adjunto" disabled />;
export const WithError = () => (
  <pm-file-upload label="Adjuntar archivo" name="adjunto" error="No se pudo subir el archivo." />
);
export const OnlyImages = () => (
  <pm-file-upload label="Solo imágenes" name="imagen" accept="image/*" />
);

export default { Playground, Default, Disabled, WithError, OnlyImages };
