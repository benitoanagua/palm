package com.apay.carga.detalle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun DetalleEnvioScreen(
    envioId: String = "ENV-001",
    onVolver: () -> Unit = {},
    onConfirmarEntrega: () -> Unit = {}
) {
    // Datos mock para demostración
    val envio = remember {
        EnvioDetalle(
            id = envioId,
            origen = "Tarija",
            destino = "Santa Cruz",
            fechaSalida = "25/12/2026 08:00",
            fechaEntregaEstimada = "26/12/2026 14:00",
            estado = "En Tránsito",
            peso = 450,
            dimensiones = "1.2 x 0.8 x 1.0 m",
            tipoCarga = "General",
            precioTotal = 202.50,
            transportista = TransportistaInfo("Juan Pérez", "CBP-12345", 4.8, 23, "+591 777 12345"),
            ubicacionActual = "Camino a Santa Cruz - Km 180",
            progreso = 65
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
                text = "Detalle de Envío",
                style = A2TextStyle.Headline,
                fontWeight = FontWeight.Bold,
                color = PrimaryDeepBlue
            )
            
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado actual con progreso
            item {
                A2Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Lucide.Truck,
                            contentDescription = "En tránsito",
                            tint = White,
                            modifier = Modifier.size(40.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = envio.estado,
                            style = A2TextStyle.Headline,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = envio.ubicacionActual,
                            style = A2TextStyle.BodySmall,
                            color = White.copy(alpha = 0.9f)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Barra de progreso
                        LinearProgressIndicator(
                            progress = { (envio.progreso / 100f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = White,
                            trackColor = White.copy(alpha = 0.3f)
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${envio.progreso}%", style = A2TextStyle.BodySmall, color = White)
                            Text("100%", style = A2TextStyle.BodySmall, color = White)
                        }
                    }
                }
            }
            
            // Información de ruta
            item {
                A2Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Información de Ruta",
                            style = A2TextStyle.BodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepBlue
                        )
                        
                        InfoRow(Lucide.MapPin, "Origen", envio.origen)
                        InfoRow(Lucide.Flag, "Destino", envio.destino)
                        InfoRow(Lucide.Calendar, "Salida", envio.fechaSalida)
                        InfoRow(Lucide.Clock, "Entrega Estimada", envio.fechaEntregaEstimada)
                    }
                }
            }
            
            // Detalles de carga
            item {
                A2Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Detalles de Carga",
                            style = A2TextStyle.BodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepBlue
                        )
                        
                        InfoRow(Lucide.Package, "Tipo", envio.tipoCarga)
                        InfoRow(Lucide.Scale, "Peso", "${envio.peso} Kg")
                        InfoRow(Lucide.Box, "Dimensiones", envio.dimensiones)
                        InfoRow(Lucide.DollarSign, "Precio Total", "Bs ${envio.precioTotal}")
                    }
                }
            }
            
            // Transportista
            item {
                A2Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Transportista",
                            style = A2TextStyle.BodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepBlue
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(envio.transportista.nombre, style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                                Text("Placa: ${envio.transportista.placa}", style = A2TextStyle.BodySmall, color = SecondaryElectric)
                            }
                            
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Lucide.Star, "Calificación", tint = androidx.compose.ui.graphics.Color(0xFFFFD700), modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("${envio.transportista.calificacion}", style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                    A2Button(
                        text = "Contactar",
                        onClick = { /* Abrir chat o llamada */ },
                        modifier = Modifier.fillMaxWidth(),
                        variant = A2ButtonVariant.Secondary
                    )
                    }
                }
            }
            
            // Acciones
            item {
                if (envio.estado == "En Tránsito") {
                    A2Button(
                        text = "Confirmar Entrega",
                        onClick = onConfirmarEntrega,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, label, tint = SecondaryElectric, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, style = A2TextStyle.BodySmall, color = SecondaryElectric)
        }
        Text(value, style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
    }
}

data class EnvioDetalle(
    val id: String,
    val origen: String,
    val destino: String,
    val fechaSalida: String,
    val fechaEntregaEstimada: String,
    val estado: String,
    val peso: Int,
    val dimensiones: String,
    val tipoCarga: String,
    val precioTotal: Double,
    val transportista: TransportistaInfo,
    val ubicacionActual: String,
    val progreso: Int
)

data class TransportistaInfo(
    val nombre: String,
    val placa: String,
    val calificacion: Double,
    val viajesCompletados: Int,
    val telefono: String
)
