import { Component, Prop, h } from '@stencil/core';

/** Linear progress bar. Pairs with `<pm-spinner>` (circular, indeterminate-only). */
@Component({ tag: 'pm-progress', styleUrl: 'progress.css', shadow: false, scoped: true })
export class PmProgress {
  @Prop() value = 0; // 0-100, ignored when indeterminate
  @Prop() indeterminate = false;
  @Prop() label = '';
  @Prop() tone: 'action' | 'success' | 'danger' | 'warning' = 'action';

  render() {
    const clamped = Math.min(Math.max(this.value, 0), 100);
    return (
      <div class="pm-progress">
        {this.label ? <span class="pm-progress__label">{this.label}</span> : null}
        <div
          class={{ 'pm-progress__track': true, [`is-${this.tone}`]: true, 'is-indeterminate': this.indeterminate }}
          role="progressbar"
          aria-label={this.label || undefined}
          aria-valuemin={this.indeterminate ? undefined : 0}
          aria-valuemax={this.indeterminate ? undefined : 100}
          aria-valuenow={this.indeterminate ? undefined : clamped}
        >
          <div class="pm-progress__fill" style={this.indeterminate ? undefined : { width: `${clamped}%` }} />
        </div>
      </div>
    );
  }
}
