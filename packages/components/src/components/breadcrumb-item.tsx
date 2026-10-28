import { Component, Prop, h } from '@stencil/core';

@Component({ tag: 'pm-breadcrumb-item', styleUrl: 'breadcrumbs.css', shadow: false, scoped: true })
export class PmBreadcrumbItem {
  @Prop() href = '';
  /** Set automatically by the parent <pm-breadcrumbs> on the last item. */
  @Prop({ reflect: true, mutable: true }) current = false;

  render() {
    return (
      <li class="pm-breadcrumbs__item">
        {this.current || !this.href ? (
          <span aria-current={this.current ? 'page' : undefined}>
            <slot />
          </span>
        ) : (
          <a href={this.href}>
            <slot />
          </a>
        )}
        {!this.current ? (
          <pm-icon name="chevron-right" class="pm-breadcrumbs__sep" aria-hidden="true" />
        ) : null}
      </li>
    );
  }
}
