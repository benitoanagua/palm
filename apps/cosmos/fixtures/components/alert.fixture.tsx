import * as React from 'react';
import { useFixtureSelect, useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [tone, setTone] = useFixtureSelect('tone', {
    options: ['info', 'success', 'warning', 'danger'],
    defaultValue: 'info',
  });
  const [dismissible, setDismissible] = useFixtureInput('dismissible', false);
  const [heading, setHeading] = useFixtureInput('heading', 'Aviso');
  return (
    <pm-alert tone={tone} heading={heading} dismissible={dismissible}>
      Mensaje de alerta de ejemplo.
    </pm-alert>
  );
};

export const Info = () => (
  <pm-alert tone="info" heading="Información">
    Mensaje informativo.
  </pm-alert>
);
export const Success = () => (
  <pm-alert tone="success" heading="Éxito">
    Operación completada correctamente.
  </pm-alert>
);
export const Warning = () => (
  <pm-alert tone="warning" heading="Advertencia">
    Revisa los campos marcados.
  </pm-alert>
);
export const Danger = () => (
  <pm-alert tone="danger" heading="Error">
    No se pudo completar la operación.
  </pm-alert>
);
export const Dismissible = () => (
  <pm-alert tone="warning" heading="Advertencia" dismissible>
    Se puede descartar.
  </pm-alert>
);

export default { Playground, Info, Success, Warning, Danger, Dismissible };
