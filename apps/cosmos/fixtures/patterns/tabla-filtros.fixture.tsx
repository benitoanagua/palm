import * as React from 'react';

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
  { name: 'Epsilon', status: 'Listo', amount: '$910' },
];

export const Default = () => (
  <div
    style={{
      padding: 'var(--pm-space-6)',
      maxWidth: 'var(--pm-container-max-width)',
      margin: '0 auto',
    }}
  >
    <h2 style={{ fontSize: 'var(--pm-type-h2-size)', marginBottom: 'var(--pm-space-4)' }}>
      Tabla con filtros
    </h2>
    <div
      style={{
        display: 'flex',
        gap: 'var(--pm-space-2)',
        alignItems: 'center',
        marginBottom: 'var(--pm-space-4)',
      }}
    >
      <pm-input placeholder="Filtrar…" aria-label="Filtrar tabla" />
      <pm-button variant="secondary" type="button">
        Restablecer
      </pm-button>
    </div>
    <pm-data-table
      caption="Elementos"
      columns={COLUMNS}
      rows={ROWS}
      filterable
      filterPlaceholder="Filtrar por nombre, estado o importe…"
      pageSize={3}
    />
  </div>
);

export default { Default };
