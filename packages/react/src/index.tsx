import * as React from 'react';
import '@palm/components';

export type PmButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
  variant?: 'primary' | 'secondary' | 'tertiary' | 'danger';
  size?: 'small' | 'medium' | 'large';
  loading?: boolean;
  fullWidth?: boolean;
};
export type PmInputProps = React.InputHTMLAttributes<HTMLInputElement> & {
  label?: string;
  hint?: string;
  error?: string;
};
export type PmTextareaProps = React.TextareaHTMLAttributes<HTMLTextAreaElement> & {
  label?: string;
  hint?: string;
  error?: string;
};
export type PmIconProps = React.HTMLAttributes<HTMLElement> & {
  name: string;
  size?: string;
  label?: string;
  decorative?: boolean;
};
export type PmIconButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
  name: string;
  label: string;
  size?: 'small' | 'medium' | 'large';
  loading?: boolean;
};
export type PmCardProps = React.HTMLAttributes<HTMLElement> & { interactive?: boolean };
export type PmBadgeProps = React.HTMLAttributes<HTMLElement> & { tone?: string };
export type PmAvatarProps = React.HTMLAttributes<HTMLElement> & {
  src?: string;
  name?: string;
  size?: 'small' | 'medium' | 'large';
  shape?: 'circle' | 'square';
  status?: 'online' | 'offline' | 'busy' | 'away';
};
export type PmProgressProps = React.HTMLAttributes<HTMLElement> & {
  value?: number;
  indeterminate?: boolean;
  label?: string;
  tone?: 'action' | 'success' | 'danger' | 'warning';
};
export type PmSkeletonProps = React.HTMLAttributes<HTMLElement> & {
  variant?: 'text' | 'circle' | 'rect';
  width?: string;
  height?: string;
};
export type PmSliderProps = Omit<React.HTMLAttributes<HTMLElement>, 'onChange'> & {
  value?: number;
  min?: number;
  max?: number;
  step?: number;
  label?: string;
  disabled?: boolean;
  showValue?: boolean;
  onHhInput?: (e: CustomEvent<number>) => void;
  onHhChange?: (e: CustomEvent<number>) => void;
};
export type PmPaginationProps = React.HTMLAttributes<HTMLElement> & {
  page?: number;
  pageCount?: number;
  siblingCount?: number;
  label?: string;
  onHhChange?: (e: CustomEvent<number>) => void;
};
export type PmBreadcrumbsProps = React.HTMLAttributes<HTMLElement> & {
  label?: string;
  maxItems?: number;
};
export type PmBreadcrumbItemProps = React.HTMLAttributes<HTMLElement> & {
  href?: string;
  current?: boolean;
};
export type PmMenuProps = React.HTMLAttributes<HTMLElement> & {
  open?: boolean;
  label?: string;
  placement?: 'bottom-start' | 'bottom-end';
  onHhOpenChange?: (e: CustomEvent<boolean>) => void;
  onHhSelect?: (e: CustomEvent<string>) => void;
};
export type PmMenuItemProps = React.HTMLAttributes<HTMLElement> & {
  value?: string;
  disabled?: boolean;
  tone?: 'default' | 'danger';
  onHhSelect?: (e: CustomEvent<string>) => void;
};
export type PmSegmentedControlProps = React.HTMLAttributes<HTMLElement> & {
  value?: string;
  label?: string;
  onHhChange?: (e: CustomEvent<string>) => void;
};
export type PmSegmentedItemProps = React.HTMLAttributes<HTMLElement> & {
  value?: string;
  disabled?: boolean;
  selected?: boolean;
};
export type PmToastRegionProps = React.HTMLAttributes<HTMLElement> & {
  placement?: 'top' | 'bottom';
  maxVisible?: number;
};
export type PmComboboxProps = React.HTMLAttributes<HTMLElement> & {
  label?: string;
  name?: string;
  value?: string;
  placeholder?: string;
  hint?: string;
  error?: string;
  required?: boolean;
  disabled?: boolean;
  allowCustomValue?: boolean;
  noResultsText?: string;
  onHhChange?: (e: CustomEvent<string>) => void;
  onHhInput?: (e: CustomEvent<string>) => void;
};
export type PmComboboxOptionProps = React.HTMLAttributes<HTMLElement> & { value?: string };
export type PmDatePickerProps = React.HTMLAttributes<HTMLElement> & {
  label?: string;
  name?: string;
  value?: string;
  min?: string;
  max?: string;
  locale?: string;
  firstDayOfWeek?: 0 | 1;
  hint?: string;
  error?: string;
  required?: boolean;
  disabled?: boolean;
  placeholder?: string;
  onHhChange?: (e: CustomEvent<string>) => void;
  onHhInput?: (e: CustomEvent<string>) => void;
};
export type PmTimePickerProps = React.HTMLAttributes<HTMLElement> & {
  label?: string;
  name?: string;
  value?: string;
  min?: string;
  max?: string;
  step?: number;
  hint?: string;
  error?: string;
  required?: boolean;
  disabled?: boolean;
  onHhChange?: (e: CustomEvent<string>) => void;
  onHhInput?: (e: CustomEvent<string>) => void;
};
export type PmFileUploadProps = React.HTMLAttributes<HTMLElement> & {
  label?: string;
  name?: string;
  accept?: string;
  multiple?: boolean;
  disabled?: boolean;
  required?: boolean;
  hint?: string;
  error?: string;
  maxSizeBytes?: number;
  dropzoneText?: string;
  browseText?: string;
  onHhFilesChange?: (e: CustomEvent<File[]>) => void;
  onHhFileError?: (e: CustomEvent) => void;
};

