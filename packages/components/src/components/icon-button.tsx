import { Component, Element, Event, type EventEmitter, Method, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-icon-button', styleUrl: 'icon-button.css', shadow: false, scoped: true })
export class PmIconButton {
  @Element() host!: HTMLElement;
  @Prop() name!: string;
  @Prop() label!: string;
  @Prop() size: 'small' | 'medium' | 'large' = 'medium';
  @Prop() disabled = false;
  @Prop() loading = false;
  @Event() pmPress!: EventEmitter<MouseEvent>;
  private control?: HTMLButtonElement;

  @Method()
  async focusControl() { this.control?.focus(); }

  render() {
    return (
      <button
        ref={(el) => this.control = el as HTMLButtonElement}
        class={{ 'pm-icon-button': true, [`is-${this.size}`]: true }}
        aria-label={this.label}
        aria-busy={this.loading ? 'true' : undefined}
        disabled={this.disabled || this.loading}
        onClick={(event) => this.pmPress.emit(event)}
      >
        <pm-icon name={this.name} aria-hidden="true" />
      </button>
    );
  }
}
