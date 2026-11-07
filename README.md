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
- `@palm/react` — React component adapters.
- `@palm/vue` — Vue component adapters.
- `@palm/angular` — Angular component registration adapter.
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

## Storybook en GitHub Pages

El artefacto principal de despliegue es el Storybook, publicado en GitHub Pages (no Cloudflare) mediante `.github/workflows/storybook-pages.yml`.

El workflow se dispara en cada `push` a `main` y bajo `workflow_dispatch`. Construye los paquetes (`pnpm build`, obligatorio antes de Storybook), construye el Storybook (`pnpm build:storybook`) y despliega el contenido de `apps/storybook/storybook-static`.

### Activarlo (una sola vez)

En GitHub, ve a **Settings → Pages → Build and deployment → Source** y selecciona **GitHub Actions**. El despliegue usa el entorno `github-pages`.

Una vez activo, el sitio queda disponible en:

```
https://benitoanagua.github.io/palm/
```

El build se sirve bajo el subpath `/palm/` (definido con `STORYBOOK_BASE` en el workflow y `viteFinal` en `.storybook/main.ts`). El archivo `.nojekyll` se añade al artefacto para evitar el procesamiento de Jekyll.

### Calidad pendiente (decisiones no tomadas)

- Agregar `@storybook/addon-a11y`: `preview.ts` declara `a11y: { test: 'todo' }` pero el paquete no está instalado. Añadirlo da cobertura de accesibilidad en las historias a costa de una dependencia nueva y compatibilidad con Storybook 10.
- Conectar el toggle de tema (`globalTypes.theme`) con un decorator que fije `data-pm-theme` en `documentElement`: hoy el selector light/dark de la barra de herramientas no se refleja en los componentes.
