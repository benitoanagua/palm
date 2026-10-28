import { newSpecPage } from '@stencil/core/testing';
import { PmFileUpload } from './file-upload';

function makeFile(name: string, size: number, type = 'text/plain'): File {
  const file = new File(['x'.repeat(size)], name, { type });
  return file;
}

describe('pm-file-upload', () => {
  it('accepts a valid file and emits pmFilesChange', async () => {
    const page = await newSpecPage({
      components: [PmFileUpload],
      html: '<pm-file-upload accept=".txt"></pm-file-upload>',
    });
    const instance = page.rootInstance as PmFileUpload;
    const spy = jest.fn();
    page.root?.addEventListener('pmFilesChange', (e: Event) =>
      spy((e as CustomEvent<File[]>).detail),
    );
    (instance as unknown as { addFiles: (files: File[]) => void }).addFiles([
      makeFile('notes.txt', 10),
    ]);
    await page.waitForChanges();
    expect(spy).toHaveBeenCalled();
    expect(page.root?.textContent).toContain('notes.txt');
  });

  it('rejects a file with a disallowed extension', async () => {
    const page = await newSpecPage({
      components: [PmFileUpload],
      html: '<pm-file-upload accept=".txt"></pm-file-upload>',
    });
    const instance = page.rootInstance as PmFileUpload;
    const spy = jest.fn();
    page.root?.addEventListener('pmFileError', (e: Event) => spy((e as CustomEvent).detail));
    (instance as unknown as { addFiles: (files: File[]) => void }).addFiles([
      makeFile('photo.png', 10, 'image/png'),
    ]);
    await page.waitForChanges();
    expect(spy).toHaveBeenCalled();
    expect(
      page.root?.querySelector('.pm-file-upload__list')?.textContent,
    ).not.toContain('photo.png');
    expect(page.root?.querySelector('[role="alert"]')?.textContent).toContain('photo.png');
  });

  it('rejects a file over maxSizeBytes', async () => {
    const page = await newSpecPage({
      components: [PmFileUpload],
      html: '<pm-file-upload max-size-bytes="5"></pm-file-upload>',
    });
    const instance = page.rootInstance as PmFileUpload;
    const spy = jest.fn();
    page.root?.addEventListener('pmFileError', (e: Event) => spy((e as CustomEvent).detail));
    (instance as unknown as { addFiles: (files: File[]) => void }).addFiles([
      makeFile('big.txt', 100),
    ]);
    await page.waitForChanges();
    expect(spy).toHaveBeenCalled();
  });
});
