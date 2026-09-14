# Palm Foundations

Palm foundations are independently defined and are not inherited from STRATA.

## Why Palm exists

Palm is a design system for power users who sometimes don't have a computer nearby — the phone has to carry real work, not just quick lookups. It borrows its visual vocabulary from ActiveAdmin (Ruby on Rails): dark header, glossy gradient buttons, solid-color `status_tag`s, dense `index_table` listings. That heritage was chosen deliberately, not by accident — see [`visual-system.md`](./visual-system.md) for the reasoning and [`../../palm-design-system.md`](../../palm-design-system.md) for the full specification these foundations implement.

Five product decisions sit above the token layer and are treated as non-negotiable (Category A in the full spec):

1. A single universal command surface (Omnibar), reachable from one persistent entry point, never a gesture.
2. Every sync state is honest and visible in three levels plus conflict — never collapsed into a generic spinner.
3. Density is the user's explicit choice ("Power to the User" — Compact / Normal / Airy), never inferred from screen size.
4. One gesture or color has exactly one meaning system-wide.
5. Nothing disappears without an explicit user action — no silent auto-close.

## What each foundation covers

- **Color** — Palm semantic color tokens, verified with real colorblindness simulation (protanopia, deuteranopia, tritanopia via Machado-Oliveira-Fernandes matrices), not just "looks distinguishable." See [`tokens.md`](./tokens.md).
- **Typography** — Palm type scale and text roles, on the classic Helvetica Neue/Arial stack (ActiveAdmin's real font, not a modern system-font default).
- **Spacing** — Palm spacing scale.
- **Motion** — Palm motion and reduced-motion rules. Motion communicates a state change; it never decorates a neutral interaction.
- **Iconography** — Tabler Icons behind the Palm `pm-icon` boundary. (Material Symbols was the top candidate for the native Android app specifically, because it ships free with Jetpack Compose; for this web system, Tabler wins because the same icon needs to render identically across React/Vue/Angular/HTML without a platform-bundled dependency — see the icon comparison referenced in the full spec.)

## Reference material

- [`../../palm-design-system.md`](../../palm-design-system.md) — the full specification (philosophy, rules, tokens, accessibility, pattern catalog, research sources, checklist).
- [`../visual/demos/`](../visual/demos/) — five approved HTML reference demos showing the visual system applied end to end. These are reference only — kept as delivered, not living source of truth for this repo's components.
