# Comandos de Compilación y Diagnóstico

## Compilar

```bash
# Debug (compila + instala en dispositivo)
./gradlew :app:installDebug

# Release
./gradlew assembleRelease

# Solo compilar (sin instalar)
./gradlew assembleDebug
```

## Verificación rápida

```bash
# Solo compilar Kotlin (más rápido)
./gradlew compileDebugKotlin

# Compilar con trace de errores
./gradlew assembleDebug --stacktrace
```

## Calidad y estilo

```bash
# Lint de Android
./gradlew lintDebug

# Advertencias de dependencias
./gradlew assembleDebug --warning-mode all
```

## Limpiar

```bash
./gradlew clean assembleDebug
```

## Tests

```bash
# Tests unitarios
./gradlew testDebugUnitTest
```

## Notas

- `compileDebugKotlin` es el comando recomendado para el día a día: solo compila Kotlin sin empaquetar APK
- Agregar `--stacktrace` cuando un error no es claro
- `google-services.json` debe existir en `app/` para compilar
- `local.properties` debe existir en la raíz con `sdk.dir=/ruta/a/Android/Sdk`
- Si ves errores de metadatos de Kotlin incompatibles, revisa las versiones en `gradle/libs.versions.toml`
- Si ves `Unresolved reference 'Home'` en `Lucide`, la versión 1.1.0 de `icons-lucide` usa `House` en lugar de `Home`

## Problemas comunes solucionados

| Problema | Causa | Solución |
|----------|-------|----------|
| `com.composables:icons-lucide` no encontrado | El artifact para Android se llama `icons-lucide-android` | Corregir en `libs.versions.toml` |
| `Unresolved reference 'A2Xxx'` | Imports apuntan a `com.apay.ui.*` en vez de `com.a2.ui.*` | Cambiar imports |
| `google-services.json is missing` | Archivo de Firebase faltante | Colocar archivo en `app/` |
| Metadata de Kotlin incompatible | Dependencia pide Kotlin 2.2.x y el proyecto usa 2.0.x | Ajustar versiones en `libs.versions.toml` |
| AGP requiere versión más nueva | Compose BOM pide AGP ≥ 8.6.0 | Actualizar AGP en `libs.versions.toml` |
