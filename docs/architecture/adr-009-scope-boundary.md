# ADR-009: Web design-system scope and mobile-app boundary

**Estado:** aceptado  
**Fecha:** 2026-10-28

## Contexto

The normative Palm design-system specification includes reusable interface patterns as well as platform-specific application behavior. The library must not absorb persistence or business workflows that belong to a consuming application.

## Decisión

- **Palm web component/composition, backlog:** Omnibar UI, floating action button, task card, critical confirmation modal, scopes and batch action bar, filter panel, and detail view.
- **Palm web component, existing behavior to verify:** sync status tag and flash/toast.
- **Stateless helper, only when a concrete consumer exists:** parser and tabular amount formatting.
- **Android application guide, outside the web component library:** Room/DataStore persistence, SavedStateHandle and offline persistence, and native Android gestures.

The closure must not implement the Omnibar, FAB, task cards, Hub, synchronization workflows, density system, or critical modal. Those remain explicit backlog items rather than implicit promises of the component library.

## Alternativas

- Implement every specification pattern in the web library immediately: rejected because it would mix application behavior with reusable UI and expand this closure beyond hardening.
- Leave ownership implicit: rejected because it encourages business and persistence logic to leak into the component packages.

## Criterio y evidencia

The canonical specification is `palm-design-system.md`; component APIs live in `packages/components`. Android persistence and lifecycle patterns are not Web Component responsibilities.

## Reversión

Revert this ADR if the product boundary changes; no runtime behavior is changed by this decision alone.
