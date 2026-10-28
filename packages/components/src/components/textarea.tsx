import { Component, Element, Event, type EventEmitter, Method, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-textarea', styleUrl: 'field.css', shadow: false, scoped: true })
export class PmTextarea {
  @Element() host!: HTMLElement;
  @Prop() label = '';
  @Prop() name = '';
  @Prop({ mutable: true }) value = '';
  @Prop() placeholder = '';
  @Prop() hint = '';
  @Prop() error = '';
  @Prop() required = false;
  @Prop() disabled = false;
  @Prop() readonly = false;
  @Prop() rows = 4;
  @Event() pmInput!: EventEmitter<string>;
  @Event() pmChange!: EventEmitter<string>;
  private control?: HTMLTextAreaElement;

  @Method()
  async focusControl() {
    this.control?.focus();
  }

  private id() {
    return this.host.id || `pm-textarea-${this.name || 'field'}`;
  }
  private onInput = (event: Event) => {
    this.value = (event.target as HTMLTextAreaElement).value;
    this.pmInput.emit(this.value);
  };
  private onChange = () => this.pmChange.emit(this.value);

  render() {
    const id = this.id();
    const describedBy =
      [this.hint ? `${id}-hint` : '', this.error ? `${id}-error` : ''].filter(Boolean).join(' ') ||
      undefined;
    return (
      <div class="field">
        {this.label ? (
          <label class="label" htmlFor={id}>
            {this.label}
            {this.required ? ' *' : ''}
          </label>
        ) : null}
        <textarea
          ref={(el) => (this.control = el as HTMLTextAreaElement)}
          id={id}
          name={this.name || undefined}
          rows={this.rows}
          value={this.value}
          placeholder={this.placeholder}
          required={this.required}
          disabled={this.disabled}
          readOnly={this.readonly}
          aria-invalid={this.error ? 'true' : undefined}
          aria-describedby={describedBy}
          onInput={this.onInput}
          onChange={this.onChange}
        />
        {this.hint ? (
          <span id={`${id}-hint`} class="message">
            {this.hint}
          </span>
        ) : null}
        {this.error ? (
          <span id={`${id}-error`} class="message error" role="alert">
            {this.error}
          </span>
        ) : null}
      </div>
    );
  }
}
