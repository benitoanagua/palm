# Apay — Plataforma de Transporte de Carga Compartida

Aplicación Android para conectar transportistas con espacio disponible en sus vehículos de carga con empresas y personas que necesitan enviar mercancías en la misma ruta, dentro y entre departamentos de Bolivia.

---

## Stack Técnico

| Capa | Tecnología |
|------|-----------|
| UI | Jetpack Compose + Material3 |
| Navegación | Navigation Compose |
| DI | Hilt |
| Backend | Firebase (Auth, Firestore, Storage, FCM) |
| Mapas y GPS | Google Maps SDK |
| Pagos | Tigo Money + QR bancario boliviano |
| Infraestructura | AWS o Google Cloud |

---

## Estructura de módulos

```
app/
├── auth/               # Login, biometría, AuthViewModel
├── carga/
│   ├── home/           # Pantalla principal de carga compartida
│   ├── rutas/          # Publicar ruta disponible
│   ├── buscar/         # Buscar transporte disponible
│   ├── envios/         # Lista de envíos del usuario
│   ├── detalle/        # Detalle de envío con rastreo
│   └── perfil/         # Perfil de transportista/cargador
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
    // Acceso general autenticado
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
    
    // Colecciones específicas para carga compartida
    
    // Rutas: solo transportistas pueden crear sus propias rutas
    match /rutas/{rutaId} {
      allow read: if request.auth != null;
      allow create: if request.auth != null && request.resource.data.transportistaId == request.auth.uid;
      allow update, delete: if request.auth != null && resource.data.transportistaId == request.auth.uid;
    }
    
    // Envíos: cargadores crean, ambos pueden actualizar estado
    match /envios/{envioId} {
      allow read: if request.auth != null && 
        (resource.data.cargadorId == request.auth.uid || 
         resource.data.transportistaId == request.auth.uid);
      allow create: if request.auth != null;
      allow update: if request.auth != null && 
        (resource.data.transportistaId == request.auth.uid || 
         resource.data.cargadorId == request.auth.uid);
    }
    
    // Transportistas: perfil público con escritura solo del dueño
    match /transportistas/{uid} {
      allow read: if request.auth != null;
      allow create: if request.auth != null && request.auth.uid == uid;
      allow update: if request.auth != null && request.auth.uid == uid;
    }
    
    // Usuarios: perfil básico
    match /usuarios/{uid} {
      allow read: if request.auth != null;
      allow create: if request.auth != null && request.auth.uid == uid;
      allow update: if request.auth != null && request.auth.uid == uid;
    }
    
    // Disputas: solo involucrados y admin pueden acceder
    match /disputas/{disputaId} {
      allow read: if request.auth != null && 
        (resource.data.cargadorId == request.auth.uid || 
         resource.data.transportistaId == request.auth.uid);
      allow create: if request.auth != null;
      allow update: if request.auth != null && 
        (resource.data.cargadorId == request.auth.uid || 
         resource.data.transportistaId == request.auth.uid);
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
| **Inicio (CargaHome)** | Dashboard principal con acceso rápido a publicar ruta o buscar transporte |
| **Publicar Ruta** | Transportista publica su ruta con capacidad disponible, fecha y precio |
| **Buscar Transporte** | Cargador busca rutas disponibles filtrando por origen, destino y fecha |
| **Mis Envíos** | Lista de envíos del usuario con filtro por estado (Pendiente, En Tránsito, Entregado) |
| **Detalle de Envío** | Información completa del envío con rastreo GPS y progreso |
| **Perfil** | Datos del usuario, calificaciones, historial de viajes, configuración |

---

## Design System

Colors, tokens y componentes en `app/src/main/kotlin/com/apay/ui/`.

- **Primario**: Azul profundo `#0A1F3D` (identidad corporativa)  
- **Secundario**: Eléctrico `#6B4CFF` (acciones y destacados)  
- **Fondo**: `#020914` (espacial oscuro)  

Componentes disponibles: `A2Button`, `A2Card`, `A2TextField`, `A2Banner`,  
`A2Tag`, `A2Avatar`, `A2Divider`, `A2TopBar`, `A2BottomNav`, etc.

---

## Modelo de negocio

### Para Transportistas
- Publica tu ruta con capacidad disponible
- Genera ingresos extra por el espacio que sobra
- Pago garantizado antes de salir
- Construye reputación digital con cada entrega

### Para Cargadores
- Encuentra transporte disponible en minutos
- Precio fijo pactado en la app
- Rastreo GPS en tiempo real
- Foto de entrega como evidencia

### Comisión
- Apay retiene 8-10% de cada envío completado
- Pago se libera al confirmar la entrega con foto

---

## Notas importantes

- Agregar `google-services.json` antes de compilar (no incluido por seguridad).
- El archivo `.jks` de firma **nunca** debe ser commiteado al repositorio.
- La biometría requiere que el dispositivo tenga un bloqueo de pantalla configurado.
- Primera plataforma en Bolivia especializada en carga compartida entre privados.

---

*Bolivia 2026 — Plataforma de Transporte de Carga Compartida*
