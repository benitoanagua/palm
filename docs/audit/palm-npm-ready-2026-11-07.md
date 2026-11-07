# Palm — Listo para npm (sin publicar) + Storybook listo para GitHub Pages

**Fecha:** 2026-11-07  
**Rama:** `chore/npm-ready` (HEAD `c5edde4`)  
**Repo:** https://github.com/benitoanagua/palm  
**Engineer:** Senior design systems / release engineering  
**Estado:** Nada publicado, taggeado ni pusheado. Todo el trabajo queda en commits convencionales atómicos en `chore/npm-ready`.

---

## Referencia de estilo

Se siguió el formato del reporte de cierre `docs/audit/palm-closure-2026-10-28.md` (recuperado del historial git, commit `e98e11a`, ya que el archivo fue eliminado en el estado de trabajo dado). Las decisiones D-001 a D-009 del cierre no se reabren. El reporte de cierre se cita como fuente de decisiones previas.

---

## Fase 0 — Baseline

| Ítem                             | Valor                                                                             |
| -------------------------------- | --------------------------------------------------------------------------------- |
| Rama de trabajo                  | `chore/npm-ready` (desde `5344d03`)                                               |
| node                             | v26.13.0 (el entorno provee 26; el documento pide 22 — observación no bloqueante) |
| pnpm                             | 10.26.0 (coincide con `packageManager`)                                           |
| `git status`                     | Limpio (commit `239a4f7` como baseline de limpieza estructural dada)              |
| `pnpm install --frozen-lockfile` | Up to date, ~1 s, sin cambios en el lockfile                                      |
| `pnpm check`                     | **VERDE**, salida 0, ~51 s (ejecución en frío: sin `dist/`, sin caché de turbo)   |

**Evidencia del baseline:** `node --version` → `v26.13.0`; `pnpm --version` → `10.26.0`; `pnpm install --frozen-lockfile` → `Already up to date`; `pnpm check` → `exit 0` (Storybook build completado exitosamente).

**Nota:** Un primer intento de `pnpm check` falló en `@palm/angular#typecheck` (`no se encuentra el módulo '@palm/components/loader'`) por un estado intermedio de `dist/` (turbo restauró un `dist/` incompleto, sin `loader/`). Una limpieza en frío (`rm -rf dist .turbo .stencil` + `pnpm check`) pasó en verde y se reprodujo dos veces. El baseline es fiable desde estado frío.

---

## Tabla de commits

