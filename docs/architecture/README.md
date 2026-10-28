# PALM Architecture

PALM is an independent design system. It owns its visual language, component logic, tokens, APIs, and behavior.

## Layers

`tokens → foundation → primitives → components`

Dependencies must point downward in this diagram only. Application/domain code does not belong in PALM.

## Runtime

- Stencil + TypeScript
- Native Web Components / Custom Elements
- Light DOM by default
- Shadow DOM only when an explicit component contract requires isolation
- CSS custom properties for theming
- Tabler Icons used exclusively through the PALM `pm-icon` API

## Icon boundary

`@palm/icons` owns the icon registry. `@palm/primitives` owns the `pm-icon` Web Component. Public consumers should use `pm-icon` and must not couple application code to the icon registry internals.

Tabler Icons are rendered as inline SVG nodes so PALM does not require a runtime icon web-component dependency.

## Public API rules

Prefer semantic HTML, slots, typed properties, and typed custom events. Do not expose implementation dependencies or internal CSS class names as public contracts.
