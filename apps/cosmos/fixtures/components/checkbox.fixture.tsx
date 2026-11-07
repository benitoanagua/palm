import * as React from 'react';

export const Unchecked = () => <pm-checkbox label="Remember me" />;
export const Checked = () => <pm-checkbox label="Remember me" checked />;
export const Disabled = () => <pm-checkbox label="Remember me" disabled checked />;

export default { Unchecked, Checked, Disabled };
