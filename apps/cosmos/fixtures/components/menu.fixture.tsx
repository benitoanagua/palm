import * as React from 'react';

export const Default = () => (
  <pm-menu label="Acciones de fila">
    <pm-button slot="trigger">Acciones</pm-button>
    <pm-menu-item value="edit">Editar</pm-menu-item>
    <pm-menu-item value="duplicate">Duplicar</pm-menu-item>
    <pm-menu-item value="delete" tone="danger">
      Eliminar
    </pm-menu-item>
  </pm-menu>
);
export const WithDisabledItem = () => (
  <pm-menu label="Acciones de fila">
    <pm-button slot="trigger">Acciones</pm-button>
    <pm-menu-item value="edit">Editar</pm-menu-item>
    <pm-menu-item value="delete" tone="danger" disabled>
      Eliminar
    </pm-menu-item>
  </pm-menu>
);

export default { Default, WithDisabledItem };
