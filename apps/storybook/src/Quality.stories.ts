import { stencilContent } from './story-helpers';

export default { title: 'PALM/Quality/State Matrix' };

export const States = {
  render: () =>
    stencilContent(`
      <div style="display:grid;gap:32px;padding:32px;max-width:960px">
        <section style="display:grid;gap:12px"><h2>Buttons</h2><div style="display:flex;gap:12px;flex-wrap:wrap"><pm-button>Default</pm-button><pm-button disabled>Disabled</pm-button><pm-button loading>Loading</pm-button><pm-button variant="danger">Danger</pm-button></div></section>
        <section style="display:grid;gap:12px"><h2>Forms</h2><pm-input label="Default" placeholder="Value"></pm-input><pm-input label="Error" error="This value is invalid" value="invalid"></pm-input><pm-checkbox label="Enabled"></pm-checkbox><pm-switch label="Enabled switch"></pm-switch></section>
        <section style="display:grid;gap:12px"><h2>Disclosure</h2><pm-accordion><pm-accordion-item value="one" label="Section one">Content one</pm-accordion-item><pm-accordion-item value="two" label="Section two">Content two</pm-accordion-item></pm-accordion></section>
      </div>`),
};
