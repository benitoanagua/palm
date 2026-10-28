import { Config } from '@stencil/core';
export const config: Config = {
  namespace: 'palm-primitives',
  sourceMap: true,
  outputTargets: [
    { type: 'dist' },
    { type: 'dist-custom-elements', customElementsExportBehavior: 'single-export-module' },
  ],
};
