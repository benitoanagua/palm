package com.apay.cuenta

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.apay.ui.components.*
import com.apay.ui.theme.*

@Composable
fun CuentaScreen(
    onLogout: () -> Unit,
    onIrAHistorial: () -> Unit = {},
    viewModel: CuentaViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var mostrarDialogoLogout by remember { mutableStateOf(false) }

    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.actualizarAvatar(it) }
    }

    if (mostrarDialogoLogout) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoLogout = false },
            containerColor   = SuperficieCristal,
            icon = {
                Icon(Lucide.LogOut, null, tint = SecondaryElectric)
            },
            title = {
                Text("Desvincular cuenta", style = A2TextStyle.Headline, color = TextPrimary)
            },
            text = {
                Text(
                    "Se cerrará tu sesión en este dispositivo. Deberás ingresar tu email y contraseña la próxima vez.",
                    style = A2TextStyle.BodyMedium,
                    color = TextSecondary
                )
            },
            confirmButton = {
                A2Button(
                    text = "Sí, desvincular",
                    onClick = {
                        mostrarDialogoLogout = false
                        onLogout()
                    },
                    variant = A2ButtonVariant.Primary
                )
            },
            dismissButton = {
                A2TextButton(
                    text    = "Cancelar",
                    onClick = { mostrarDialogoLogout = false },
                    color   = TextMuted
                )
            }
        )
    }

    if (state.isLoading) {
        A2Loading()
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // ── Profile header ──────────────────────────────────────────────────
        item {
            A2Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    A2Avatar(
                        initials = state.nombre.take(2).ifEmpty { "AP" },
                        size = 56.dp
                    )
                    Column(Modifier.weight(1f)) {
                        A2Headline(text = state.nombre)
                        A2BodySmall(text = state.email)
                        if (state.rol.isNotEmpty()) {
                            Spacer(Modifier.height(4.dp))
                            A2Tag(text = state.rol, variant = A2TagVariant.Subtle)
                        }
                    }
                }
            }
        }

        // ── Saldo ───────────────────────────────────────────────────────────
        if (state.deudaBolivianos != 0.0) {
            item {
                A2Card(modifier = Modifier.fillMaxWidth()) {
                    A2BodySmall(text = "Saldo / Deuda")
                    Spacer(Modifier.height(4.dp))
                    A2GradientDisplayLg(
                        text = "${state.deudaBolivianos} BOB"
                    )
                }
            }
        }

        // ── Últimos movimientos ─────────────────────────────────────────────
        if (state.ultimosMovimientos.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    A2Headline(text = "Últimos movimientos")
                    A2TextButton(text = "Ver historial", onClick = onIrAHistorial)
                }
            }
            items(state.ultimosMovimientos.take(5)) { mov ->
                A2CardGlass(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            A2BodySmall(text = mov.concepto, color = TextPrimary)
                        }
                        A2Body(
                            text = "${if (mov.tipo == "CREDITO") "+" else "-"} ${mov.monto} BOB",
                            color = if (mov.tipo == "CREDITO") PrimaryElectric else Error
                        )
                    }
                }
            }
        }

        // ── Logout ──────────────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(8.dp))
            A2Button(
                text = "Desvincular cuenta",
                onClick = { mostrarDialogoLogout = true },
                variant = A2ButtonVariant.Secondary,
                icon = Lucide.LogOut,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
