package com.apay.carga.buscar

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
import com.a2.ui.components.*
import com.a2.ui.theme.*

@Composable
fun BuscarCargaScreen(
    onVolver: () -> Unit = {},
    onReservar: (RutaDisponible) -> Unit = {}
) {
    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var tipoCarga by remember { mutableStateOf("General") }
    
    // Datos mock para demostración
    val rutasDisponibles = remember {
        listOf(
            RutaDisponible("Tarija", "Santa Cruz", "25/12/2026 08:00", "500 Kg", "0.45", "Juan Pérez", 4.8, 23),
            RutaDisponible("Cochabamba", "La Paz", "26/12/2026 10:00", "300 Kg", "0.50", "Carlos Mamani", 4.5, 15),
            RutaDisponible("Santa Cruz", "Sucre", "27/12/2026 06:00", "800 Kg", "0.40", "Roberto Sánchez", 4.9, 45)
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
                text = "Buscar Transporte",
                style = A2TextStyle.Headline,
                fontWeight = FontWeight.Bold,
                color = PrimaryDeepBlue
            )
            
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Filtros de búsqueda
        A2Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    A2Input(
                        modifier = Modifier.weight(1f),
                        value = origen,
                        onValueChange = { origen = it },
                        placeholder = "Origen",
                        leadingIcon = Lucide.MapPin
                    )
                    A2Input(
                        modifier = Modifier.weight(1f),
                        value = destino,
                        onValueChange = { destino = it },
                        placeholder = "Destino",
                        leadingIcon = Lucide.Flag
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    A2Input(
                        modifier = Modifier.weight(1f),
                        value = fecha,
                        onValueChange = { fecha = it },
                        placeholder = "Fecha",
                        leadingIcon = Lucide.Calendar
                    )
                    // Selector tipo de carga
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        A2Input(
                            value = tipoCarga,
                            onValueChange = {},
                            placeholder = "Tipo Carga",
                            leadingIcon = Lucide.Package,
                            enabled = false
                        )
                    }
                }
                
                A2Button(
                    text = "Buscar",
                    onClick = { /* Lógica de búsqueda */ },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Resultados
        Text(
            text = "Rutas Disponibles",
            style = A2TextStyle.BodyMedium,
            fontWeight = FontWeight.Bold,
            color = PrimaryDeepBlue
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rutasDisponibles) { ruta ->
                RutaCard(ruta = ruta, onReservar = { onReservar(ruta) })
            }
        }
    }
}

@Composable
private fun RutaCard(
    ruta: RutaDisponible,
    onReservar: () -> Unit
) {
    A2Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
                        Text(ruta.origen, style = A2TextStyle.BodySmall, color = SecondaryElectric)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Lucide.Flag, "Destino", tint = PrimaryDeepBlue, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(ruta.destino, style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                    }
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Bs ${ruta.precio}/Kg",
                        style = A2TextStyle.Headline,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepBlue
                    )
                }
            }
            
            HorizontalDivider(color = SecondaryElectric.copy(alpha = 0.2f))
            
            // Detalles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Lucide.Calendar, "Fecha", tint = SecondaryElectric, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(ruta.fecha, style = A2TextStyle.BodySmall, color = SecondaryElectric)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Lucide.Scale, "Capacidad", tint = SecondaryElectric, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(ruta.capacidad, style = A2TextStyle.BodySmall, color = SecondaryElectric)
                }
            }
            
            // Transportista
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Lucide.User, "Transportista", tint = SecondaryElectric, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(ruta.transportista, style = A2TextStyle.BodySmall)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Lucide.Star, "Calificación", tint = Estrella, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${ruta.calificacion}", style = A2TextStyle.BodySmall, fontWeight = FontWeight.Bold)
                }
            }
            
            // Botón reservar
            A2Button(
                text = "Reservar Espacio",
                onClick = onReservar,
                modifier = Modifier.fillMaxWidth(),
                variant = A2ButtonVariant.Secondary
            )
        }
    }
}

data class RutaDisponible(
    val origen: String,
    val destino: String,
    val fecha: String,
    val capacidad: String,
    val precio: String,
    val transportista: String,
    val calificacion: Double,
    val viajesCompletados: Int
)
