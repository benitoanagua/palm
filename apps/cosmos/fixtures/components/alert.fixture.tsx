import * as React from 'react';

export const Info = () => (
  <pm-alert tone="info" heading="Información">
    Esto es un mensaje informativo.
  </pm-alert>
);
export const Success = () => (
  <pm-alert tone="success" heading="Operación completada">
    Los cambios se guardaron correctamente.
  </pm-alert>
);
export const Warning = () => (
  <pm-alert tone="warning" heading="Atención">
    Revisa los campos resaltados antes de continuar.
  </pm-alert>
);
export const Danger = () => (
  <pm-alert tone="danger" heading="Error">
    No se pudo procesar la solicitud.
  </pm-alert>
);
export const Dismissible = () => (
  <pm-alert tone="success" heading="Guardado" dismissible>
    Puedes cerrar este aviso.
  </pm-alert>
);

export default { Info, Success, Warning, Danger, Dismissible };
