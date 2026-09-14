import { stencilContent } from './story-helpers';

export default { title: 'PALM/Components/Overlays' };

export const DialogAndDrawer = {
  render: () => stencilContent(`
      <div style="display:flex;gap:12px;align-items:center">
        <pm-button onclick="document.querySelector('pm-dialog').show()">Open dialog</pm-button>
        <pm-button variant="secondary" onclick="document.querySelector('pm-drawer').show()">Open drawer</pm-button>
        <pm-tooltip text="Helpful context"><pm-icon name="info-circle" aria-label="Information"></pm-icon></pm-tooltip>
        <pm-dialog label="Confirm action"><p>This dialog uses the native dialog element and restores focus on close.</p><span slot="footer"><pm-button variant="secondary" onclick="document.querySelector('pm-dialog').close()">Cancel</pm-button><pm-button onclick="document.querySelector('pm-dialog').close()">Confirm</pm-button></span></pm-dialog>
        <pm-drawer label="Navigation"><pm-nav><pm-nav-item active href="#home">Home</pm-nav-item><pm-nav-item href="#settings">Settings</pm-nav-item></pm-nav></pm-drawer>
      </div>`)
};
