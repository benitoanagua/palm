import '@palm/tokens/tokens.css';
import { defineCustomElements as defineComponents } from '@palm/components/loader';

defineComponents();
import './styles.css';

type View = 'overview' | 'activity' | 'forms' | 'settings';

const app = document.querySelector<HTMLDivElement>('#app')!;

app.innerHTML = `
  <div class="shell">
    <aside class="sidebar" aria-label="Primary navigation">
      <div class="brand">
        <div class="brand-mark">P</div>
        <div><strong>PALM</strong><span>Showcase</span></div>
      </div>
      <nav class="sidebar-nav">
        <button type="button" class="nav-link is-active" data-view="overview"><pm-icon name="activity"></pm-icon><span>Overview</span></button>
        <button type="button" class="nav-link" data-view="activity"><pm-icon name="activity"></pm-icon><span>Activity</span></button>
        <button type="button" class="nav-link" data-view="forms"><pm-icon name="clipboard"></pm-icon><span>Forms</span></button>
        <button type="button" class="nav-link" data-view="settings"><pm-icon name="settings"></pm-icon><span>Settings</span></button>
      </nav>
      <div class="sidebar-bottom">
        <pm-status tone="success" label="System ready"></pm-status>
        <small>Stencil · Web Components</small>
      </div>
    </aside>

    <main class="main">
      <header class="topbar">
        <div class="topbar-title"><span class="eyebrow">PRODUCT SURFACE</span><h1 id="page-title">Overview</h1></div>
        <div class="topbar-actions">
          <button class="icon-control" id="theme-toggle" aria-label="Toggle theme"><pm-icon name="settings"></pm-icon></button>
          <button class="icon-control" id="menu-toggle" aria-label="Open navigation"><pm-icon name="menu-2"></pm-icon></button>
          <pm-button id="create-button"><pm-icon slot="start" name="plus"></pm-icon>Create</pm-button>
        </div>
      </header>

      <div class="content">
        <section class="view" data-panel="overview">
          <pm-alert tone="info" heading="PALM is running the whole surface" dismissible>
            Every element below is composed from PALM Web Components, tokens and Tabler Icons.
          </pm-alert>

          <div class="hero-grid">
            <div class="hero-copy">
              <span class="eyebrow">DESIGN SYSTEM IN ACTION</span>
              <h2>One system, many surfaces.</h2>
              <p>Use this application as the reference surface for composition, responsive behavior, accessibility and visual consistency.</p>
              <div class="hero-actions">
                <pm-button id="hero-create"><pm-icon slot="start" name="plus"></pm-icon>Create item</pm-button>
                <pm-button variant="secondary" id="hero-learn"><pm-icon slot="start" name="info-circle"></pm-icon>Explore system</pm-button>
              </div>
            </div>
            <pm-card class="hero-card">
              <span slot="header" class="card-heading">System health</span>
              <div class="health"><pm-status tone="success">Healthy</pm-status><strong>99.9%</strong></div>
              <p class="muted">Components are rendering consistently across the supported viewport matrix.</p>
              <div slot="footer" class="card-footer"><span>Last check</span><strong>Just now</strong></div>
            </pm-card>
          </div>

          <div class="metrics-grid">
            <pm-metric-card label="Components" value="24" trend="+4 this release" tone="positive"><pm-icon slot="icon" name="settings"></pm-icon></pm-metric-card>
            <pm-metric-card label="Visual states" value="86" trend="All core states covered" tone="positive"><pm-icon slot="icon" name="activity"></pm-icon></pm-metric-card>
            <pm-metric-card label="A11y checks" value="100%" trend="Keyboard + semantics" tone="positive"><pm-icon slot="icon" name="check"></pm-icon></pm-metric-card>
            <pm-metric-card label="Icon set" value="1" trend="Tabler only"><pm-icon slot="icon" name="settings"></pm-icon></pm-metric-card>
          </div>

          <section class="surface-grid">
            <pm-card>
              <span slot="header" class="card-heading">Recent activity</span>
              <div class="activity-list">
                <div class="activity-row"><span class="activity-icon"><pm-icon name="plus"></pm-icon></span><div><strong>New component</strong><p>pm-empty-state added to the showcase.</p></div><time>2m</time></div>
                <div class="activity-row"><span class="activity-icon"><pm-icon name="activity"></pm-icon></span><div><strong>Theme updated</strong><p>Dark mode tokens verified across surfaces.</p></div><time>18m</time></div>
                <div class="activity-row"><span class="activity-icon"><pm-icon name="check"></pm-icon></span><div><strong>Accessibility passed</strong><p>Keyboard and focus checks completed.</p></div><time>42m</time></div>
              </div>
              <div slot="footer" class="card-footer"><span>Showing latest</span><pm-button variant="tertiary" size="small" id="activity-link">View all</pm-button></div>
            </pm-card>

            <pm-card>
              <span slot="header" class="card-heading">Composition</span>
              <div class="composition-demo">
                <pm-status tone="info">Reusable</pm-status>
                <h3>Slots keep components open for composition.</h3>
                <p class="muted">Icons, actions and content can be inserted without changing component logic.</p>
                <div class="mini-actions"><pm-button size="small" variant="secondary">Secondary</pm-button><pm-button size="small">Primary</pm-button></div>
              </div>
            </pm-card>
          </section>
        </section>

        <section class="view" data-panel="activity" hidden>
          <div class="section-intro"><div><span class="eyebrow">DATA DISPLAY</span><h2>Activity</h2><p>Responsive tables, semantic status and toolbar composition.</p></div><pm-button><pm-icon slot="start" name="arrow-down"></pm-icon>Export</pm-button></div>
          <pm-toolbar>
            <span slot="start" class="toolbar-label">Recent events</span>
            <pm-input id="activity-search" placeholder="Search events" aria-label="Search events"></pm-input>
            <div slot="end"><pm-button size="small" variant="secondary">Filters</pm-button></div>
          </pm-toolbar>
          <div id="table-mount"></div>
          <div class="two-col">
            <pm-empty-state heading="No saved filters" description="Create a saved view to keep your most useful activity filters close at hand."><pm-icon slot="icon" name="settings"></pm-icon><div slot="actions"><pm-button>Create filter</pm-button></div></pm-empty-state>
            <pm-card><span slot="header" class="card-heading">Statuses</span><div class="status-stack"><pm-status tone="success">Ready</pm-status><pm-status tone="warning">Review</pm-status><pm-status tone="danger">Blocked</pm-status><pm-status tone="info">Processing</pm-status></div></pm-card>
          </div>
        </section>

        <section class="view" data-panel="forms" hidden>
          <div class="section-intro"><div><span class="eyebrow">FORMS + ACCESSIBILITY</span><h2>Forms</h2><p>Native semantics, clear errors and predictable focus behavior.</p></div></div>
          <div class="form-layout">
            <pm-card>
              <span slot="header" class="card-heading">Create workspace</span>
              <form id="demo-form" class="demo-form">
                <pm-input id="workspace-name" label="Workspace name" name="workspace" required hint="Use a short, recognizable name."></pm-input>
                <pm-input id="workspace-email" label="Owner email" name="email" type="email" required></pm-input>
                <pm-select id="workspace-type" label="Workspace type" name="type" required><option value="">Select one</option><option value="product">Product</option><option value="design">Design system</option><option value="internal">Internal</option></pm-select>
                <pm-textarea id="workspace-notes" label="Notes" name="notes" hint="Optional context for your team."></pm-textarea>
                <div class="check-row"><pm-checkbox id="workspace-agree" label="I understand this is a demo surface."></pm-checkbox></div>
                <div class="form-actions"><pm-button type="reset" variant="secondary">Reset</pm-button><pm-button type="submit">Create workspace</pm-button></div>
                <div id="form-feedback" aria-live="polite"></div>
              </form>
            </pm-card>
            <div class="form-side">
              <pm-card><span slot="header" class="card-heading">Form contract</span><ul class="contract-list"><li><pm-icon name="check"></pm-icon>Native form semantics</li><li><pm-icon name="check"></pm-icon>Associated labels and descriptions</li><li><pm-icon name="check"></pm-icon>Visible keyboard focus</li><li><pm-icon name="check"></pm-icon>Consistent validation states</li></ul></pm-card>
              <pm-alert tone="success" heading="Accessible by default">The components expose the semantics; the application only owns the workflow.</pm-alert>
            </div>
          </div>
        </section>

        <section class="view" data-panel="settings" hidden>
          <div class="section-intro"><div><span class="eyebrow">SYSTEM CONFIGURATION</span><h2>Settings</h2><p>A small surface for demonstrating tabs, disclosure and drawer composition.</p></div></div>
          <pm-tabs id="settings-tabs">
            <pm-tab value="appearance">Appearance</pm-tab>
            <pm-tab value="behavior">Behavior</pm-tab>
            <pm-tab-panel slot="panel" value="appearance"><div class="settings-panel"><pm-switch label="Dark mode" id="settings-dark"></pm-switch><pm-switch label="Reduced motion" id="settings-motion"></pm-switch><pm-accordion multiple><pm-accordion-item value="tokens" label="Tokens"><p>Primitive and semantic tokens provide the visual foundation.</p></pm-accordion-item><pm-accordion-item value="icons" label="Icons"><p>PALM exposes Tabler Icons through a minimal pm-icon API.</p></pm-accordion-item></pm-accordion></div></pm-tab-panel>
            <pm-tab-panel slot="panel" value="behavior"><div class="settings-panel"><pm-alert tone="info" heading="Keyboard first">Try Tab, Enter, Space and Escape across the surface.</pm-alert><pm-button id="open-drawer">Open drawer</pm-button></div></pm-tab-panel>
          </pm-tabs>
        </section>
      </div>
    </main>
  </div>
  <pm-dialog id="create-dialog" label="Create item"><p>This dialog demonstrates native dialog semantics, focus restoration and composable footer actions.</p><div slot="footer"><pm-button variant="secondary" id="cancel-dialog">Cancel</pm-button><pm-button id="confirm-dialog">Create item</pm-button></div></pm-dialog>
  <pm-drawer id="mobile-drawer" label="Navigation"><div class="drawer-nav"><button type="button" data-view="overview">Overview</button><button type="button" data-view="activity">Activity</button><button type="button" data-view="forms">Forms</button><button type="button" data-view="settings">Settings</button></div></pm-drawer>
`;

