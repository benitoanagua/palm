# Palm — Design System para Power Users Mobile-First

*(Palm: nombrado por lo que cabe en la palma de una mano, no por un sistema operativo — el power user que a veces no tiene una computadora cerca, solo el teléfono que sostiene con una mano mientras hace todo lo demás con la otra. No es un clon de Haiku OS ni un ERP de escritorio achicado — es lo que queda de esa tradición cuando la única pantalla disponible es la del bolsillo.)*

*(Estructura de este documento tomada como referencia de formato de otro proyecto, "Bench" — la organización en Filosofía → Reglas → Tokens → Accesibilidad → Catálogo de patrones → Research → Checklist se adopta porque ya demostró servir para dejar de repetir contenido entre secciones. El contenido es 100% de Palm.)*

Estructura: **0 Filosofía** (por qué existe) → **1 Reglas inviolables** → **2 Tokens** (fuente única de verdad visual) → **3 Accesibilidad** → **4 Catálogo de patrones** → **5 Research** (de qué sistema viene cada decisión) → **6 Checklist**.

---

## 0. Filosofía — por qué existe Palm

Palm existe para que un power user que a veces no tiene una computadora cerca pueda seguir siendo productivo desde el teléfono, sin que la interfaz le pida gestos imposibles con una mano ni le esconda funciones "para simplificar". La multitarea se resuelve con cards, no con pestañas de escritorio; los comandos viven en un Omnibar accesible con el pulgar, no en atajos de teclado; la densidad de información es una elección del perfil del usuario, no una concesión del tamaño de pantalla; y la conectividad intermitente es la norma, no la excepción — toda acción se ve reflejada de inmediato, se sincroniza cuando puede, y nunca miente sobre en qué estado está. Palm optimiza para el momento en que no hay plan B (sin compu, sin señal, con una mano ocupada), no para la foto de la primera pantalla.

### 0.1 La razón de fondo

La mayoría de las apps mobile están diseñadas asumiendo que el teléfono es el dispositivo secundario — la notificación que te lleva de vuelta a la compu. Palm parte de la premisa contraria: **el teléfono es a veces el único dispositivo disponible**, y la interfaz tiene que sostener trabajo real, no solo consulta rápida.

| Problema real del power user mobile-only | Cómo responde Palm |
|---|---|
| No tiene con qué "dejar algo abierto" al lado — el teléfono es la única pantalla | Cards de tareas (4.2), Hub centralizado (4.3) |
| Su conexión es intermitente, no ausente ni constante | Sync en 3 estados + conflicto (4.3), escritura local optimista |
| Escribe rápido y busca funciones sin navegar menús | Omnibar vía FAB (4.1) |
| A veces usa teclado plegable, a veces solo el pulgar | Atajos de teclado físico + FAB alcanzable con una mano |
| Necesita ver el dato crítico (saldo, estado) sin abrir nada | Densidad glanceable, contraste AAA en datos financieros (2.7) |
| Un error de lectura de un monto le puede costar dinero real | Extensión financiera (2.7), tipografía tabular (2.4) |
| No puede permitirse perder una tarea a medio hacer si el sistema mata el proceso | Persistencia en dos capas (4.6) |
| Ya aprendió cómo funciona esto — un cambio de comportamiento sin razón le cuesta tiempo | Reglas de Categoría B (1), delegación a convención en vez de reinventar (5) |

Esta tabla es el criterio de aceptación de cualquier propuesta nueva — incluidas las que llegan como prompt externo. Antes de aplicar algo, la pregunta es a cuál de estos problemas reales le resuelve algo a alguien que abre esta app parado en la calle, sin señal, con una mano ocupada.

---

## 1. Reglas inviolables

Nueve reglas en dos categorías. La implementación concreta de las reglas 7-9 vive en **2 · Tokens**; acá solo la ley y cuándo se viola.

### Categoría A — Comportamiento (las 5 decisiones propias de Palm)

