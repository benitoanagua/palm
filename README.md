# Apay (A2) — Transporte de Carga Compartida

Aplicación Android para conectar transportistas con espacio disponible en sus vehículos de carga con empresas y personas que necesitan enviar mercancías, dentro y entre departamentos de Bolivia.

---

## Stack

| Capa | Tecnología |
|------|-----------|
| UI | Jetpack Compose + Material3 |
| Navegación | Navigation Compose |
| DI | Hilt |
| Backend | Firebase (Auth, Firestore, Storage, FCM, Crashlytics) |
| Red | Ktor (CIO) |
| Cámara | CameraX |
| Seguridad | Biometric + Security Crypto |
| Íconos | Lucide (com.composables:icons-lucide-android 1.1.0) |

## Requisitos

- Android Studio Ladybug (o superior)
- JDK 17
- Android SDK 35
- Gradle 8.9 (wrapper incluido)
- Dispositivo/emulador con API 26+

## Setup

```bash
# 1. Clonar
git clone <repo> && cd apay-app

# 2. Firebase
# Crear proyecto en Firebase Console, agregar app Android (com.apay),
# descargar google-services.json y colocarlo en app/

# 3. Compilar e instalar
./gradlew :app:installDebug
```

## Estructura

```
app/src/main/kotlin/com/apay/
├── A2App.kt                 # Application (@HiltAndroidApp)
├── MainActivity.kt           # Entry point + auth flow
├── auth/                     # Login, biometría, AuthViewModel
├── navigation/               # Screen (rutas), AppNavHost, MainShell
├── domain/
│   ├── model/                # Usuario, Transportista, Ruta, Envio
│   └── repository/           # Interfaces de repositorio
├── data/repository/          # Implementaciones Firestore (callbackFlow)
├── di/                       # Módulos Hilt (AppModule, RepositoryModule)
├── carga/
│   ├── home/                 # Dashboard principal
│   ├── rutas/                # Publicar ruta
│   ├── buscar/               # Buscar transporte
│   ├── envios/               # Mis envíos
│   ├── detalle/              # Detalle de envío
│   └── perfil/               # Perfil de usuario
├── notifications/            # FCM service
└── ui/ (com.a2.ui.*)
    ├── components/           # Design system (A2Button, A2Input, etc.)
    └── theme/                # Colores, tipografía, tokens, gradientes
```

## Arquitectura

MVVM + Repository + Hilt DI:

```
UI (Composables) → ViewModel → Repository (interfaz)
                                    ↓
                            Firestore*Repository (implementación)
```

**Nota:** Solo `AuthViewModel` existe. Las pantallas de carga usan datos mock locales (pendiente conectar a ViewModels/repositorios).

## Diseño

- **Tema oscuro** solamente
- **90% Primary (Teal #009688), 8% Secondary (Púrpura #9C27B0), 2% Tertiary (Violeta #673AB7)**
- **Fondo:** Espacial oscuro `#020914` con glows ambientales
- Componentes en `com.a2.ui.components.*` (paquete `com.a2`, no `com.apay`)
- Tipografía custom vía `A2TextStyle` (9 tokens: DisplayLg a Caption)

## Convenciones

- **Paquetes:** El código fuente está en `com.apay.*`; el design system en `com.a2.ui.*`
- **Navegación:** `Screen` sealed class + `AppNavHost` con `MainShell` (TopBar + BottomNav)
- **Data real-time:** Repositorios exponen `Flow<T>` vía `callbackFlow` con `addSnapshotListener`
- **Errores:** Operaciones suspendidas retornan `Result<T>` con `runCatching`
- **DI:** Todos los módulos Hilt instalados en `SingletonComponent`

## Comandos

```bash
# Compilar e instalar en dispositivo
./gradlew :app:installDebug

# Solo compilar Kotlin (rápido)
./gradlew compileDebugKotlin

# Release
./gradlew assembleRelease

# Tests
./gradlew testDebugUnitTest
```

Ver `docs/comandos.md` para más detalles.

---

*Bolivia 2026 — Primera plataforma de carga compartida entre privados*
