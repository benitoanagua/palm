package com.apay.carga.rutas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.*
import com.a2.ui.components.*
import com.a2.ui.theme.*

@Composable
fun PublicarRutaScreen(
    onVolver: () -> Unit = {},
    onPublicar: (RutaData) -> Unit = {}
) {
    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var tipoCarga by remember { mutableStateOf("General") }

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
                text = "Publicar Ruta",
                style = A2TextStyle.Headline,
                fontWeight = FontWeight.Bold,
                color = PrimaryDeepBlue
            )
            
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Formulario
        A2Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Origen
                Text("Origen", style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                A2Input(
                    value = origen,
                    onValueChange = { origen = it },
                    placeholder = "Ej: Tarija",
                    leadingIcon = Lucide.MapPin
                )
                
                // Destino
                Text("Destino", style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                A2Input(
                    value = destino,
                    onValueChange = { destino = it },
                    placeholder = "Ej: Santa Cruz",
                    leadingIcon = Lucide.Flag
                )
                
                // Fecha
                Text("Fecha de Salida", style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                A2Input(
                    value = fecha,
                    onValueChange = { fecha = it },
                    placeholder = "Ej: 25/12/2026 08:00",
                    leadingIcon = Lucide.Calendar
                )
                
                // Capacidad disponible
                Text("Capacidad Disponible", style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    A2Input(
                        modifier = Modifier.weight(1f),
                        value = capacidad,
                        onValueChange = { capacidad = it },
                        placeholder = "Kg",
                        leadingIcon = Lucide.Scale
                    )
                    A2Input(
                        modifier = Modifier.weight(1f),
                        value = capacidad,
                        onValueChange = { capacidad = it },
                        placeholder = "m³",
                        leadingIcon = Lucide.Box
                    )
                }
                
                // Precio
                Text("Precio por Kg (Bs)", style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                A2Input(
                    value = precio,
                    onValueChange = { precio = it },
                    placeholder = "Ej: 0.50",
                    leadingIcon = Lucide.DollarSign,
                    keyboardType = KeyboardType.Number
                )
                
                // Tipo de carga
                Text("Tipo de Carga que aceptas", style = A2TextStyle.BodyMedium, fontWeight = FontWeight.Bold)
                val tipos = listOf("General", "Frágil", "Refrigerada", "Peligrosa")
                tipos.forEach { tipo ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(tipo, style = A2TextStyle.BodyMedium)
                        Checkbox(
                            checked = tipoCarga == tipo,
                            onCheckedChange = { if (it) tipoCarga = tipo }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Botón publicar
                A2Button(
                    text = "Publicar Ruta",
                    onClick = {
                        onPublicar(
                            RutaData(
                                origen = origen,
                                destino = destino,
                                fecha = fecha,
                                capacidad = capacidad,
                                precio = precio,
                                tipoCarga = tipoCarga
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Información de comisión
        A2Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Lucide.Info, "Info", tint = PrimaryDeepBlue)
                Column {
                    Text(
                        text = "Comisión APAY: 10%",
                        style = A2TextStyle.BodySmall,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepBlue
                    )
                    Text(
                        text = "El pago se libera al confirmar la entrega",
                        style = A2TextStyle.BodySmall,
                        color = SecondaryElectric
                    )
                }
            }
        }
    }
}

data class RutaData(
    val origen: String,
    val destino: String,
    val fecha: String,
    val capacidad: String,
    val precio: String,
    val tipoCarga: String
)
