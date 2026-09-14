import { Component, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-spinner', styleUrl: 'spinner.css', shadow: false, scoped: true })
export class PmSpinner {
  @Prop() label = 'Loading';
  render() {
    return <span class="pm-spinner" role="status" aria-label={this.label} />;
  }
}
