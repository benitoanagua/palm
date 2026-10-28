import { Component, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-empty-state', styleUrl: 'empty-state.css', shadow: false, scoped: true })
export class PmEmptyState {
  @Prop() heading = '';
  @Prop() description = '';

  render() {
    return (
      <section class="pm-empty-state">
        <div class="pm-empty-state__icon">
          <slot name="icon">
            <pm-icon name="inbox" aria-label=""></pm-icon>
          </slot>
        </div>
        <h3>{this.heading}</h3>
        {this.description ? <p>{this.description}</p> : null}
        <div class="pm-empty-state__actions">
          <slot name="actions" />
        </div>
      </section>
    );
  }
}
