import * as React from 'react';

export const Default = () => (
  <pm-data-table
    caption="Recent items"
    columns={[
      { key: 'name', label: 'Name' },
      { key: 'status', label: 'Status' },
    ]}
    rows={[
      { name: 'Alpha', status: 'Ready' },
      { name: 'Beta', status: 'Review' },
    ]}
  ></pm-data-table>
);
export const Filterable = () => (
  <pm-data-table
    caption="Items"
    filterable
    filterPlaceholder="Buscar…"
    pageSize={5}
    columns={[{ key: 'name', label: 'Name' }]}
    rows={[
      { name: 'Alpha' },
      { name: 'Beta' },
      { name: 'Gamma' },
      { name: 'Delta' },
      { name: 'Epsilon' },
    ]}
  ></pm-data-table>
);

export default { Default, Filterable };
