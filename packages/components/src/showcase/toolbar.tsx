import { Component, h } from '@stencil/core';

@Component({ tag: 'pm-toolbar', styleUrl: 'toolbar.css', shadow: false, scoped: true })
export class PmToolbar {
  render() {
    return (
      <div class="pm-toolbar">
        <div class="pm-toolbar__start">
          <slot name="start" />
        </div>
        <div class="pm-toolbar__main">
          <slot />
        </div>
        <div class="pm-toolbar__end">
          <slot name="end" />
        </div>
      </div>
    );
  }
}