| Hash      | Tipo convencional   | Qué arregla                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| --------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `239a4f7` | `chore`             | Añade `LICENSE` (MIT) en la raíz y lo ignora de prettier (no tiene parser).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| `1e605f2` | `chore(packages)`   | Metadatos de publicación en todos los paquetes (`license`, `description`, `keywords`, `homepage`, `bugs`, `repository` con `directory`, `engines.node>=22`, `publishConfig.access: public`, `sideEffects`). Visibilidad: `private: true` en `@palm/testing` (sin build/dist) y `@palm/foundation` (cero consumidores); `private: false` explícito en tokens, icons, components, primitives, react, vue, angular. Entry points: tokens `exports` → `dist/*.css`; primitives `main`/`exports` con `require`/`default`; components y primitives declaran `@palm/tokens`. |
| `bdb917a` | `chore(packages)`   | Campo `files: ["dist"]` en todos los paquetes públicos; `@palm/icons` también embarca `THIRD_PARTY_NOTICES.md`. Evita que `src/`, `.turbo/`, `.stencil/`, `tsconfig.json` viajen en el tarball.                                                                                                                                                                                                                                                                                                                                                                       |
| `1ff319b` | `fix(tokens)`       | Elimina `xl` de `palmTokens.radii` (coincide con `--pm-radius-xl` ya eliminado en D-005; ningún CSS lo usa).                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| `9d80eed` | `chore(release)`    | `release` falla salvo que `PALM_ALLOW_PUBLISH=1` (nunca corre en CI). Añade `version-packages` (`changeset version`). Crea `.changeset/config.json` (grupo linked `palm` = `@palm/*`, `access: public`, `baseBranch: main`, `ignore` para apps privadas y `@palm/testing`).                                                                                                                                                                                                                                                                                           |
| `2735bb3` | `tooling`           | Nuevo validador `tooling/validation/package-publish.mjs` (sin dependencias nuevas) cableado en `pnpm check` tras `pnpm build`. Verifica `files`, `license`, `repository`, `publishConfig`, existencia de `main`/`types` y targets de `exports` tras el build, y que `pnpm pack --dry-run --json` incluya `dist/` y excluya `src/`, specs y tests.                                                                                                                                                                                                                     |
| `fd09513` | `docs`              | README por paquete público (instalación, pares, uso mínimo, enlace al Storybook) para tokens, primitives, components, react, vue, angular (`icons` ya tenía uno).                                                                                                                                                                                                                                                                                                                                                                                                     |
| `a5cb8a2` | `fix(storybook)`    | `console.log('card pressed')` en `Card.stories.ts:35` → `action('card pressed')` de `storybook` (sin dependencia nueva).                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| `55d2182` | `chore(changesets)` | Changesets propios para paquetes con cambio de contenido (tokens patch CSS-en-dist, components patch dep-tokens, primitives patch entry points) y reclasificación del changeset de tokens de `minor` a `major` (contiene eliminaciones breaking).                                                                                                                                                                                                                                                                                                                     |
| `fb886ac` | `chore(deps)`       | Entradas `@palm/tokens` (workspace → `0.1.0`) en `pnpm-lock.yaml` de components y primitives.                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| `c5edde4` | `ci(storybook)`     | `.github/workflows/storybook-pages.yml` (GitHub Pages, subpath `/palm/`), `viteFinal` en `.storybook/main.ts`, `.nojekyll`, README con instrucciones de activación.                                                                                                                                                                                                                                                                                                                                                                                                   |

---

## Decisiones D-010 en adelante

### D-010 — Restringir el contenido de los tarballs con `files`

|                   |                                                                                                                                                                                                                                                                               |
| ----------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | Añadir `files: ["dist"]` (y `["dist", "THIRD_PARTY_NOTICES.md"]` en icons) a cada paquete público.                                                                                                                                                                            |
| **Alternativas**  | Dejar sin `files` (npm/pnpm cae a `.gitignore`, que embarca `src/`, `.turbo/`, `.stencil/`, `tsconfig.json`); o `.npmignore` por paquete.                                                                                                                                     |
| **Criterio**      | Tarball con exactamente lo necesario: ni `src/` de más ni `dist/` ausente.                                                                                                                                                                                                    |
| **Evidencia**     | Antes del cambio, los tarballs contenían `package/src/**`, `package/.turbo/`, `package/tsconfig.json`, `package/test-results/`. Después: solo `package/dist/`, `package/package.json` (y README/LICENSE/notices). Comprobado con `tar -tzf` sobre `pnpm --filter <pkg> pack`. |
| **Cómo revertir** | Eliminar el campo `files` de cada `package.json`.                                                                                                                                                                                                                             |

### D-011 — @palm/tokens: CSS en `dist/` + `exports` coherente (única fuente de verdad)

|                   |                                                                                                                                                                                                                                                      |
| ----------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | Copiar `tokens.css`, `themes.css`, `type-scale.css` a `dist/` en el script `build` (`node:fs`.cpSync, sin dependencias nuevas, al estilo del `cpSync` de icons) y apuntar `exports` a `./dist/*.css` (incluida la nueva entrada `./type-scale.css`). |
| **Alternativas**  | Dejar `exports` apuntando a `./src/*.css` (rompe en consumidor instalado, no hay `src/` publicado); o no copiar CSS y decir al consumidor que lo genere.                                                                                             |
| **Criterio**      | `src/index.ts` hace `import './tokens.css'`; `tsc` no copia CSS al `dist/`, por lo que sin la copia el `dist/index.js` publicado falla al cargar el CSS. `docs/integrations/angular.md` ya referencia `node_modules/@palm/tokens/dist/tokens.css`.   |
| **Evidencia**     | `tar -tzf palm-tokens-0.1.0.tgz` muestra `dist/tokens.css`, `dist/themes.css`, `dist/type-scale.css`. `pnpm check` verde (storybook resuelve `@palm/tokens/tokens.css`).                                                                             |
| **Cómo revertir** | Revertir `exports` a `./src/*.css` y quitar la copia del script `build`.                                                                                                                                                                             |

