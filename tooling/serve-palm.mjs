import { createServer } from 'node:http';
import { createReadStream } from 'node:fs';
import { stat } from 'node:fs/promises';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';

// Serves a directory EXACTLY under a mount prefix:
//   /palm/index.html      ->  <dir>/index.html
//   /palm/renderer.html   ->  <dir>/renderer.html
// No candidate fallbacks, no magic resolution, real 404 for missing files.
// Portable: works on Windows (fileURLToPath) and any mount prefix (incl. '/' for root).
export function createStaticServer(dir, prefix = '', port = 8123) {
  const base = (prefix || '').replace(/\/+$/, '');
  const root = join(dir);

  const mime = {
    '.html': 'text/html; charset=utf-8',
    '.js': 'application/javascript',
    '.mjs': 'application/javascript',
    '.css': 'text/css',
    '.json': 'application/json',
    '.svg': 'image/svg+xml',
    '.woff2': 'font/woff2',
    '.ico': 'image/x-icon',
    '.png': 'image/png',
    '.jpg': 'image/jpeg',
  };

  async function handle(req, res) {
    const url = new URL(req.url, 'http://localhost');
    let p = url.pathname;
    if (p === base || p === base + '/') p = base + '/index.html';
    if (base && !p.startsWith(base + '/')) {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('404: outside mount prefix\n');
      return;
    }
    let relative = p.slice(base.length) || '/';
    relative = relative.replace(/^\/+/, '') || 'index.html';
    // block directory traversal
    if (relative.includes('..') || relative.startsWith('/') || relative.startsWith('\\')) {
      res.writeHead(403, { 'Content-Type': 'text/plain' });
      res.end('403: invalid path\n');
      return;
    }
    const fp = join(root, relative);
    let st;
    try {
      st = await stat(fp);
    } catch {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end(`404: ${req.url}\n`);
      return;
    }
    if (!st.isFile()) {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end(`404: ${req.url}\n`);
      return;
    }
    const ext = '.' + relative.split('.').pop().toLowerCase();
    res.writeHead(200, { 'Content-Type': mime[ext] || 'application/octet-stream' });
    const stream = createReadStream(fp);
    stream.pipe(res);
  }

  return new Promise((resolve, reject) => {
    const srv = createServer(handle);
    srv.listen(port, () => resolve(srv));
    srv.on('error', reject);
  });
}

// CLI usage: node serve-palm.mjs <dir> [prefix] [port]
if (process.argv[1] === fileURLToPath(import.meta.url)) {
  const dir = process.argv[2];
  const prefix = process.argv[3] || '';
  const port = Number(process.argv[4] || 8123);
  if (!dir) {
    console.error('Uso: node serve-palm.mjs <directorio> [prefijo] [puerto]');
    process.exit(2);
  }
  createStaticServer(dir, prefix, port).then((srv) => {
    const addr = srv.address();
    const portListening = typeof addr === 'object' && addr !== null ? addr.port : port;
    console.log(`serving ${dir} at http://localhost:${portListening}${base()}/`);
  });
  function base() {
    return (prefix || '').replace(/\/+$/, '');
  }
}
