package com.apay.carga.perfil

import androidx.compose.foundation.background
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
fun PerfilScreen(
    esTransportista: Boolean = true,
    onVolver: () -> Unit = {},
    onEditarPerfil: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    val usuario = remember {
        UsuarioInfo(
            nombre = "Juan Pérez",
            email = "juan.perez@email.com",
            telefono = "+591 777 12345",
            calificacion = 4.8,
            viajesCompletados = 23,
            miembroDesde = "Dic 2026",
            placa = "CBP-12345",
            tipoVehiculo = "Camión 3 ejes",
            capacidadMaxima = "2000 Kg"
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
                text = "Mi Perfil",
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
            // Información principal del usuario
            item {
                A2Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(White.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Lucide.User,
                                contentDescription = "Usuario",
                                tint = White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = usuario.nombre,
                            style = A2TextStyle.Headline,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Text(
                            text = if (esTransportista) "Transportista Verificado" else "Cargador",
                            style = A2TextStyle.BodySmall,
                            color = White.copy(alpha = 0.9f)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Calificación
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Lucide.Star,
                                "Calificación",
                                tint = androidx.compose.ui.graphics.Color(0xFFFFD700),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${usuario.calificacion}",
                                style = A2TextStyle.Headline,
                                color = White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "(${usuario.viajesCompletados} viajes)",
                                style = A2TextStyle.BodySmall,
                                color = White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
            
            // Estadísticas
            item {
                A2Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem("Viajes", usuario.viajesCompletados.toString())
                        VerticalDivider(modifier = Modifier.height(40.dp), color = SecondaryElectric.copy(alpha = 0.2f))
                        StatItem("Miembro desde", usuario.miembroDesde)
                        VerticalDivider(modifier = Modifier.height(40.dp), color = SecondaryElectric.copy(alpha = 0.2f))
                        StatItem("Calificación", "${usuario.calificacion}")
                    }
                }
            }
            
            // Información de contacto
            item {
                A2Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Información de Contacto",
                            style = A2TextStyle.BodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryDeepBlue
                        )
                        
                        InfoRow(Lucide.Mail, "Email", usuario.email)
                        InfoRow(Lucide.Phone, "Teléfono", usuario.telefono)
                    }
                }
            }
            
            // Información del vehículo (solo transportistas)
            if (esTransportista) {
                item {
                    A2Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Información del Vehículo",
                                style = A2TextStyle.BodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryDeepBlue
                            )
                            
                            InfoRow(Lucide.Truck, "Tipo", usuario.tipoVehiculo)
                            InfoRow(Lucide.Hash, "Placa", usuario.placa)
                            InfoRow(Lucide.Scale, "Capacidad Máx.", usuario.capacidadMaxima)
                        }
                    }
                }
            }
            
            // Acciones
            item {
                A2Button(
                    text = "Editar Perfil",
                    onClick = onEditarPerfil,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                A2Button(
                    text = "Cerrar Sesión",
                    onClick = onCerrarSesion,
                    modifier = Modifier.fillMaxWidth(),
                    variant = A2ButtonVariant.Secondary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = A2TextStyle.BodyMedium,
            fontWeight = FontWeight.Bold,
            color = PrimaryDeepBlue
        )
        Text(
            text = label,
            style = A2TextStyle.BodySmall,
            color = SecondaryElectric
        )
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
        Text(value, style = A2TextStyle.BodyMedium)
    }
}

data class UsuarioInfo(
    val nombre: String,
    val email: String,
    val telefono: String,
    val calificacion: Double,
    val viajesCompletados: Int,
    val miembroDesde: String,
    val placa: String = "",
    val tipoVehiculo: String = "",
    val capacidadMaxima: String = ""
)
