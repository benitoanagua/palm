import * as React from 'react';

export const Default = () => <pm-badge tone="success">Active</pm-badge>;
export const Info = () => <pm-badge tone="info">Nuevo</pm-badge>;
export const Warning = () => <pm-badge tone="warning">Pendiente</pm-badge>;
export const Danger = () => <pm-badge tone="danger">Bloqueado</pm-badge>;

export default { Default, Info, Warning, Danger };
