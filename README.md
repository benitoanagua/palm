# APAY — Android App

Aplicación Android para gestión de pagos, comprobantes y tasa de cambio.  
Basada en la arquitectura de TuPago, migrada a un proyecto nativo Android puro.

---

## Stack Técnico

| Capa | Tecnología |
|------|-----------|
| UI | Jetpack Compose + Material3 |
| Navegación | Navigation Compose |
| DI | Hilt |
| Backend | Firebase (Auth, Firestore, Storage, FCM) |
| Imágenes | CameraX + Coil |
| Workers | WorkManager (subida de comprobantes) |
| Red | Ktor Client (CIO) |
| Biometría | AndroidX Biometric |

---

## Estructura de módulos

```
app/
├── auth/               # Login, biometría, AuthViewModel
├── chats/              # Lista de chats, nuevo chat
├── comprobante/        # Captura de foto + subida con WorkManager
├── conversacion/       # Pantalla de mensajes en tiempo real
├── cuenta/             # Perfil, saldo, historial
├── tasa/               # Tasa de cambio en tiempo real
├── data/repository/    # Implementaciones Firestore
├── di/                 # Módulos Hilt
├── domain/             # Modelos y contratos de repositorio
├── navigation/         # NavHost, Screen, shell BottomNav
├── notifications/      # FCM Service
└── ui/
    ├── components/     # Design system APAY (botones, cards, inputs…)
    └── theme/          # Colores, tipografía, tokens, gradientes
```

---

## Setup inicial

### 1. Firebase
1. Crear proyecto en [Firebase Console](https://console.firebase.google.com)
2. Agregar app Android con package `com.apay`
3. Descargar `google-services.json` → colocarlo en `app/`
4. Habilitar: **Authentication (email/password)**, **Firestore**, **Storage**, **Cloud Messaging**

### 2. Reglas Firestore (desarrollo)
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

### 3. Firma de release
Configurar variables de entorno:
```bash
export KEYSTORE_PATH=/ruta/a/apay.jks
export KEYSTORE_PASSWORD=tu_password
export KEY_ALIAS=apay
export KEY_PASSWORD=tu_key_password
```

### 4. Build
```bash
# Debug
./gradlew assembleDebug

# Release
./gradlew assembleRelease
```

---

## Flujo de autenticación

```
App abre
├── Sin sesión Firebase → LoginScreen (email + contraseña)
└── Con sesión Firebase
    ├── Dispositivo con PIN/biometría → LockScreen (prompt biométrico)
    │   ├── Éxito → AppNavHost (app principal)
    │   └── Error → LockScreen (reintento)
    └── Sin bloqueo configurado → AppNavHost (acceso directo)
```

---

## Pantallas principales

| Pantalla | Descripción |
|----------|-------------|
| **Login** | Email + contraseña. Recuperación de contraseña por email |
| **Chats** | Lista de conversaciones con indicador de no leídos |
| **Tasa** | Tasa de cambio USD→BOB en tiempo real desde Firestore |
| **Comprobante** | Captura de foto con CameraX + subida asíncrona |
| **Cuenta** | Perfil, saldo, historial de movimientos, logout |
| **Conversación** | Chat en tiempo real con Firestore |

---

## Design System

Colors, tokens y componentes en `app/src/main/kotlin/com/apay/ui/`.

- **Primario**: Teal `#009688` (90 % del uso)  
- **Secundario**: Púrpura `#9C27B0` (solo features premium)  
- **Fondo**: `#020914` (espacial oscuro)  

Componentes disponibles: `APAYButton`, `APAYCard`, `APAYInput`, `APAYBanner`,  
`APAYTag`, `APAYAvatar`, `APAYDivider`, `APAYTopBar`, `APAYBottomNav`, etc.

---

## Notas importantes

- Agregar `google-services.json` antes de compilar (no incluido por seguridad).
- El archivo `.jks` de firma **nunca** debe ser commiteado al repositorio.
- WorkManager garantiza la subida de comprobantes incluso si la app se cierra.
- La biometría requiere que el dispositivo tenga un bloqueo de pantalla configurado.
