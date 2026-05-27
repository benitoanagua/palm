# 30° — Ivory Linen / Warm Structure

> Especificación Arquitectónica de Diseño · Agnóstica a Tecnología

---

## I. Manifiesto

Esta filosofía representa la **disciplina cálida**. Es el espacio entre la ensoñación etérea del mediodía (0°) y la claridad funcional del diseño plano (60°). La interfaz se concibe como un estudio de arquitectura de alto diseño, o una sala editorial de revista de lujo: estructurada, ordenada, pero construida con materiales naturales que dan temperatura y presencia.

No es minimalismo por sustracción (quitar hasta el límite). Es minimalismo por selección: cada elemento que existe tiene una razón de ser, un peso y un lugar preciso.

**Principio rector:** Orden sin frialdad. Estructura sin rigidez. El diseño como artesanía consciente.

---

## II. Teoría del Color — Paleta Natural Ordenada

### Fondos

| Token                 | Valor     | Descripción                                                |
| --------------------- | --------- | ---------------------------------------------------------- |
| Fondo base            | `#F8F5EF` | Lino claro. Beige sofisticado, ni amarillento ni grisáceo. |
| Superficie primaria   | `#FAFAF8` | Blanco hueso. Limpio pero no estéril.                      |
| Superficie secundaria | `#F2EEE7` | Lino medio. Para zonas de distinción suave.                |

### Acentos

| Token            | Valor     | Uso                                                             |
| ---------------- | --------- | --------------------------------------------------------------- |
| Primario         | `#B05E3C` | Terracota templada. Presencia sin agresividad.                  |
| Secundario       | `#6A8B74` | Verde salvia. Calma natural y equilibrio.                       |
| Terciario        | `#8C7B6A` | Madera tostada. Para bordes sutiles y separadores.              |
| Texto principal  | `#3A3530` | Grafito cálido. Oscuro pero con temperatura marrón perceptible. |
| Texto secundario | `#7A6E65` | Tierra media. Para etiquetas, metadatos.                        |

---

## III. Geometría y Morfología — Proporción Artesanal

El sistema usa radios moderados: ni la suavidad extrema del 0° ni la rectitud total del 90°. Las formas recuerdan a objetos manufacturados de calidad: un libro encuadernado, una caja de madera bien cortada.

### Radios de borde

| Elemento            | Radio       | Sensación                                     |
| ------------------- | ----------- | --------------------------------------------- |
| Tarjetas grandes    | 16px – 24px | Redondeado moderado. Volumen sin exageración. |
| Botones primarios   | 8px – 12px  | Presencia sólida.                             |
| Botones secundarios | 6px – 8px   | Más contenidos, casi rectangulares.           |
| Inputs              | 8px         | Campo de trabajo claro.                       |
| Tags / Badges       | 4px – 6px   | Pequeños y precisos.                          |

### Estructura de grid

- Columnas definidas, alineación estricta.
- Márgenes generosos (mínimo 24px) pero no excesivos como en 0°.
- Proporción áurea o 4:3 en bloques de contenido.

---

## IV. Tipografía — Voz Editorial Cálida

Combina la seriedad editorial con la accesibilidad de las sans-serif modernas.

### Familias

| Rol               | Tipo                                       | Características                                  |
| ----------------- | ------------------------------------------ | ------------------------------------------------ |
| Display / Títulos | Serif moderna o sans-serif de alta calidad | Elegancia sin arcaísmo. Ligereza en pesos bajos. |
| Subtítulos        | Sans-serif geométrica media                | Contraste limpio con el display.                 |
| Cuerpo            | Sans-serif con x-height alto               | Legibilidad óptima a cualquier tamaño.           |
| Etiquetas / Datos | Monoespaciada o sans condensada            | Distingue datos técnicos visualmente.            |

### Tratamiento

- Títulos en peso **Medium (500)** o **SemiBold (600)**, nunca Black en contextos cálidos.
- Cuerpo en Regular (400), línea de altura 1.65.
- Interletrado ligeramente positivo en titulares (`letter-spacing: 0.01em`).
- Tracking neutro en cuerpo, nunca negativo.

---

## V. Sistema de Elevación — Capas de Papel

La metáfora material es el **papel sobre papel**: capas que se distinguen por sutiles diferencias de sombra difusa, sin efectos dramáticos.

### Sombras difusas

| Nivel | Especificación                                        | Uso                               |
| ----- | ----------------------------------------------------- | --------------------------------- |
| E-0   | Sin sombra                                            | Fondo, separadores.               |
| E-1   | Offset 0, blur 4px, color `rgba(58,53,48,0.06)`       | Inputs hundidos, placas de datos. |
| E-2   | Offset 0 2px, blur 8px, color `rgba(58,53,48,0.08)`   | Tarjetas en reposo.               |
| E-3   | Offset 0 8px, blur 24px, color `rgba(58,53,48,0.12)`  | Tarjetas destacadas, drawers.     |
| E-4   | Offset 0 16px, blur 48px, color `rgba(58,53,48,0.18)` | Modales, overlays.                |

