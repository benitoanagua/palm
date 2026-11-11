import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [text, setText] = useFixtureInput('text', 'Más información');
  const [placement, setPlacement] = useFixtureSelect('placement', {
    options: ['top', 'bottom'],
    defaultValue: 'top',
  });
  return (
    <pm-tooltip text={text} placement={placement}>
      <pm-button>Pasar el ratón o recibir el foco</pm-button>
    </pm-tooltip>
  );
};

export const Default = () => (
  <pm-tooltip text="Más información" placement="top">
    <pm-button>Información</pm-button>
  </pm-tooltip>
);
export const Bottom = () => (
  <pm-tooltip text="Eliminar elemento" placement="bottom">
    <pm-button>Eliminar</pm-button>
  </pm-tooltip>
);

export default { Playground, Default, Bottom };