Todo lo que no está en esta lista se resuelve citando convención de plataforma (Nielsen, Ley de Fitts, WCAG, Material Design 3, guía de arquitectura Android, APIs nativas) — no se redocumenta acá.

1. **Omnibar como comando universal único.** Un solo punto de entrada para registros y funciones, accesible por FAB persistente, nunca por gesto (los gestos de Android cambian entre versiones/OEMs). *Se viola cuando* una función solo es alcanzable navegando menús y no aparece en el índice del Omnibar.
2. **Todo estado de sincronización es honesto y visible en 3 niveles + conflicto** (guardado local / enviado-esperando / confirmado / conflicto-revisar), nunca colapsado en un spinner genérico. *Se viola cuando* dos estados distintos comparten la misma representación visual, o un conflicto se resuelve en silencio sin que el usuario decida.
3. **"Power to the User": el modo experto coexiste con el modo simple, nunca lo reemplaza.** Densidad alta es una elección del perfil, no del tamaño de pantalla. *Se viola cuando* el sistema infiere densidad por el dispositivo en vez de por la preferencia explícita del usuario.
4. **Un mismo gesto o color tiene un solo significado en todo el sistema.** *Se viola cuando* el mismo swipe hace cosas distintas según la pantalla, o un color usado para "interactivo" se reutiliza para un estado no interactivo (por eso `--pending` nunca puede ser azul: azul ya es `--action`).
5. **Cierre explícito, nunca silencioso.** Una tarea o card solo desaparece por acción directa del usuario (swipe/botón) o por completarse — nunca por decisión del sistema sin avisar. *Se viola cuando* el proceso de Android muere y el usuario percibe eso como "el sistema cerró mi tarea".

### Categoría B — Visual, autoral, no negociable

6. **Forma: clon deliberado de ActiveAdmin (Ruby on Rails)** *(05/sep, reemplaza tres direcciones visuales anteriores: calibración Haiku, escala Material/HIG, piel plana tipo panel profesional — el usuario pidió expresamente "debería ser un clon")* → **ver Tokens 2.2**. Radio único de 3px, degradé glossy en botones, header oscuro, tags de estado en color sólido. *Se viola cuando* un componente nuevo no sigue el vocabulario visual de AA documentado en 2.2/4, o inventa un radio/degradé fuera de lo ya establecido.
7. **La única desviación permitida al clon es la evidencia de accesibilidad ya construida** → **ver Tokens 2.1/2.2**. Paleta semántica verificada contra daltonismo (no el azul histórico de AA) y bordes con contraste real medido (no el gris casi invisible de AA). *Se viola cuando* alguien "por fidelidad al clon" revierte esta paleta o estos bordes a los valores originales de AA.
8. **Color verificado con simulación real de los 3 tipos de daltonismo (protanopia, deuteranopia, tritanopia), nunca solo "parece distinguible".** Contraste mínimo AAA (7:1) en todo texto de dato financiero → **ver Tokens 2.1**. *Se viola cuando* se agrega un color semántico sin correr la simulación contra los 3 tipos.
9. **Los estados semánticos se comunican como `status_tag`: rectángulo de esquina chica, color sólido, texto blanco en mayúsculas** *(05/sep, reemplaza el tag en píldora con tinte de la iteración anterior — así se ven los status_tag reales de AA)* → **ver Tokens 2.1/4.3**. *Se viola cuando* un estado nuevo usa tinte suave en vez de color sólido, o depende solo de un ícono sin texto.

### Tabla de decisión rápida

| Pregunta | Si la respuesta es sí → |
|---|---|
| ¿La función solo es alcanzable por un gesto, sin equivalente en el Omnibar? | No se acepta — agregar al índice del Omnibar primero |
| ¿Es un color semántico nuevo? | Correr la simulación de 2.1 contra los 3 tipos de daltonismo antes de aprobarlo |
| ¿Es un radio, sombra o curva de movimiento nueva? | Tomarlo de Tokens (2.2/2.4/2.5), nunca inventarlo a ojo |
| ¿El módulo edita un saldo o registra una transacción real? | Activa la extensión financiera (2.7) |
| ¿Depende de que el proceso de Android siga vivo en memoria? | No es persistencia válida — debe sobrevivir a `Room`/`DataStore` (4.6) |

