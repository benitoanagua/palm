package com.apay.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.EyeOff
import com.composables.icons.lucide.Lock
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import com.a2.ui.components.*
import com.a2.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email        by remember { mutableStateOf("") }
    var password     by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val state        by viewModel.authState.collectAsState()

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) onLoginSuccess()
    }

    LaunchedEffect(state.success) {
        if (state.success != null) {
            delay(5_000)
            viewModel.clearError()
        }
    }

    Box(Modifier.fillMaxSize()) {
        A2Background()

        BoxWithConstraints(Modifier.fillMaxSize()) {
            val contentMod = if (maxWidth > 600.dp) Modifier.widthIn(max = 440.dp) else Modifier.fillMaxWidth()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // ── Logo ────────────────────────────────────────────────────
                A2GradientDisplayLg(text = "A2")
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Gestión de pagos y cambios",
                    color = TextMuted,
                    style = A2TextStyle.BodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))

                // ── Form ────────────────────────────────────────────────────
                A2Card(modifier = contentMod) {
                    A2Headline(
                        text = "Bienvenido de nuevo",
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(Modifier.height(16.dp))

                    A2Input(
                        value = email,
                        onValueChange = { email = it.trim() },
                        label = "Email",
                        leadingIcon = Lucide.Mail,
                        placeholder = "ejemplo@apay.com",
                        enabled = !state.isLoading,
                        keyboardType = KeyboardType.Email
                    )
                    Spacer(Modifier.height(8.dp))

                    A2Input(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        leadingIcon = Lucide.Lock,
                        trailingIcon = if (showPassword) Lucide.EyeOff else Lucide.Eye,
                        onTrailingIconClick = { showPassword = !showPassword },
                        isPassword = !showPassword,
                        placeholder = "••••••••",
                        enabled = !state.isLoading
                    )

                    A2TextButton(
                        text = "Olvidé mi contraseña",
                        onClick = {
                            if (email.isNotBlank()) viewModel.resetPassword(email)
                            else viewModel.clearError()
                        },
                        modifier = Modifier.align(Alignment.End),
                        color = PrimaryElectric
                    )

                    // Error / success banners
                    if (state.error != null) {
                        Spacer(Modifier.height(4.dp))
                        A2Banner(
                            message = state.error!!,
                            type = A2BannerType.Error,
                            dismissible = true,
                            onDismiss = { viewModel.clearError() }
                        )
                    }
                    if (state.success != null) {
                        Spacer(Modifier.height(4.dp))
                        A2Banner(
                            message = state.success!!,
                            type = A2BannerType.Success
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    A2Button(
                        text = "Ingresar",
                        onClick = { viewModel.login(email, password) },
                        loading = state.isLoading,
                        enabled = email.isNotEmpty() && password.length >= 6 && !state.isLoading,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(16.dp))

                TextButton(onClick = {}) {
                    Text(
                        text = "¿No tienes cuenta? Regístrate",
                        color = PrimaryElectric.copy(alpha = 0.5f),
                        fontWeight = FontWeight.SemiBold,
                        style = A2TextStyle.BodyMedium
                    )
                }
            }
        }
    }
}
