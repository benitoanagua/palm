# ADR-002: Icon providers by platform

**Estado:** aceptado  
**Fecha:** 2026-10-28

## Contexto

PALM is a framework-agnostic Web Component design system. The Android application has native platform requirements that should not introduce a second icon provider into the web component library.

## Decisión

- Web components use Tabler Icons through `@palm/icons` and `pm-icon`.
- The Android application uses Material Symbols through native Android mechanisms.
- Icon data derived from the installed `@tabler/icons` package is generated locally by `tooling/generate-tabler-data.mjs`; generated JSON and image assets are not committed.
- No icon provider is published or deployed from CI.

## Alternativas

- One provider across web and Android: rejected because it weakens native Android integration.
- Multiple icon providers in PALM web packages: rejected to keep the public API and bundle behavior consistent.

## Criterio y evidencia

The root README documents Tabler as the web provider and the repository's Android boundary remains outside `@palm/icons`. The package manifest declares `@tabler/icons` as the source of generated data.

## Reversión

Revert the commit that introduces this ADR and any paired implementation changes; no data migration is required.
