import http from 'node:http';
import fs from 'node:fs';
import path from 'node:path';
import url from 'node:url';

const __dirname = path.dirname(new URL(import.meta.url).pathname);
const ROOT = path.join(__dirname, '..', 'apps', 'cosmos', 'cosmos-export');
const BASE = '/palm';

const mime = {
  '.html': 'text/html',
  '.js': 'application/javascript',
  '.css': 'text/css',
  '.json': 'application/json',
  '.svg': 'image/svg+xml',
  '.woff2': 'font/woff2',
  '.ico': 'image/x-icon',
  '.png': 'image/png',
};

function resolveFile(p) {
  const relativePath = p.slice(BASE.length) || '/';
  const candidates = [
    path.join(ROOT, BASE, relativePath === '/' ? 'index.html' : relativePath),
    path.join(ROOT, relativePath === '/' ? 'index.html' : relativePath),
  ];
  for (const fp of candidates) {
    try {
      const st = fs.statSync(fp);
      if (st.isFile()) return fp;
    } catch {}
  }
  return null;
}

const server = http.createServer((req, res) => {
  const u = new URL(req.url, 'http://localhost');
  let p = u.pathname;
  if (p === BASE || p === BASE + '/') p = BASE + '/index.html';
  if (!p.startsWith(BASE + '/')) {
    res.writeHead(404);
    res.end('404');
    return;
  }
  const fp = resolveFile(p);
  if (!fp) {
    res.writeHead(404);
    res.end('404 ' + req.url);
    return;
  }
  const ext = path.extname(fp).toLowerCase();
  res.writeHead(200, { 'Content-Type': mime[ext] || 'application/octet-stream' });
  fs.createReadStream(fp).pipe(res);
});

server.listen(8123, () => console.log('serving cosmos-export on http://localhost:8123' + BASE));