void customElements.whenDefined('pm-data-table').then(() => {
  const table = document.createElement('pm-data-table') as HTMLElement & {
    columns: unknown;
    rows: unknown;
  };
  table.columns = [
    { key: 'name', label: 'Event' },
    { key: 'status', label: 'Status' },
    { key: 'owner', label: 'Owner' },
    { key: 'amount', label: 'Value', numeric: true },
  ];
  table.rows = [
    { name: 'Component published', status: 'Ready', owner: 'Maya', amount: '$2,410' },
    { name: 'Visual baseline', status: 'Review', owner: 'Leo', amount: '$860' },
    { name: 'A11y audit', status: 'Ready', owner: 'Nina', amount: '$1,240' },
    { name: 'Token update', status: 'Ready', owner: 'Owen', amount: '$540' },
  ];
  document.querySelector('#table-mount')?.appendChild(table);
});

const dialog = document.querySelector('#create-dialog') as HTMLElement & {
  show: () => Promise<void>;
};
document
  .querySelectorAll('#create-button, #hero-create')
  .forEach((button) => button.addEventListener('pmPress', () => dialog.show()));
document
  .querySelector('#cancel-dialog')
  ?.addEventListener('pmPress', () =>
    (dialog as HTMLElement & { close: () => Promise<void> }).close(),
  );