---

## 2. Tokens de Palm

**Fuente de verdad:** `palm-mobile-mockup.html` / `palm-demo-definitiva.html` (idénticos salvo el `<title>`) y `palm-demo-componentes-activeadmin.html` (componentes adicionales: tabla índice, formulario, detalle). Esta sección se reescribió completa el 05/sep para describir el clon de ActiveAdmin (Ruby on Rails) tal como quedó implementado — ya no describe ninguna piel anterior.

### 2.1 Color — semántico, verificado contra daltonismo, con el tratamiento de AA

```css
--header-bg:#2c2c2c; --header-text:#ffffff; --header-text-dim:#b8b8b8;
--page-bg:#f1f2f4; --surface:#ffffff; --surface-alt:#f5f5f5; --surface-hover:#f9f9f9;
--border-color:#727272;       /* 4.8:1 */
--border-color-light:#949494;  /* 3.0:1 */
--text-primary:#333333; --text-secondary:#666666; --text-strong:#1a1a1a;
--on-color:#ffffff;

--action:#0b5a92;     /* 7.26:1 con --on-color encima */
--action-strong:#00426f;
--pending:#8d410b;    /* 7.26:1 */
--confirmed:#086335;  /* 7.38:1 */
--conflict:#ae0e20;   /* 7.28:1 */
```

**Verificación obligatoria (Regla 8):** los 4 colores semánticos se simularon con matrices de Machado-Oliveira-Fernandes para protanopia, deuteranopia y tritanopia. Queda una debilidad matemática irreductible bajo tritanopia entre `--action`/`--confirmed` (dist. ~25) — es el límite real de 4 colores planos, no un descuido. La red de seguridad final es la Regla 9: cada estado se comunica como `status_tag` con texto, nunca solo color.

**Por qué color sólido y no tinte (implementa Regla 9):** los `status_tag`/`mini-tag`/insignias son rectángulos de esquina chica (`--radius-base`) con el color semántico de **fondo sólido** y texto en `--on-color` (blanco), mayúsculas, negrita — así se ven los tags reales de ActiveAdmin. Contraste blanco-sobre-color ya verificado arriba (7.26-7.38:1), no hace falta una variante "on-tint" separada porque no hay tinte: el texto sale contra el color puro.

**Por qué estos bordes y no los de AA (implementa Regla 7):** el gris real de ActiveAdmin (~`#e5e5e5`) da 1.1-1.4:1 contra blanco — invisible para baja visión, muy por debajo del mínimo WCAG 1.4.11 (≥3:1 para límites de componentes). Se reusan los valores ya corregidos en una iteración anterior: `--border-color` 4.8:1 para bordes de control/card, `--border-color-light` 3.0:1 para divisores internos.

### 2.2 Forma, degradé y sombra (implementa Reglas 6-7)

```css
--radius-base:3px; /* único radio del sistema — así es AA, sin escala */
--focus-ring: 0 0 0 2px color-mix(in srgb, var(--action) 30%, transparent);
```

**Degradé glossy en botones** (marca de fábrica de AA, vuelve después de haberlo prohibido en una iteración anterior — el clon lo exige):

```css
background: linear-gradient(to bottom, #fdfdfd, #e2e2e2);   /* botón default */
background: linear-gradient(to bottom, color-mix(in srgb, var(--action) 70%, white), var(--action)); /* botón primary */
box-shadow: 0 1px 0 rgba(255,255,255,.7) inset;               /* resalto superior */
text-shadow: 0 1px 0 rgba(255,255,255,.6);                    /* letra con relieve leve */
```

