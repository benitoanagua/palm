import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [placement, setPlacement] = useFixtureSelect('placement', {
    options: ['bottom-start', 'bottom-end'],
    defaultValue: 'bottom-start',
  });
  const [label, setLabel] = useFixtureInput('label', 'Acciones de fila');
  return (
    <pm-menu label={label} placement={placement}>
      <pm-button slot="trigger" onClick={() => {}}>
        Menú
      </pm-button>
      <pm-menu-item value="editar">Editar</pm-menu-item>
      <pm-menu-item value="duplicar">Duplicar</pm-menu-item>
      <pm-menu-item value="eliminar" tone="danger">
        Eliminar
      </pm-menu-item>
    </pm-menu>
  );
};

export const Default = () => (
  <pm-menu label="Acciones de fila">
    <pm-button slot="trigger" onClick={() => {}}>
      Menú
    </pm-button>
    <pm-menu-item value="editar">Editar</pm-menu-item>
    <pm-menu-item value="duplicar">Duplicar</pm-menu-item>
    <pm-menu-item value="eliminar" tone="danger">
      Eliminar
    </pm-menu-item>
  </pm-menu>
);
export const WithDisabledItem = () => (
  <pm-menu label="Acciones de fila">
    <pm-button slot="trigger" onClick={() => {}}>
      Menú
    </pm-button>
    <pm-menu-item value="editar">Editar</pm-menu-item>
    <pm-menu-item value="duplicar" disabled>
      Duplicar
    </pm-menu-item>
    <pm-menu-item value="eliminar" tone="danger">
      Eliminar
    </pm-menu-item>
  </pm-menu>
);

export default { Playground, Default, WithDisabledItem };
