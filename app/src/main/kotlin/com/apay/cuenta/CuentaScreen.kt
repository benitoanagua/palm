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
                Text("Desvincular cuenta", style = APAYTextStyle.Headline, color = TextPrimary)
            },
            text = {
                Text(
                    "Se cerrará tu sesión en este dispositivo. Deberás ingresar tu email y contraseña la próxima vez.",
                    style = APAYTextStyle.BodyMedium,
                    color = TextSecondary
                )
            },
            confirmButton = {
                APAYButton(
                    text = "Sí, desvincular",
                    onClick = {
                        mostrarDialogoLogout = false
                        onLogout()
                    },
                    variant = APAYButtonVariant.Primary
                )
            },
            dismissButton = {
                APAYTextButton(
                    text    = "Cancelar",
                    onClick = { mostrarDialogoLogout = false },
                    color   = TextMuted
                )
            }
        )
    }

    if (state.isLoading) {
        APAYLoading()
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
            APAYCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    APAYAvatar(
                        initials = state.nombre.take(2).ifEmpty { "AP" },
                        size = 56.dp
                    )
                    Column(Modifier.weight(1f)) {
                        APAYHeadline(text = state.nombre)
                        APAYBodySmall(text = state.email)
                        if (state.rol.isNotEmpty()) {
                            Spacer(Modifier.height(4.dp))
                            APAYTag(text = state.rol, variant = APAYTagVariant.Subtle)
                        }
                    }
                }
            }
        }

        // ── Saldo ───────────────────────────────────────────────────────────
        if (state.deudaBolivianos != 0.0) {
            item {
                APAYCard(modifier = Modifier.fillMaxWidth()) {
                    APAYBodySmall(text = "Saldo / Deuda")
                    Spacer(Modifier.height(4.dp))
                    APAYGradientDisplayLg(
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
                    APAYHeadline(text = "Últimos movimientos")
                    APAYTextButton(text = "Ver historial", onClick = onIrAHistorial)
                }
            }
            items(state.ultimosMovimientos.take(5)) { mov ->
                APAYCardGlass(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            APAYBodySmall(text = mov.concepto, color = TextPrimary)
                        }
                        APAYBody(
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
            APAYButton(
                text = "Desvincular cuenta",
                onClick = { mostrarDialogoLogout = true },
                variant = APAYButtonVariant.Secondary,
                icon = Lucide.LogOut,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
