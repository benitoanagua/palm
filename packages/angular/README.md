# @palm/angular

PALM Angular component registration adapter.

## Instalación

```sh
npm install @palm/angular @palm/components @palm/tokens @angular/core
# o
pnpm add @palm/angular @palm/components @palm/tokens @angular/core
```

## Peer dependencies

- @angular/core: >=17

## Uso

```ts
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { defineCustomElements } from '@palm/components/loader';
import '@palm/tokens/tokens.css';
import '@palm/tokens/themes.css';

defineCustomElements();

@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  ...
})
export class AppModule {}
```

## Storybook

Documentación y ejemplos interactivos: https://benitoanagua.github.io/palm/