**Sombras** — no están tokenizadas globalmente (cada componente flotante define la suya inline: `.phone` usa `0 1px 3px rgba(0,0,0,.2)`, `.peek` usa `0 2px 6px rgba(0,0,0,.25)`, `.modal` usa `0 -2px 8px rgba(0,0,0,.3)`). Es deuda técnica menor, no un problema de diseño — ver Checklist.

**Header oscuro con acento de color:** `header{background:var(--header-bg); border-bottom:3px solid var(--action);}` — el acento inferior es lo único que conecta el header con la paleta semántica del resto del sistema.

### 2.3 Movimiento

```css
--duration-base: 180ms;
--easing-standard: ease-in-out;
```

El swipe-to-dismiss de las cards lee estos tokens en tiempo real vía `getComputedStyle` en vez de duplicar los números en JS. `prefers-reduced-motion: reduce` colapsa toda transición/animación a ≤0.01ms.

### 2.4 Tipografía

`"Helvetica Neue", Helvetica, Arial, sans-serif` a 13px base — la pila clásica de Rails/AA, no `system-ui`. Monoespaciada (`ui-monospace`/`SFMono`/Consolas) para todo dato tabular (montos, cuentas, timestamps, con `font-variant-numeric:tabular-nums`). Sin fuentes externas — no depender de una red que puede no estar (offline-first).

### 2.5 Iconografía — decisión resuelta

**Material Symbols.** Cero dependencia nueva (ya viene con `androidx.compose.material.icons`), delega a convención Android igual que el resto de la Categoría A. Comparación completa en `palm-iconografia.md`; Tabler queda como alternativa reversible, no descartada. En el mockup HTML los íconos se mantienen dibujados a mano en SVG de trazo simple.

### 2.6 Escala — densidad y viewport

**3 niveles, cíclicos desde un único control** (ver 4.6 para el detalle de uso previsto de cada uno):

| Nivel | Card `min-height` | Fila del Hub, padding | `row-title` |
|---|---|---|---|
| Compacto | 104px | 6px 8px | 12px |
| Normal | 132px | 10px 8px | 13px |
| Aireado | 160px | 16px 14px | 15px / line-height 1.4 |

El vocabulario visual de AA no cambia entre niveles — solo espaciado, tamaño de letra y `line-height`. Mobile es el form factor primario.

### 2.7 Extensión financiera (opt-in)

**Criterio de calificación:** (1) muestra un saldo que puede cambiar de estado; (2) escribe o modifica un registro real; (3) un error de lectura numérica tendría consecuencia monetaria directa.

**Ya implementado:** contraste AAA (7:1+) en los 4 colores semánticos; formato tabular con separador de miles (`$1.284.560,00`); insignia "2FA activo" en el header (reutiliza `--action`); modal crítico con checkbox de entendimiento obligatorio antes de habilitar la confirmación de una acción irreversible.

### 2.8 Componentes de listado y formulario (05/sep, nuevo — de `palm-demo-componentes-activeadmin.html`)

| Componente | Vocabulario visual AA aplicado |
|---|---|
| Breadcrumb | Texto tenue sobre el header oscuro, separador `/`, último tramo en blanco |
| Scopes (pestañas de filtro) | Igual que la tabbar: texto gris, activo en `--action` con borde inferior de color, contador en monoespaciado |
| Flash notice / alert | Banda de color con fondo tinte muy claro y texto del color semántico — única superficie del sistema que sigue usando tinte en vez de sólido, porque un flash de página completa en color sólido sería agresivo |
| `index_table` | Encabezados grises con texto en mayúsculas chicas, flecha de orden en `--action`, columna de checkbox, filas alternadas + hover, `mini-tag` de estado en color sólido |
| Barra de acciones por lote | Aparece solo con selección activa, fondo ámbar claro (color de "atención", no semántico) |
| Paginación | Números con borde, actual en `--action` sólido |
| Panel de filtros | Caja con franja de título gris, colapsable, mismos `.field`/`label`/`input` que el formulario |
| Vista de detalle | Tabla de atributos: `<th>` gris a la izquierda, valor a la derecha, sin bordes verticales |
| Formulario | `fieldset`+`legend` con franja gris, campos con hint chico debajo, acciones "Crear/Cancelar" al pie |

