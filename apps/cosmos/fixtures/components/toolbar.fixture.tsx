import * as React from 'react';

export const Default = () => (
  <pm-toolbar>
    <span slot="start" style={{ fontWeight: 700 }}>
      Acciones
    </span>
    <pm-button variant="secondary">Cancelar</pm-button>
    <div slot="end">
      <pm-button>Guardar cambios</pm-button>
    </div>
  </pm-toolbar>
);

export default { Default };
