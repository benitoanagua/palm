import { stencilContent } from './story-helpers';

export default { title: 'PALM/Components/Forms' };
export const Fields = { render: () => stencilContent(`<div style="display:grid;gap:16px;max-width:420px"><pm-input label="Email" placeholder="you@example.com"></pm-input><pm-textarea label="Message"></pm-textarea><pm-checkbox label="Remember me"></pm-checkbox><pm-radio name="plan" value="basic" label="Basic"></pm-radio><pm-switch label="Notifications"></pm-switch><pm-select label="Country"><option value="bo">Bolivia</option><option value="ar">Argentina</option></pm-select></div>`) };
