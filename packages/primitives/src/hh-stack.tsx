import { Component, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-stack', styleUrl: 'pm-stack.css', shadow: false, scoped: true })
export class PmStack {
  @Prop() direction: 'row' | 'column' = 'column';
  @Prop() gap = 'var(--pm-space-4)';
  @Prop() align: 'start' | 'center' | 'end' | 'stretch' = 'stretch';

  render() {
    return (
      <div
        class="pm-stack"
        style={{
          '--pm-stack-direction': this.direction,
          '--pm-stack-gap': this.gap,
          '--pm-stack-align': this.align,
        }}
      >
        <slot />
      </div>
    );
  }
}