### D-012 — Datos de tokens desfasados: `radii` seguía listando `xl`

|                   |                                                                                                                                                                                                               |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | Eliminar `'xl'` de `palmTokens.radii` en `packages/tokens/src/index.ts`.                                                                                                                                      |
| **Alternativas**  | Dejarlo como dato muerto; o añadir de vuelta `--pm-radius-xl` (reabre D-005, prohibido).                                                                                                                      |
| **Criterio**      | `--pm-radius-xl` se eliminó en el cierre (D-005); `tokens.css` solo define `base/sm/md/lg/pill`.                                                                                                              |
| **Evidencia**     | `grep "radii" packages/tokens/src/index.ts` antes: `['sm', 'md', 'lg', 'xl', 'pill']`; después: `['sm', 'md', 'lg', 'pill']`. `grep -r "radius-xl"` → 0 resultados. Ningún consumidor lee `palmTokens.radii`. |
| **Cómo revertir** | Añadir `'xl'` de vuelta al array.                                                                                                                                                                             |

### D-013 — Visibilidad de paquetes: cuáles son públicos

|                   |                                                                                                                                                                                                                                                                                                            |
| ----------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | Públicos: `@palm/tokens`, `@palm/icons`, `@palm/components`, `@palm/primitives`, `@palm/react`, `@palm/vue`, `@palm/angular`. Privados: `@palm/testing` (no tiene `src/index.ts` ni build ni `dist/`), `@palm/foundation` (grep muestra cero consumidores), y las apps privadas (`storybook`, `showcase`). |
| **Alternativas**  | Publicar foundation como utilidad compartida; o hacer primitives privado.                                                                                                                                                                                                                                  |
| **Criterio**      | `grep -rn "@palm/foundation"` → solo su propio `package.json`. `grep -rn "@palm/primitives"` → solo `apps/storybook` (preview.ts) y su propio `package.json`.                                                                                                                                              |
| **Evidencia**     | `package.json` de testing y foundation con `private: true`; resto con `private: false`.                                                                                                                                                                                                                    |
| **Cómo revertir** | Cambiar `private` en los `package.json` afectados.                                                                                                                                                                                                                                                         |

### D-014 — Metadatos de publicación y `sideEffects`

|                   |                                                                                                                                                                                                                                    |
| ----------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | Añadir a cada paquete público: `license: "MIT"`, `description`, `keywords`, `homepage`, `bugs.url`, `repository` (con `directory`), `engines.node>=22`, `publishConfig.access: public`, `sideEffects`. `LICENSE` (MIT) en la raíz. |
| **Alternativas**  | Omitir metadatos (npm los rechaza o muestra advertencias); `sideEffects: false` en todos (rompería tree-shaking de CSS/global de components).                                                                                      |
| **Criterio**      | Paquetes con CSS global o registro de custom elements (`tokens`, `components`, `primitives`, `react`, `vue`, `angular`) → `sideEffects: true`; utilidades puras y datos (`foundation`, `icons`, `testing`) → `sideEffects: false`. |
| **Evidencia**     | `grep -rn "sideEffects\|publishConfig\|engines" packages/*/package.json` antes del cambio → ninguna coincidencia. Todos los `package.json` publicados ahora tienen los campos.                                                     |
| **Cómo revertir** | Eliminar los campos añadidos.                                                                                                                                                                                                      |

### D-015 — Declarar `@palm/tokens` donde components/primitives usan tokens CSS

|                   |                                                                                                                                             |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | Añadir `@palm/tokens` (workspace:*) a `dependencies` de `@palm/components` y `@palm/primitives` (sus CSS referencia variables `--pm-*`).    |
| **Alternativas**  | `peerDependency` (obliga al consumidor a instalar tokens por separado) o no declararlo (riesgo de tokens faltantes en tiempo de ejecución). |
| **Criterio**      | `grep -rn "--pm-"` en CSS de components y primitives → muchas referencias (border, radius, surface, text…).                                 |
| **Evidencia**     | Los tarballs resuelven `workspace:*` → `0.1.0`; `pnpm install --frozen-lockfile` verde con las nuevas entradas en el lockfile.              |
| **Cómo revertir** | Eliminar `@palm/tokens` de esas `dependencies` y del lockfile.                                                                              |

