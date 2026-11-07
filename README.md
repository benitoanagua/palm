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
- `@palm/primitives` — small layout/surface primitives.
- `@palm/icons` — the Tabler icon data used by `pm-icon`.
- `@palm/components` — canonical Web Components.
- `@palm/react` — React component adapters.
- `@palm/vue` — Vue component adapters.
- `@palm/angular` — Angular component registration adapter.
- `@palm/cosmos` — React Cosmos documentation app (private).

## Icon usage

```html
<pm-icon name="search"></pm-icon>
```

Accessible icon:

```html
<pm-icon name="search" aria-label="Search"></pm-icon>
```

No other icon provider is supported.

## Commands

```bash
pnpm install --frozen-lockfile   # install dependencies
pnpm cosmos                      # open Cosmos UI locally
pnpm build                       # build all packages + Cosmos export
pnpm check                       # run quality gate
pnpm test:e2e                    # run Playwright e2e tests (needs Chromium)
```

## Quality gate

`pnpm check` runs static checks, formatting, lint, typecheck, unit tests,
package builds, and the Cosmos static export. It does not launch a browser.

Browser checks require Chromium:

```bash
pnpm test:e2e
```

## Cosmos en GitHub Pages

El artefacto principal de despliegue es el sitio de React Cosmos, publicado
en GitHub Pages (no Cloudflare) mediante `.github/workflows/pages.yml`.

El workflow se dispara en cada `push` a `main` y bajo `workflow_dispatch`.
Construye los paquetes (`pnpm build`, obligatorio antes del export de Cosmos),
exporta el sitio de Cosmos (`pnpm build:cosmos`) y despliega el contenido de
`apps/cosmos/cosmos-export`.

### Activarlo (una sola vez)

En GitHub, ve a **Settings → Pages → Build and deployment → Source** y selecciona
**GitHub Actions**. El despliegue usa el entorno `github-pages`.

Una vez activo, el sitio queda disponible en:

```
https://benitoanagua.github.io/palm/
```

El build se sirve bajo el subpath `/palm/` (definido con `publicUrl` en
`cosmos.config.json` y `base` en `vite.config.ts`). El archivo `.nojekyll`
se añade al artefacto para evitar el procesamiento de Jekyll.
