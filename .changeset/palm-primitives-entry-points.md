---
'@palm/primitives': patch
---

Declare `@palm/tokens` as a dependency (primitives' CSS references
`--pm-*` token variables) and complete the `exports` entry points with
`require` and `default` conditions so CJS and ESM consumers resolve
correctly.
