import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [open, setOpen] = useFixtureInput('open', false);
  const [closeOnEscape, setCloseOnEscape] = useFixtureInput('closeOnEscape', true);
  const [label, setLabel] = useFixtureInput('label', 'Confirmar acción');
  return (
    <>
      <button onClick={() => setOpen(true)}>Abrir diálogo</button>
      <pm-dialog
        label={label}
        open={open}
        closeOnEscape={closeOnEscape}
        onPmClose={() => setOpen(false)}
      >
        <p>¿Estás seguro de que quieres continuar?</p>
        <div
          slot="footer"
          style={{ display: 'flex', gap: 'var(--pm-space-2)', justifyContent: 'flex-end' }}
        >
          <pm-button variant="secondary" onPmPress={() => setOpen(false)}>
            Cancelar
          </pm-button>
          <pm-button onPmPress={() => setOpen(false)}>Aceptar</pm-button>
        </div>
      </pm-dialog>
    </>
  );
};

export const Default = () => (
  <>
    <pm-button onClick={() => {}}>Mostrar diálogo</pm-button>
    <pm-dialog label="Confirmar acción" open closeOnEscape>
      <p>¿Estás seguro de que quieres continuar?</p>
    </pm-dialog>
  </>
);
export const Open = () => (
  <pm-dialog label="Editar perfil" open closeOnEscape>
    <p>Cambia los datos de tu perfil aquí.</p>
  </pm-dialog>
);
export const WithoutClose = () => (
  <pm-dialog label="Sin cierre por Escape" open closeOnEscape={false}>
    <p>Este diálogo no se cierra pulsando Escape.</p>
  </pm-dialog>
);

export default { Playground, Default, Open, WithoutClose };
