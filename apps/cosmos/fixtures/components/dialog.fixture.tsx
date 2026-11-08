import * as React from 'react';

export const Default = () => (
  <pm-dialog label="Confirmar acción" closeOnEscape open={false}>
    <p>¿Estás seguro de que quieres continuar?</p>
  </pm-dialog>
);
export const Open = () => (
  <pm-dialog label="Editar perfil" open closeOnEscape>
    <p>Cambia los datos de tu perfil aquí.</p>
  </pm-dialog>
);

export default { Default, Open };