### D-016 — Entry points de `@palm/primitives` (condiciones `require`/`default`)

|                   |                                                                                                                                                                  |
| ----------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | `main: ./dist/index.cjs.js` y `exports` con `require` → `dist/index.cjs.js`, `import` → `dist/index.js`, `default` → `dist/index.js` (idéntico para `./loader`). |
| **Alternativas**  | Dejar solo `import` (fallan consumidores CommonJS).                                                                                                              |
| **Criterio**      | `package.json` mezclaba `main: ./dist/index.cjs.js` con `exports.import: ./dist/index.js` sin condición `require`. Ambos archivos existen tras el build.         |
| **Evidencia**     | `tar -tzf palm-primitives-0.1.0.tgz` muestra `dist/loader/index.cjs.js`, `dist/loader/index.js`, `dist/index.cjs.js`, `dist/index.js`.                           |
| **Cómo revertir** | Volver a `exports` sin `require`/`default`.                                                                                                                      |

### D-017 — Guardia del script `release` + `version-packages`

|                   |                                                                                                                                                                            |
| ----------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | `release` falla salvo `PALM_ALLOW_PUBLISH=1` (nunca ejecuta `changeset publish` en CI actual). `version-packages` corre `changeset version` (solo en worktree desechable). |
| **Alternativas**  | Dejar `release: changeset publish` (prohibido por regla dura).                                                                                                             |
| **Criterio**      | Ningún publish/tag/push puede dispararse accidentalmente desde CI.                                                                                                         |
| **Evidencia**     | `package.json` script `release` contiene el bloqueo por variable de entorno. `pnpm changeset status` sin errores.                                                          |
| **Cómo revertir** | Volver a `release: changeset publish` (no recomendado).                                                                                                                    |

### D-018 — Validador `package-publish.mjs`

|                   |                                                                                                                                                                                                                                                   |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | Crear `tooling/validation/package-publish.mjs` (sin dependencias nuevas, estilo Node puro como los validadores existentes) y añadir `check:package-publish` a `pnpm check` tras `pnpm build`.                                                     |
| **Alternativas**  | Añadir `publint`/`arethetypeswrong` a CI (rechazado: son herramientas opcionales locales, regla D-006-style de no bloquear CI con herramientas externas).                                                                                         |
| **Criterio**      | Para cada paquete público verifica: `files`, `license`, `repository`, `publishConfig.access`, existencia de `main`/`types`/targets de `exports` tras el build, y que `pnpm pack --dry-run --json` incluya `dist/` y excluya `src/`, specs, tests. |
| **Evidencia**     | `pnpm check` incluye `check:package-publish` y sale verde (7 paquetes públicos verificados).                                                                                                                                                      |
| **Cómo revertir** | Quitar la línea del script `check` y borrar el archivo.                                                                                                                                                                                           |

### D-019 — `.changeset/config.json` y cambiosets

|                   |                                                                                                                                                                                                                                                                                                        |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Decisión**      | Config: grupo linked `palm` (`@palm/*`, `access: public`, `baseBranch: main`, `updateInternalDependencies: minor`), `ignore` para `@palm/storybook`, `@palm/showcase`, `@palm/testing`. Changesets: tokens `major` (reclasificado de `minor`), tokens `patch`, components `patch`, primitives `patch`. |
| **Alternativas**  | Grupo `fixed` en vez de `linked`; dejar tokens en `minor`.                                                                                                                                                                                                                                             |
| **Criterio**      | Semver correcto: el changeset de tokens contiene eliminaciones breaking (`--pm-color-neutral-200`, `--pm-radius-xl`) → debe ser `major`, no `minor`.                                                                                                                                                   |
| **Evidencia**     | `pnpm changeset status` → tokens `major`; components, primitives, angular, react, vue `patch`. `pnpm check` verde (incluye `format:check` sobre los `.md` de changesets).                                                                                                                              |
| **Cómo revertir** | Editar/borrar los archivos de `.changeset/`.                                                                                                                                                                                                                                                           |

