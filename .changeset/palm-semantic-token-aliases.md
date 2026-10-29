---
'@palm/tokens': minor
---

Add semantic color aliases, component-scoped tokens and WCAG 2.4.7 focus tokens.

**Added**

- Semantic color aliases: `--pm-color-pending`, `--pm-color-confirmed`, `--pm-color-conflict`,
  `--pm-color-on-color`, `--pm-color-header-bg`, `--pm-color-header-text`,
  `--pm-color-header-text-dim`, `--pm-color-border-light`, `--pm-color-action-contrast`,
  `--pm-color-danger-contrast`, `--pm-color-success-contrast`, `--pm-color-warning-contrast`
- Focus tokens: `--pm-focus-offset`
- Button component tokens: `--pm-button-border`, `--pm-button-bg-gradient-start`,
  `--pm-button-bg-gradient-end`, `--pm-button-hover-bg-gradient-start`,
  `--pm-button-hover-bg-gradient-end`, `--pm-button-active-bg`
- Shadow component tokens: `--pm-shadow-button-inset`, `--pm-shadow-switch-handle`,
  `--pm-shadow-field-inset`
- Dark theme overrides for all new semantic aliases

**Removed** (breaking — consumers using these primitive tokens must update)

- `--pm-color-neutral-200` (unused primitive; no CSS consumers found)
- `--pm-radius-xl` (unused alias of `--pm-radius-base`; no CSS consumers found)
