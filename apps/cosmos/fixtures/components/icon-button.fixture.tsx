import * as React from 'react';

export const Default = () => <pm-icon-button name="plus" label="Agregar" />;
export const WithName = () => <pm-icon-button name="settings" label="Configuración" />;
export const Disabled = () => <pm-icon-button name="trash" label="Eliminar" disabled />;
export const Loading = () => <pm-icon-button name="save" label="Guardando" loading />;

export default { Default, WithName, Disabled, Loading };
