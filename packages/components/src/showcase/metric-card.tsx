import { Component, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-metric-card', styleUrl: 'metric-card.css', shadow: false, scoped: true })
export class PmMetricCard {
  @Prop() label = '';
  @Prop() value = '';
  @Prop() trend = '';
  @Prop() tone: 'neutral' | 'positive' | 'negative' = 'neutral';

  render() {
    return (
      <article class={`pm-metric-card pm-metric-card--${this.tone}`}>
        <div class="pm-metric-card__top">
          <span class="pm-metric-card__label">{this.label}</span>
          <slot name="icon" />
        </div>
        <strong class="pm-metric-card__value">{this.value}</strong>
        {this.trend ? <span class="pm-metric-card__trend">{this.trend}</span> : null}
        <slot />
      </article>
    );
  }
}
