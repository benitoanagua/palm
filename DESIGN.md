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

| Token                   | Valor     | Descripción                                                |
| ----------------------- | --------- | ---------------------------------------------------------- |
| Fondo base              | `#FFF8F4` | Lino cálido. Beige sofisticado, tinte rosáceo muy sutil.    |
| Superficie primaria     | `#FFF8F4` | Mismo que fondo. Superficies continuas y limpias.           |
| Superficie secundaria   | `#F2EEE7` | Lino medio. Para zonas de distinción suave.                |
| Superficie elevada      | `#FFFFFF` | Blanco puro. Para cards destacados, inputs en focus.       |

### Acentos

| Token               | Valor     | Uso                                                             |
| ------------------- | --------- | --------------------------------------------------------------- |
| Primario profundo   | `#924626` | Terracota profunda (Wayfarer primary). CTAs, acento fuerte.     |
| Primario medio      | `#B05E3C` | Terracota templada (Wayfarer primary-container). USA primario.   |
| Secundario profundo | `#466550` | Salvia profunda (Wayfarer secondary).                           |
| Secundario medio    | `#6A8B74` | Verde salvia. Calma natural y equilibrio.                       |
| Terciario           | `#8C7B6A` | Madera tostada. Para bordes sutiles y separadores.              |
| Texto principal     | `#3A3530` | Grafito cálido. Oscuro pero con temperatura marrón perceptible. |
| Texto secundario    | `#7A6E65` | Tierra media. Para etiquetas, metadatos.                        |

---

## III. Geometría y Morfología — Proporción Artesanal

El sistema usa radios moderados: ni la suavidad extrema del 0° ni la rectitud total del 90°. Las formas recuerdan a objetos manufacturados de calidad: un libro encuadernado, una caja de madera bien cortada.

### Radios de borde

| Elemento            | Radio       | Sensación                                     |
| ------------------- | ----------- | --------------------------------------------- |
| Tarjetas            | 20px        | Redondeado moderado. Volumen sin exageración. |
| Botones             | 10px        | Presencia sólida.                             |
| Inputs              | 8px         | Campo de trabajo claro.                       |
| Search bar          | 20px        | Cómodo, integrado al diseño de tarjetas.      |
| Tags / Badges       | 6px         | Pequeños y precisos (radius pill = 9999).     |

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

- Borde sutil `0.5px solid rgba(139,123,106,0.125)` (`BordeCard`) en tarjetas para definir límite sin agresividad.
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
| Focus    | Borde terracota profundo `2px solid #924626`, sin glow. |
| Disabled | Opacidad 0.45, cursor prohibido.                |

---

## VII. Materialidad de Componentes

### Tarjeta "Papel Grueso"

- **Constructo:** Rectángulo 20px radius (`A2Card`, `A2CardElevated`, `A2CardGlass`).
- **Material:** Fondo lino cálido (`#FFF8F4`), borde `0.5px solid rgba(139,123,106,0.125)` (`BordeCard`).
- **Sombra:** E-2 en reposo (elevación 2dp), `A2CardElevated` usa 4dp.
- **Estructura interna:** Padding horizontal 16dp, vertical 12dp.

### Botón "Sello Terracota"

- **Primario:** Fondo terracota profundo (`#924626`), texto blanco, radius 10px, altura 40dp.
- **Secundario:** Fondo transparente, borde `1.5px solid #924626`, texto terracota profundo.
- **Ghost:** Solo texto, sin borde ni fondo, color terracota profundo.
- **Active:** alpha `0.85`, sin scale (solo feedback visual).

### Input "Campo de Trabajo"

- **Constructo:** Rectángulo radius 8px, altura mínima 40dp (`InputHeight = ButtonHeight = TouchTarget`).
- **Material:** Fondo lino cálido (`#FFF8F4`), borde `1px solid rgba(139,123,106,0.2)` (`BordeSutil`).
- **Focus:** Borde se vuelve terracota profundo `2px solid #924626`, fondo blanco puro (`SuperficieElevada`).
- **Error:** Borde `2px solid #C0392B`, container fondo rojo sutil.

