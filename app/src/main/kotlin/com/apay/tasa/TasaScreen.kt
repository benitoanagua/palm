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
                APAYBanner(
                    message = "Error: ${state.error}",
                    type = APAYBannerType.Error
                )
            }
            state.tasa != null -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // ── Tasa card ──────────────────────────────────────────
                    APAYCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            APAYTag(text = "Tasa en vivo", variant = APAYTagVariant.Filled)
                            Spacer(Modifier.height(20.dp))

                            APAYBodySmall(text = "1 USD")
                            Spacer(Modifier.height(4.dp))

                            APAYGradientDisplayLg(text = "${state.tasa!!.valor} BOB")
                            Spacer(Modifier.height(12.dp))

                            APAYDivider()
                            Spacer(Modifier.height(12.dp))

                            APAYCaption(text = "Publicada por ${state.tasa!!.publicadaPor}")
                        }
                    }

                    // ── Info card ──────────────────────────────────────────
                    APAYCard(modifier = Modifier.fillMaxWidth()) {
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
                                APAYBodySmall(text = "Actualización en tiempo real")
                                Spacer(Modifier.height(2.dp))
                                APAYCaption(text = "Se actualiza automáticamente cuando hay una nueva publicación.")
                            }
                        }
                    }

                    // ── CTA ───────────────────────────────────────────────
                    APAYButton(
                        text = "Subir comprobante",
                        onClick = onIrAComprobante,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
