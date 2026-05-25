package com.apay.tasa

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Lightbulb
import com.composables.icons.lucide.Lucide
import com.apay.ui.components.*
import com.apay.ui.theme.*

@Composable
fun TasaScreen(
    onIrAComprobante: () -> Unit,
    onIrACuenta: () -> Unit,
    onIrAChats: () -> Unit,
    viewModel: TasaViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(color = PrimaryElectric)
            }
            state.error != null -> {
                A2Banner(
                    message = "Error: ${state.error}",
                    type = A2BannerType.Error
                )
            }
            state.tasa != null -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // ── Tasa card ──────────────────────────────────────────
                    A2Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            A2Tag(text = "Tasa en vivo", variant = A2TagVariant.Filled)
                            Spacer(Modifier.height(20.dp))

                            A2BodySmall(text = "1 USD")
                            Spacer(Modifier.height(4.dp))

                            APAYGradientDisplayLg(text = "${state.tasa!!.valor} BOB")
                            Spacer(Modifier.height(12.dp))

                            A2Divider()
                            Spacer(Modifier.height(12.dp))

                            A2Caption(text = "Publicada por ${state.tasa!!.publicadaPor}")
                        }
                    }

                    // ── Info card ──────────────────────────────────────────
                    A2Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Box(
                                modifier = Modifier.size(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Lucide.Lightbulb,
                                    contentDescription = null,
                                    tint = PrimaryElectric,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                A2BodySmall(text = "Actualización en tiempo real")
                                Spacer(Modifier.height(2.dp))
                                A2Caption(text = "Se actualiza automáticamente cuando hay una nueva publicación.")
                            }
                        }
                    }

                    // ── CTA ───────────────────────────────────────────────
                    A2Button(
                        text = "Subir comprobante",
                        onClick = onIrAComprobante,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
