import { defineConfig } from 'vite';
import path from 'node:path';

// Empaqueta los módulos de colección de los primitivos (pm-stack, pm-surface) en
// el entry principal del export. Así vite resuelve @stencil/core y Stencil no
// intenta recuperar chunks perezosos que react-cosmos/vite no genera para el
// paquete @palm/primitives.
const PRIM_COLLECTION = path.resolve(__dirname, '../../packages/primitives/dist/collection');

export default defineConfig({
  plugins: [],
  resolve: {
    alias: {
      '@palm/primitives/dist/collection/pm-stack.js': `${PRIM_COLLECTION}/pm-stack.js`,
      '@palm/primitives/dist/collection/pm-surface.js': `${PRIM_COLLECTION}/pm-surface.js`,
    },
  },
});
