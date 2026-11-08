// JSX types for PALM custom elements (pm-*).
// React 19 exposes JSX under React.JSX, so we augment the `react` module
// instead of declaring a global `JSX` namespace (which React 19 no longer uses).
// The `pm-${string}` index signature lets any pm-* tag accept standard HTML
// attributes plus the PALM custom events and component-specific props
// (variant, tone, checked, …), without touching normal tags (span, p, div, …)
// which keep their own React intrinsic typings.
import * as React from 'react';

declare module 'react' {
  namespace JSX {
    interface IntrinsicElements {
      [tagName: `pm-${string}`]: React.DetailedHTMLProps<
        React.HTMLAttributes<HTMLElement>,
        HTMLElement
      > & {
        onPmPress?: (event: CustomEvent) => void;
        onPmClose?: (event: CustomEvent) => void;
        onPmChange?: (event: CustomEvent) => void;
        [prop: string]: unknown;
      };
    }
  }
}
