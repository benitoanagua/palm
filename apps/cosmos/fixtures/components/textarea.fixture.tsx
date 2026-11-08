import * as React from 'react';

export const Default = () => (
  <pm-textarea label="Comentarios" name="comment" placeholder="Escribe aquí…" />
);
export const WithValue = () => (
  <pm-textarea label="Comentarios" name="comment" value="Texto de ejemplo" />
);
export const Disabled = () => <pm-textarea label="Comentarios" name="comment" value="" disabled />;

export default { Default, WithValue, Disabled };
