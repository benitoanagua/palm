import { defineCustomElements } from '@palm/components/loader';
import { defineCustomElements as definePrimitives } from '@palm/primitives/loader';

// Incluye los módulos de los componentes primitivos en el bundle principal para
// que vite los empaquete (resolviendo @stencil/core) y Stencil no intente
// recuperar chunks perezosos (`pm-stack.entry.js`, `pm-surface.entry.js`) que
// react-cosmos/vite no genera para el paquete @palm/primitives.
import '@palm/primitives/dist/collection/pm-stack.js';
import '@palm/primitives/dist/collection/pm-surface.js';

definePrimitives();
defineCustomElements();
