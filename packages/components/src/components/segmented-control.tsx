import { Component, Element, Event, type EventEmitter, Prop, h } from '@stencil/core';

/**
 *   <pm-segmented-control value="week" label="Range">
 *     <pm-segmented-item value="day">Day</pm-segmented-item>
 *     <pm-segmented-item value="week">Week</pm-segmented-item>
 *     <pm-segmented-item value="month">Month</pm-segmented-item>
 *   </pm-segmented-control>
 */
@Component({ tag: 'pm-segmented-control', styleUrl: 'segmented-control.css', shadow: false, scoped: true })
export class PmSegmentedControl {
  @Element() host!: HTMLElement;
  @Prop({ mutable: true }) value = '';
  @Prop() label = '';
  @Event() pmChange!: EventEmitter<string>;
  private list?: HTMLElement;

  componentDidLoad() {
    this.refresh();
    this.list?.addEventListener('pmSelect', this.onItemSelect as EventListener);
  }
  disconnectedCallback() {
    this.list?.removeEventListener('pmSelect', this.onItemSelect as EventListener);
  }

  private refresh = () => {
    const items = Array.from(this.host.querySelectorAll(':scope > pm-segmented-item')) as HTMLElement[];
    if (!this.value && items[0]) this.value = items[0].getAttribute('value') || '';
    this.syncChildren(items);
  };

  private syncChildren(items: HTMLElement[]) {
    items.forEach((item) => item.toggleAttribute('selected', item.getAttribute('value') === this.value));
  }

  private onItemSelect = (event: CustomEvent<string>) => {
    this.value = event.detail;
    this.syncChildren(Array.from(this.host.querySelectorAll(':scope > pm-segmented-item')) as HTMLElement[]);
    this.pmChange.emit(this.value);
  };

  render() {
    return (
      <div class="pm-segmented-control" role="radiogroup" aria-label={this.label || undefined} ref={(el) => (this.list = el as HTMLElement)}>
        <slot onSlotchange={this.refresh} />
      </div>
    );
  }
}
