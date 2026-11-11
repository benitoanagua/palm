import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [open, setOpen] = useFixtureInput('open', false);
  const [side, setSide] = useFixtureSelect('side', {
    options: ['end', 'start', 'left', 'right'],
    defaultValue: 'end',
  });
  const [label, setLabel] = useFixtureInput('label', 'Cajón');
  return (
    <>
      <pm-button onClick={() => setOpen(true)}>Abrir cajón</pm-button>
      <pm-drawer
        label={label}
        open={open}
        side={side}
        closeOnEscape
        onPmClose={() => setOpen(false)}
      >
        <p>Contenido del cajón de navegación.</p>
      </pm-drawer>
    </>
  );
};

export const Default = () => (
  <>
    <pm-button onClick={() => {}}>Mostrar cajón</pm-button>
    <pm-drawer label="Cajón" open side="end" closeOnEscape>
      <p>Contenido del cajón de navegación.</p>
    </pm-drawer>
  </>
);
export const Start = () => (
  <>
    <pm-button onClick={() => {}}>Mostrar cajón (inicio)</pm-button>
    <pm-drawer label="Cajón" open side="start" closeOnEscape>
      <p>Contenido del cajón anclado al inicio.</p>
    </pm-drawer>
  </>
);

export default { Playground, Default, Start };
