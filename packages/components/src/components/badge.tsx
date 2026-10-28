import { Component, Prop, h } from '@stencil/core';
@Component({ tag: 'pm-badge', styleUrl: 'badge.css', shadow: false, scoped: true })
export class PmBadge {
  @Prop() tone: 'neutral' | 'info' | 'success' | 'warning' | 'danger' = 'neutral';
  render() {
    return (
      <span class={`badge is-${this.tone}`}>
        <slot />
      </span>
    );
  }
}
