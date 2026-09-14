import { Component, Element, Event, type EventEmitter, Prop, State, h } from '@stencil/core';

@Component({ tag: 'pm-tabs', styleUrl: 'tabs.css', shadow: false, scoped: true })
export class PmTabs {
  @Element() host!: HTMLElement;
  @Prop({ mutable: true }) value = '';
  @Event() pmChange!: EventEmitter<string>;
  @State() private items: HTMLElement[] = [];
  private tabList?: HTMLElement;

  componentDidLoad() {
    this.refresh();
    this.tabList?.addEventListener('pmPress', this.onTabPress as EventListener);
  }
  disconnectedCallback() {
    this.tabList?.removeEventListener('pmPress', this.onTabPress as EventListener);
  }
  private refresh = () => {
    this.items = Array.from(this.host.querySelectorAll(':scope > pm-tab')) as HTMLElement[];
    if (!this.value && this.items[0]) this.value = this.items[0].getAttribute('value') || '';
    this.syncChildren();
  };
  private syncChildren = () => {
    this.items.forEach((tab) => tab.setAttribute('active', String(tab.getAttribute('value') === this.value)));
    Array.from(this.host.querySelectorAll(':scope > pm-tab-panel')).forEach((panel) => {
      const el = panel as HTMLElement;
      el.hidden = el.getAttribute('value') !== this.value;
    });
  };
  private onTabPress = (event: CustomEvent<string>) => {
    const next = event.detail;
    if (!next) return;
    this.value = next;
    this.syncChildren();
    this.pmChange.emit(this.value);
  };

  render() {
    return <div class="tabs"><div class="tab-list" role="tablist" aria-label="Tabs" ref={(el) => this.tabList = el}><slot onSlotchange={this.refresh} /></div><slot name="panel" /></div>;
  }
}

