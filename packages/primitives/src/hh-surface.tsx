import { Component, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-surface', styleUrl: 'pm-surface.css', shadow: false, scoped: true })
export class PmSurface {
  @Prop() elevated = false;
  render() {
    return <div class={{ surface: true, elevated: this.elevated }}><slot /></div>;
  }
}
