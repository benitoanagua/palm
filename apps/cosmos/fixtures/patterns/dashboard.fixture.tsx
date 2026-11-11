import * as React from 'react';

export const Default = () => (
  <div
    style={{
      padding: 'var(--pm-space-6)',
      maxWidth: 'var(--pm-container-max-width)',
      margin: '0 auto',
    }}
  >
    <div style={{ display: 'grid', gap: 'var(--pm-space-2)', marginBottom: 'var(--pm-space-6)' }}>
      <span
        style={{
          fontSize: 'var(--pm-type-caption-size)',
          lineHeight: 'var(--pm-type-caption-line-height)',
          fontWeight: 'var(--pm-type-caption-weight)',
          letterSpacing: 'var(--pm-type-overline-letter-spacing)',
          textTransform: 'uppercase',
          color: 'var(--pm-color-text-muted)',
        }}
      >
        Composable UI
      </span>
      <h1
        style={{
          margin: 0,
          fontSize: 'var(--pm-type-display-size)',
          lineHeight: 'var(--pm-type-display-line-height)',
          fontWeight: 'var(--pm-type-display-weight)',
          letterSpacing: 'var(--pm-type-display-letter-spacing)',
        }}
      >
        PALM en acción
      </h1>
      <p
        style={{
          margin: 0,
          maxWidth: '65ch',
          color: 'var(--pm-color-text-muted)',
          fontSize: 'var(--pm-type-body-md-size)',
          lineHeight: 'var(--pm-type-body-md-line-height)',
        }}
      >
        Un conjunto de componentes de mayor nivel demuestra tokens, ranuras, semántica nativa,
        comportamiento responsivo, estados e iconografía de Tabler trabajando conjuntamente.
      </p>
    </div>

    <pm-alert tone="success" heading="Todo está compuesto a partir de primitivas PALM.">
      No se requiere lógica de UI específica de un framework.
    </pm-alert>

    <div
      style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit,minmax(190px,1fr))',
        gap: 'var(--pm-space-3)',
      }}
    >
      <pm-metric-card label="Componentes" value="20+" trend="Creciendo" tone="positive">
        <pm-icon slot="icon" name="settings" />
      </pm-metric-card>
      <pm-metric-card label="Base de accesibilidad" value="AA" trend="Integrada" />
      <pm-metric-card label="Conjunto de iconos" value="Tabler" trend="Una única fuente" />
    </div>

    <pm-toolbar>
      <span slot="start" style={{ fontWeight: 700 }}>
        Elementos recientes
      </span>
      <pm-input placeholder="Filtrar" aria-label="Filtrar elementos" />
      <div slot="end">
        <pm-button variant="secondary">
          <pm-icon slot="start" name="arrow-down" /> Exportar
        </pm-button>
        <pm-button>
          <pm-icon slot="start" name="plus" /> Crear
        </pm-button>
      </div>
    </pm-toolbar>

    <pm-data-table
      caption="Elementos recientes"
      columns={[
        { key: 'name', label: 'Nombre' },
        { key: 'status', label: 'Estado' },
        { key: 'amount', label: 'Importe', numeric: true },
      ]}
      rows={[
        { name: 'Alpha', status: 'Listo', amount: '$1,240' },
        { name: 'Beta', status: 'Revisión', amount: '$860' },
        { name: 'Gamma', status: 'Listo', amount: '$2,410' },
      ]}
    ></pm-data-table>

    <div
      style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit,minmax(260px,1fr))',
        gap: 'var(--pm-space-4)',
      }}
    >
      <pm-card>
        <span slot="header">Composición</span>
        <p style={{ margin: 0, color: 'var(--pm-color-text-muted)' }}>
          Tarjetas, botones, iconos, campos y estados se componen sin filtrar detalles de
          implementación.
        </p>
        <span slot="footer">
          <pm-status tone="success">Listo</pm-status>
        </span>
      </pm-card>
      <pm-empty-state
        heading="No hay vistas guardadas"
        description="Crea una vista para mantener tus filtros favoritos a mano."
      >
        <pm-icon slot="icon" name="settings" aria-hidden="true" />
        <div slot="actions">
          <pm-button variant="secondary">Más información</pm-button>
          <pm-button>Crear vista</pm-button>
        </div>
      </pm-empty-state>
    </div>
  </div>
);

export default { Default };
