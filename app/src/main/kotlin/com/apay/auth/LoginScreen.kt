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
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.EyeOff
import com.composables.icons.lucide.Lock
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import com.a2.ui.components.*
import com.a2.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var email        by remember { mutableStateOf("") }
    var password     by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var isLoading    by remember { mutableStateOf(false) }
    var errorMsg     by remember { mutableStateOf<String?>(null) }

    val canLogin = email.isNotEmpty() && password.length >= 6

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
                A2GradientDisplayLg(text = "APAY")
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
                        enabled = !isLoading,
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
                        enabled = !isLoading
                    )

                    A2TextButton(
                        text = "Olvidé mi contraseña",
                        onClick = {
                            errorMsg = if (email.isNotBlank())
                                "Función disponible cuando se active Firebase"
                            else
                                null
                        },
                        modifier = Modifier.align(Alignment.End),
                        color = PrimaryElectric
                    )

                    // Error banner
                    if (errorMsg != null) {
                        Spacer(Modifier.height(4.dp))
                        A2Banner(
                            message = errorMsg!!,
                            type = A2BannerType.Error,
                            dismissible = true,
                            onDismiss = { errorMsg = null }
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    A2Button(
                        text = "Ingresar",
                        onClick = {
                            isLoading = true
                            // Mock login para fase sin Firebase
                            onLoginSuccess()
                        },
                        loading = isLoading,
                        enabled = canLogin && !isLoading,
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
