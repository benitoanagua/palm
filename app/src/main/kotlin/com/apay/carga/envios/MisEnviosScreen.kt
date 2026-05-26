package com.apay.carga.envios

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.*
import com.apay.ui.components.*
import com.apay.ui.theme.*

@Composable
fun MisEnviosScreen(
    onVolver: () -> Unit = {},
    onVerDetalle: (Envio) -> Unit = {}
) {
    // Datos mock para demostración
    val envios = remember {
        listOf(
            Envio("ENV-001", "Tarija", "Santa Cruz", "25/12/2026", "En Tránsito", 450, "Juan Pérez", 4.8),
            Envio("ENV-002", "Cochabamba", "La Paz", "20/12/2026", "Entregado", 280, "Carlos Mamani", 4.5),
            Envio("ENV-003", "Santa Cruz", "Sucre", "28/12/2026", "Pendiente", 600, "Roberto Sánchez", 4.9)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Lucide.ArrowLeft, "Volver", tint = PrimaryDeepBlue)
            }
            
            Text(
                text = "Mis Envíos",
                style = A2TextStyle.Headline,
                fontWeight = FontWeight.Bold,
                color = PrimaryDeepBlue
            )
            
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Pestañas de estado
        var estadoSeleccionado by remember { mutableStateOf("Todos") }
        val estados = listOf("Todos", "Pendiente", "En Tránsito", "Entregado")
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            estados.forEach { estado ->
                FilterChip(
                    selected = estadoSeleccionado == estado,
                    onClick = { estadoSeleccionado = estado },
                    label = { Text(estado, style = A2TextStyle.BodySmall) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Lista de envíos
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(envios) { envio ->
                EnvioCard(envio = envio, onClick = { onVerDetalle(envio) })
            }
        }
    }
}

@Composable
private fun EnvioCard(
    envio: Envio,
    onClick: () -> Unit
) {
    val colorEstado = when (envio.estado) {
        "Entregado" -> androidx.compose.ui.graphics.Color(0xFF22C55E)
        "En Tránsito" -> androidx.compose.ui.graphics.Color(0xFF3B82F6)
        "Pendiente" -> androidx.compose.ui.graphics.Color(0xFFF59E0B)
        else -> SecondaryElectric
    }
    
    A2Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header con ID y estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = envio.id,
                    style = A2TextStyle.BodySmall,
                    color = SecondaryElectric
                )
                
                Box(
                    modifier = Modifier
                        .background(
                            color = colorEstado.copy(alpha = 0.1f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = envio.estado,
                        style = A2TextStyle.BodySmall,
                        fontWeight = FontWeight.Bold,
                        color = colorEstado
                    )
                }
            }
            
            // Ruta
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Lucide.MapPin, "Origen", tint = SecondaryElectric, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(envio.origen, style = A2TextStyle.BodyMedium)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Lucide.Flag, "Destino", tint = PrimaryDeepBlue, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(envio.destino, style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                    }
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${envio.peso} Kg",
                        style = A2TextStyle.BodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepBlue
                    )
                    Text(
                        text = envio.fecha,
                        style = A2TextStyle.BodySmall,
                        color = SecondaryElectric
                    )
                }
            }
            
            HorizontalDivider(color = SecondaryElectric.copy(alpha = 0.2f))
            
            // Transportista
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Lucide.User, "Transportista", tint = SecondaryElectric, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(envio.transportista, style = A2TextStyle.BodySmall)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Lucide.Star, "Calificación", tint = androidx.compose.ui.graphics.Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${envio.calificacion}", style = A2TextStyle.BodySmall, fontWeight = FontWeight.Bold)
                }
            }
            
            // Acciones según estado
            if (envio.estado == "En Tránsito") {
                A2ButtonSecondary(
                    text = "Rastrear",
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

data class Envio(
    val id: String,
    val origen: String,
    val destino: String,
    val fecha: String,
    val estado: String,
    val peso: Int,
    val transportista: String,
    val calificacion: Double
)