## 3. Accesibilidad: foco visible

```css
button:focus-visible, input:focus-visible, .row:focus-visible {
  outline: 2px solid var(--action);
  outline-offset: 1px; /* WCAG 2.4.7 */
}
.overlay-header input:focus {
  border-color: var(--action);
  box-shadow: var(--focus-ring); /* anillo de color, patrón de campo enfocado */
}
```

- Contraste mínimo 4.5:1 en texto general, **7:1 (AAA)** en todo dato financiero (2.7), verificado con cálculo real, no estimado.
- Ningún estado semántico depende solo del color — siempre tag con texto, nunca un color de fondo solo (Regla 9/A4).
- Las 5 filas custom del Hub (`div[role="button"]` con `tabindex="0"`) responden a Enter/Espacio vía un listener delegado, no solo a mouse — patrón ARIA APG estándar para controles custom que no son un `<button>` nativo.
- `prefers-reduced-motion` colapsa toda transición/animación a ≤0.01ms.

---

## 4. Catálogo de patrones

### 4.1 Navegación: Omnibar

**Patrón:** FAB persistente (nunca gesto — los gestos de Android cambian entre versiones/OEMs) que abre una búsqueda de pantalla completa sobre registros y funciones, agrupados y filtrados en vivo. Sin sintaxis de campo:valor ni macros todavía — eso existe en sistemas de referencia (Oracle EBS, Bench) pero Palm no lo necesitó hasta ahora; queda como extensión posible, no como deuda.

**Por qué FAB y no gesto (decisión cerrada):** swipe-down choca con el notification shade; long-press en home choca con el asistente de voz en varios OEMs; swipe-up choca con "ir a home" en gesture nav. El FAB es la única superficie que no depende de qué modo de navegación tenga activado el dispositivo.

**Tratamiento visual:** el input es monoespaciado (line de comandos, no buscador genérico) con un `box-shadow` interior que lo hunde levemente — como un campo de búsqueda clásico de Rails. Cada resultado tiene borde propio (filas alternadas + hover en azul claro), agrupado por "Registros"/"Funciones" con una etiqueta de sección gris, igual que un `index_table` de dos secciones.

### 4.2 Multitarea: Cards

**Patrón** *(herencia: grid de pestañas de Chrome para Android, no un sistema de cards propio)*: cada tarea es una card con miniatura real de su último estado. Umbral de **6 cards visibles sin scroll** (grilla 2×3); de 7 a 12 se agrupan por relación (mismo cliente/pedido) en un tile colapsable; más de 12, el sistema sugiere cerrar tareas completadas sin auto-cerrar (Regla A5).

**Swipe-to-dismiss:** convención nativa de Android (RecyclerView/ItemTouchHelper). El botón "×" se mantiene como alternativa accesible — no todo el mundo puede gesticular.

### 4.3 Hub: sync, conflicto y Peek

**Hub centralizado** *(convención: NotificationChannel nativo de Android)*: todas las fuentes de interrupción en un solo lugar, con 3 niveles de detalle (resumen → expandir → actuar).

**Estados de sync (Regla A2):** cada estado es un `status_tag` completo en color sólido — gris para guardado local, ámbar (`--pending`) para enviado-esperando, verde (`--confirmed`) para confirmado. Un 4° estado, **conflicto-revisar** (`--conflict`), se resuelve con comparación de 2 columnas ("Tu versión" / "Versión del servidor") y 3 acciones explícitas: mantener la mía, mantener la del servidor, revisar campo por campo. Ninguna resolución es automática.

