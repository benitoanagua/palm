import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [checked, setChecked] = useFixtureInput('checked', false);
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [label, setLabel] = useFixtureInput('label', 'Activar notificaciones');
  return <pm-switch label={label} checked={checked} disabled={disabled} name="notificaciones" />;
};

export const Default = () => <pm-switch label="Activar notificaciones" name="notify" />;
export const Checked = () => <pm-switch label="Activar notificaciones" name="notify" checked />;
export const Disabled = () => <pm-switch label="Activar notificaciones" name="notify" disabled />;

export default { Playground, Default, Checked, Disabled };
