import * as React from 'react';

export const Default = () => (
  <pm-breadcrumbs label="Ruta de navegación">
    <pm-breadcrumb-item href="/">Inicio</pm-breadcrumb-item>
    <pm-breadcrumb-item href="/configuracion">Configuración</pm-breadcrumb-item>
    <pm-breadcrumb-item href="/configuracion/equipo">Equipo</pm-breadcrumb-item>
  </pm-breadcrumbs>
);
export const WithCurrent = () => (
  <pm-breadcrumbs label="Ruta actual">
    <pm-breadcrumb-item href="/">Inicio</pm-breadcrumb-item>
    <pm-breadcrumb-item href="/documentos" current>
      Documentos
    </pm-breadcrumb-item>
  </pm-breadcrumbs>
);

export default { Default, WithCurrent };
