import { Component, Element, Prop, h } from '@stencil/core';

/**
 * Wraps slotted `<pm-breadcrumb-item>` children, marking the last item as
 * the current page and (optionally) collapsing the middle items behind a
 * clickable ellipsis when there are more than `maxItems`.
 *
 *   <pm-breadcrumbs max-items="3">
 *     <pm-breadcrumb-item href="/">Home</pm-breadcrumb-item>
 *     <pm-breadcrumb-item href="/settings">Settings</pm-breadcrumb-item>
 *     <pm-breadcrumb-item href="/settings/team">Team</pm-breadcrumb-item>
 *     <pm-breadcrumb-item>Profile</pm-breadcrumb-item>
 *   </pm-breadcrumbs>
 *
 * `shadow: false` means slotted items are real light-DOM children of the
 * host, so collapsing is done by inserting/removing a real ellipsis <li>
 * node next to them rather than trying to reorder projected slot content.
 */
@Component({ tag: 'pm-breadcrumbs', styleUrl: 'breadcrumbs.css', shadow: false, scoped: true })
export class PmBreadcrumbs {
  @Element() host!: HTMLElement;
  @Prop() label = 'Breadcrumb';
  @Prop() maxItems = 0; // 0 = show all, never collapse
  private collapsed = true;
  private ellipsis?: HTMLLIElement;

  componentDidLoad() {
    this.refresh();
  }

  private items() {
    return Array.from(this.host.querySelectorAll(':scope > pm-breadcrumb-item')) as HTMLElement[];
  }

  private refresh = () => {
    const items = this.items();
    items.forEach((item, index) => {
      item.toggleAttribute('current', index === items.length - 1);
    });
    this.applyCollapse(items);
  };

  private applyCollapse(items: HTMLElement[]) {
    const shouldCollapse = Boolean(this.maxItems) && items.length > this.maxItems && this.collapsed;
    if (!shouldCollapse) {
      items.forEach((item) => {
        item.hidden = false;
      });
      this.ellipsis?.remove();
      this.ellipsis = undefined;
      return;
    }
    const keepTail = Math.max(this.maxItems - 1, 1);
    items.forEach((item, index) => {
      const isFirst = index === 0;
      const isTail = index >= items.length - keepTail;
      item.hidden = !(isFirst || isTail);
    });
    if (!this.ellipsis) {
      const li = document.createElement('li');
      li.className = 'pm-breadcrumbs__ellipsis-item';
      const button = document.createElement('button');
      button.type = 'button';
      button.className = 'pm-breadcrumbs__ellipsis';
      button.setAttribute('aria-label', 'Show all breadcrumbs');
      button.textContent = '…';
      button.addEventListener('click', () => {
        this.collapsed = false;
        this.refresh();
      });
      li.appendChild(button);
      const sep = document.createElement('pm-icon');
      sep.setAttribute('name', 'chevron-right');
      sep.setAttribute('class', 'pm-breadcrumbs__sep');
      sep.setAttribute('aria-hidden', 'true');
      li.appendChild(sep);
      this.ellipsis = li;
    }
    const firstTailIndex = items.length - keepTail;
    this.host.insertBefore(this.ellipsis, items[firstTailIndex] ?? null);
  }

  render() {
    return (
      <nav class="pm-breadcrumbs" aria-label={this.label}>
        <ol>
          <slot onSlotchange={this.refresh} />
        </ol>
      </nav>
    );
  }
}
