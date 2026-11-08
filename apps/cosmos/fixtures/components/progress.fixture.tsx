import * as React from 'react';

export const Default = () => <pm-progress label="Carga" value={50} tone="action" />;
export const Success = () => <pm-progress label="Éxito" value={100} tone="success" />;
export const Danger = () => <pm-progress label="Error" value={30} tone="danger" />;
export const Indeterminate = () => <pm-progress label="Procesando" indeterminate />;

export default { Default, Success, Danger, Indeterminate };
