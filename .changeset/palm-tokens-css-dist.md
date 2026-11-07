---
'@palm/tokens': patch
---

Ship token CSS files (`tokens.css`, `themes.css`, `type-scale.css`) in
`dist/` and point the `exports` entries at `dist/` (single source of truth;
see `docs/integrations/angular.md`). Align `palmTokens.radii` with the
removed `--pm-radius-xl` token (no consumer reads it).