**Peek** *(convención: long-press preview estándar de Android)*: mantener presionado revela un vistazo temporal y no-destructivo — si se suelta sin actuar, no marca como leído ni consume el elemento. Es una caja blanca simple con borde y sombra (`0 2px 6px rgba(0,0,0,.25)`) — sin translúcido, sin esquina especial: el mismo `--radius-base` de 3px que todo lo demás. AA no tiene materiales translúcidos.

### 4.4 Acciones irreversibles

**Modal crítico**, distinto del modal de conflicto: borde superior de 3px en `--conflict` (el modal de conflicto usa el mismo grosor en `--action`) — la diferenciación es de color, no de forma, siguiendo el vocabulario de AA donde el énfasis se marca con una franja de color, no con efectos adicionales. El botón de confirmar arranca deshabilitado y solo se habilita al marcar un checkbox de entendimiento explícito del monto/acción. Fricción visual deliberada, reservada a lo que de verdad no se puede deshacer.

### 4.5 Persistencia

**Dos capas, nunca una sola:**
1. Estado de UI efímero (tab activo, scroll) → `ViewModel` + `SavedStateHandle`.
2. Estado real de la tarea (formulario a medio llenar) → `Room`/`DataStore`, escrito en cada cambio significativo.

*Por qué:* `SavedStateHandle` se pierde si el usuario fuerza el cierre desde Ajustes en algunos OEMs — el contenido real de una tarea no puede depender de eso (Regla A5).

### 4.6 Modos y feedback ("Power to the User")

**3 niveles de densidad (05/sep, implementado)**, cíclicos desde un único control, elección explícita del usuario, nunca inferida por tamaño de pantalla (Regla A3):

| Nivel | Uso previsto |
|---|---|
| Compacto | Uso intensivo, máxima densidad glanceable |
| Normal | Uso general (default) |
| Aireado | Lectura relajada, primer uso, o cualquier momento en que el usuario prefiera más aire — ya no está atado a un flujo de onboarding que no existe |

El vocabulario visual de AA (franja de título, borde, `status_tag` sólido) es idéntico en los 3 niveles — solo cambian espaciado, tamaño de letra y `line-height`. El modo experto (densidad compacta + Omnibar + atajos de teclado físico) coexiste con los otros dos — nunca los reemplaza.

### 4.7 Listado y formulario (`palm-demo-componentes-activeadmin.html`)

Demo separada que profundiza el vocabulario de AA en componentes que la demo principal no necesitaba: **breadcrumb** (ruta sobre el header oscuro), **scopes** (pestañas de filtro con contador, mismo lenguaje que la tabbar), **flash notice/alert** (única superficie del sistema en tinte suave — un flash de página completa en color sólido sería agresivo), **`index_table`** completa (encabezados ordenables, columna de checkbox, `mini-tag` de estado, filas alternadas), **barra de acciones por lote** (aparece solo con selección activa), **paginación**, **panel de filtros** colapsable, **vista de detalle** (tabla de atributos label/valor) y **formulario** (`fieldset`+`legend`+hints). Ver 2.8 para la tabla de mapeo componente↔vocabulario visual.

### 4.8 Dashboard (`palm-demo-dashboard-activeadmin.html`)

La pantalla de inicio real de AA, sin equivalente en las otras demos: **stat callouts** (número grande monoespaciado, coloreado por estado semántico), panel de **saldo** destacado, panel de **actividad reciente** (ícono circular de color sólido + texto + timestamp relativo — reutiliza los mismos 3 colores semánticos que el resto del sistema), **accesos rápidos** (grilla 2×2 de enlaces separados por líneas finas) y **comentarios** (feature real de páginas show de AA: hilo + `textarea` + botón enviar). Sin toggle de densidad propio.

### 4.9 Componentes de interacción (`palm-demo-adicionales-activeadmin.html`)

Cuarta demo, componentes sin equivalente en ninguna de las anteriores:

