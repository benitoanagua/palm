import type { StorybookConfig } from '@stencil/storybook-plugin';

// En GitHub Pages el sitio se sirve bajo un subpath /<usuario>/<repo>/.
// En desarrollo local se sirve en la raiz. STORYBOOK_BASE permite forzar el
// base (el workflow de Pages lo define a /palm/).
const base = process.env.STORYBOOK_BASE || '/';

const config: StorybookConfig = {
  stories: ['../src/**/*.stories.@(ts|tsx|js)'],
  framework: {
    name: '@stencil/storybook-plugin',
    options: {},
  },
  viteFinal: (config) => {
    config.base = base;
    return config;
  },
};

export default config;
