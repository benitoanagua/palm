import * as React from 'react';

export const Default = () => <pm-switch label="Activar notificaciones" name="notify" />;
export const Checked = () => <pm-switch label="Activar notificaciones" name="notify" checked />;
export const Disabled = () => (
  <pm-switch label="Activar notificaciones" name="notify" disabled checked />
);

export default { Default, Checked, Disabled };