- **Offcanvas**: drawer de navegación desde el header (hamburguesa), desliza desde la izquierda con backdrop — es la traducción mobile de la navegación de AA (tabs horizontales o sidebar en desktop).
- **Accordion**: secciones colapsables con chevron rotado — sin precedente directo en AA, se construyó con su mismo vocabulario (franja de header por sección, borde fino).
- **Dropdown de acciones (⋮)**: menú contextual por fila, con la acción destructiva siempre al final y en `--conflict` — patrón real de los botones de acción de `index_table` en AA.
- **Tooltip**: burbuja oscura con flecha, activada por toque en un ícono de ayuda — no es un patrón de AA (AA usa `title` nativo del navegador), pero es necesario en mobile donde no existe hover.
- **Toast**: notificación temporal con acento de color a la izquierda, se autodesaparece — reemplaza al `window.confirm`/alert nativo del navegador que AA usa por defecto (Rails UJS `data-confirm`), inapropiado para un diseño propio.
- **Modal de confirmación simple**: centrado, sin franjas ni checkbox — deliberadamente más liviano que el modal de conflicto (4.3) y el modal crítico (4.4), reservado a confirmaciones destructivas comunes (no financieras) donde pedir un checkbox de entendimiento sería fricción excesiva (violaría la Regla A5 por el lado del exceso).

### 4.10 Formularios y estados (`palm-demo-formularios-estados-activeadmin.html`)

Quinta demo, 5 pantallas navegables:

- **Login**: patrón clásico Devise/Rails — caja blanca centrada sobre fondo oscuro (`--header-bg`), campos con el mismo `box-shadow` hundido que el resto de los inputs del sistema, "recordarme" con checkbox nativo, link de recuperación de contraseña.
- **Estado vacío**: ícono + título + descripción + acción de salida, dentro de un `.panel` normal — no es un componente nuevo, es el mismo panel con contenido centrado.
- **Formulario anidado dinámico**: agregar/quitar filas de verdad (no solo visual — el total recalcula en JS), patrón real de `cocoon`/`nested_form` de AA para asociaciones `has_many`. El botón "+ Agregar" usa borde punteado — excepción deliberada a una decisión de minimalismo de una iteración visual anterior ("sin bordes punteados en ningún componente"), ya superada por el pivot a AA; acá el punteado comunica algo real: "esto no es un dato, es un control para crear uno".
- **Exportación**: enlaces CSV/JSON/XML siempre visibles arriba del índice — así los expone AA por default, nunca escondidos en un menú de opciones.
- **Switches**: alternativa a los checkboxes ya usados (modal crítico, "recordarme") para ajustes de tipo on/off — pista visual de estado inmediato (verde=activo) sin depender de leer una palabra.

---

## 5. Sistemas y convenciones de origen (research base)

*Nota (05/sep): las filas marcadas con † fueron la base de pieles visuales ya reemplazadas (Haiku, Material 3, panel plano) — se mantienen como historial, no como fuente vigente. ActiveAdmin es la fuente activa de la piel actual.*

| Fuente | Qué aportó | Dónde vive |
|---|---|---|
| **ActiveAdmin (Ruby on Rails)** | Header oscuro, botones glossy con degradé, `status_tag` sólido, `index_table`, breadcrumb, scopes, flash notices, paginación, panel de filtros — vocabulario visual vigente completo | **2, 4.1-4.4, 4.7** |
| Haiku OS (capturas reales) † | Forma rectangular, pestaña de ventana redondeada, bisel de botón medido en píxel | histórico |
| AmigaOS Style Guide | Herencia original de "Power to the User", consistencia como necesidad maestra | 0, 4.6 |
| Material Design 3 † | Elevación con nombre de propósito, state layers, sistema de movimiento completo | histórico |
| Android (App Shortcuts, Quick Settings, NotificationChannel, ItemTouchHelper) | Delegación de toda función que la plataforma ya resuelve | 4.1-4.3 |
| WCAG 2.4.7 / 2.4.11 | Foco visible, contraste mínimo | 3 |
| Machado-Oliveira-Fernandes (simulación de daltonismo) | Verificación real de los 4 colores semánticos contra 3 tipos de CVD — **vigente sin cambios en todos los pivots visuales** | 2.1 |
| Nielsen Norman Group (10 heurísticas) | Lenguaje llano, prevención de error, consistencia | Categoría A (delegado, no redocumentado) |
| Ley de Fitts | Tamaño/distancia de objetivos táctiles | Delegado, no redocumentado |

