import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

const COLUMNS = [
  { key: 'name', label: 'Nombre' },
  { key: 'status', label: 'Estado' },
  { key: 'amount', label: 'Importe', numeric: true },
];
const ROWS = [
  { name: 'Alpha', status: 'Listo', amount: '$1,240' },
  { name: 'Beta', status: 'Revisión', amount: '$860' },
  { name: 'Gamma', status: 'Listo', amount: '$2,410' },
  { name: 'Delta', status: 'Pendiente', amount: '$450' },
];

export const Playground = () => {
  const [filterPlaceholder, setFilterPlaceholder] = useFixtureInput(
    'filterPlaceholder',
    'Filtrar…',
  );
  return (
    <pm-data-table
      caption="Elementos recientes"
      columns={COLUMNS}
      rows={ROWS}
      filterable
      filterPlaceholder={filterPlaceholder}
    />
  );
};

export const Default = () => (
  <pm-data-table caption="Elementos recientes" columns={COLUMNS} rows={ROWS} />
);
export const Filterable = () => (
  <pm-data-table
    caption="Elementos recientes"
    columns={COLUMNS}
    rows={ROWS}
    filterable
    filterPlaceholder="Filtrar elementos…"
  />
);
export const Paginated = () => (
  <pm-data-table caption="Elementos recientes" columns={COLUMNS} rows={ROWS} pageSize={2} />
);
export const Empty = () => <pm-data-table caption="Sin resultados" columns={COLUMNS} rows={[]} />;

export default { Playground, Default, Filterable, Paginated, Empty };
