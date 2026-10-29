import iconData from './data/tabler-outline.json';
import type { TablerIconName } from './icon-names';

export type { TablerIconName } from './icon-names';

export type TablerNode = readonly [string, Record<string, string | number>];

/**
 * Full Tabler Icons (outline set) node data — ~5,100 icons, sourced from
 * @tabler/icons (MIT License, (c) Pawel Kuna) at build time. See
 * packages/icons/THIRD_PARTY_NOTICES.md for attribution details.
 *
 * This module resolves against the complete Tabler outline icon set generated
 * at build time, so `pm-icon` supports the full Tabler catalog without silent
 * rendering failures (see docs/architecture/adr-007-icon-coverage.md).
 */
const icons = iconData as unknown as Record<string, readonly TablerNode[]>;

export function resolveTablerIcon(name: string): readonly TablerNode[] | undefined {
  const normalized = name.trim().toLowerCase();
  return icons[normalized];
}

export function isTablerIconName(name: string): name is TablerIconName {
  return Boolean(resolveTablerIcon(name));
}
