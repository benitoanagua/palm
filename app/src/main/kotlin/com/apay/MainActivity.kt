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
import com.google.firebase.auth.FirebaseAuth
import com.apay.auth.BiometricHelper
import com.apay.auth.LoginScreen
import com.apay.navigation.AppNavHost
import com.a2.ui.components.*
import com.a2.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

/**
 * Flujo de autenticación local (biométrica / PIN):
 *
 * ┌────────────────────────────────────────────────────────┐
 * │ App abre                                               │
 * │ ├── Usuario NO logueado en Firebase → LoginScreen      │
 * │ └── Usuario logueado en Firebase                       │
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

        val auth = FirebaseAuth.getInstance()

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
private fun AppEntry(auth: FirebaseAuth) {
    val activity = androidx.compose.ui.platform.LocalContext.current as FragmentActivity

    var isLoggedIn  by remember { mutableStateOf(auth.currentUser != null) }
    var isUnlocked  by remember { mutableStateOf(false) }
    var authError   by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val loggedIn = firebaseAuth.currentUser != null
            if (!loggedIn && isLoggedIn) {
                isUnlocked = false
                authError = null
            }
            isLoggedIn = loggedIn
        }
        auth.addAuthStateListener(listener)
        onDispose { auth.removeAuthStateListener(listener) }
    }

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
    ) { (loggedIn, unlocked, _) ->
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
            !unlocked -> {
                LockScreen(
                    error = authError,
                    onDesbloquear = {
                        authError = null
                        BiometricHelper.autenticar(
                            activity    = activity,
                            onExito     = { isUnlocked = true },
                            onError     = { error -> authError = error },
                            onCancelado = { authError = null }
                        )
                    }
                )
            }
            else -> {
                val chatId = (activity as? FragmentActivity)
                    ?.intent?.getStringExtra("chatId")
                AppNavHost(
                    onLogout = {
                        auth.signOut()
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
