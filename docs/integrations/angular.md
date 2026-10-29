# Angular Integration (`@palm/angular`)

Palm Web Components work natively in Angular via the `CUSTOM_ELEMENTS_SCHEMA`. The `@palm/angular`
package provides a thin module wrapper and type exports — no Stencil behavior is duplicated in Angular.

## Installation

```bash
pnpm add @palm/components @palm/tokens @palm/angular
```

## Setup

### Option A — `PalmModule` (recommended for Angular < 16 / NgModule apps)

Import `PalmModule` in the consuming `NgModule`. Call `registerPalmElements()` once at app
initialisation to define all custom elements.

```ts
// app.module.ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { PalmModule, registerPalmElements } from '@palm/angular';

registerPalmElements(); // defines all pm-* custom elements

@NgModule({
  imports: [BrowserModule, PalmModule],
  bootstrap: [AppComponent],
})
export class AppModule {}
```

### Option B — Standalone components (Angular 16+)

Use `CUSTOM_ELEMENTS_SCHEMA` directly in a standalone component:

```ts
// my.component.ts
import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { registerPalmElements } from '@palm/angular';

registerPalmElements();

@Component({
  selector: 'app-my',
  standalone: true,
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  template: `<pm-button>Save</pm-button>`,
})
export class MyComponent {}
```

## Token setup

Import the design tokens stylesheet in your application root:

```ts
// main.ts
import '@palm/tokens';
```

Or in `angular.json` under `styles`:

```json
"styles": ["node_modules/@palm/tokens/dist/tokens.css"]
```

## Template binding

Palm components expose attributes and properties. Angular can bind to both:

```html
<!-- attribute binding -->
<pm-button variant="primary" [disabled]="isSaving">Save</pm-button>

<!-- property binding (arrays, objects) -->
<pm-data-table [rows]="tableRows" [columns]="tableColumns"></pm-data-table>

<!-- event binding -->
<pm-button (pmPress)="onSave($event)">Save</pm-button>
<pm-input (pmInput)="onInput($event)" [value]="formValue"></pm-input>
```

## Type exports

`@palm/angular` exports:

| Export                        | Description                                      |
| ----------------------------- | ------------------------------------------------ |
| `PalmModule`                  | `NgModule` with `CUSTOM_ELEMENTS_SCHEMA`         |
| `PALM_CUSTOM_ELEMENTS_SCHEMA` | Re-export of `CUSTOM_ELEMENTS_SCHEMA`            |
| `registerPalmElements()`      | Calls `defineCustomElements()` once (idempotent) |
| `PalmElementTagName`          | Union type of all `pm-*` tag names               |
| `PalmCustomEvent<T>`          | Typed wrapper for Palm `CustomEvent` payloads    |

## Two-way data binding (ngModel)

Palm form controls dispatch native `input` and `change` events in addition to Palm-specific events.
`ngModel` does **not** work out of the box — use reactive forms or explicit bindings:

```ts
// In component class:
value = '';
onInput(event: Event): void {
  this.value = (event as CustomEvent<{ value: string }>).detail.value;
}
```

```html
<pm-input [value]="value" (pmInput)="onInput($event)"></pm-input>
```

## Change detection

Palm components mutate their Shadow DOM internally; Angular's change detection is unaffected.
Use `ChangeDetectionStrategy.OnPush` freely — component updates driven by property/attribute
changes will render correctly without triggering Angular's zone.

## Server-Side Rendering (SSR / Angular Universal)

`registerPalmElements()` is guarded by `typeof window === 'undefined'` and is a no-op on the
server. Components render as empty custom elements during SSR and hydrate on the client.

## Troubleshooting

| Symptom                                                | Fix                                                           |
| ------------------------------------------------------ | ------------------------------------------------------------- |
| `Can't bind to 'rows' since it isn't a known property` | Add `CUSTOM_ELEMENTS_SCHEMA` or import `PalmModule`           |
| Custom elements not upgrading                          | Ensure `registerPalmElements()` is called before first render |
| Events not firing                                      | Use `(pmPress)` not `(click)` for Palm-managed interactions   |
| Styles not applied                                     | Confirm `@palm/tokens/dist/tokens.css` is imported            |
