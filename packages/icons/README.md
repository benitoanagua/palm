# @palm/icons

PALM uses **Tabler Icons exclusively**.

The public API is intentionally small. Components consume `pm-icon` with a Tabler icon name. No provider abstraction, aliases, fallback collections, or alternate icon families are supported.

```html
<pm-icon name="search"></pm-icon>
```

The current registry contains only the Tabler icons required by PALM. New icons must be sourced from Tabler and added with their official Tabler SVG path data.
