# PALM Foundations & Tokens

PALM tokens are authored and maintained as the source of truth for PALM.

Token hierarchy:

1. **Primitive** — raw scales such as neutral colors, spacing, radii, typography and motion.
2. **Semantic** — intent such as background, surface, text, border, action, success, warning and danger.
3. **Component** — local component decisions only when semantic tokens are insufficient.

Tokens are CSS custom properties and should be consumed rather than hard-coded inside components.