### Navegación

- Top bar fija con fondo lino (`FondoBase`), borde inferior `0.5px` sutil.
- Bottom nav con superficie elevada, items con indicator redondeado en `PrimaryContainer` (terracota medio).
- Item activo en terracota profundo (`#924626`), inactivo en texto mutado.
- Sin efectos glassmorphic.

---

## VIII. Principios de Composición

1. **Ritmo tipográfico:** La escala de tipos establece jerarquía clara. No usar más de 3 pesos diferentes en una vista.
2. **Espaciado basado en múltiplos de 8:** 8, 16, 24, 32, 48, 64px. Nunca valores arbitrarios.
3. **Densidad media:** Ni tan aireado como 0° ni tan denso como 90°–180°.
4. **Imágenes con borde sutil:** Las fotos van enmarcadas en borde `1px solid rgba(58,53,48,0.1)` para no contaminar el grid con colores externos.
5. **Asimetría permitida:** Composiciones en L o layouts editoriales con columna ancha + columna angosta.

---

## IX. Resumen de Valores Técnicos

| Atributo           | Especificación                                                            |
| ------------------ | ------------------------------------------------------------------------- |
| Fondo dominante    | Lino cálido `#FFF8F4`                                                     |
| Superficie UI      | Lino cálido `#FFF8F4` / blando puro `#FFFFFF` (elevado)                   |
| Acento primario    | Terracota profunda `#924626`                                              |
| Acento secundario  | Salvia profunda `#466550`                                                 |
| Texto principal    | Grafito cálido `#3A3530`                                                  |
| Border radius      | 20px (tarjetas), 10px (botones), 8px (inputs), 6px (tags)                |
| Sombra base        | Color tierra (`rgba(58,53,48,0.08)`), blur 8px (E-2)                     |
| Tipografía         | Literata (display) SemiBold/Medium / Manrope (body) Regular/Medium/SemiBold |
| Estado de foco     | Borde terracota profundo `2px solid #924626`                              |
| Textura            | Ninguna o grano muy sutil ≤2%                                             |
| Easing             | `ease-out`, 200ms–300ms                                                   |

### Componentes del sistema

| Componente             | Tokens clave en código                          |
| ---------------------- | ----------------------------------------------- |
| `A2Button`             | `TerracotaProfunda`, `RadiusButton = 10dp`      |
| `A2Input`              | `RadiusInput = 8dp`, `InputHeight = 40dp`       |
| `A2Card`               | `RadiusCard = 20dp`, `BordeCard`, `ElevationCard` |
| `A2CardElevated`       | Fondo `SuperficieElevada (#FFF)`, elevación 4dp  |
| `A2CardGlass`          | Fondo `SuperficieSecundaria`, bordes suaves      |
| `A2SearchBar`          | `RadiusSearch = 20dp`, overlay transparente      |
| `A2PillFilter`         | `RadiusPill`, toggle selected/unselected         |
| `A2ProgressStepper`    | Círculos numerados + barra de progreso linear    |
| `A2JourneyTimeline`    | Línea vertical con dots + labels                 |
| `A2HeroImage`          | Gradient overlay con texto                       |
| `A2StatsGrid`          | Grid de valores + etiquetas                      |
| `A2PromoCard`          | Card con CTA interna                             |
| `A2MapPlaceholder`     | Área mock para mapa                              |
| `A2RatingBar`          | Estrellas `Estrella (#D4A24E)`                   |
| `A2DriverProfileCard`  | Avatar + rating + viajes                         |
| `A2TopBar`             | `TopBarPadH/V`, borde inferior sutil             |
| `A2BottomNav`          | `NavigationBar` con `indicatorColor` primary     |
| `A2Tag`                | `RadiusTag = 6dp`, variant Filled/Outlined/Subtle |
| `A2Banner`             | Backgrounds semánticos + borde                   |

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
