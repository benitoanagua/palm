import iconData from './data/tabler-outline.json';
import type { TablerIconName } from './icon-names';

export type { TablerIconName, CommonTablerIconName } from './icon-names';

export type TablerNode = readonly [string, Record<string, string | number>];

/**
 * Full Tabler Icons (outline set) node data — ~5,100 icons, sourced from
 * @tabler/icons (MIT License, (c) Pawel Kuna) at build time. See
 * packages/icons/THIRD_PARTY_NOTICES.md for attribution details.
 *
 * Previously this file hand-authored ~20 icons as inline SVG node arrays,
 * which meant any icon name outside that list silently failed to render
 * (see docs/architecture/adr-007-icon-coverage.md). This module resolves
 * against the complete icon set instead, so `name="add"`-style lookups
 * only fail for names that genuinely don't exist in Tabler.
 */
const icons = iconData as unknown as Record<string, readonly TablerNode[]>;

export function resolveTablerIcon(name: string): readonly TablerNode[] | undefined {
  const normalized = name.trim().toLowerCase();
  return icons[normalized];
}

export function isTablerIconName(name: string): name is TablerIconName {
  return Boolean(resolveTablerIcon(name));
}

/** Total number of icons available in the resolved set. Useful for tests/docs. */
export const tablerIconCount = Object.keys(icons).length;

/** All available icon names, sorted. Useful for a documentation/icon-picker page. */
export function listTablerIconNames(): string[] {
  return Object.keys(icons).sort();
}
