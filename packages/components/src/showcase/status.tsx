import { Component, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-status', styleUrl: 'status.css', shadow: false, scoped: true })
export class PmStatus {
  @Prop() tone: 'neutral' | 'success' | 'warning' | 'danger' | 'info' = 'neutral';
  @Prop() label = '';

  render() {
    return (
      <span class={`pm-status pm-status--${this.tone}`} role="status">
        <span class="pm-status__dot" aria-hidden="true" />
        {this.label || <slot />}
      </span>
    );
  }
}
