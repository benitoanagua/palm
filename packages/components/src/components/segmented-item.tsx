import { Component, Event, type EventEmitter, Prop, h } from '@stencil/core';

@Component({
  tag: 'pm-segmented-item',
  styleUrl: 'segmented-control.css',
  shadow: false,
  scoped: true,
})
export class PmSegmentedItem {
  @Prop() value = '';
  @Prop() disabled = false;
  /** Set by the parent <pm-segmented-control>. */
  @Prop({ reflect: true, mutable: true }) selected = false;
  @Event() pmSelect!: EventEmitter<string>;

  render() {
    return (
      <button
        type="button"
        role="radio"
        aria-checked={this.selected ? 'true' : 'false'}
        class={{ 'pm-segmented-item': true, 'is-selected': this.selected }}
        disabled={this.disabled}
        onClick={() => this.pmSelect.emit(this.value)}
      >
        <slot />
      </button>
    );
  }
}