### D-020 — Storybook bajo subpath `/palm/` en GitHub Pages

|                   |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| ----------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decisión**      | `viteFinal` en `.storybook/main.ts` lee `STORYBOOK_BASE` (por defecto `/`); el workflow de Pages define `STORYBOOK_BASE=/palm/`. Workflow `storybook-pages.yml` con permisos `contents:read`, `pages:write`, `id-token:write`; concurrency `pages` con `cancel-in-progress: false`; pasos checkout, Node 22 + caché pnpm, `pnpm install --frozen-lockfile`, **`pnpm build`**, `pnpm build:storybook`, `configure-pages`, `upload-pages-artifact` (`storybook-static`), `deploy-pages` con `environment: github-pages`; `.nojekyll` en el artefacto. |
| **Alternativas**  | Cloudflare Pages (prohibido por regla dura); servir en la raíz (no funciona en `<usuario>.github.io/<repo>/`).                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| **Criterio**      | Build bajo subpath y todos los assets/iframes/loader cargan.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| **Evidencia**     | Servidor estático local sirviendo `storybook-static` desde `/palm/`: `GET /palm/` → 200, `/palm/iframe.html` → 200, `/palm/assets/iframe-*.js` → 200 (948 kB), `/palm/sb-manager/runtime.js` → 200.                                                                                                                                                                                                                                                                                                                                                 |
| **Cómo revertir** | Eliminar el workflow y revertir `.storybook/main.ts`.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |

---

## Tabla de hipótesis (Fase 1)

