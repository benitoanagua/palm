# Palm Hardening Closure — Audit Report

**Date:** 2026-10-28 / 2026-10-29  
**Branch:** `chore/palm-closure` → merged to `main`  
**Tag before:** `pre-closure` (`c88567f`)  
**Tag after:** `post-closure` (merge commit, pending)  
**Engineer:** Automated hardening pass (Antigravity)

---

## Baseline (`pnpm check` before any changes)

Run in `/tmp/palm-clean` worktree at `c88567f` (= `main` before closure).  
**Result: GREEN** — all validators and builds passed at the start.

---

## Commits (chore/palm-closure)

| Hash      | Conventional type | What it fixes                                                                                                                                                                                                                                                                                                                                      |
| --------- | ----------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `afe26b3` | `chore(repo)`     | Untrack generated `packages/primitives/src/components.d.ts` (was in git index; `.gitignore` already excluded it)                                                                                                                                                                                                                                   |
| `8ec8881` | `ci`              | Delete one-off `palm-hardening.yml`; create canonical `ci.yml` with `check` + `e2e` jobs, `contents: read`, concurrency + cancel-in-progress                                                                                                                                                                                                       |
| `42b3e54` | `fix(spec)`       | Remove "Bench" / external-project references from spec; align canonical paths (`docs/visual/demos/` instead of `palm-mobile-mockup.html`); fix `button.css` comment path                                                                                                                                                                           |
| `cafb095` | `docs`            | Consolidate docs: delete duplicate `docs/contracts/component-matrix.md`, merge `showcase.md` → `showcase-components.md`, merge `visual-acceptance.md` → `docs/visual/README.md`; fix dual `THIRD*NOTICES.md` in icons; add `ADR-002` status; add 19 missing rows to component matrix; add `component-matrix.mjs` validator + `check:matrix` script |
| `b436f0a` | `style(focus)`    | Replace `outline: none` + box-shadow with WCAG 2.4.7 `outline` + `outline-offset` + `forced-colors: active` fallback in 18 CSS files; add `--pm-focus-offset` token; remove `--pm-color-neutral-200` and `--pm-radius-xl`; add button/switch/field component tokens; add dark-theme overrides for semantic aliases                                 |

---

## Decisions

### D-001 — `jest`/`jest-cli` in `packages/components` devDependencies

|                   |                                                                                                                                                              |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Decision**      | Keep `jest` and `jest-cli` even though `knip` flags them as unused                                                                                           |
| **Alternatives**  | Remove them and rely solely on Stencil's bundled Jest                                                                                                        |
| **Criterion**     | `stencil test --spec` invokes Jest under the hood via `@stencil/core/internal`; without explicit devDeps the install is fragile across pnpm hoisting changes |
| **Evidence**      | `knip` output 2026-10-28; `stencil test --spec` passes with them present                                                                                     |
| **How to revert** | `pnpm remove --filter @palm/components jest jest-cli`                                                                                                        |

### D-002 — `@tabler/icons` reported unused by `depcheck` in `packages/icons`

|                   |                                                                                                           |
| ----------------- | --------------------------------------------------------------------------------------------------------- |
| **Decision**      | Keep; mark as false positive                                                                              |
| **Alternatives**  | Move to root devDependencies                                                                              |
| **Criterion**     | `tooling/generate-tabler-data.mjs` imports from `@tabler/icons`; `depcheck` doesn't trace tooling scripts |
| **Evidence**      | `depcheck` run 2026-10-28; `generate` script works                                                        |
| **How to revert** | N/A (no change made)                                                                                      |

### D-003 — `onHh*` event handler names in `packages/react`

|                   |                                                                                                                                                     |
| ----------------- | --------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Decision**      | Not fixed in this pass (API-breaking)                                                                                                               |
| **Alternatives**  | Rename to `onPm*` to match Stencil event names                                                                                                      |
| **Criterion**     | Any consuming app using `onHhPress` etc. would break. Requires major version bump and consumer migration guide. Out of scope for hardening closure. |
| **Evidence**      | `grep -r "onHh" packages/react/src/index.tsx` — 40+ handler names                                                                                   |
| **How to revert** | N/A (no change made)                                                                                                                                |
| **Follow-up**     | Create a GitHub issue: "Align React adapter event handlers to `onPm*`"                                                                              |

