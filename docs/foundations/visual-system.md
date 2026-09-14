# Palm Visual System

Palm is designed independently from STRATA. Its look is a deliberate clone of ActiveAdmin (Ruby on Rails) — not a generic modern flat theme — because the target user is the same one ActiveAdmin was built for: someone doing dense, repetitive data work, who values legibility and predictability over decoration.

## Principles

- Mobile-first and touch-friendly.
- **Clone ActiveAdmin's visual vocabulary faithfully**: dark header with a color accent, glossy gradient buttons (`linear-gradient` + inset highlight + text-shadow — this is not skeuomorphism for its own sake, it is literally what the referenced system looks like), solid-color `status_tag`s (never a soft tint — color communicates state at a glance, text confirms it), dense bordered tables, a single small border-radius (no Material-style radius scale).
- **The one deliberate deviation from a literal clone**: the color palette and border contrast values are Palm's own, verified for accessibility, not ActiveAdmin's historical defaults. ActiveAdmin's real border gray (~1.2:1 against white) is invisible to low-vision users; Palm's borders measure 4.8:1 and 3.0:1 against white (WCAG 1.4.11). ActiveAdmin's historical blue was never simulated for colorblindness; Palm's four semantic colors were, against all three common forms (protanopia, deuteranopia, tritanopia), and reach ≥7:1 (AAA) contrast with white text on top. Fidelity to a reference never overrides accessibility evidence already built — that trade only ever goes one way.
- Semantic contrast over decorative color: every status is a `status_tag` (solid fill + white text), never color alone.
- Consistent control heights and spacing.
- Focus is always visible for keyboard users.
- Motion communicates state; it does not decorate unnecessarily.
- `prefers-reduced-motion` disables transition durations.
- Light and dark themes use the same semantic token names.
- Components consume tokens instead of hard-coded visual values.

## Density

Density is a first-class, user-controlled setting (Compact / Normal / Airy), not a layout breakpoint. All three levels share the exact same visual vocabulary above — only spacing, type size and line-height scale between them. See the full spec's §2.6/§4.6 for the concrete measurements per level.

## Responsive strategy

Prefer container-aware composition and fluid sizing. Use viewport breakpoints only for layout decisions that truly depend on the viewport.

## States

Interactive components must define default, hover, active, focus-visible, disabled and loading/error states where applicable. Hover/active on buttons darken the gradient (matching the reference system); they do not lift with a modern card-style `translateY`.

## Touch

Interactive targets should normally provide at least a 44px effective hit area even when their visual glyph is smaller.

## Reference demos

Five approved HTML demos live at [`../visual/demos/`](../visual/demos/) and are kept unchanged as delivered — they are the visual reference this system implements, not a living spec. See [`../../palm-design-system.md`](../../palm-design-system.md) §4 for what each one covers.
