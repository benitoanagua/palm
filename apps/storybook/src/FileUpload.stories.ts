import type { Meta } from '@stencil/storybook-plugin';
import { h } from '@stencil/core';
import { PmFileUpload } from '@palm/components';

const meta = {
  title: 'PALM/Components/Forms/FileUpload',
  component: PmFileUpload,
} satisfies Meta<PmFileUpload>;

export default meta;

export const Default = { render: () => h('pm-file-upload', { label: 'Attachments' }) };
export const ImagesOnly = {
  render: () =>
    h('pm-file-upload', {
      label: 'Profile photo',
      accept: 'image/*',
      dropzoneText: 'Drag an image here or ',
    }),
};
export const Multiple = {
  render: () => h('pm-file-upload', { label: 'Documents', multiple: true }),
};
export const WithError = {
  render: () => h('pm-file-upload', { label: 'Attachments', error: 'File exceeds the size limit' }),
};
export const Disabled = {
  render: () => h('pm-file-upload', { label: 'Attachments', disabled: true }),
};
