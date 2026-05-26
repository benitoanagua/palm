package com.apay

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lock
import com.composables.icons.lucide.LockOpen
import com.composables.icons.lucide.Lucide
import com.apay.auth.BiometricHelper
import com.apay.auth.LoginScreen
import com.apay.navigation.AppNavHost
import com.a2.ui.components.*
import com.a2.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * Flujo de autenticación local (biométrica / PIN):
 *
 * ┌────────────────────────────────────────────────────────┐
 * │ App abre                                               │
 * │ ├── Usuario NO logueado → LoginScreen (mock/local)     │
 * │ └── Usuario logueado                                   │
 * │     ├── Dispositivo tiene PIN/biometría configurado    │
 * │     │   → LockScreen (prompt biometría / PIN)          │
 * │     │   ├── Éxito → AppNavHost                         │
 * │     │   └── Error → LockScreen (reintento)             │
 * │     └── Sin ningún bloqueo configurado → AppNavHost    │
 * └────────────────────────────────────────────────────────┘
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Firebase Auth (comentado - activar cuando se configure)
        // val auth = FirebaseAuth.getInstance()
        val auth = null // Mock auth para fase sin Firebase

        enableEdgeToEdge()

        setContent {
            A2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Background
                ) {
                    Box(Modifier.fillMaxSize()) {
                        A2Background()
                        AppEntry(auth = auth)
                    }
                }
            }
        }
    }
}

@Composable
private fun AppEntry(auth: Any?) {
    val activity = androidx.compose.ui.platform.LocalContext.current as FragmentActivity

    var isLoggedIn  by remember { mutableStateOf(auth?.let { false } ?: false) }
    var isUnlocked  by remember { mutableStateOf(false) }
    var authError   by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isLoggedIn, isUnlocked) {
        if (isLoggedIn && !isUnlocked) {
            if (BiometricHelper.isDisponible(activity)) {
                BiometricHelper.autenticar(
                    activity  = activity,
                    onExito   = { isUnlocked = true },
                    onError   = { error -> authError = error },
                    onCancelado = { authError = null }
                )
            } else {
                isUnlocked = true
            }
        }
    }

    AnimatedContent(
        targetState = Triple(isLoggedIn, isUnlocked, isLoggedIn),
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "app_entry"
    ) { (loggedIn, _, _) ->
        when {
            !loggedIn -> {
                LoginScreen(
                    onLoginSuccess = {
                        isUnlocked = false
                        isLoggedIn = true
                        authError  = null
                    }
                )
            }
            else -> {
                AppNavHost(
                    onLogout = {
                        isUnlocked = false
                        isLoggedIn = false
                        authError  = null
                    }
                )
            }
        }
    }
}

@Composable
fun LockScreen(
    error: String?,
    onDesbloquear: () -> Unit
) {
    val activity = androidx.compose.ui.platform.LocalContext.current as FragmentActivity
    val tieneBiometria = remember { BiometricHelper.tieneBiometria(activity) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .windowInsetsPadding(WindowInsets.systemBars),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(A2Spacing.lg),
            modifier = Modifier
                .widthIn(max = 360.dp)
                .padding(A2Spacing.xxl)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(PrimaryContainer, androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (tieneBiometria) Lucide.Lock else Lucide.LockOpen,
                    contentDescription = "Bloqueado",
                    tint = PrimaryElectric,
                    modifier = Modifier.size(40.dp)
                )
            }

            A2GradientDisplayLg(text = "A2")

            A2BodySmall(
                text = if (tieneBiometria)
                    "Usa tu huella, cara o PIN para acceder"
                else
                    "Ingresa tu PIN o patrón para acceder",
                color = TextMuted
            )

            if (error != null) {
                A2Card(modifier = Modifier.fillMaxWidth()) {
                    A2BodySmall(text = error, color = Error)
                }
            }

            A2Button(
                text = if (tieneBiometria) "Desbloquear con biometría" else "Ingresar PIN",
                onClick = onDesbloquear,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ── A2Theme reference
@Composable
private fun A2Theme(content: @Composable () -> Unit) {
    A2Theme(content = content)
}
