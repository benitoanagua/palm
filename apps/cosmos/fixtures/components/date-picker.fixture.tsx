import * as React from 'react';

export const Default = () => (
  <pm-date-picker label="Fecha de viaje" name="travel-date" value="2026-01-15" />
);
export const WithLocale = () => (
  <pm-date-picker label="Fecha" name="date" locale="es-BO" firstDayOfWeek={1} value="2026-01-15" />
);
export const Disabled = () => <pm-date-picker label="Fecha" name="date" value="" disabled />;

export default { Default, WithLocale, Disabled };
