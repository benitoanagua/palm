# @palm/tokens

PALM design tokens and themes (CSS custom properties).

## Instalación

```sh
npm install @palm/tokens
# o
pnpm add @palm/tokens
```

## Uso

Desde HTML:

```html
<link rel="stylesheet" href="node_modules/@palm/tokens/dist/tokens.css" />
<link rel="stylesheet" href="node_modules/@palm/tokens/dist/themes.css" />
<link rel="stylesheet" href="node_modules/@palm/tokens/dist/type-scale.css" />
```

Desde JS (bundler):

```js
import '@palm/tokens/tokens.css';
import '@palm/tokens/themes.css';
import '@palm/tokens/type-scale.css';
```

Los tokens se expone también como objeto JS:

```js
import { palmTokens } from '@palm/tokens';
```

## Peer dependencies

Ninguna.

## Storybook

Documentación y ejemplos interactivos: https://benitoanagua.github.io/palm/