---

## 6. Checklist de cierre para implementación real

- [x] **Librería de íconos resuelta**: Material Symbols, ver 2.5. Tabler queda documentado como alternativa reversible.
- [x] **Lado del FAB resuelto**: configurable por el propio usuario (mantener presionado el FAB alterna el lado).
- [x] **Clon de ActiveAdmin completo (05/sep)**: header oscuro, botones glossy, `status_tag` sólido, y en la demo de componentes: breadcrumb, scopes, `index_table` ordenable, batch actions, paginación, panel de filtros, detalle, formulario. Contraste verificado en cada pieza nueva (flash notices 6.09-6.79:1, mini-tags 7.26-7.38:1).
- [x] **Densidad de 3 niveles (05/sep)**: Compacto/Normal/Aireado, cíclico desde un único botón, mismo vocabulario visual de AA en los 3.
- [x] **Dashboard (05/sep)**: stat callouts, actividad reciente, accesos rápidos, comentarios — contraste 7.26-7.38:1 en los stats.
- [x] **Componentes de interacción (05/sep)**: offcanvas, accordion, dropdown de acciones, tooltip, toast, modal de confirmación simple — contraste 9.7-17.4:1 en toast/tooltip/offcanvas.
- [x] **Formularios y estados (05/sep)**: login, estado vacío, formulario anidado dinámico (agregar/quitar filas real, no solo visual), exportación CSV/JSON/XML, switches — contraste 7.26-7.38:1 verificado.
- [x] **Auditoría de tokens muertos (iteración anterior)**: 10 tokens sin uso real eliminados, 2 valores compartidos por accidente corregidos.
- [x] **Accesibilidad de teclado**: las 5 filas custom del Hub (`div[role="button"]`) responden a Enter/Espacio vía listener delegado.
- [x] **Contraste de bordes**: corregidos a 4.8:1/3.0:1 (WCAG 1.4.11), valor reusado sin cambios en el clon de AA.
- [ ] **Deuda técnica (05/sep, detectada al consolidar):** las sombras de `.phone`/`.peek`/`.modal` están escritas inline en cada regla, no como tokens `--shadow-*` reutilizables — funciona, pero si el valor cambia hay que tocar 3 lugares en vez de 1. No es urgente, es prolijidad pendiente.
- [ ] Umbral exacto de agrupación de cards (4.2) validado contra dispositivos reales, no solo el mockup.
- [ ] Auditoría de contraste AAA (2.7) en cualquier pantalla financiera nueva antes de mergear.
- [ ] Simulación de daltonismo (2.1) corrida de nuevo si se agrega un 5° color semántico.
- [ ] Verificar que `Room`/`DataStore` (4.5) realmente sobrevive un `adb shell am kill` en pruebas, no solo minimizar la app.
- [ ] Confirmar que ningún componente nuevo introduce un radio distinto de `--radius-base` (3px) — es el único radio del sistema, sin escala, así es AA.
- [ ] Extender el patrón `role="button"` + listener delegado a cualquier control custom nuevo que use `tabindex` en vez de un `<button>` nativo.
- [ ] La tabla índice (`palm-demo-componentes-activeadmin.html`) usa `overflow-x:auto` para caber en mobile — validar en dispositivo real que el scroll horizontal se sienta natural y no como un error de layout.
