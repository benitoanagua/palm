import { stencilContent } from './story-helpers';

export default {
  title: 'PALM/Components/Gallery',
};

export const Core = {
  render: () => stencilContent(`
      <div style="display:grid;gap:24px;padding:32px;max-width:900px;background:var(--pm-color-background);color:var(--pm-color-text)">
        <section style="display:flex;gap:12px;align-items:center;flex-wrap:wrap">
          <pm-button variant="primary"><pm-icon slot="start" name="plus"></pm-icon>Primary</pm-button>
          <pm-button variant="secondary">Secondary</pm-button>
          <pm-button variant="tertiary">Tertiary</pm-button>
          <pm-button variant="danger">Danger</pm-button>
          <pm-icon-button name="search" label="Search"></pm-icon-button><pm-spinner label="Loading"></pm-spinner>
        </section>
        <section style="display:grid;gap:16px;max-width:420px">
          <pm-input label="Email" placeholder="you@example.com" hint="Use your account email"></pm-input>
          <pm-textarea label="Message" placeholder="Write a message"></pm-textarea>
          <pm-checkbox label="Remember me"></pm-checkbox>
          <pm-radio name="choice" value="one" label="Option one"></pm-radio>
          <pm-switch label="Notifications"></pm-switch>
          <pm-select label="Country"><option value="bo">Bolivia</option><option value="ar">Argentina</option></pm-select>
        </section>
        <section style="display:grid;gap:12px">
          <pm-card>
            <span slot="header">Card header</span>
            Content area
            <span slot="footer"><pm-badge tone="success">Ready</pm-badge></span>
          </pm-card>
        </section>
      </div>
    `),
};
