import { stencilContent } from './story-helpers';

export default {
  title: 'PALM/Quality/Production Matrix',
};

const controls = `
  <div style="display:grid;gap:24px;max-width:720px">
    <section><h3>Actions</h3><div style="display:flex;gap:12px;flex-wrap:wrap">
      <pm-button variant="primary">Primary</pm-button>
      <pm-button variant="secondary">Secondary</pm-button>
      <pm-button variant="danger">Delete</pm-button>
      <pm-button loading>Saving</pm-button>
      <pm-icon-button name="search" label="Search"></pm-icon-button>
    </div></section>
    <section style="display:grid;gap:12px"><h3>Forms</h3>
      <pm-input label="Email" value="person@example.com" hint="We'll never share your email"></pm-input>
      <pm-input label="Invalid" error="Enter a valid value" value="bad"></pm-input>
      <pm-textarea label="Message" rows="4"></pm-textarea>
      <pm-checkbox label="Remember me"></pm-checkbox>
      <pm-radio name="matrix" value="one" label="Option one"></pm-radio>
      <pm-radio name="matrix" value="two" label="Option two"></pm-radio>
      <pm-switch label="Notifications"></pm-switch>
      <pm-select label="Country"><option value="bo">Bolivia</option><option value="ar">Argentina</option></pm-select>
    </section>
    <section><h3>Composition</h3><pm-card interactive><span slot="header">Interactive card</span>Keyboard accessible content.<span slot="footer"><pm-badge tone="success">Ready</pm-badge></span></pm-card></section>
    <section><h3>Navigation & disclosure</h3>
      <pm-tabs value="one"><pm-tab value="one">Overview</pm-tab><pm-tab value="two">Details</pm-tab><pm-tab-panel slot="panel" value="one">Overview content</pm-tab-panel><pm-tab-panel slot="panel" value="two">Details content</pm-tab-panel></pm-tabs>
      <br/><pm-accordion><pm-accordion-item value="a" label="First section">First content.</pm-accordion-item><pm-accordion-item value="b" label="Second section">Second content.</pm-accordion-item></pm-accordion>
      <br/><pm-nav><pm-nav-item active href="#overview">Overview</pm-nav-item><pm-nav-item href="#settings">Settings</pm-nav-item></pm-nav>
    </section>
  </div>`;

export const Production = { render: () => stencilContent(controls) };
