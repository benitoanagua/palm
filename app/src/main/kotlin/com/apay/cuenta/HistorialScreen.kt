package com.apay.cuenta

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.apay.ui.components.*
import com.apay.ui.theme.*

@Composable
fun HistorialScreen(onVolver: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            IconButton(onClick = onVolver) {
                Icon(Lucide.ArrowLeft, "Volver", tint = TextPrimary)
            }
            Spacer(Modifier.width(8.dp))
            APAYHeadline(text = "Historial")
        }
        Spacer(Modifier.height(16.dp))
        APAYCard(modifier = Modifier.fillMaxWidth()) {
            APAYBodySmall(text = "No hay movimientos registrados aún.")
        }
    }
}
