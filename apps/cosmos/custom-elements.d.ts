// JSX types for PALM custom elements (pm-*).
// React 19 spreads unknown props onto the DOM element, so this index
// signature lets any pm-* tag accept arbitrary props without per-tag types.
declare namespace JSX {
  interface IntrinsicElements {
    [tagName: `pm-${string}`]: React.DetailedHTMLProps<
      React.HTMLAttributes<HTMLElement>,
      HTMLElement
    > & {
      onPmPress?: (event: CustomEvent) => void;
      onPmClose?: (event: CustomEvent) => void;
      onPmChange?: (event: CustomEvent) => void;
    };
  }
}
