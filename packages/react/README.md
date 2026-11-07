# @palm/react

PALM React component adapters.

## Instalación

```sh
npm install @palm/react @palm/components @palm/tokens react react-dom
# o
pnpm add @palm/react @palm/components @palm/tokens react react-dom
```

## Peer dependencies

- react: ^18.3.0
- react-dom: >=18

## Uso

```jsx
import { PmIcon } from '@palm/react';
import '@palm/components/loader';
import '@palm/tokens/tokens.css';
import '@palm/tokens/themes.css';

export default () => <PmIcon name="search" aria-label="Buscar" />;
```

Registra los elementos personalizados una vez en la aplicación:

```js
import '@palm/components/loader';
```

## Cosmos

Documentación y ejemplos interactivos: https://benitoanagua.github.io/palm/
