import { Component, Event, type EventEmitter, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-checkbox', styleUrl: 'choice.css', shadow: false, scoped: true })
export class PmCheckbox {
  @Prop() label = '';
  @Prop({ mutable: true }) checked = false;
  @Prop() disabled = false;
  @Prop() name = '';
  @Prop() value = 'on';
  @Prop() required = false;
  @Event() pmChange!: EventEmitter<boolean>;
  render() { return <label class="choice"><input type="checkbox" name={this.name || undefined} value={this.value} checked={this.checked} disabled={this.disabled} required={this.required} onChange={(event) => { this.checked = (event.target as HTMLInputElement).checked; this.pmChange.emit(this.checked); }} /><span>{this.label}<slot /></span></label>; }
}