### D-004 — `--pm-color-neutral-200` removed

|                   |                                                                                           |
| ----------------- | ----------------------------------------------------------------------------------------- |
| **Decision**      | Remove from `tokens.css`                                                                  |
| **Alternatives**  | Keep as deprecated; add comment                                                           |
| **Criterion**     | Zero CSS consumers found via `git grep`; pure primitive scale value with no semantic role |
| **Evidence**      | `grep -r "neutral-200" packages apps` → 0 results                                         |
| **How to revert** | `git revert b436f0a` or add back `--pm-color-neutral-200: #c4c4c4;` to `tokens.css`       |

### D-005 — `--pm-radius-xl` removed

|                   |                                                                             |
| ----------------- | --------------------------------------------------------------------------- |
| **Decision**      | Remove alias                                                                |
| **Alternatives**  | Keep as deprecated alias                                                    |
| **Criterion**     | Zero CSS consumers; was an alias of `--pm-radius-base` with no unique value |
| **Evidence**      | `grep -r "radius-xl" packages apps` → 0 results                             |
| **How to revert** | Add back `--pm-radius-xl: var(--pm-radius-base);` to `tokens.css`           |

### D-006 — `knip` / `depcheck` do NOT block CI

|                   |                                                                                                                |
| ----------------- | -------------------------------------------------------------------------------------------------------------- |
| **Decision**      | Excluded from `ci.yml`; documented as manual audit tool in `docs/contributing/`                                |
| **Alternatives**  | Add as blocking CI step                                                                                        |
| **Criterion**     | Both tools produce false positives (see D-001, D-002); blocking CI would create noise. Run manually on demand. |
| **Evidence**      | Multiple false positives documented above                                                                      |
| **How to revert** | Add `pnpm knip` step to `ci.yml`                                                                               |

### D-007 — Visual baselines not versioned; visual suite excluded from CI

|                   |                                                                                                                             |
| ----------------- | --------------------------------------------------------------------------------------------------------------------------- |
| **Decision**      | `test:visual` and `test:visual:update` are local-only; `pnpm check` does not include them                                   |
| **Alternatives**  | Run visual suite in CI with a reference browser container                                                                   |
| **Criterion**     | User decision (pre-existing constraint): baselines are OS/GPU dependent; CI would need Docker + screenshot comparison infra |
| **How to revert** | Add `pnpm test:visual` to `ci.yml`                                                                                          |

### D-008 — `no-legacy-icons.mjs` `ignored` set extended

|                   |                                                                                                                                  |
| ----------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| **Decision**      | Added `storybook-static`, `.stencil`, `www`, `loader`, `.turbo`, `.cache`, `playwright-report`, `blob-report` to the ignored set |
| **Alternatives**  | Use `.gitignore` patterns instead of hardcoded set                                                                               |
| **Criterion**     | Local build artifacts were causing false positives; approach mirrors `docs-links.mjs` pattern                                    |
| **Evidence**      | `storybook-static/assets/*.js` triggered the check on a machine with a prior Storybook build                                     |
| **How to revert** | Revert `tooling/validation/no-legacy-icons.mjs`                                                                                  |

### D-009 — `design-rules.mjs` extended with hex-color and px-radius enforcement

|                   |                                                                                                                                                                                                  |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Decision**      | Added two new rules: (1) no hardcoded hex colors in `packages/` CSS (outside token definition files); (2) no bare `px` border-radius (calc() with var() offsets allowed; `50%`, `999px` allowed) |
| **Alternatives**  | Stylelint with custom rules; separate script                                                                                                                                                     |
| **Criterion**     | Zero violations in current source; catches future regressions at commit time via `pnpm lint`                                                                                                     |
| **Evidence**      | Verified zero violations before committing rule                                                                                                                                                  |
| **How to revert** | Remove added rule block from `tooling/lint/design-rules.mjs`                                                                                                                                     |

