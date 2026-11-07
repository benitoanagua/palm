# ADR-010 — Hardening, Tooling Exceptions, and Quality Gate Boundaries

**Estado:** aceptado  
**Fecha:** 2026-10-29

## Contexto

El monorepo PALM integra componentes web con Stencil, adaptadores para React, Vue y Angular, Storybook, Playwright y herramientas de análisis estático. Para mantener el sistema predecible y evitar fallos espurios en CI o durante la compilación, se requería formalizar las decisiones arquitectónicas sobre dependencias de desarrollo, límites de ejecución y cumplimiento de accesibilidad.

## Decisiones

### 1. Excepciones de dependencias en tooling

- **`jest` y `jest-cli` en `@palm/components`**: Stencil compila y ejecuta las pruebas de especificación (`stencil test --spec`) utilizando el ejecutor interno de Jest. Aunque herramientas de análisis estático (como Knip o depcheck) marquen estas dependencias como no importadas directamente en código fuente, son obligatorias para la estabilidad del runner en entornos pnpm.
- **`@tabler/icons` en `@palm/icons`**: Es consumido por el script de compilación `tooling/generate-tabler-data.mjs` invocado en `prebuild` y `pretypecheck`. Los datos generados no se versionan en Git.

### 2. Compuertas de calidad y ejecución desacoplada

- **`pnpm check` (Compuerta canónica sin navegador)**: Es la compuerta obligatoria en pre-commit y CI. Incluye validadores de arquitectura, consistencia de dependencias, enlaces documentales, cobertura de matriz de componentes, formateo Prettier, linting, verificación de tipos (TypeScript/Stencil), tests unitarios de Stencil, compilación de paquetes y compilación estática de Storybook.
- **`pnpm test:e2e` (Pruebas de navegador aisladas)**: Pruebas de contratos (`test:contracts`) y auditoría de accesibilidad (`test:a11y`) mediante Playwright. Requieren un entorno de navegador (Chromium).
- **Herramientas de diagnóstico (`knip`, `depcheck`)**: Herramientas manuales para auditorías periódicas de dependencias y código huérfano. No bloquean el CI para evitar falsos positivos derivados de convenciones de Stencil y workspaces.

### 3. Accesibilidad y foco (WCAG 2.4.7)

- Queda prohibido el uso de `outline: none` en estados de foco sin una alternativa que funcione en modos de alto contraste.
- Todo elemento interactivo debe implementar un contorno visible (`outline`) con `--pm-focus-offset` y soporte explícito para `@media (forced-colors: active) { outline: 2px solid Highlight; }`.

### 4. Reglas de diseño y tokens

- Todos los radios de borde en CSS de componentes deben provenir de tokens `--pm-radius-*` (con excepción de formas circulares al `50%` y cálculos estructurales relativos `calc()`).
- Quedan prohibidos los valores de color hexadecimal hardcodeados en las hojas de estilo de `packages/`. Deben consumirse tokens semánticos `--pm-color-*`.

### 5. Baselines visuales

- Las capturas y baselines visuales de regresión no se versionan en Git para evitar diferencias dependientes de la GPU o del sistema operativo.
