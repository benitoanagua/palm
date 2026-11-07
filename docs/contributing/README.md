# Contributing to PALM

Define PALM contracts first, implement with Stencil, document in React Cosmos, then add contract and accessibility tests.

- See [Component Workflow](./component-workflow.md) for the process for new and updated components.

## Required quality gate

Before opening or merging a pull request, run:

```sh
pnpm install --frozen-lockfile
pnpm check
```

The quality gate is browser-free. It covers repository rules, dependency consistency, documentation links, formatting, linting, typechecking, unit tests, package builds, and the Cosmos static export.

## Optional dependency and dead-code audits

Knip and depcheck are diagnostic tools, not CI blockers. Run them with `pnpm dlx` when changing package boundaries, exports or dependencies:

```sh
pnpm dlx depcheck@1.4.7 --json
```

Review findings manually before removing files or dependencies; workspace exports and Stencil metadata can be detected as false positives.

## Browser checks

`pnpm test:e2e` runs Playwright contract and accessibility checks against the Cosmos static export and requires Chromium. In WSL, the developer-local Playwright config can point at the system Chromium; do not commit that local config.
