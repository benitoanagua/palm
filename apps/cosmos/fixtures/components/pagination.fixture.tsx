import * as React from 'react';

export const Default = () => (
  <pm-pagination label="Paginación" page={1} pageCount={10} siblingCount={1} />
);
export const LastPage = () => (
  <pm-pagination label="Paginación" page={10} pageCount={10} siblingCount={1} />
);

export default { Default, LastPage };
