import { Component, Element, Event, type EventEmitter, Method, Prop, h } from '@stencil/core';
import type { PmButtonVariant, PmSize } from '../types';

export type PmButtonSize = PmSize;

@Component({ tag: 'pm-button', styleUrl: 'button.css', shadow: false, scoped: true })
export class PmButton {
  @Element() host!: HTMLElement;
  @Prop() variant: PmButtonVariant = 'primary';
  @Prop() size: PmButtonSize = 'medium';
  @Prop() disabled = false;
  @Prop() loading = false;
  @Prop() type: 'button' | 'submit' | 'reset' = 'button';
  @Prop({ attribute: 'full-width' }) fullWidth = false;
  @Event() pmPress!: EventEmitter<MouseEvent>;
  private control?: HTMLButtonElement;

  @Method()
  async focusControl() {
    this.control?.focus();
  }

  private handleClick = (event: MouseEvent) => {
    if (!this.disabled && !this.loading) this.pmPress.emit(event);
  };

  render() {
    const classes = {
      'pm-button': true,
      [`is-${this.variant}`]: true,
      [`is-${this.size}`]: true,
      'is-loading': this.loading,
      'is-full': this.fullWidth,
    };

    return (
      <button
        ref={(el) => (this.control = el as HTMLButtonElement)}
        class={classes}
        disabled={this.disabled || this.loading}
        type={this.type}
        aria-busy={this.loading ? 'true' : undefined}
        onClick={this.handleClick}
      >
        <span class="start">
          {this.loading ? <pm-spinner label="Loading" /> : <slot name="start" />}
        </span>
        <span class="label">
          <slot />
        </span>
        <span class="end">
          <slot name="end" />
        </span>
      </button>
    );
  }
}
