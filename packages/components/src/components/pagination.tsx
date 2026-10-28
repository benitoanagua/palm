import { Component, Event, type EventEmitter, Prop, h } from '@stencil/core';

type PageToken = number | 'ellipsis';

@Component({ tag: 'pm-pagination', styleUrl: 'pagination.css', shadow: false, scoped: true })
export class PmPagination {
  @Prop() page = 1;
  @Prop() pageCount = 1;
  @Prop() siblingCount = 1;
  @Prop() label = 'Pagination';
  @Event() pmChange!: EventEmitter<number>;

  private goTo(page: number) {
    const clamped = Math.min(Math.max(1, page), this.pageCount);
    if (clamped !== this.page) this.pmChange.emit(clamped);
  }

  /** Builds a windowed page list with ellipses, e.g. 1 … 4 5 [6] 7 8 … 20 */
  private buildPages(): PageToken[] {
    const total = this.pageCount;
    const current = this.page;
    const sibling = this.siblingCount;
    const totalVisible = sibling * 2 + 5; // first + last + current + 2 ellipses + siblings
    if (total <= totalVisible) return Array.from({ length: total }, (_, i) => i + 1);

    const left = Math.max(current - sibling, 2);
    const right = Math.min(current + sibling, total - 1);
    const pages: PageToken[] = [1];
    if (left > 2) pages.push('ellipsis');
    for (let p = left; p <= right; p++) pages.push(p);
    if (right < total - 1) pages.push('ellipsis');
    pages.push(total);
    return pages;
  }

  render() {
    const pages = this.buildPages();
    return (
      <nav class="pm-pagination" aria-label={this.label}>
        <pm-icon-button
          name="chevron-left"
          label="Previous page"
          size="small"
          disabled={this.page <= 1}
          onHhPress={() => this.goTo(this.page - 1)}
        />
        <ul class="pm-pagination__list">
          {pages.map((token, index) =>
            token === 'ellipsis' ? (
              <li key={`e-${index}`} class="pm-pagination__ellipsis" aria-hidden="true">
                …
              </li>
            ) : (
              <li key={token}>
                <button
                  type="button"
                  class={{ 'pm-pagination__page': true, 'is-current': token === this.page }}
                  aria-current={token === this.page ? 'page' : undefined}
                  aria-label={`Page ${token}`}
                  onClick={() => this.goTo(token)}
                >
                  {token}
                </button>
              </li>
            ),
          )}
        </ul>
        <pm-icon-button
          name="chevron-right"
          label="Next page"
          size="small"
          disabled={this.page >= this.pageCount}
          onHhPress={() => this.goTo(this.page + 1)}
        />
      </nav>
    );
  }
}
