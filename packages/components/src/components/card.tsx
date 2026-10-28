import { Component, Event, type EventEmitter, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-card', styleUrl: 'card.css', shadow: false, scoped: true })
export class PmCard {
  @Prop() interactive = false;
  @Event() pmPress!: EventEmitter<MouseEvent | KeyboardEvent>;

  private handleKeyDown = (event: KeyboardEvent) => {
    if (!this.interactive) return;
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
      this.pmPress.emit(event);
    }
  };

  render() {
    return (
      <article
        class={{ card: true, interactive: this.interactive }}
        tabindex={this.interactive ? 0 : undefined}
        role={this.interactive ? 'button' : undefined}
        onClick={(event) => this.interactive && this.pmPress.emit(event)}
        onKeyDown={this.handleKeyDown}
      >
        <header>
          <slot name="header" />
        </header>
        <div class="body">
          <slot />
        </div>
        <footer>
          <slot name="footer" />
        </footer>
      </article>
    );
  }
}
