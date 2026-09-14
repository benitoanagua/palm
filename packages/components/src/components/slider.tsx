import { Component, Element, Event, type EventEmitter, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-slider', styleUrl: 'slider.css', shadow: false, scoped: true })
export class PmSlider {
  @Element() host!: HTMLElement;
  @Prop({ mutable: true }) value = 0;
  @Prop() min = 0;
  @Prop() max = 100;
  @Prop() step = 1;
  @Prop() label = '';
  @Prop() disabled = false;
  @Prop() showValue = false;
  @Event() pmInput!: EventEmitter<number>;
  @Event() pmChange!: EventEmitter<number>;

  private onInput = (event: Event) => {
    this.value = Number((event.target as HTMLInputElement).value);
    this.pmInput.emit(this.value);
  };
  private onChange = (event: Event) => {
    this.value = Number((event.target as HTMLInputElement).value);
    this.pmChange.emit(this.value);
  };

  render() {
    const percent = ((this.value - this.min) / (this.max - this.min)) * 100;
    const id = `${this.host.id || 'pm-slider'}-input`;
    return (
      <div class="pm-slider">
        {this.label ? (
          <div class="pm-slider__row">
            <label htmlFor={id}>{this.label}</label>
            {this.showValue ? <span class="pm-slider__value">{this.value}</span> : null}
          </div>
        ) : null}
        <input
          id={id}
          type="range"
          class="pm-slider__input"
          style={{ '--pm-slider-percent': `${percent}%` }}
          min={String(this.min)}
          max={String(this.max)}
          step={String(this.step)}
          value={String(this.value)}
          disabled={this.disabled}
          onInput={this.onInput}
          onChange={this.onChange}
        />
      </div>
    );
  }
}
