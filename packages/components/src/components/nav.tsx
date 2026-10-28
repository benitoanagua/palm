import { Component, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-nav', styleUrl: 'nav.css', shadow: false, scoped: true })
export class PmNav {
  @Prop() orientation: 'horizontal' | 'vertical' = 'vertical';
  render() {
    return (
      <nav class={`nav-list is-${this.orientation}`}>
        <slot />
      </nav>
    );
  }
}
