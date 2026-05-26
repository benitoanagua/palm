package com.apay.empresa.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardEmpresarialScreen(
    empresaId: String,
    onNavigateToSubUsuarios: () -> Unit = {},
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(empresaId) {
        viewModel.cargarDashboard(empresaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard Empresarial") },
                actions = {
                    IconButton(onClick = { /* Refresh */ }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.cargando) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(uiState.error!!, color = MaterialTheme.colorScheme.error)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Resumen de la empresa
                item {
                    EmpresaCard(uiState.empresa)
                }

                // Estadísticas del mes actual
                item {
                    EstadisticasMesCard(uiState.estadisticasMesActual)
                }

                // Métricas clave
                item {
                    MetricasClaveCard(uiState.reporteEnvios)
                }

                // Rutas más usadas
                if (!uiState.reporteEnvios?.rutasMasUsadas.isNullOrEmpty()) {
                    item {
                        RutasFrecuentesCard(uiState.reporteEnvios!!.rutasMasUsadas)
                    }
                }

                // Transportistas frecuentes
                if (!uiState.reporteEnvios?.transportistasFrecuentes.isNullOrEmpty()) {
                    item {
                        TransportistasFrecuentesCard(
                            uiState.reporteEnvios!!.transportistasFrecuentes
                        )
                    }
                }

                // Sub-usuarios
                item {
                    SubUsuariosResumenCard(
                        subUsuarios = uiState.subUsuarios,
                        maxSubUsuarios = uiState.empresa?.subUsuariosMaximos ?: 10,
                        onVerTodos = onNavigateToSubUsuarios
                    )
                }

                // Acciones de exportación
                item {
                    ExportarReportesCard(
                        onExportarPDF = { mes, anio ->
                            viewModel.exportarReportePDF(mes, anio)
                        },
                        onExportarExcel = { mes, anio ->
                            viewModel.exportarReporteExcel(mes, anio)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmpresaCard(empresa: Empresa?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Información de la Empresa",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Divider()

            InfoRow("Nombre", empresa?.nombre ?: "N/A")
            InfoRow("NIT", empresa?.nit ?: "N/A")
            InfoRow("Ciudad", empresa?.ciudad ?: "N/A")
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Límite Mensual:", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = formatCurrency(empresa?.limiteGastoMensual ?: 0.0),
                    fontWeight = FontWeight.Bold
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Gasto Actual:", style = MaterialTheme.typography.bodyMedium)
                val porcentaje = if ((empresa?.limiteGastoMensual ?: 0.0) > 0) {
                    ((empresa.gastoActualMes / empresa.limiteGastoMensual) * 100).toInt()
                } else 0
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = formatCurrency(empresa?.gastoActualMes ?: 0.0),
                        fontWeight = FontWeight.Bold,
                        color = if (porcentaje > 80) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    )
                    LinearProgressIndicator(
                        progress = (porcentaje.coerceIn(0, 100) / 100f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .padding(top = 4.dp),
                        color = if (porcentaje > 80) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EstadisticasMesCard(estadisticas: EstadisticaMensual?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Estadísticas del Mes Actual",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.LocalShipping,
                    label = "Total Envíos",
                    value = estadisticas?.totalEnvios?.toString() ?: "0"
                )
                
                StatItem(
                    icon = Icons.Default.AttachMoney,
                    label = "Costo Total",
                    value = formatCurrency(estadisticas?.costoTotal ?: 0.0)
                )
                
                StatItem(
                    icon = Icons.Default.TrendingUp,
                    label = "Entregas a Tiempo",
                    value = "${estadisticas?.tasaEntregaATiempo?.toInt() ?: 0}%"
                )
            }
        }
    }
}

@Composable
private fun MetricasClaveCard(reporte: ReporteEnvios?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Métricas Clave",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Divider()

            MetricaItem(
                label = "Ahorro Estimado vs. Mercado Informal",
                valor = formatCurrency(reporte?.ahorroEstimado ?: 0.0),
                icon = Icons.Default.Savings,
                color = MaterialTheme.colorScheme.secondary
            )

            MetricaItem(
                label = "Emisiones CO₂ Evitadas",
                valor = "${((reporte?.emisionesCO2Evitadas ?: 0.0) / 1000).toInt()} kg",
                icon = Icons.Default.Eco,
                color = MaterialTheme.colorScheme.tertiary
            )

            MetricaItem(
                label = "Peso Total Transportado",
                valor = "${(reporte?.enviosPorTipoCarga?.values?.sum()?.times(100) ?: 0) / 1000} toneladas",
                icon = Icons.Default.FitnessCenter,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun RutasFrecuentesCard(rutas: List<RutaFrecuente>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Rutas Más Usadas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Divider()

            rutas.take(5).forEach { ruta ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "${ruta.origen} → ${ruta.destino}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${ruta.cantidad} viajes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = formatCurrency(ruta.costoPromedio),
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
private fun TransportistasFrecuentesCard(transportistas: List<TransportistaFrecuente>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Transportistas Frecuentes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Divider()

            transportistas.take(5).forEach { transportista ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = transportista.transportistaNombre,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${transportista.cantidadEnvios} envíos",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", transportista.calificacionPromedio),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
private fun SubUsuariosResumenCard(
    subUsuarios: List<SubUsuario>,
    maxSubUsuarios: Int,
    onVerTodos: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onVerTodos,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sub-Usuarios",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Ver todos"
                )
            }
            
            Divider()

            Text(
                text = "${subUsuarios.size} de $maxSubUsuarios usuarios activos",
                style = MaterialTheme.typography.bodyLarge
            )

            LinearProgressIndicator(
                progress = (subUsuarios.size.toFloat() / maxSubUsuarios),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
            )

            if (subUsuarios.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                subUsuarios.take(3).forEach { usuario ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = usuario.nombre,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Badge {
                            Text(usuario.rol)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ExportarReportesCard(
    onExportarPDF: (Int, Int) -> Unit,
    onExportarExcel: (Int, Int) -> Unit
) {
    val calendar = Calendar.getInstance()
    val mesActual = calendar.get(Calendar.MONTH) + 1
    val anioActual = calendar.get(Calendar.YEAR)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Exportar Reportes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Divider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { onExportarPDF(mesActual, anioActual) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.PictureAsPdf,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Exportar PDF")
                }

                Button(
                    onClick = { onExportarExcel(mesActual, anioActual) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(
                        Icons.Default.TableChart,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Exportar Excel")
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StatItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MetricaItem(label: String, valor: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: androidx.compose.ui.graphics.Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = valor,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

private fun formatCurrency(amount: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("es", "BO")).apply {
        currency = Currency.getInstance("USD")
    }.format(amount)
}
