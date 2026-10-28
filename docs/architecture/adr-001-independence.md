# ADR-001: PALM Independence

## Status

Accepted.

## Context

PALM needs an explicit boundary around its design-system ownership so its public contracts remain stable and independently maintainable.

## Decision

PALM is an independently designed design system. It owns its visual language, design tokens, component behavior, public APIs, and implementation choices.

## Consequences

- PALM code and tokens are authoritative for PALM.
- Component behavior is defined by PALM's own contracts and tests.
- Stencil is the component runtime.
- The system remains framework-agnostic through Web Components.
