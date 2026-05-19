# APAY ProGuard rules

# Kotlin
-keep class kotlin.** { *; }
-keepclassmembers class **$WhenMappings { <fields>; }

# Firebase
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Hilt
-keep class dagger.hilt.** { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

# Coroutines
-keepclassmembernames class kotlinx.** { volatile <fields>; }

# WorkManager
-keep class androidx.work.** { *; }
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.CoroutineWorker

# Ktor
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Compose
-keep class androidx.compose.** { *; }

# App models
-keep class com.apay.domain.model.** { *; }
-keep class com.apay.data.** { *; }
