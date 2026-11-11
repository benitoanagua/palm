import { Config } from '@stencil/core';
export const config: Config = {
  namespace: 'palm-primitives',
  sourceMap: true,
  // Los dos primitivos se empaquetan en un único bundle y se definen de forma
  // inmediata (sin lazy-load), para que el export estático de Cosmos no tenga
  // que recuperar chunks perezosos que vite no genera para este paquete.
  bundles: [{ components: ['pm-stack', 'pm-surface'] }],
  extras: { lazyLoad: false, enableImportInjection: true },
  outputTargets: [
    { type: 'dist' },
    { type: 'dist-custom-elements', customElementsExportBehavior: 'single-export-module' },
  ],
};