### Bordes

- Borde sutil `1px solid rgba(139,123,106,0.2)` en tarjetas para definir límite sin agresividad.
- En dark mode: `1px solid rgba(255,255,255,0.08)`.

---

## VI. Interacción y Cinética — Movimiento Artesanal

El movimiento es deliberado y suave. No hay rebotes ni físicas exageradas; tampoco la frialdad instantánea del 90°.

### Curvas de tiempo

- Easing estándar: `ease-out` o `cubic-bezier(0.25, 0.1, 0.25, 1)`.
- Duración base: 200ms – 300ms.
- Transiciones más largas (400ms) solo para modales y overlays.

### Estados

| Estado   | Comportamiento                                  |
| -------- | ----------------------------------------------- |
| Reposo   | Estático. Sin animaciones de flotación.         |
| Hover    | Sombra ligeramente aumentada + fondo muy tenue. |
| Press    | `scale(0.98)`, sombra reduce a E-1.             |
| Focus    | Borde terracota `2px solid #B05E3C`, sin glow.  |
| Disabled | Opacidad 0.45, cursor prohibido.                |

---

## VII. Materialidad de Componentes

### Tarjeta "Papel Grueso"

- **Constructo:** Rectángulo 20px radius.
- **Material:** Fondo lino claro (`#F8F5EF`), borde `1px` sutil.
- **Sombra:** E-2 en reposo, E-3 en hover.
- **Estructura interna:** Header con línea divisoria `1px solid rgba(139,123,106,0.15)`, cuerpo con padding generoso.

### Botón "Sello Terracota"

- **Primario:** Fondo terracota sólido (`#B05E3C`), texto blanco, radius 10px.
- **Secundario:** Fondo transparente, borde `1.5px solid #B05E3C`, texto terracota.
- **Terciario:** Solo texto, sin borde ni fondo.
- **Active:** `scale(0.97)`, sombra desaparece.

### Input "Campo de Trabajo"

- **Constructo:** Rectángulo radius 8px.
- **Material:** Fondo blanco hueso (`#FAFAF8`), borde `1px solid rgba(139,123,106,0.3)`.
- **Focus:** Borde se vuelve terracota `2px solid #B05E3C`, fondo ligeramente más claro.
- **Error:** Borde `2px solid #C0392B`, ícono de error a la derecha.

### Navegación

- Barra fija con fondo lino, borde inferior `1px` sutil.
- Items de nav con peso Medium, el activo subrayado con línea terracota de 2px.
- Sin efectos de fondo glassmorphic.

---

## VIII. Principios de Composición

1. **Ritmo tipográfico:** La escala de tipos establece jerarquía clara. No usar más de 3 pesos diferentes en una vista.
2. **Espaciado basado en múltiplos de 8:** 8, 16, 24, 32, 48, 64px. Nunca valores arbitrarios.
3. **Densidad media:** Ni tan aireado como 0° ni tan denso como 90°–180°.
4. **Imágenes con borde sutil:** Las fotos van enmarcadas en borde `1px solid rgba(58,53,48,0.1)` para no contaminar el grid con colores externos.
5. **Asimetría permitida:** Composiciones en L o layouts editoriales con columna ancha + columna angosta.

---

## IX. Resumen de Valores Técnicos

| Atributo          | Especificación                                                            |
| ----------------- | ------------------------------------------------------------------------- |
| Fondo dominante   | Lino claro `#F8F5EF`                                                      |
| Superficie UI     | Blanco hueso `#FAFAF8`                                                    |
| Acento primario   | Terracota `#B05E3C`                                                       |
| Acento secundario | Salvia `#6A8B74`                                                          |
| Texto principal   | Grafito cálido `#3A3530`                                                  |
| Border radius     | 16px–24px (tarjetas), 8px–12px (botones)                                  |
| Sombra base       | Color tierra, blur 8px–24px                                               |
| Tipografía        | Serif moderna o sans alta calidad (display) / Sans x-height alto (cuerpo) |
| Estado de foco    | Borde terracota sólido 2px                                                |
| Textura           | Ninguna o grano muy sutil ≤2%                                             |
| Easing            | `ease-out`, 200ms–300ms                                                   |

---

## X. Contextos de Uso

**Ideal para:**

- Marcas de lujo accesible (moda, gastronomía, hogar).
- Portafolios de arquitectura, fotografía, diseño de interiores.
- Plataformas editoriales (revistas digitales, blogs de autor).
- Aplicaciones de productividad orientadas a creadores.
- Productos de bienestar con enfoque más estructurado que 0°.

**Posiciones cercanas en la rueda:** 0° (Solar Zenith) · 60° (Modern Flat)
**Opuesto filosófico:** 210° (Glitch Field)