---

## Knip / Depcheck Findings Summary

| Tool     | Package            | Finding                   | Status                 |
| -------- | ------------------ | ------------------------- | ---------------------- |
| knip     | `@palm/components` | `jest`, `jest-cli` unused | False positive — D-001 |
| depcheck | `@palm/icons`      | `@tabler/icons` unused    | False positive — D-002 |
| depcheck | all others         | Clean                     | OK                     |

---

## Token Changes Summary

**Removed** (breaking — but zero consumers found):

- `--pm-color-neutral-200`
- `--pm-radius-xl`

**Added** (non-breaking):

- Focus: `--pm-focus-offset`
- Button: `--pm-button-border`, `--pm-button-bg-gradient-start/end`, `--pm-button-hover-bg-gradient-start/end`, `--pm-button-active-bg`
- Shadow: `--pm-shadow-button-inset`, `--pm-shadow-switch-handle`, `--pm-shadow-field-inset`
- Semantic: `--pm-color-on-color`, `--pm-color-header-bg`, `--pm-color-header-text`, `--pm-color-header-text-dim`, `--pm-color-border-light`, `--pm-color-pending`, `--pm-color-confirmed`, `--pm-color-conflict`
- Contrast: `--pm-color-action-contrast`, `--pm-color-danger-contrast`, `--pm-color-success-contrast`, `--pm-color-warning-contrast`

---

## WCAG 2.4.7 Focus Fix (Visual Change)

**18 CSS files** had `outline: none` which fails WCAG 2.4.7 (Focus Visible) in forced-colors/high-contrast mode.

**Before:**

```css
:host(:focus-visible) {
  outline: none;
  box-shadow: var(--pm-focus-ring);
}
```

**After:**

```css
:host(:focus-visible) {
  outline: var(--pm-focus-width) solid var(--pm-focus-color);
  outline-offset: var(--pm-focus-offset);
  box-shadow: var(--pm-focus-ring);
}
@media (forced-colors: active) {
  :host(:focus-visible) {
    outline: 2px solid Highlight;
  }
}
```

Files changed: `accordion.css`, `breadcrumbs.css`, `button.css`, `card.css`, `choice.css`, `date-picker.css`, `dialog.css`, `drawer.css`, `field.css`, `file-upload.css`, `menu.css`, `nav.css`, `pagination.css`, `segmented-control.css`, `slider.css`, `tabs.css`, `data-table.css`, `styles/global.css`.

This is a **visual change** (focus ring now renders in forced-colors mode). Visual baseline update required locally via `pnpm test:visual:update`.

---

## CI Changes

**Deleted:** `.github/workflows/palm-hardening.yml` (one-off script; created tags and pushed from CI; had `contents: write`)

**Created:** `.github/workflows/ci.yml`

- Triggers: `pull_request`, `push` to `main`, `workflow_dispatch`
- Permissions: `contents: read`
- Concurrency: per-ref, `cancel-in-progress: true`
- Job `check`: pnpm install → `pnpm check` (no browser needed)
- Job `e2e` (needs `check`): Playwright install Chromium → build → `pnpm test:e2e`
- No tags, no pushes, no secret access

---

## Open Gaps (not fixed in this pass)

| Gap                                                           | Risk                                            | Recommended next action                                    |
| ------------------------------------------------------------- | ----------------------------------------------- | ---------------------------------------------------------- |
| `onHh*` in `packages/react` adapter                           | Medium — misaligned with Stencil event names    | Create issue; fix in `@palm/react` v2 with migration guide |
| Unit tests for accordion, alert, dialog, drawer, nav, tooltip | Low — Storybook stories provide manual coverage | Add Stencil spec tests per component                       |
| `console.log('card pressed')` in `Card.stories.ts:35`         | Low — Storybook story only, not production      | Remove or replace with `action()` from Storybook           |
