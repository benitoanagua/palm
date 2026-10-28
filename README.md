# PALM Design System

PALM is a design system built from scratch with Stencil, TypeScript and Web Components.

## Principles

- PALM owns its component logic, tokens, visual rules, APIs and behavior.
- Tabler Icons is the only icon family used by PALM.
- `pm-icon` has a deliberately minimal API: `name` and `aria-label`.
- Native HTML semantics are preferred over custom behavior.
- Components are framework-agnostic; React, Vue and Angular are adapters only.
- No business logic belongs in PALM.

## Packages

- `@palm/tokens` — design tokens and themes.
- `@palm/foundation` — shared DOM, focus and accessibility utilities.
- `@palm/primitives` — small layout/surface primitives.
- `@palm/icons` — the Tabler icon data used by `pm-icon`.
- `@palm/components` — canonical Web Components.
- `@palm/testing` — visual and accessibility test infrastructure.

## Icon usage

```html
<pm-icon name="search"></pm-icon>
```

Accessible icon:

```html
<pm-icon name="search" aria-label="Search"></pm-icon>
```

No other icon provider is supported.

## Quality gate

```bash
pnpm install --frozen-lockfile
pnpm check
```

`pnpm check` runs static checks, formatting, lint, typecheck, unit tests, package builds and the Storybook build. It does not launch a browser.

Browser checks require Chromium and Storybook:

```bash
pnpm test:e2e
```

On WSL, use the local `packages/testing/playwright.local.config.ts` with the system Chromium executable at `/usr/bin/chromium`. That local config is intentionally not committed. Visual checks and snapshot updates are local-only; baselines and image artifacts are not versioned:

```bash
pnpm test:visual:update
```

## Showcase

Run the product-like PALM showcase with:

```bash
pnpm showcase
```

Build it with:

```bash
pnpm build:showcase
```

The showcase is intentionally domain-agnostic: it demonstrates the design system without introducing business logic.
