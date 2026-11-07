# Contributing to PALM

Define PALM contracts first, implement with Stencil, document in Storybook, then add unit, accessibility, and visual coverage.

- See [Component Workflow](./component-workflow.md) for the 10-step process for new and updated components.

## Required quality gate

Before opening or merging a pull request, run:

```sh
pnpm install --frozen-lockfile
pnpm check
```

The quality gate is browser-free. It covers repository rules, dependency consistency, documentation links, the component matrix, formatting, linting, typechecking, unit tests, package builds and the Storybook production build.

## Optional dependency and dead-code audits

Knip and depcheck are diagnostic tools, not CI blockers. Run them when changing package boundaries, exports or dependencies:

```sh
pnpm dlx --package=knip@5.64.0 --package=typescript@5.9.3 knip
```

For depcheck, run it from each workspace package directory that has a `package.json`:

```sh
pnpm dlx depcheck@1.4.7 --json
```

Review findings manually before removing files or dependencies; workspace exports, Stencil metadata, Storybook stories and test consumers can be detected as false positives. Documented exceptions are defined in [ADR-010](../architecture/adr-010-hardening-and-structural-boundaries.md).

## Browser checks

`pnpm test:e2e` runs Playwright contract and accessibility checks and requires Chromium. In WSL, the developer-local Playwright config can point at the system Chromium; do not commit that local config. Visual tests and screenshot baselines are intentionally excluded from CI and are not versioned.