document
  .querySelector('#confirm-dialog')
  ?.addEventListener('pmPress', () =>
    (dialog as HTMLElement & { close: () => Promise<void> }).close(),
  );

document.querySelector('#theme-toggle')?.addEventListener('click', () => {
  const dark = document.documentElement.dataset.theme === 'dark';
  document.documentElement.dataset.theme = dark ? 'light' : 'dark';
});

document
  .querySelector('#menu-toggle')
  ?.addEventListener('click', () =>
    (
      document.querySelector('#mobile-drawer') as HTMLElement & { show: () => Promise<void> }
    ).show(),
  );
document
  .querySelector('#open-drawer')
  ?.addEventListener('pmPress', () =>
    (
      document.querySelector('#mobile-drawer') as HTMLElement & { show: () => Promise<void> }
    ).show(),
  );

document.querySelector('#settings-dark')?.addEventListener('pmChange', (event) => {
  const checked = (event as CustomEvent<boolean>).detail;
  document.documentElement.dataset.theme = checked ? 'dark' : 'light';
});

document.querySelector('#demo-form')?.addEventListener('submit', (event) => {
  event.preventDefault();
  const feedback = document.querySelector('#form-feedback')!;
  feedback.innerHTML =
    '<pm-alert tone="success" heading="Workspace created">The demo workflow completed successfully.</pm-alert>';
});

document.querySelectorAll('.nav-link, .drawer-nav button').forEach((item) => {
  item.addEventListener('click', () => switchView(item.getAttribute('data-view') as View));
});

document.querySelector('#activity-link')?.addEventListener('pmPress', () => switchView('activity'));

function switchView(view: View) {
  document.querySelectorAll<HTMLElement>('.view').forEach((panel) => {
    panel.hidden = panel.dataset.panel !== view;
  });
  document
    .querySelectorAll('.nav-link')
    .forEach((item) => item.classList.toggle('is-active', item.getAttribute('data-view') === view));
  const title = document.querySelector('#page-title');
  if (title) title.textContent = view[0].toUpperCase() + view.slice(1);
  document
    .querySelectorAll('.drawer-nav button')
    .forEach((item) => item.classList.toggle('is-active', item.getAttribute('data-view') === view));
}
