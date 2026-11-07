import fs from 'node:fs';
import path from 'node:path';
import { execSync } from 'node:child_process';

const root = process.cwd();
const packagesDir = path.join(root, 'packages');

const errors = [];

function fail(pkgName, msg) {
  errors.push(`${pkgName}: ${msg}`);
}

function readJson(filePath) {
  return JSON.parse(fs.readFileSync(filePath, 'utf8'));
}

// Recoge todas las rutas de destino de un campo `exports` (string, array o
// objeto con condiciones). Devuelve rutas relativas al paquete.
function resolveExportTarget(target) {
  const out = [];
  if (typeof target === 'string') {
    out.push(target);
  } else if (Array.isArray(target)) {
    // Último elemento es la ruta de destino.
    out.push(target[target.length - 1]);
  } else if (target && typeof target === 'object') {
    for (const value of Object.values(target)) {
      out.push(...resolveExportTarget(value));
    }
  }
  return out;
}

function collectExportPaths(exports) {
  const paths = [];
  if (!exports || typeof exports !== 'object') return paths;
  for (const target of Object.values(exports)) {
    paths.push(...resolveExportTarget(target));
  }
  return paths;
}

function fileExists(pkgDir, rel) {
  return fs.existsSync(path.join(pkgDir, rel.replace(/^\.\//, '')));
}

// Paquetes públicos: todo lo que no declare private: true.
const publicPkgs = [];
for (const entry of fs.readdirSync(packagesDir, { withFileTypes: true })) {
  if (!entry.isDirectory()) continue;
  const pkgPath = path.join(packagesDir, entry.name);
  const pkgJson = path.join(pkgPath, 'package.json');
  if (!fs.existsSync(pkgJson)) continue;
  const pkg = readJson(pkgJson);
  if (pkg.private === true) continue;
  publicPkgs.push({ name: pkg.name || entry.name, dir: pkgPath, pkg });
}

for (const { name, dir, pkg } of publicPkgs) {
  // Metadatos requeridos para publicar.
  if (!pkg.license) fail(name, 'falta license');
  if (!pkg.repository || !pkg.repository.url || !pkg.repository.directory) {
    fail(name, 'falta repository.url o repository.directory');
  }
  if (!pkg.publishConfig || pkg.publishConfig.access !== 'public') {
    fail(name, 'falta publishConfig.access: public');
  }
  if (!pkg.files || !Array.isArray(pkg.files) || pkg.files.length === 0) {
    fail(name, 'falta files');
  }
  // main/types deben existir tras el build.
  if (pkg.main && !fileExists(dir, pkg.main)) fail(name, `main no existe tras build: ${pkg.main}`);
  if (pkg.types && !fileExists(dir, pkg.types))
    fail(name, `types no existe tras build: ${pkg.types}`);
  // Cada target de exports debe existir tras el build.
  if (pkg.exports) {
    for (const targetPath of collectExportPaths(pkg.exports)) {
      if (!fileExists(dir, targetPath))
        fail(name, `exports target no existe tras build: ${targetPath}`);
    }
  }
  // pnpm pack --dry-run --json: incluye dist/, excluye src/, specs, tests.
  try {
    const out = execSync('pnpm pack --dry-run --json', {
      cwd: dir,
      encoding: 'utf8',
      stdio: ['pipe', 'pipe', 'pipe'],
    });
    const info = JSON.parse(out);
    const files = (info.files || []).map((f) => f.path);
    const hasDist = files.some((p) => p === 'dist' || p.startsWith('dist/'));
    if (!hasDist) fail(name, 'pack --dry-run no incluye dist/');
    const forbidden = files.filter(
      (p) =>
        p.startsWith('src/') ||
        p.includes('/src/') ||
        p.endsWith('.test.ts') ||
        p.endsWith('.spec.ts') ||
        p.startsWith('tests/') ||
        p.includes('/tests/'),
    );
    if (forbidden.length) {
      fail(name, `pack --dry-run incluye archivos prohibidos: ${forbidden.join(', ')}`);
    }
  } catch (err) {
    fail(name, `pack --dry-run fallo: ${err.message}`);
  }
}

if (errors.length) {
  console.error('PALM package-publish validation failed:');
  for (const e of errors) console.error(' -', e);
  process.exit(1);
}
console.log(`PALM package-publish check passed (${publicPkgs.length} public packages).`);
