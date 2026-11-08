import * as React from 'react';

export const Default = () => (
  <pm-empty-state
    heading="No hay resultados"
    description="Intenta con otros términos de búsqueda o ajusta los filtros."
  >
    <pm-icon slot="icon" name="search" aria-label="Búsqueda" />
    <div slot="actions">
      <pm-button variant="secondary">Limpiar filtros</pm-button>
      <pm-button>Buscar de nuevo</pm-button>
    </div>
  </pm-empty-state>
);
export const Minimal = () => <pm-empty-state heading="Vacío" description="Aún no hay contenido." />;

export default { Default, Minimal };
