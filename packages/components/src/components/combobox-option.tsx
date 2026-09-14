import { Component, Event, type EventEmitter, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-combobox-option', styleUrl: 'combobox.css', shadow: false, scoped: true })
export class PmComboboxOption {
  @Prop() value = '';
  /** All three are set by the parent <pm-combobox> — presence-based (toggleAttribute), not string values. */
  @Prop({ reflect: true, mutable: true }) matched = true;
  @Prop({ reflect: true, mutable: true }) active = false;
  @Prop({ reflect: true, mutable: true }) selected = false;
  @Event() pmSelect!: EventEmitter<string>;

  render() {
    return (
      <div
        role="option"
        aria-selected={this.selected ? 'true' : 'false'}
        class={{ 'pm-combobox__option': true, 'is-active': this.active }}
        onMouseDown={(event) => { event.preventDefault(); this.pmSelect.emit(this.value); }}
      >
        <slot />
      </div>
    );
  }
}