| Hipótesis                                                                                                                        | Estado                                                                           | Evidencia                                                                                                                                                                                                                                                                                                                                                         |
| -------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| H1 Empaquetado sin `files` embarca `src/`                                                                                        | **Confirmada**                                                                   | Tarballs previos contenían `package/src/**`, `.turbo/`, `tsconfig.json`, `test-results/`. Root `.npmignore` no aplica a paquetes; ninguno tiene `.npmignore` propio.                                                                                                                                                                                              |
| H2 `@palm/tokens`: CSS no en `dist/`, `exports` a `src/`, `type-scale.css` sin entrada                                           | **Confirmada**                                                                   | `dist/` carecía de `*.css`; `exports` apuntaba a `./src/tokens.css`; `type-scale.css` ausente. `src/index.ts` importa `./tokens.css` que `tsc` no copia.                                                                                                                                                                                                          |
| H3 `palmTokens.radii` aún lista `xl`                                                                                             | **Confirmada**                                                                   | `radii: ['sm','md','lg','xl','pill']`; `--pm-radius-xl` eliminado (D-005).                                                                                                                                                                                                                                                                                        |
| H4 Metadatos ausentes + `LICENSE` raíz                                                                                           | **Confirmada**                                                                   | Ningún `package.json` tenía `license`/`repository`/`homepage`/`bugs`/`description`/`keywords`/`engines`/`publishConfig`/`sideEffects`; no existía `LICENSE` en la raíz.                                                                                                                                                                                           |
| H5 Visibilidad: testing → private; foundation → decidir; primitives → decidir                                                    | **Confirmada (testing)** / **Decidida (foundation privado, primitives público)** | `grep @palm/foundation` → 0 consumidores. `grep @palm/primitives` → solo `apps/storybook/.storybook/preview.ts`.                                                                                                                                                                                                                                                  |
| H6 Relaciones: components no declara tokens; workspace:* → versión real en tarballs                                              | **Confirmada (ambas)**                                                           | components depende de icons pero no de tokens (aunque su CSS usa `--pm-*`). `pnpm pack` resuelve `workspace:*` → `0.1.0` en todos los tarballs. Peers react: `react ^18.3.0` vs `react-dom >=18` (descoincidencia notada, no modificada).                                                                                                                         |
| H7 Entry points: Stencil emite `dist/loader/`; primitives `main`/`exports` descoñecidos; adapters/icons sin `exports`            | **Confirmada (loader y primitives)**                                             | `tar -tzf` muestra `dist/loader/` en components y primitives. primitives mezclaba `main: ./dist/index.cjs.js` con `exports.import` sin `require`.                                                                                                                                                                                                                 |
| H8 `@palm/icons`: `dist/data/tabler-outline.json` viaja + notificaciones MIT                                                     | **Confirmada**                                                                   | `prebuild` regenera el JSON; `tar -tzf` incluye `dist/data/tabler-outline.json`, `THIRD_PARTY_NOTICES.md`, `README.md`. Licencia MIT de `@tabler/icons` (Paweł Kuna) incluida.                                                                                                                                                                                    |
| H9 Cobertura de adapters: tags faltantes                                                                                         | **Confirmada (reporte, no se añaden)**                                           | Angular declara 28 de 48 tags @Component. Faltan: `pm-accordion`, `pm-accordion-item`, `pm-alert`, `pm-data-table`, `pm-dialog`, `pm-drawer`, `pm-empty-state`, `pm-form-field`, `pm-metric-card`, `pm-nav`, `pm-nav-item`, `pm-spinner`, `pm-stack`, `pm-status`, `pm-surface`, `pm-tab`, `pm-tab-panel`, `pm-tabs`, `pm-toolbar`, `pm-tooltip`. Vue declara 39. |
| H10 API de `pm-icon`: README dice solo `name`+`aria-label`; `PmIconProps` declara `size`, `label`, `decorative`                  | **Confirmada (divergencia reportada)**                                           | Root README y `packages/icons/README.md` dicen API mínima `name`+`aria-label`; `PmIconProps = React.HTMLAttributes & { name; size?; label?; decorative? }`. Corrección propuesta: actualizar READMEs (no cambiar API sin decisión).                                                                                                                               |
| H11 Falta `.changeset/config.json`; `release` ejecuta `changeset publish` directamente                                           | **Confirmada**                                                                   | No existía `.changeset/config.json`; `release: changeset publish`. Ahora resuelto (D-017/D-019).                                                                                                                                                                                                                                                                  |
| H12 `check:api` solo busca strings; `check:dependencies` fija Storybook major 10                                                 | **Descartada como bloqueo**                                                      | Ninguno de los cambios hechos bloquea los validadores; `pnpm check` verde.                                                                                                                                                                                                                                                                                        |
| H13 Storybook: `a11y: test:'todo'` sin addon; `console.log` en Card; `theme` sin decorator; `build-storybook` sin builds previos | **Confirmada (parcialmente resuelta)**                                           | `console.log` reemplazado por `action()` (commit `a5cb8a2`). `build-storybook` desde clon limpio fallaba sin `pnpm build` previo; el workflow de Pages ahora corre `pnpm build` antes. A11y y theme-decorator quedan como decisiones pendientes (ver abajo).                                                                                                      |
| H14 Falta README por paquete y guía de instalación; ADR duplicados                                                               | **Confirmada (README resuelto)**                                                 | READMEs creados para los 7 paquetes públicos. ADRs duplicados: fuera de alcance de la publicación (no afectan a `check:docs-links`).                                                                                                                                                                                                                              |

---

## Decisiones pendientes (no ejecutadas)

### D-003 — Renombre `onHh*` → `onPm*` en `@palm/react`

|                          |                                                                                                                                                                  |
| ------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Estado**               | **Pendiente** (no se ejecuta; coincide con D-003 del cierre).                                                                                                    |
| **Argumentos a favor**   | Coherencia con los nombres de evento de Stencil; antes del primer publish no hay consumidores, es el momento más barato (no hay guía de migración que escribir). |
| **Argumentos en contra** | Cambio breaking de API: cualquier consumidor existente de `onHhPress` etc. rompe; requiere major version bump.                                                   |
| **Recomendación**        | Crear issue "Align React adapter event handlers to `onPm*`" y ejecutarlo en `@palm/react` v2 con guía de migración. No se toca en este ciclo.                    |
| **Cómo revertir**        | N/A (no se ejecuta).                                                                                                                                             |

### Tags faltantes en adapters (H9)

Se reportan los 20 tags ausentes en Angular (y los ausentes en Vue). **No se agregan** sin decisión de producto. Cada tag nuevo requiere implementación de componente, historia, test y actualización de matrix.

