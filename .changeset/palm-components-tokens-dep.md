---
'@palm/components': patch
---

Declare `@palm/tokens` as a dependency: the components' CSS references the
`--pm-*` design-token variables, so `@palm/tokens` must be installed
alongside `@palm/components`.
