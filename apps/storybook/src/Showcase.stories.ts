import { stencilContent } from './story-helpers';

export default { title: 'PALM/Showcase' };

export const ProductSurface = {
  render: () =>
    stencilContent(`
      <div style="display:grid;gap:24px;padding:32px;max-width:1100px;background:var(--pm-color-background);color:var(--pm-color-text)">
        <div style="display:grid;gap:8px">
          <span style="font-size:.75rem;color:var(--pm-color-text-muted);text-transform:uppercase;letter-spacing:.08em">Composable UI</span>
          <h1 style="margin:0;font-size:clamp(2rem,5vw,3.5rem);letter-spacing:-.04em">PALM in action</h1>
          <p style="margin:0;max-width:65ch;color:var(--pm-color-text-muted)">A small set of higher-level components demonstrates tokens, slots, native semantics, responsive behavior, states and Tabler iconography working together.</p>
        </div>

        <pm-alert tone="success" heading="Everything is composed from PALM primitives.">No framework-specific UI logic is required.</pm-alert>

        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(190px,1fr));gap:12px">
          <pm-metric-card label="Components" value="20+" trend="Growing" tone="positive"><pm-icon slot="icon" name="settings"></pm-icon></pm-metric-card>
          <pm-metric-card label="A11y baseline" value="AA" trend="Built in"></pm-metric-card>
          <pm-metric-card label="Icon set" value="Tabler" trend="One source"></pm-metric-card>
        </div>

        <pm-toolbar>
          <span slot="start" style="font-weight:700">Recent items</span>
          <pm-input placeholder="Filter" aria-label="Filter items"></pm-input>
          <div slot="end"><pm-button variant="secondary"><pm-icon slot="start" name="arrow-down"></pm-icon>Export</pm-button><pm-button><pm-icon slot="start" name="plus"></pm-icon>Create</pm-button></div>
        </pm-toolbar>

        <pm-data-table caption="Recent items" columns='[{"key":"name","label":"Name"},{"key":"status","label":"Status"},{"key":"amount","label":"Amount","numeric":true}]' rows='[{"name":"Alpha","status":"Ready","amount":"$1,240"},{"name":"Beta","status":"Review","amount":"$860"},{"name":"Gamma","status":"Ready","amount":"$2,410"}]'></pm-data-table>

        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:16px">
          <pm-card><span slot="header">Composition</span><p style="margin:0;color:var(--pm-color-text-muted)">Cards, buttons, icons, fields and status elements compose without leaking implementation details.</p><span slot="footer"><pm-status tone="success">Ready</pm-status></span></pm-card>
          <pm-empty-state heading="No saved views" description="Create a view to keep your favorite filters close at hand."><pm-icon slot="icon" name="settings" aria-label=""></pm-icon><div slot="actions"><pm-button variant="secondary">Learn more</pm-button><pm-button>Create view</pm-button></div></pm-empty-state>
        </div>
      </div>
    `),
};

export const Responsive = {
  render: () =>
    stencilContent(
      `<div style="padding:24px;max-width:720px"><pm-toolbar><span slot="start">Filters</span><pm-input placeholder="Search" aria-label="Search"></pm-input><div slot="end"><pm-button variant="secondary">Reset</pm-button><pm-button>Apply</pm-button></div></pm-toolbar></div>`,
    ),
};
