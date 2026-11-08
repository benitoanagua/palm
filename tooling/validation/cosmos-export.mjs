/**
 * Validador del export de React Cosmos (sin navegador).
 *
 * Garantiza que el artefacto subido a GitHub Pages tenga:
 *  - layout plano (index.html, renderer.html, assets/ en la raíz del export);
 *  - sin carpeta anidada `palm/`;
 *  - sin rutas absolutas (`/palm/`, `/assets/`) en HTML/JS/CSS, que romperían
 *    el despliegue bajo un subpath como `/palm/`.
 *
 * Uso: node tooling/validation/cosmos-export.mjs <directorio-export>
 */
import { readFile, readdir, stat } from 'node:fs/promises';
import { join } from 'node:path';

const ROOT = process.argv[2];
if (!ROOT) {
  console.error('Uso: node tooling/validation/cosmos-export.mjs <directorio-export>');
  process.exit(2);
}

const REQUIRED_FILES = ['index.html', 'renderer.html'];
const REQUIRED_DIRS = ['assets'];
const FORBIDDEN_DIRS = ['palm'];
const ABSOLUTE_PATH_RE = /["('`]\/(palm|assets)\//;

let failed = false;

function fail(msg) {
  console.error(`[cosmos-export validator] FAIL: ${msg}`);
  failed = true;
}

async function scanDir(dir) {
  let entries;
  try {
    entries = await readdir(dir, { withFileTypes: true });
  } catch {
    fail(`no se puede leer el directorio: ${dir}`);
    return;
  }
  for (const entry of entries) {
    const full = join(dir, entry.name);
    if (entry.isDirectory()) {
      if (FORBIDDEN_DIRS.includes(entry.name)) {
        fail(`carpeta prohibida encontrada en la raíz del export: ${entry.name}/`);
      }
      await scanDir(full);
    } else if (entry.isFile()) {
      await checkFile(full);
    }
  }
}

async function checkFile(file) {
  const ext = file.slice(file.lastIndexOf('.')).toLowerCase();
  if (!['.html', '.js', '.css'].includes(ext)) return;
  let content;
  try {
    content = await readFile(file, 'utf8');
  } catch {
    fail(`no se puede leer el archivo: ${file}`);
    return;
  }
  const match = content.match(ABSOLUTE_PATH_RE);
  if (match) {
    fail(`ruta absoluta encontrada en ${file}: ${match[0]}`);
  }
}

async function main() {
  let st;
  try {
    st = await stat(ROOT);
  } catch {
    fail(`el directorio de export no existe: ${ROOT}`);
    process.exit(failed ? 1 : 0);
  }
  if (!st.isDirectory()) {
    fail(`la ruta de export no es un directorio: ${ROOT}`);
    process.exit(failed ? 1 : 0);
  }

  for (const name of REQUIRED_FILES) {
    try {
      await stat(join(ROOT, name));
    } catch {
      fail(`falta el archivo requerido en la raíz del export: ${name}`);
    }
  }
  for (const name of REQUIRED_DIRS) {
    try {
      const s = await stat(join(ROOT, name));
      if (!s.isDirectory()) fail(`falta el directorio requerido: ${name}/`);
    } catch {
      fail(`falta el directorio requerido: ${name}/`);
    }
  }

  await scanDir(ROOT);

  if (failed) {
    console.error('[cosmos-export validator] export mal formado');
    process.exit(1);
  }
  console.log('[cosmos-export validator] export válido');
  process.exit(0);
}

main();
