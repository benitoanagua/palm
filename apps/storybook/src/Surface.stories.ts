import { stencilContent } from './story-helpers';

export default { title: 'PALM/Components/Surface' };
export const CardAndBadge = {
  render: () =>
    stencilContent(
      `<pm-card><span slot="header">Account</span><p>Reusable content surface.</p><span slot="footer"><pm-badge tone="success">Active</pm-badge></span></pm-card>`,
    ),
};
