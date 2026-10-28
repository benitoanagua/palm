import { stencilContent } from './story-helpers';

export default { title: 'PALM/Introduction' };
export const Overview = {
  render: () =>
    stencilContent(
      `<div style="font-family:system-ui;padding:32px;max-width:760px"><h1>PALM</h1><p>A new design system built from scratch with Stencil and Web Components.</p><p>STRATA is not a source of component logic, visual rules, tokens, or APIs.</p></div>`,
    ),
};
