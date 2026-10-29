# Showcase and compositional components

The Showcase (`apps/showcase`) is a product-like application demonstrating PALM as a unified system rather than an isolated component catalog. It contains no domain logic, API client, persistence or framework-specific UI implementation.

## Compositional components

These components are intentionally compositional. They demonstrate the strengths of PALM without introducing application or domain logic:

- `pm-metric-card`: responsive data emphasis and component slots.
- `pm-status`: compact semantic state tag.
- `pm-alert`: feedback, tone and composition.
- `pm-empty-state`: empty content with icon/action slots.
- `pm-data-table`: native table semantics, numeric alignment and responsive overflow.
- `pm-toolbar`: responsive composition using named slots.

They are suitable for product surfaces and documentation examples, but remain domain-agnostic.

## Showcase surfaces

- Overview: composition, metrics, alerts, cards and responsive layout.
- Activity: toolbar, search field, data table, statuses and empty state.
- Forms: native form semantics, validation surface and accessible descriptions.
- Settings: tabs, switches, accordion and drawer composition.

## Visual intent

The showcase is a PALM consumer and the reference surface for detecting inconsistencies against PALM's approved visual baselines.

## Commands

```bash
pnpm showcase
pnpm build:showcase
pnpm test:showcase
```
