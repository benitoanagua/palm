import * as React from 'react';

export const Default = () => <pm-file-upload label="Adjuntar archivo" name="attachment" />;
export const Disabled = () => (
  <pm-file-upload label="Adjuntar archivo" name="attachment" disabled />
);
export const WithError = () => (
  <pm-file-upload
    label="Adjuntar archivo"
    name="attachment"
    error="El archivo supera el tamaño permitido"
  />
);

export default { Default, Disabled, WithError };
