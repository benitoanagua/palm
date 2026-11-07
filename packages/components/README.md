# @palm/components

PALM canonical Web Components (`pm-*`).

## Instalación

```sh
npm install @palm/components @palm/tokens
# o
pnpm add @palm/components @palm/tokens
```

@palm/components referencia las variables de diseño de `@palm/tokens`, por lo
que ambos paquetes deben instalarse juntos.

## Uso

```html
<script type="module">
  import '@palm/components/loader';
  import '@palm/tokens/tokens.css';
  import '@palm/tokens/themes.css';
</script>

<pm-button>Hola</pm-button>
```

También se puede cargar solo el loader de elementos personalizados:

```js
import '@palm/components/loader';
```

## Peer dependencies

Ninguna.

## Storybook

Documentación y ejemplos interactivos: https://benitoanagua.github.io/palm/
