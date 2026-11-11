import * as React from 'react';
import { useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [placement, setPlacement] = useFixtureSelect('placement', {
    options: ['top', 'bottom'],
    defaultValue: 'bottom',
  });
  return (
    <>
      <button
        onClick={() => {
          const region = document.querySelector('pm-toast-region');
          void region?.show({
            tone: 'success',
            heading: 'Éxito',
            message: 'Cambios guardados.',
            duration: 3000,
          });
        }}
      >
        Mostrar éxito
      </button>
      <button
        onClick={() => {
          const region = document.querySelector('pm-toast-region');
          void region?.show({
            tone: 'danger',
            heading: 'Error',
            message: 'No se pudo guardar.',
            duration: 0,
          });
        }}
      >
        Mostrar error (persistente)
      </button>
      <pm-toast-region placement={placement} maxVisible={3} />
    </>
  );
};

export const Default = () => (
  <>
    <button
      onClick={() => {
        const region = document.querySelector('pm-toast-region');
        void region?.show({ tone: 'success', heading: 'Éxito', message: 'Cambios guardados.' });
      }}
    >
      Mostrar éxito
    </button>
    <pm-toast-region placement="bottom" maxVisible={3} />
  </>
);
export const Top = () => (
  <>
    <pm-button
      onClick={() => {
        const region = document.querySelector('pm-toast-region');
        void region?.show({ tone: 'info', heading: 'Aviso', message: 'Nueva notificación.' });
      }}
    >
      Mostrar notificación
    </pm-button>
    <pm-toast-region placement="top" maxVisible={1} />
  </>
);

export default { Playground, Default, Top };
