# Dependency Rules

1. `@palm/tokens` has no runtime package dependencies.
2. `@palm/foundation` may consume tokens, but never components.
3. `@palm/primitives` may consume tokens and foundation.
4. `@palm/components` may consume tokens, foundation, primitives, and icons.
5. 6. No PALM package may depend on application/domain code.
6. Lit is forbidden. React/Vue/Angular are not runtime dependencies of core packages.
7. Consumers must not depend on internal source paths.
