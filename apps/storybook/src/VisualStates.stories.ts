import { stencilContent } from './story-helpers';

export default { title: 'PALM/Visual/State Matrix' };

export const Controls = {
  render: () => stencilContent(`
      <div style="display:grid;gap:32px;padding:32px;max-width:960px;background:var(--pm-color-background);color:var(--pm-color-text)">
        <section style="display:grid;gap:12px">
          <h2>Buttons</h2>
          <div style="display:flex;gap:12px;flex-wrap:wrap">
            <pm-button>Default</pm-button>
            <pm-button disabled>Disabled</pm-button>
            <pm-button loading>Loading</pm-button>
            <pm-button variant="secondary">Secondary</pm-button>
            <pm-button variant="danger">Danger</pm-button>
          </div>
        </section>
        <section style="display:grid;gap:12px;max-width:420px">
          <h2>Fields</h2>
          <pm-input label="Default" placeholder="Value"></pm-input>
          <pm-input label="Error" error="This value is invalid" value="invalid"></pm-input>
          <pm-input label="Disabled" disabled value="Disabled"></pm-input>
          <pm-textarea label="Textarea" hint="Supporting text"></pm-textarea>
        </section>
        <section style="display:flex;gap:16px;align-items:center">
          <pm-badge tone="neutral">Neutral</pm-badge>
          <pm-badge tone="info">Info</pm-badge>
          <pm-badge tone="success">Success</pm-badge>
          <pm-badge tone="warning">Warning</pm-badge>
          <pm-badge tone="danger">Danger</pm-badge>
        </section>
      </div>
    `),
};
