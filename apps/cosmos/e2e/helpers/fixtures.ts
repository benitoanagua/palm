/**
 * Descubrimiento de fixtures del catálogo PALM.
 *
 * Lee el árbol real de `apps/cosmos/fixtures` con node:fs y extrae los
 * exports nombrados de cada `*.fixture.tsx` (la convención documentada del
 * catálogo: cada `export const` es un fixture). No hay nada hardcodeado.
 */
import { readdirSync, readFileSync } from 'node:fs';
import { join, relative, sep } from 'node:path';

const FIXTURES_DIR = join(process.cwd(), 'fixtures');
const ROOT_DIR = process.cwd();

const FIXTURE_RE = /^export\s+const\s+([A-Za-z0-9_$]+)\s*=/gm;

export interface DiscoveredFixture {
  /** Ruta relativa desde apps/cosmos, p. ej. `fixtures/components/button.fixture.tsx`. */
  relativeFilePath: string;
  fileName: string;
  name: string | null;
  parents: string[];
  /** Carpeta/archivo > nombre del fixture, legible en reportes. */
  label: string;
}

function walk(dir: string, out: string[]): void {
  for (const entry of readdirSync(dir, { withFileTypes: true })) {
    const full = join(dir, entry.name);
    if (entry.isDirectory()) walk(full, out);
    else if (entry.isFile() && /\.fixture\.tsx?$/.test(entry.name)) out.push(full);
  }
}

export function discoverFixtures(): DiscoveredFixture[] {
  const files: string[] = [];
  walk(FIXTURES_DIR, files);
  files.sort();

  const result: DiscoveredFixture[] = [];
  const fixturesRootRel = relative(ROOT_DIR, FIXTURES_DIR); // 'fixtures'

  for (const full of files) {
    const relativeFilePath = relative(ROOT_DIR, full).split(sep).join('/');
    const fileName = full.split(sep).pop()!;
    const parents = relative(join(ROOT_DIR, fixturesRootRel), full)
      .split(sep)
      .slice(0, -1)
      .filter(Boolean);
    const content = readFileSync(full, 'utf8');
    let match: RegExpExecArray | null;
    while ((match = FIXTURE_RE.exec(content))) {
      const name = match[1];
      result.push({
        relativeFilePath,
        fileName,
        name,
        parents,
        label: [...parents, fileName.replace('.fixture.tsx', ''), name].filter(Boolean).join(' / '),
      });
    }
  }
  return result;
}

/** URL del renderer (export estático servido bajo /palm/) para un fixture. */
export function fixtureRendererUrl(fixture: DiscoveredFixture): string {
  const fixtureId = JSON.stringify({ path: fixture.relativeFilePath, name: fixture.name });
  return `/palm/renderer.html?fixtureId=${encodeURIComponent(fixtureId)}&locked=true`;
}
