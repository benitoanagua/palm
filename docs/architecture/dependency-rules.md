# Dependency Rules

1. `@palm/tokens` has no runtime package dependencies.
2. `@palm/primitives` may consume tokens.
3. `@palm/components` may consume tokens, primitives, and icons.
4. No PALM package may depend on application/domain code.
5. Lit is forbidden. React/Vue/Angular are not runtime dependencies of core packages.
6. Consumers must not depend on internal source paths.

## Tooling and build-time exceptions

- `@palm/icons` declares `@tabler/icons` in `devDependencies` to supply data for `tooling/generate-tabler-data.mjs` during package prebuild.
- Diagnostic tools (`knip`, `depcheck`) are run manually with `pnpm dlx`, not installed as dependencies.
