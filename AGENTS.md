# AGENTS.md — Apay (A2)

## Identidad del proyecto

App Android de carga compartida para Bolivia. Marca "A2", paquete `com.apay`.

## Stack

- Kotlin 2.0.21 + Compose BOM 2024.09.00 + Material3
- Hilt 2.51.1 (DI)
- Firebase (Auth, Firestore, Storage, FCM, Crashlytics)
- Ktor CIO + kotlinx.serialization
- CameraX, Biometric, Security Crypto
- Lucide icons 1.1.0 (`com.composables:icons-lucide-android`)

## Reglas de código

1. **No agregar comentarios** a menos que sea estrictamente necesario
2. **No crear archivos** sin necesidad explícita
3. **No modificar estructura** de paquetes sin consultar

## Convenciones

### Paquetes

- Código fuente → `com.apay.*`
- Design system (ui/theme, ui/components) → `com.a2.ui.*`
- Al importar componentes/theme usar `com.a2.ui.components.*` y `com.a2.ui.theme.*`

### Arquitectura

- MVVM con repositorios. Las pantallas se conectan a ViewModels vía `hiltViewModel()`
- Repositorios exponen `Flow<T>` para datos reactivos (Firestore `callbackFlow`)
- Operaciones suspendidas retornan `Result<T>` con `runCatching`
- Modelos de dominio en `domain/model/`, interfaces en `domain/repository/`

### Design System

- Solo tema oscuro
- 90% Primary (Teal), 8% Secondary (Púrpura), 2% Tertiary (Violeta)
- Colores: `PrimaryElectric`, `SecondaryElectric`, `FondoEspacial`, `TextPrimary`, etc.
- Tipografía: `A2TextStyle.Headline`, `A2TextStyle.Body`, `A2TextStyle.Small`, etc.
- Componentes: `A2Button(variant)`, `A2Input`, `A2Card`, `A2TopBar`, `A2BottomNav`, etc.

### Navegación

- `Screen` sealed class define rutas
- `AppNavHost` usa Navigation Compose
- `MainShell` envuelve pantallas con TopBar + BottomNav
- BottomNav screens: `CargaHome`, `MisEnvios`, `Perfil`

### Nombres

- Español para strings, UI, modelos de negocio (`Usuario`, `Envio`, `Ruta`)
- Inglés para código técnico (`repository`, `viewModel`, `flow`)
- Prefijo `A2` para componentes del design system

## Errores comunes

| Símbolo | Problema | Solución |
|---------|----------|----------|
| `A2Xxx` no resuelto | Import apunta a `com.apay.ui.*` | Usar `com.a2.ui.*` |
| `Lucide.Home` no resuelto | Versión 1.1.0 usa `House` en vez de `Home` | Cambiar a `Lucide.House` |
| `BodySmall` no resuelto | No existe en A2TextStyle | Usar `A2TextStyle.Small` o `BodySmall` (alias) |
| `White` no resuelto | Definido en A2Colors como alias | `com.a2.ui.theme.White` |
| `A2TextField` no resuelto | El componente se llama `A2Input` | Reemplazar con `A2Input(...)` |
| `A2ButtonSecondary` no resuelto | No existe | Usar `A2Button(variant = A2ButtonVariant.Secondary)` |
| `backgroundColor`, `textColor` en A2Button | No son parámetros | Usar `variant` en su lugar |
| `Terracota` como acento primario | Ahora es `PrimaryContainer` (#B05E3C) | Usar `TerracotaProfunda` (#924626) para CTAs/accent |
| `Salvia` como secundario | Ahora es `SecondaryContainer` (#6A8B74) | Usar `SalviaProfunda` (#466550) para secondary |
| `PrimaryElectric` azul | No existe azul en la paleta | Usar `TerracotaProfunda` como primary |
| `A2Card` sin shadow | Shadow usa color tierra (#8C7B6A) integrado en M3 | No requiere cambio — usar `A2CardElevated` para más elevación |
| `google-services.json` faltante | Requerido para compilar | Colocar archivo en `app/` |

## Comandos

```bash
./gradlew :app:installDebug   # Compila + instala
./gradlew compileDebugKotlin  # Solo chequea errores de compilación
./gradlew assembleRelease     # Build release
./gradlew lintDebug           # Lint
```

## Modelos

- `Usuario`: uid, nombre, email, rol (TRANSPORTISTA/CARGADOR), avatarUrl, enLinea, telefono, calificacion, viajesCompletados
- `Transportista`: uid, nombre, email, telefono, placa, tipoVehiculo, capacidadMaximaKg, capacidadMaximaM3, verificado
- `Ruta`: id, transportistaId, origen, destino, fechaSalida, capacidadDisponibleKg/M3, precioPorKg, tiposCargaAceptados, estado
- `Envio`: id, rutaId, cargadorId, transportistaId, origen, destino, pesoKg, dimensiones, tipoCarga, precioTotal, estado, evidenciaEntregaUrl

## Pendientes conocidos

- Solo existe `AuthViewModel`; las pantallas de carga usan datos mock
- Las pantallas no están conectadas a los repositorios Firestore
- Pagos (Tigo Money, QR) no implementados
- Cámara para evidencia de entrega no integrada
- Funcionalidades marcadas con `// TODO` están sin implementar