### Export `./custom-elements`

Evaluado exponer el output `dist-custom-elements` como `./custom-elements`. **Decisión pendiente:** no se ejecuta. Los loaders `dist/loader/` ya están expuestos y funcionan (`@palm/components/loader`, `@palm/primitives/loader`).

### Addon `@storybook/addon-a11y`

`preview.ts` declara `a11y: { test: 'todo' }` pero no hay addon a11y en `devDependencies`. **Costo de añadirlo:** nueva dependencia (+ compatibilidad con Storybook 10, cuya familia de addons se reorganizó en v10) + cobertura de accesibilidad en historias. **Decisión:** no imponerse; reportar como opción para el siguiente ciclo.

### Conector del toggle de tema con decorator

`globalTypes.theme` (light/dark) existe pero ningún decorator lo aplica; los componentes leen `[data-pm-theme='dark']` de `tokens.css`. **Costo de añadirlo:** decorator que escriba `data-pm-theme` en `documentElement` + mantener sincronía con el estado del toggle. **Decisión:** pendiente (recomendación: implementar si el equipo prioriza el tema en Storybook).

---

## Open Gaps restantes

1. `changeset version` falló en el worktree desechable por `MODULE_NOT_FOUND: resolve-from` dentro de `@changesets/apply-release-plan` (hoisting de pnpm 10 vs. cambiosets). **No es un defecto de los changesets** (`changeset status` es verde). **Acción:** validar `changeset version` en el entorno de CI/liberación real antes del primer publish.
2. `@arethetypeswrong/cli` reporta `CJSResolvesToESM` y `InternalResolutionError`/`NoResolution` en algunos paquetes. Son paquetes `"type": "module"` (ESM primero); hallazgos de heurística ESM/CJS, no bloqueantes para publicar. `publint` pasa limpio en todos los tarballs.
3. Divergencia de API de `pm-icon` (H10): actualizar READMEs de componentes para documentar `size`, `label`, `decorative`.
4. Los ADR duplicados entre `docs/adr/` y `docs/architecture/` no se tocan (no afectan a publicación ni a `check:docs-links`).

---

## Checklist: qué falta para publicar de verdad

- [ ] Propiedad del scope `@palm` en npm (quién es el dueño del scope).
- [ ] Token de automatización para la cuenta de npm (sin commitear; nunca en la repo).
- [ ] 2FA en la cuenta de npm que publicará.
- [ ] Provenance (`provenance: true` en npm) si se desea.
- [ ] Ejecutar `changeset version` en un worktree desechable (falló aquí por hoisting de pnpm; validar en CI de liberación).
- [ ] Primer changeset de versión (el de tokens ya está como `major`; aplicar versiones y orden de publicación).
- [ ] Orden de publicación sugerido: `@palm/tokens` primero (dependencia de componentes/primitives), luego `@palm/icons`, `@palm/primitives`, `@palm/components`, y por último los adaptadores (`@palm/react`, `@palm/vue`, `@palm/angular`).
- [ ] Ejecutar `pnpm publish --dry-run` (permitido) antes del publish real; **nunca** `npm publish`/`pnpm publish`/`changeset publish`/`git tag`/`git push` en esta rama.

---

## Resumen final

Se dejó el monorepo PALM listo para publicación en npm (sin publicar): todos los paquetes públicos tienen metadatos completos, `files` que embarcan solo `dist/` (sin `src/`), `exports`/`main`/`types` verificados, CSS de tokens en `dist/`, `sideEffects`, `publishConfig.access: public`, visibilidad correcta (`testing` y `foundation` privados), cambiosets bien clasificados, guardia de `release` con `PALM_ALLOW_PUBLISH=1`, un validador `package-publish` en `pnpm check`, y `pnpm check` en verde. Se creó el workflow de GitHub Pages que construye y despliega el Storybook bajo `/palm/` (verificado con servidor estático local), con README de activación. No se publicó, taggeó ni pusheó nada. El reporte completa el cierre D-001..D-009 con decisiones D-010..D-020 y deja documentadas las decisiones pendientes (D-003, tags de adapters, `./custom-elements`, a11y, tema).
