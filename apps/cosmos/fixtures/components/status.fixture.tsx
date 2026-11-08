import * as React from 'react';

export const Default = () => <pm-status tone="success">Ready</pm-status>;
export const Warning = () => <pm-status tone="warning">Degraded</pm-status>;
export const Danger = () => <pm-status tone="danger">Down</pm-status>;
export const Info = () => <pm-status tone="info" label="Idle" />;

export default { Default, Warning, Danger, Info };
