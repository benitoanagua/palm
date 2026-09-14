import '@palm/tokens/tokens.css';
import '@palm/tokens/themes.css';
import { defineCustomElements as defineComponents } from '@palm/components/loader';
import { defineCustomElements as definePrimitives } from '@palm/primitives/loader';

definePrimitives();
defineComponents();

export const parameters = {
  controls: { expanded: true },
  a11y: { test: 'todo' },
  backgrounds: { default: 'light' },
};

export const globalTypes = {
  theme: {
    description: 'PALM theme',
    defaultValue: 'light',
    toolbar: {
      title: 'Theme',
      icon: 'mirror',
      items: ['light', 'dark'],
    },
  },
};
