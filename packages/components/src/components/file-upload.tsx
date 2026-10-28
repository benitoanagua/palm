import { Component, Element, Event, type EventEmitter, Prop, State, h } from '@stencil/core';

function formatBytes(bytes: number): string {
  if (bytes < 1024) return `${bytes} B`;
  if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(1)} KB`;
  return `${(bytes / (1024 * 1024)).toFixed(1)} MB`;
}

function matchesAccept(file: File, accept: string): boolean {
  const patterns = accept
    .split(',')
    .map((p) => p.trim())
    .filter(Boolean);
  if (patterns.length === 0) return true;
  return patterns.some((pattern) => {
    if (pattern.startsWith('.')) return file.name.toLowerCase().endsWith(pattern.toLowerCase());
    if (pattern.endsWith('/*')) return file.type.startsWith(pattern.slice(0, -1));
    return file.type === pattern;
  });
}

export interface PmFileUploadError {
  fileName: string;
  reason: string;
}

/**
 * Real drag-and-drop + click-to-browse upload with client-side accept/size
 * validation — not just a styled `<input type="file">`. The native input
 * stays in the DOM (visually hidden, but a real element) so the platform
 * file dialog and native drag events both work; this component adds the
 * dropzone, validation, and the selected-files list around it.
 *
 * Client-side validation is a UX convenience, not a security boundary —
 * always re-validate file type/size server-side.
 */
@Component({ tag: 'pm-file-upload', styleUrl: 'file-upload.css', shadow: false, scoped: true })
export class PmFileUpload {
  @Element() host!: HTMLElement;
  @Prop() label = '';
  @Prop() name = '';
  @Prop() accept = '';
  @Prop() multiple = false;
  @Prop() disabled = false;
  @Prop() required = false;
  @Prop() hint = '';
  @Prop() error = '';
  @Prop() maxSizeBytes?: number;
  @Prop() dropzoneText = 'Drag files here or ';
  @Prop() browseText = 'browse';
  @Event() pmFilesChange!: EventEmitter<File[]>;
  @Event() pmFileError!: EventEmitter<PmFileUploadError[]>;

  @State() private files: File[] = [];
  @State() private dragOver = false;
  @State() private validationErrors: PmFileUploadError[] = [];
  private inputEl?: HTMLInputElement;

  private openDialog = () => {
    if (!this.disabled) this.inputEl?.click();
  };

  private validate(candidates: File[]): { accepted: File[]; errors: PmFileUploadError[] } {
    const accepted: File[] = [];
    const errors: PmFileUploadError[] = [];
    for (const file of candidates) {
      if (this.accept && !matchesAccept(file, this.accept)) {
        errors.push({ fileName: file.name, reason: `File type not accepted (${this.accept})` });
        continue;
      }
      if (this.maxSizeBytes && file.size > this.maxSizeBytes) {
        errors.push({
          fileName: file.name,
          reason: `File exceeds ${formatBytes(this.maxSizeBytes)} limit`,
        });
        continue;
      }
      accepted.push(file);
    }
    return { accepted, errors };
  }

  private addFiles(candidates: File[]) {
    const { accepted, errors } = this.validate(candidates);
    this.validationErrors = errors;
    if (errors.length) this.pmFileError.emit(errors);
    this.files = this.multiple ? [...this.files, ...accepted] : accepted.slice(-1);
    this.pmFilesChange.emit(this.files);
  }

  private onInputChange = (event: Event) => {
    const input = event.target as HTMLInputElement;
    this.addFiles(Array.from(input.files ?? []));
    input.value = '';
  };

  private onDrop = (event: DragEvent) => {
    event.preventDefault();
    this.dragOver = false;
    if (this.disabled) return;
    this.addFiles(Array.from(event.dataTransfer?.files ?? []));
  };

  private removeFile(index: number) {
    this.files = this.files.filter((_, i) => i !== index);
    this.pmFilesChange.emit(this.files);
  }

  private onDropzoneKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
      this.openDialog();
    }
  };

  render() {
    const id = this.host.id || `pm-file-upload-${this.name || 'field'}`;
    return (
      <div class="field pm-file-upload">
        {this.label ? (
          <span class="label">
            {this.label}
            {this.required ? ' *' : ''}
          </span>
        ) : null}
        <div
          class={{
            'pm-file-upload__dropzone': true,
            'is-drag-over': this.dragOver,
            'is-disabled': this.disabled,
          }}
          role="button"
          tabIndex={this.disabled ? -1 : 0}
          aria-disabled={this.disabled ? 'true' : undefined}
          aria-describedby={`${id}-hint`}
          onClick={this.openDialog}
          onKeyDown={this.onDropzoneKeyDown}
          onDragOver={(event) => {
            event.preventDefault();
            if (!this.disabled) this.dragOver = true;
          }}
          onDragLeave={() => {
            this.dragOver = false;
          }}
          onDrop={this.onDrop}
        >
          <pm-icon name="cloud-upload" aria-hidden="true" />
          <p>
            {this.dropzoneText}
            <span class="pm-file-upload__browse">{this.browseText}</span>
          </p>
          <input
            ref={(el) => (this.inputEl = el as HTMLInputElement)}
            id={id}
            type="file"
            class="pm-file-upload__input"
            name={this.name || undefined}
            accept={this.accept || undefined}
            multiple={this.multiple}
            disabled={this.disabled}
            required={this.required && this.files.length === 0}
            onChange={this.onInputChange}
            tabIndex={-1}
          />
        </div>

        {this.files.length > 0 ? (
          <ul class="pm-file-upload__list">
            {this.files.map((file, index) => (
              <li>
                <pm-icon name="file" aria-hidden="true" />
                <span class="pm-file-upload__name">{file.name}</span>
                <span class="pm-file-upload__size">{formatBytes(file.size)}</span>
                <pm-icon-button
                  name="x"
                  label={`Remove ${file.name}`}
                  size="small"
                  onHhPress={() => this.removeFile(index)}
                />
              </li>
            ))}
          </ul>
        ) : null}

        {this.validationErrors.length > 0 ? (
          <ul class="message error" role="alert">
            {this.validationErrors.map((e) => (
              <li>
                {e.fileName}: {e.reason}
              </li>
            ))}
          </ul>
        ) : null}

        {this.hint ? (
          <span id={`${id}-hint`} class="message">
            {this.hint}
          </span>
        ) : null}
        {this.error ? (
          <span class="message error" role="alert">
            {this.error}
          </span>
        ) : null}
      </div>
    );
  }
}
