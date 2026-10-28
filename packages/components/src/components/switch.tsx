import { Component, Event, type EventEmitter, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-switch', styleUrl: 'choice.css', shadow: false, scoped: true })
export class PmSwitch {
  @Prop() label = '';
  @Prop({ mutable: true }) checked = false;
  @Prop() disabled = false;
  @Prop() name = '';
  @Event() pmChange!: EventEmitter<boolean>;
  render() {
    return (
      <label class="choice">
        <input
          type="checkbox"
          role="switch"
          name={this.name || undefined}
          checked={this.checked}
          disabled={this.disabled}
          onChange={(event) => {
            this.checked = (event.target as HTMLInputElement).checked;
            this.pmChange.emit(this.checked);
          }}
        />
        <span>
          {this.label}
          <slot />
        </span>
      </label>
    );
  }
}
