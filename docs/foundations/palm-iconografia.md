# Palm — Decisión de iconografía

*(Formato de este documento tomado de una referencia externa (`iconografia.md` de otro proyecto, "Bench") — la estructura de tabla comparativa + lectura razonada + recomendación condicional se adopta; el contenido, las reglas citadas y la herencia (IBM Plex/AS400) son de ese otro proyecto y no aplican acá. Palm tiene su propia herencia: Haiku OS + AmigaOS Style Guide + convención nativa de Android, ya establecidas en `palm-especificacion-completa.md`.)*

## La restricción que hay que nombrar primero

Los íconos reales de Haiku (visibles en las capturas que ya usamos para calibrar forma y bisel: carpeta, papelera, correo, el ícono de WebPositive) son **ilustraciones a color con volumen**, no trazos monocromáticos. Ningún set de íconos moderno mantenido activamente reproduce ese lenguaje — es una técnica de ilustración de icon artists de comienzos de los 2000, no una librería descargable. Clonarlo significaría encargar ilustración custom para cada ícono del sistema, lo cual contradice directamente la Decisión propia #5 de Palm (prueba KISS: no se agrega complejidad que no se puede sostener). Así que la comparación real no es "¿qué tan cerca está de Haiku?" — ninguna opción lo está — sino "¿qué tan bien sigue la lógica de herencia que Palm sí puede sostener hoy: Android nativo primero, convención citable después?".

## Comparación contra la filosofía de Palm

| Criterio | **Material Symbols** (Google) | **Tabler** | **Lucide** | **Phosphor** |
|---|---|---|---|---|
| Herencia con Palm | **Directa** — es literalmente el set que Android/Compose ya trae, cero dependencia nueva | Ninguna | Ninguna | Ninguna |
| Dominio de origen | Android/Material, apps móviles nativas | Admin panels / SaaS backoffice | Multipropósito general | Multipropósito, apps de consumo |
| Estilo de trazo | Variable (peso/relleno/grado ajustable vía ejes de fuente variable) | Stroke uniforme 2px | Stroke uniforme 2px | Stroke 1.5px, **6 pesos distintos** |
| Tamaño del set | ~2,500+ | ~5,800+ | ~1,500 | ~9,000+ (×6 pesos) |
| Riesgo de ambigüedad | Bajo (los ejes son continuos, no variantes discretas a elegir mal) | Bajo | Bajo | **Alto** (6 pesos invita inconsistencia entre pantallas) |
| Costo de integración en Compose | **Nulo** — `androidx.compose.material.icons` ya lo trae | Alto (SVG externo, sin soporte nativo de Compose) | Alto (ídem) | Alto (ídem) |
| Encaja con el bisel/alto contraste ya decidido | Puede subirse a peso "Bold" para verse más sólido, coherente con el bisel | Requiere grosor de trazo manual mayor al default | Requiere lo mismo | Ídem, con el riesgo extra de mezclar pesos sin querer |

## Mi lectura

**Material Symbols es la opción que no compite con una decisión que Palm ya tomó, no una preferencia estética nueva.** Palm ya decidió, como una de sus 5 decisiones propias, delegar todo lo que Android resuelve de fábrica en vez de importar algo externo (así se resolvieron App Shortcuts, Quick Settings, persistencia de estado, notificaciones). Un ícono no es distinto: `androidx.compose.material.icons` ya está en el proyecto en el momento en que alguien agrega la dependencia de Compose — no es "una librería más para evaluar", es infraestructura que ya está ahí. Además, su eje de peso variable permite subir la iconografía a un trazo más grueso/relleno que combina mejor con el bisel sólido que ya calibramos, en vez de quedar como líneas finas flotando sobre botones biselados.

**El costo real de Material Symbols:** no tiene ninguna relación visual con la identidad Haiku/AmigaOS del resto del sistema — es explícitamente Android genérico. Eso es una pérdida real de coherencia narrativa (la misma tensión que Bench describe con Carbon vs. la narrativa IBM, pero inversa: acá la opción con más coherencia de herencia con el *sistema operativo objetivo* es la que menos coherencia tiene con la *inspiración visual* del proyecto).

**Tabler sería el pragmático** si en algún momento Palm deja de vivir solo en Compose y necesita los mismos SVG en el mockup HTML, en una web companion, o en cualquier lugar sin acceso al paquete de Compose — cobertura enorme, mismo paradigma de trazo uniforme que ya usamos a mano en el mockup (el ícono de búsqueda del Omnibar es, sin saberlo, un trazo Tabler-compatible). Migrar de los SVG hechos a mano a Tabler sería casi gratis.

**Lucide** no aporta nada que Tabler no dé ya con más cobertura — no hay razón para preferirlo sobre Tabler en este proyecto.

**Phosphor se descarta por la misma razón que en Bench**: 6 pesos es una invitación estructural a que dos pantallas de la misma app usen pesos distintos sin que nadie lo note, lo cual choca directo con la Decisión propia #4 de Palm ("un atajo, un solo significado" — el mismo principio de "una sola forma correcta" aplica a iconografía).

## Mi recomendación

Si priorizás **cero dependencia nueva y aprovechar lo que Compose ya trae**: **Material Symbols**, con el eje de peso subido a Bold/Medium (no el default Regular, que se ve demasiado fino contra el bisel). Es la opción que más defendería, porque no agrega nada que mantener — literalmente ya está en el proyecto.

Si priorizás que el mismo ícono se vea idéntico en el mockup HTML, en Compose, y en cualquier prototipo futuro sin depender de qué stack se esté usando: **Tabler**, como reemplazo directo de los SVG hechos a mano que ya hay en el mockup, sin cambiar nada del enfoque técnico (sigue siendo `stroke`, no paths rellenos).
