import * as React from 'react';

export const Default = () => (
  <pm-toolbar>
    <span slot="start" style={{ fontWeight: 700 }}>
      Elementos recientes
    </span>
    <pm-input placeholder="Filtrar" aria-label="Filtrar elementos" />
    <div slot="end">
      <pm-button variant="secondary">
        <pm-icon slot="start" name="arrow-down" /> Exportar
      </pm-button>
      <pm-button>
        <pm-icon slot="start" name="plus" /> Crear
      </pm-button>
    </div>
  </pm-toolbar>
);

export default { Default };