export const PmButton = React.forwardRef<HTMLElement, PmButtonProps>((props, ref) => (
  <pm-button ref={ref as React.Ref<any>} {...props} />
));
export const PmIcon = React.forwardRef<HTMLElement, PmIconProps>((props, ref) => (
  <pm-icon ref={ref} {...props} />
));
export const PmIconButton = React.forwardRef<HTMLElement, PmIconButtonProps>((props, ref) => (
  <pm-icon-button ref={ref as React.Ref<any>} {...props} />
));
export const PmInput = React.forwardRef<HTMLElement, PmInputProps>((props, ref) => (
  <pm-input ref={ref as React.Ref<any>} {...props} />
));
export const PmTextarea = React.forwardRef<HTMLElement, PmTextareaProps>((props, ref) => (
  <pm-textarea ref={ref as React.Ref<any>} {...props} />
));
export const PmCheckbox = React.forwardRef<
  HTMLElement,
  React.HTMLAttributes<HTMLElement> & { label?: string; checked?: boolean }
>((props, ref) => <pm-checkbox ref={ref} {...props} />);
export const PmRadio = React.forwardRef<
  HTMLElement,
  React.HTMLAttributes<HTMLElement> & { label?: string; checked?: boolean }
>((props, ref) => <pm-radio ref={ref} {...props} />);
export const PmSwitch = React.forwardRef<
  HTMLElement,
  React.HTMLAttributes<HTMLElement> & { label?: string; checked?: boolean }
