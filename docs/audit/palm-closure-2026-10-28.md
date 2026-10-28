# Palm closure audit — 2026-10-28

## Status

**Incomplete; do not merge PR #14 yet.** The closure branch contains the first batch of changes, but the full requested closure has not been validated and the current repository workflow has not been replaced. This report deliberately distinguishes completed edits from checks that have not passed.

- Base commit measured: `61615893844a365daaa194ada424cb7ec46b9904` (merge of PR #13).
- Baseline CI: [run #40779518831](https://github.com/benitoanagua/palm/actions/runs/40779518831) passed on that base, using the legacy `.github/workflows/palm-hardening.yml`; it does not establish that the new branch is green.
- First closure CI: [run #40782866694](https://github.com/benitoanagua/palm/actions/runs/40782866694) failed in the new matrix validator because `pm-stack` and `pm-surface` were found in the matrix but not in `packages/components/src`.
- Follow-up correction expands the validator scan to all `packages/*/src` workspaces. Validation for that revision is pending; see [PR #14](https://github.com/benitoanagua/palm/pull/14).

## Phase 0 — measured state and preflight

- Default branch: `main`; base SHA `61615893844a365daaa194ada424cb7ec46b9904`.
- Existing recovery point: tag `pre-hardening` is used by the legacy workflow. The workflow also uploads a recovery bundle as a 90-day artifact.
- Recent merged work: PR #11 fixed the workflow's PR trigger; PR #12 changed the accessibility check away from a pointer click; PR #13 changed the test to walk the Tab sequence. PR #13's main push CI passed under the legacy workflow.
- `main` still had root `test` wired to Playwright and the legacy one-off workflow with `contents: write`, tag/bundle side effects, a `chore/palm-final-ci` trigger, and a standalone icon-generation step.
- This session uses GitHub repository APIs, not the user's WSL shell. Therefore local `git status`, `git stash list`, `gh auth status`, local worktree checks, and a local `~/palm-pre-closure.bundle` cannot be honestly reported as executed.

## Changes made on `chore/palm-closure`

| Element | Category | Evidence / action | Revert |
| --- | --- | --- | --- |
| Root `test` runs `turbo test`; browser tests moved to `test:e2e` | regression | `package.json`; first commit of unit-test separation | `2c6135052c0068c78dcb0743e44b47161167d83a` |
| Playwright is excluded from Turbo unit tests | regression | `packages/testing/package.json` uses a non-browser informational test task | `8ca34c847f7fd840739f1be45fadc46c6e0e7120` |
| Local WSL config selection | CI/transitorio | `tooling/run-e2e.mjs` selects `playwright.local.config.ts` if present, otherwise the committed config; root `test:e2e` calls it | `0b2ce0a56634c19226207e8ecbd043d3035a4294`, `085e25edf593ec142896a882cb69a5dc7103ca31` |
| Tabler JSON generation | rompe-build | `@palm/icons` has `generate`, `prebuild`, and `pretypecheck`; the generator itself already resolves the package-local pnpm symlink and copies into `dist/data` during build | `2f4b6576643e3b45df9b4dc5071c52f38b9b2ca3` |
| Turbo generation dependency | rompe-build | `build` and `typecheck` now depend on `generate`; this is awaiting CI validation | `f7cc5a6961fa48f074463163eaaa04859284fc33` |
| Generated artifacts ignore policy | transitorio | `.gitignore` no longer ignores `data` or `public`; includes build/test output, environment files, images, generated Stencil declarations and generated Tabler data; `*.svg` remains unignored | `2bf9506389551ed993c1b367f1376077c008739c`, `1419e1034c1c7d89dd6a4e189c1ea40eb10d6ffb` |
| Component matrix | gap de spec | Added the 19 requested component rows; rows marked `review` are unverified coverage gaps, not false claims of passing tests | `de383fdb221c55c5f0c1ea1198897fd0738b45ca` |
| Component-matrix validator | CI | Added `tooling/validation/component-matrix.mjs` and `check:matrix` to `pnpm check`; first run exposed scope mismatch, validator was widened to all workspace packages and must be rechecked | `28bb4eb038c2f4435be66db3975da5e3348fa755`, `e7e0e9a179fee2804331f4ed01a6d82ef1ca6c07` |
| Quality/test documentation | docs | Root README documents browser-free `pnpm check`, `test:e2e`, local WSL config and local-only visual snapshots; `docs/contributing.md` describes manual Knip/depcheck audits | `9b0813f73264d49e1244b545ed1a7f7a441c1de9`, `d330b88f56641eba7c1ceb507281ba07fe02801f` |
| Icon-provider ADR | gap de spec | Added ADR-002: Tabler for web, Material Symbols for Android | `9b66b726cd27c785c554d1666858041cc9bac035` |
| Scope ADR | gap de spec | Added ADR-003 to separate web patterns, stateless helpers and Android persistence/gestures | `9126724ab8e93b877af646b82dfaed637b05da37` |

## Quality-gate comparison

| Gate | Before | After |
| --- | --- | --- |
| `pnpm check` on clean clone | Not executed in a clean local clone in this session. The base's legacy GitHub Actions run passed, but it generated Tabler data before checking and is not equivalent to the requested clean-clone baseline. | Not green yet. The first PR run failed at `check:matrix`; the validator fix has a pending follow-up run. |
| `pnpm test:e2e` | The prior root `test` incorrectly invoked Playwright. The prior CI workflow installed Chromium and ran this indirectly through `pnpm check`. | Not yet confirmed on the closure branch. |
| Clean clone / Stencil declarations | Not executed locally. | Not confirmed; `packages/components/src/components.d.ts` is still tracked because the file-removal operation was blocked by repository tooling safety checks. |
| CI workflow | Legacy `palm-hardening.yml`, with `contents: write`, recovery/tag side effects and standalone icon generation. | Replacement workflow not applied; workflow-file creation was blocked by repository tooling safety checks. |

## Decisions and blockers

- **Unit tests and E2E are separate:** the required `pnpm check` is browser-free; Playwright remains explicit in `pnpm test:e2e`.
- **Generated icon data is not committed:** build/typecheck lifecycle generation is responsible for creating it from the installed Tabler package.
- **Matrix gaps are explicit:** `review` denotes an unverified claim and must not be presented as completed coverage.
- **Workflow replacement is blocked:** the GitHub tool rejected creating `.github/workflows/ci.yml`; the legacy workflow remains. Do not merge this branch until a proper read-only CI workflow runs `pnpm check` and a dependent E2E job.
- **Generated Stencil declaration removal is blocked:** the GitHub tool rejected deleting `packages/components/src/components.d.ts`. Confirm in a clean clone that `typecheck` succeeds without it; if it fails, keep the file or make the build dependency explicit as described in the prompt.
- **Pre-closure tag and bundle are not created:** this session has no local shell and the available repository actions do not expose tag creation or a local bundle output. The existing `pre-hardening` tag and workflow artifact remain the available recovery points.
- **Spec/comment cleanup remains open:** references to Bench in `palm-design-system.md`, any other stale paths, ADR status normalization, status styling/contrast, focus outlines/forced-colors, token aliases, Angular adapter, unused dependencies/stories, license notice consolidation, and changeset have not been completed or verified.
- **No visual baselines/images were added; no npm publication occurred; Git history was not rewritten.**

## Required next steps before integration

1. Make the CI workflow replacement and remove the one-off workflow with a tool path that permits workflow-file changes; then validate the exact workflow on a clean checkout.
2. Remove generated Stencil declarations from the index only after verifying clean-clone typecheck, and ensure the generated Tabler JSON is produced before icon build/typecheck.
3. Re-run the matrix validator and the complete quality gate. Fix all new failures rather than merging red.
4. Run `test:e2e` with system Chromium locally and with Playwright-installed Chromium in CI.
5. Complete remaining spec, focus, status, token, Angular, ADR, notices, dead-code, and changeset work; record each decision with the commit hash.
6. Only then merge PR #14, create `post-closure`, prune only merged unprotected branches, and verify the final main CI.
