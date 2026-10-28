# PALM — Decisión de iconografía

## Decisión

PALM usa una familia de iconos por plataforma, con límites explícitos:

- **Web Components y showcase web:** Tabler Icons, consumidos mediante la API pública `<pm-icon>`.
- **Aplicación Android nativa:** Material Symbols, siguiendo las convenciones visuales y de accesibilidad de Android.
- **No se añaden familias alternativas** a estas superficies sin una decisión arquitectónica registrada.

## Contrato web

`@palm/icons` mantiene el registro Tabler y `pm-icon` es el punto de entrada público. Los consumidores no deben importar el registro interno ni introducir otra biblioteca de iconos directamente en componentes PALM.

Los iconos decorativos no deben anunciarse a tecnologías de asistencia. Cuando un icono comunique significado o sea el único contenido de un control, el componente debe exponer un nombre accesible mediante su contrato público.

## Contrato Android

Las pantallas nativas Android deben usar Material Symbols y seguir las convenciones nativas de tamaño, peso, estado y descripción accesible. Esta regla no implica añadir dependencias Android al paquete web de PALM.

## Criterios de aceptación

- Los componentes web usan Tabler a través de `pm-icon`.
- La implementación Android usa Material Symbols.
- Los nombres, registros y dependencias internas de iconos no se convierten en API pública de PALM.
- Los controles con iconos conservan nombre accesible y foco visible.
