import {
  Component,
  Element,
  Event,
  type EventEmitter,
  Method,
  Prop,
  State,
  h,
} from '@stencil/core';

/**
 * A trigger + popover menu. The first slotted element (typically an
 * `<pm-button>` or `<pm-icon-button>` with `slot="trigger"`) opens the menu;
 * slotted `<pm-menu-item>` children make up the menu content.
 *
 *   <pm-menu label="Row actions">
 *     <pm-button slot="trigger">Actions</pm-button>
 *     <pm-menu-item value="edit">Edit</pm-menu-item>
 *     <pm-menu-item value="duplicate">Duplicate</pm-menu-item>
 *     <pm-menu-item value="delete" tone="danger">Delete</pm-menu-item>
 *   </pm-menu>
 */
@Component({ tag: 'pm-menu', styleUrl: 'menu.css', shadow: false, scoped: true })
export class PmMenu {
  @Element() host!: HTMLElement;
  @Prop({ mutable: true }) open = false;
  @Prop() label = '';
  @Prop() placement: 'bottom-start' | 'bottom-end' = 'bottom-start';
  @Event() pmOpenChange!: EventEmitter<boolean>;
  @Event() pmSelect!: EventEmitter<string>;

  @State() private items: HTMLElement[] = [];
  private root?: HTMLElement;
  private trigger?: HTMLElement;
  private list?: HTMLElement;

  private bindList = (el?: HTMLElement) => {
    if (el === this.list) return;
    this.list?.removeEventListener('pmSelect', this.onItemSelect as EventListener);
    this.list = el;
    this.list?.addEventListener('pmSelect', this.onItemSelect as EventListener);
  };

  @Method() async show() {
    this.setOpen(true);
  }
  @Method() async close() {
    this.setOpen(false);
  }

  componentDidLoad() {
    this.refreshItems();
    document.addEventListener('click', this.onDocumentClick, true);
  }
  disconnectedCallback() {
    document.removeEventListener('click', this.onDocumentClick, true);
    this.list?.removeEventListener('pmSelect', this.onItemSelect as EventListener);
  }

  private refreshItems = () => {
    this.items = Array.from(this.host.querySelectorAll(':scope > pm-menu-item')) as HTMLElement[];
  };

  private setOpen(next: boolean) {
    if (this.open === next) return;
    this.open = next;
    this.pmOpenChange.emit(next);
    if (next)
      requestAnimationFrame(() =>
        (this.items[0]?.querySelector('button') as HTMLElement | null)?.focus(),
      );
    else (this.trigger?.querySelector('button, a') as HTMLElement | null)?.focus();
  }

  private onTriggerClick = () => this.setOpen(!this.open);

  private onDocumentClick = (event: MouseEvent) => {
    if (!this.open) return;
    if (!this.root?.contains(event.target as Node)) this.setOpen(false);
  };

  private onKeyDown = (event: KeyboardEvent) => {
    if (!this.open) return;
    const buttons = this.items
      .map((item) => item.querySelector('button'))
      .filter(Boolean) as HTMLButtonElement[];
    const currentIndex = buttons.findIndex((b) => b === document.activeElement);
    if (event.key === 'Escape') {
      event.preventDefault();
      this.setOpen(false);
      return;
    }
    if (event.key === 'ArrowDown') {
      event.preventDefault();
      buttons[(currentIndex + 1 + buttons.length) % buttons.length]?.focus();
      return;
    }
    if (event.key === 'ArrowUp') {
      event.preventDefault();
      buttons[(currentIndex - 1 + buttons.length) % buttons.length]?.focus();
      return;
    }
    if (event.key === 'Home') {
      event.preventDefault();
      buttons[0]?.focus();
      return;
    }
    if (event.key === 'End') {
      event.preventDefault();
      buttons[buttons.length - 1]?.focus();
      return;
    }
  };

  private onItemSelect = (event: CustomEvent<string>) => {
    this.pmSelect.emit(event.detail);
    this.setOpen(false);
  };

  render() {
    const menuId = `${this.host.id || 'pm-menu'}-list`;
    return (
      <div class="pm-menu" ref={(el) => (this.root = el as HTMLElement)} onKeyDown={this.onKeyDown}>
        <span
          class="pm-menu__trigger"
          ref={(el) => (this.trigger = el as HTMLElement)}
          onClick={this.onTriggerClick}
        >
          <slot name="trigger" />
        </span>
        <div
          id={menuId}
          class={{ 'pm-menu__list': true, [`is-${this.placement}`]: true, 'is-open': this.open }}
          role="menu"
          aria-label={this.label || undefined}
          hidden={!this.open}
          ref={(el) => this.bindList(el as HTMLElement | undefined)}
        >
          <slot onSlotchange={this.refreshItems} />
        </div>
      </div>
    );
  }
}
