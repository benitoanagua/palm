import * as React from 'react';

export const Default = () => <pm-slider label="Volumen" value={50} min={0} max={100} />;
export const Disabled = () => <pm-slider label="Volumen" value={0} min={0} max={100} disabled />;
export const WithStep = () => (
  <pm-slider label="Escala" value={1} min={1} max={10} step={1} showValue />
);

export default { Default, Disabled, WithStep };
