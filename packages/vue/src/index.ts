import type { App, Plugin } from 'vue';
import '@palm/components';

export const PalmVue: Plugin = {
  install(app: App) {
    app.config.compilerOptions.isCustomElement = (tag) => tag.startsWith('pm-');
  },
};


declare module 'vue' {
  interface GlobalComponents {
    'pm-button': HTMLElement;
    'pm-icon': HTMLElement;
    'pm-icon-button': HTMLElement;
    'pm-input': HTMLElement;
    'pm-textarea': HTMLElement;
    'pm-checkbox': HTMLElement;
    'pm-radio': HTMLElement;
    'pm-switch': HTMLElement;
    'pm-select': HTMLElement;
    'pm-card': HTMLElement;
    'pm-badge': HTMLElement;
    'pm-avatar': HTMLElement;
    'pm-progress': HTMLElement;
    'pm-skeleton': HTMLElement;
    'pm-slider': HTMLElement;
    'pm-pagination': HTMLElement;
    'pm-breadcrumbs': HTMLElement;
    'pm-breadcrumb-item': HTMLElement;
    'pm-menu': HTMLElement;
    'pm-menu-item': HTMLElement;
    'pm-segmented-control': HTMLElement;
    'pm-segmented-item': HTMLElement;
    'pm-toast-region': HTMLElement;
    'pm-combobox': HTMLElement;
    'pm-combobox-option': HTMLElement;
    'pm-date-picker': HTMLElement;
    'pm-time-picker': HTMLElement;
    'pm-file-upload': HTMLElement;
  }
}
