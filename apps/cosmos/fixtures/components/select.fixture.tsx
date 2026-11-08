import * as React from 'react';

export const Default = () => (
  <pm-select label="Estado" name="status" value="ready">
    <option value="ready">Listo</option>
    <option value="review">Revisión</option>
    <option value="done">Completado</option>
  </pm-select>
);
export const Disabled = () => (
  <pm-select label="Estado" name="status" value="" disabled>
    <option value="ready">Listo</option>
  </pm-select>
);
export const Required = () => (
  <pm-select label="Estado" name="status" value="" required>
    <option value="ready">Listo</option>
  </pm-select>
);

export default { Default, Disabled, Required };
