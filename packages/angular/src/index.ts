import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { defineCustomElements } from '@palm/components/loader';

export type PalmElementTagName =
  | 'pm-button'
  | 'pm-icon'
  | 'pm-icon-button'
  | 'pm-input'
  | 'pm-textarea'
  | 'pm-checkbox'
  | 'pm-radio'
  | 'pm-switch'
  | 'pm-select'
  | 'pm-card'
  | 'pm-badge'
  | 'pm-avatar'
  | 'pm-progress'
  | 'pm-skeleton'
  | 'pm-slider'
  | 'pm-pagination'
  | 'pm-breadcrumbs'
  | 'pm-breadcrumb-item'
  | 'pm-menu'
  | 'pm-menu-item'
  | 'pm-segmented-control'
  | 'pm-segmented-item'
  | 'pm-toast-region'
  | 'pm-combobox'
  | 'pm-combobox-option'
  | 'pm-date-picker'
  | 'pm-time-picker'
  | 'pm-file-upload';

export type PalmCustomEvent<T = unknown> = CustomEvent<T>;

let registered = false;
export function registerPalmElements(): void {
  if (registered || typeof window === 'undefined') return;
  defineCustomElements(window);
  registered = true;
}

@NgModule({ schemas: [CUSTOM_ELEMENTS_SCHEMA] })
export class PalmModule {}

export const PALM_CUSTOM_ELEMENTS_SCHEMA = CUSTOM_ELEMENTS_SCHEMA;
