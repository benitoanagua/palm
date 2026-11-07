# @palm/primitives

PALM layout and surface primitives (`pm-stack`, `pm-surface`).

## Instalación

```sh
npm install @palm/primitives @palm/tokens
# o
pnpm add @palm/primitives @palm/tokens
```

@palm/primitives referencia las variables de diseño de `@palm/tokens`, por lo
que ambos paquetes deben instalarse juntos.

## Uso

```html
<script type="module">
  import '@palm/primitives/loader';
  import '@palm/tokens/tokens.css';
  import '@palm/tokens/themes.css';
</script>

<pm-stack gap="var(--pm-space-4)">
  <pm-surface>Contenido</pm-surface>
</pm-stack>
```

## Peer dependencies

Ninguna.

## Cosmos

Documentación y ejemplos interactivos: https://benitoanagua.github.io/palm/
