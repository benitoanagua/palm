package com.apay.carga.home

import androidx.compose.foundation.layout.*
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
fun CargaHomeScreen(
    onPublicarRuta: () -> Unit = {},
    onBuscarCarga: () -> Unit = {},
    onVerEnvios: () -> Unit = {},
    onIrAPerfil: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header de bienvenida
        Text(
            text = "A2 Carga Compartida",
            style = A2TextStyle.Headline,
            color = PrimaryDeepBlue,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Conecta tu capacidad disponible con quien necesita enviar mercancías",
            style = A2TextStyle.BodyMedium,
            color = SecondaryElectric
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Tarjeta principal - Acción rápida para transportista
        A2Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Lucide.Truck,
                    contentDescription = "Transporte",
                    tint = White,
                    modifier = Modifier.size(48.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "¿Tienes espacio en tu camión?",
                    style = A2TextStyle.Headline,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Publica tu ruta y genera ingresos extra por el espacio que sobra",
                    style = A2TextStyle.BodyMedium,
                    color = White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                A2Button(
                    text = "Publicar Ruta",
                    onClick = onPublicarRuta
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Sección para cargadores
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Buscar transporte
            A2Card(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Lucide.Search,
                        contentDescription = "Buscar",
                        tint = PrimaryDeepBlue,
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Buscar Transporte",
                        style = A2TextStyle.BodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepBlue
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "Encuentra transporte disponible",
                        style = A2TextStyle.BodySmall,
                        color = SecondaryElectric
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    A2Button(
                        text = "Buscar",
                        onClick = onBuscarCarga,
                        variant = A2ButtonVariant.Secondary
                    )
                }
            }
            
            // Ver envíos
            A2Card(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Lucide.Package,
                        contentDescription = "Envíos",
                        tint = PrimaryDeepBlue,
                        modifier = Modifier.size(32.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Mis Envíos",
                        style = A2TextStyle.BodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDeepBlue
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "Rastrea tus cargas",
                        style = A2TextStyle.BodySmall,
                        color = SecondaryElectric
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    A2Button(
                        text = "Ver",
                        onClick = onVerEnvios,
                        variant = A2ButtonVariant.Secondary
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Estadísticas rápidas
        A2Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Tu Actividad",
                    style = A2TextStyle.BodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryDeepBlue
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("Rutas Publicadas", "0")
                    StatItem("Envíos Completados", "0")
                    StatItem("Calificación", "—")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Botón de perfil
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onIrAPerfil) {
                Icon(
                    imageVector = Lucide.User,
                    contentDescription = "Perfil",
                    tint = PrimaryDeepBlue
                )
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = A2TextStyle.Headline,
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
