import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [checked, setChecked] = useFixtureInput('checked', false);
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [required, setRequired] = useFixtureInput('required', false);
  const [label, setLabel] = useFixtureInput('label', 'Aceptar términos y condiciones');
  return <pm-checkbox label={label} checked={checked} disabled={disabled} required={required} />;
};

export const Unchecked = () => <pm-checkbox label="Aceptar términos y condiciones" />;
export const Checked = () => <pm-checkbox label="Aceptar términos y condiciones" checked />;
export const Disabled = () => (
  <pm-checkbox label="Aceptar términos y condiciones" disabled checked />
);
export const Required = () => <pm-checkbox label="Aceptar términos y condiciones" required />;

export default { Playground, Unchecked, Checked, Disabled, Required };
