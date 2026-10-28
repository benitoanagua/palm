# PALM Showcase

The Showcase is a small product-like application used to demonstrate PALM as a system rather than as an isolated component catalog.

It intentionally contains no domain logic, API client, persistence or framework-specific UI implementation.

## Surfaces

- Overview: composition, metrics, alerts, cards and responsive layout.
- Activity: toolbar, search field, data table, statuses and empty state.
- Forms: native form semantics, validation surface and accessible descriptions.
- Settings: tabs, switches, accordion and drawer composition.

## Visual intent

The application is a PALM consumer and the reference surface for detecting inconsistencies against PALM's own approved visual baselines.

## Commands

```bash
pnpm showcase
pnpm build:showcase
pnpm test:showcase
```
