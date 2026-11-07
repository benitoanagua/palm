# @palm/vue

PALM Vue component adapters.

## Instalación

```sh
npm install @palm/vue @palm/components @palm/tokens vue
# o
pnpm add @palm/vue @palm/components @palm/tokens vue
```

## Peer dependencies

- vue: ^3.5.0

## Uso

```ts
import { defineCustomElements } from '@palm/components/loader';
import '@palm/tokens/tokens.css';
import '@palm/tokens/themes.css';

defineCustomElements();
```

```vue
<template>
  <pm-icon name="search" aria-label="Buscar" />
</template>
```

## Storybook

Documentación y ejemplos interactivos: https://benitoanagua.github.io/palm/
