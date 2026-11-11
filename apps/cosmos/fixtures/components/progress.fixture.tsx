import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [value, setValue] = useFixtureInput('value', 50);
  const [indeterminate, setIndeterminate] = useFixtureInput('indeterminate', false);
  const [tone, setTone] = useFixtureSelect('tone', {
    options: ['action', 'success', 'danger', 'warning'],
    defaultValue: 'action',
  });
  const [label, setLabel] = useFixtureInput('label', 'Carga');
  return <pm-progress label={label} value={value} indeterminate={indeterminate} tone={tone} />;
};

export const Default = () => <pm-progress label="Carga" value={50} tone="action" />;
export const Success = () => <pm-progress label="Éxito" value={100} tone="success" />;
export const Danger = () => <pm-progress label="Error" value={30} tone="danger" />;
export const Warning = () => <pm-progress label="Advertencia" value={70} tone="warning" />;
export const Indeterminate = () => <pm-progress label="Procesando" indeterminate />;

export default { Playground, Default, Success, Danger, Warning, Indeterminate };
