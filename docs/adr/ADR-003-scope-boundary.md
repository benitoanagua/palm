# ADR-003: Web design-system scope and mobile-app boundary

**Estado:** aceptado  
**Fecha:** 2026-10-28

## Contexto

The normative Palm design-system specification includes reusable interface patterns as well as platform-specific application behavior. The library must not absorb persistence or business workflows that belong to a consuming application.

## Decisión

Classify patterns by ownership:

| Pattern or capability | Ownership | Scope |
| --- | --- | --- |
| Omnibar UI | Palm web component | Backlog; not implemented in this closure |
| Floating action button | Palm web component | Backlog; not implemented in this closure |
| Task card | Palm web component/composition | Backlog; not implemented in this closure |
| Sync status tag | Palm web component | Existing status component alignment and contract review |
| Critical confirmation modal | Palm web component/composition | Backlog; not implemented in this closure |
| Scopes and batch action bar | Palm web components/composition | Backlog; not implemented in this closure |
| Filter panel and detail view | Palm web composition | Backlog; not implemented in this closure |
| Flash/toast | Palm web component | Existing toast primitives; verify behavior against spec |
| Parser and tabular amount formatting | Stateless helper | Separate helper package only when a concrete consumer exists |
| Room/DataStore persistence | Android application guide | Outside the web component library |
| SavedStateHandle and offline persistence | Android application guide | Outside the web component library |
| Native Android gestures | Android application guide | Outside the web component library |

## Alternativas

- Implement every specification pattern in the web library immediately: rejected because it would mix application behavior with reusable UI and expand this closure beyond hardening.
- Leave ownership implicit: rejected because it encourages business and persistence logic to leak into the component packages.

## Criterio y evidencia

The repository's canonical specification is `palm-design-system.md`; component APIs live in `packages/components`. The Android persistence and lifecycle patterns are not Web Component responsibilities.

## Reversión

Revert this ADR if the product boundary changes; no runtime behavior is changed by this decision alone.
