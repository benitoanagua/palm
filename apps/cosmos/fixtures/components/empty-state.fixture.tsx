import * as React from 'react';

export const Default = () => (
  <pm-empty-state heading="No hay resultados" description="Ajusta los filtros o prueba de nuevo.">
    <pm-icon slot="icon" name="search" />
    <div slot="actions">
      <pm-button variant="secondary">Restablecer filtros</pm-button>
      <pm-button>Buscar de nuevo</pm-button>
    </div>
  </pm-empty-state>
);
export const Minimal = () => <pm-empty-state heading="Vacío" description="Aún no hay contenido." />;

export default { Default, Minimal };
