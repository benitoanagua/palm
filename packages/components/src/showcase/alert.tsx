import { Component, Event, type EventEmitter, Prop, State, h } from '@stencil/core';

@Component({ tag: 'pm-alert', styleUrl: 'alert.css', shadow: false, scoped: true })
export class PmAlert {
  @State() visible = true;
  @Event() pmDismiss!: EventEmitter<void>;

  @Prop() tone: 'info' | 'success' | 'warning' | 'danger' = 'info';
  @Prop() heading = '';
  @Prop() dismissible = false;

  private dismiss = () => {
    this.visible = false;
    this.pmDismiss.emit();
  };

  render() {
    if (!this.visible) return null;
    return (
      <section
        class={`pm-alert pm-alert--${this.tone}`}
        role={this.tone === 'danger' ? 'alert' : 'status'}
      >
        <div class="pm-alert__content">
          {this.heading ? <strong>{this.heading}</strong> : null}
          <div>
            <slot />
          </div>
        </div>
        {this.dismissible ? (
          <pm-icon-button name="x" label="Dismiss" size="small" onPmPress={this.dismiss} />
        ) : null}
      </section>
    );
  }
}
