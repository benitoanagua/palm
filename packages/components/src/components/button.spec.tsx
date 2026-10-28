import { newSpecPage } from '@stencil/core/testing';
import { PmButton } from './button';

describe('pm-button', () => {
  it('renders its content', async () => {
    const page = await newSpecPage({
      components: [PmButton],
      html: '<pm-button>Continue</pm-button>',
    });
    expect(page.root?.textContent).toContain('Continue');
  });
});
