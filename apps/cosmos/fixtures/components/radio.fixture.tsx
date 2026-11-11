import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [value, setValue] = useFixtureInput('value', 'a');
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  return (
    <>
      <pm-radio
        label="Opción A"
        name="opcion"
        value="a"
        checked={value === 'a'}
        disabled={disabled}
      />
      <pm-radio
        label="Opción B"
        name="opcion"
        value="b"
        checked={value === 'b'}
        disabled={disabled}
      />
      <pm-radio
        label="Opción C"
        name="opcion"
        value="c"
        checked={value === 'c'}
        disabled={disabled}
      />
    </>
  );
};

export const Default = () => <pm-radio label="Opción A" name="opcion" value="a" />;
export const Checked = () => <pm-radio label="Opción B" name="opcion" value="b" checked />;
export const Disabled = () => <pm-radio label="Opción C" name="opcion" value="c" disabled />;

export default { Playground, Default, Checked, Disabled };
