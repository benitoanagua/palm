import * as React from 'react';

export const Default = () => <pm-radio label="Opción A" name="choice" value="a" />;
export const Checked = () => <pm-radio label="Opción B" name="choice" value="b" checked />;
export const Disabled = () => <pm-radio label="Opción C" name="choice" value="c" disabled />;

export default { Default, Checked, Disabled };
