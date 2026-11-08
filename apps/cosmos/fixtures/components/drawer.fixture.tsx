import * as React from 'react';

export const Default = () => (
  <pm-drawer label="Filtros" side="end" closeOnEscape open={false}>
    <p>Ajusta los filtros de la lista desde este panel lateral.</p>
  </pm-drawer>
);
export const Start = () => (
  <pm-drawer label="Menú" side="start" open={false}>
    <p>Opciones de navegación secundaria.</p>
  </pm-drawer>
);

export default { Default, Start };
