import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmAccordion } from '@palm/components';

const meta = {
  title: 'PALM/Components/Surface/Accordion',
  component: PmAccordion,
} satisfies Meta<PmAccordion>;

export default meta;

export const Default = {
  render: () => h('pm-accordion', null,
    h('pm-accordion-item', { value: 'a', label: 'What is Palm?', open: true }, 'A mobile-first component library.'),
    h('pm-accordion-item', { value: 'b', label: 'Is it accessible?' }, 'Yes, built with ARIA patterns in mind.'),
  ),
};
export const Multiple = {
  render: () => h('pm-accordion', { multiple: true },
    h('pm-accordion-item', { value: 'a', label: 'Section A', open: true }, 'Content A.'),
    h('pm-accordion-item', { value: 'b', label: 'Section B', open: true }, 'Content B.'),
  ),
};
