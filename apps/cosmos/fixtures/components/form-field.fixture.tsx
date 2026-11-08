import * as React from 'react';

export const Default = () => (
  <pm-form-field label="Correo electrónico" hint="neveremos tu correo" />
);
export const Required = () => (
  <pm-form-field label="Correo electrónico" hint="Obligatorio" required />
);
export const WithError = () => (
  <pm-form-field label="Correo electrónico" error="Correo inválido" required />
);

export default { Default, Required, WithError };