>((props, ref) => <pm-switch ref={ref} {...props} />);
export type PmSelectProps = Omit<React.SelectHTMLAttributes<HTMLSelectElement>, 'value'> & {
  label?: string;
  hint?: string;
  error?: string;
  value?: string;
};
export const PmSelect = React.forwardRef<HTMLElement, PmSelectProps>((props, ref) => (
  <pm-select ref={ref as React.Ref<any>} {...props} />
));
export const PmCard = React.forwardRef<HTMLElement, PmCardProps>((props, ref) => (
  <pm-card ref={ref} {...props} />
));
export const PmBadge = React.forwardRef<HTMLElement, PmBadgeProps>((props, ref) => (
  <pm-badge ref={ref} {...props} />
));
export const PmAvatar = React.forwardRef<HTMLElement, PmAvatarProps>((props, ref) => (
  <pm-avatar ref={ref} {...props} />
));
export const PmProgress = React.forwardRef<HTMLElement, PmProgressProps>((props, ref) => (
  <pm-progress ref={ref} {...props} />
));
export const PmSkeleton = React.forwardRef<HTMLElement, PmSkeletonProps>((props, ref) => (
  <pm-skeleton ref={ref} {...props} />
));
export const PmSlider = React.forwardRef<HTMLElement, PmSliderProps>((props, ref) => (
  <pm-slider ref={ref as React.Ref<any>} {...props} />
));
export const PmPagination = React.forwardRef<HTMLElement, PmPaginationProps>((props, ref) => (
  <pm-pagination ref={ref} {...props} />
));
export const PmBreadcrumbs = React.forwardRef<HTMLElement, PmBreadcrumbsProps>((props, ref) => (
  <pm-breadcrumbs ref={ref} {...props} />
));
export const PmBreadcrumbItem = React.forwardRef<HTMLElement, PmBreadcrumbItemProps>(
  (props, ref) => <pm-breadcrumb-item ref={ref} {...props} />,
);
export const PmMenu = React.forwardRef<HTMLElement, PmMenuProps>((props, ref) => (
  <pm-menu ref={ref} {...props} />
));
export const PmMenuItem = React.forwardRef<HTMLElement, PmMenuItemProps>((props, ref) => (
  <pm-menu-item ref={ref} {...props} />
));
export const PmSegmentedControl = React.forwardRef<HTMLElement, PmSegmentedControlProps>(
  (props, ref) => <pm-segmented-control ref={ref} {...props} />,
);
export const PmSegmentedItem = React.forwardRef<HTMLElement, PmSegmentedItemProps>((props, ref) => (
  <pm-segmented-item ref={ref} {...props} />
));
export const PmToastRegion = React.forwardRef<HTMLElement, PmToastRegionProps>((props, ref) => (
  <pm-toast-region ref={ref} {...props} />
));
export const PmCombobox = React.forwardRef<HTMLElement, PmComboboxProps>((props, ref) => (
  <pm-combobox ref={ref} {...props} />
));
export const PmComboboxOption = React.forwardRef<HTMLElement, PmComboboxOptionProps>(
  (props, ref) => <pm-combobox-option ref={ref} {...props} />,
);
export const PmDatePicker = React.forwardRef<HTMLElement, PmDatePickerProps>((props, ref) => (
  <pm-date-picker ref={ref} {...props} />
));
export const PmTimePicker = React.forwardRef<HTMLElement, PmTimePickerProps>((props, ref) => (
  <pm-time-picker ref={ref} {...props} />
));
export const PmFileUpload = React.forwardRef<HTMLElement, PmFileUploadProps>((props, ref) => (
  <pm-file-upload ref={ref} {...props} />
));

declare global {
  namespace JSX {
    interface IntrinsicElements {
      'pm-button': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmButtonProps;
      'pm-icon': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmIconProps;
      'pm-icon-button': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmIconButtonProps;
      'pm-input': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmInputProps;
      'pm-textarea': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmTextareaProps;
      'pm-checkbox': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> & {
        label?: string;
        checked?: boolean;
      };
      'pm-radio': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> & {
        label?: string;
        checked?: boolean;
      };
      'pm-switch': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> & {
        label?: string;
        checked?: boolean;
      };
      'pm-select': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> & {
        label?: string;
        value?: string;
        disabled?: boolean;
      };
      'pm-card': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> & {
        interactive?: boolean;
      };
      'pm-badge': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> & {
        tone?: string;
      };
      'pm-avatar': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmAvatarProps;
      'pm-progress': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmProgressProps;
      'pm-skeleton': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmSkeletonProps;
      'pm-slider': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmSliderProps;
      'pm-pagination': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmPaginationProps;
      'pm-breadcrumbs': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmBreadcrumbsProps;
      'pm-breadcrumb-item': React.DetailedHTMLProps<
        React.HTMLAttributes<HTMLElement>,
        HTMLElement
      > &
        PmBreadcrumbItemProps;
      'pm-menu': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmMenuProps;
      'pm-menu-item': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmMenuItemProps;
      'pm-segmented-control': React.DetailedHTMLProps<
        React.HTMLAttributes<HTMLElement>,
        HTMLElement
      > &
        PmSegmentedControlProps;
      'pm-segmented-item': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmSegmentedItemProps;
      'pm-toast-region': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmToastRegionProps;
      'pm-combobox': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmComboboxProps;
      'pm-combobox-option': React.DetailedHTMLProps<
        React.HTMLAttributes<HTMLElement>,
        HTMLElement
      > &
        PmComboboxOptionProps;
      'pm-date-picker': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmDatePickerProps;
      'pm-time-picker': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmTimePickerProps;
      'pm-file-upload': React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement> &
        PmFileUploadProps;
    }
  }
}
