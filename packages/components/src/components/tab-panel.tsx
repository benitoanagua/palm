import { Component, Element, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-tab-panel', styleUrl: 'tabs.css', shadow: false, scoped: true })
export class PmTabPanel {
  @Element() host!: HTMLElement;
  @Prop() value!: string;
  render() {
    const id = `${this.host.id || `pm-tab-panel-${this.value}`}`;
    return <section id={id} class="tab-panel" role="tabpanel" aria-labelledby={`pm-tab-${this.value}`} tabIndex={0}><slot /></section>;
  }
}
