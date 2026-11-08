import * as React from 'react';

export const Default = () => (
  <pm-nav orientation="vertical">
    <pm-nav-item href="/dashboard">Dashboard</pm-nav-item>
    <pm-nav-item href="/settings" active>
      Configuración
    </pm-nav-item>
    <pm-nav-item href="/profile">Perfil</pm-nav-item>
  </pm-nav>
);
export const Horizontal = () => (
  <pm-nav orientation="horizontal">
    <pm-nav-item href="/home">Inicio</pm-nav-item>
    <pm-nav-item href="/explore">Explorar</pm-nav-item>
  </pm-nav>
);

export default { Default, Horizontal };
