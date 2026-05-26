package com.apay.empresa.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apay.domain.model.*
import com.apay.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val empresa: Empresa? = null,
    val estadisticasMesActual: EstadisticaMensual? = null,
    val subUsuarios: List<SubUsuario> = emptyList(),
    val reporteEnvios: ReporteEnvios? = null,
    val cargando: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val empresaRepository: EmpresaRepository,
    private val subUsuarioRepository: SubUsuarioRepository,
    private val reporteRepository: ReporteRepository,
    private val envioRepository: EnvioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun cargarDashboard(empresaId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(cargando = true)
            
            try {
                // Cargar datos de la empresa
                val empresaResult = empresaRepository.obtenerEmpresaPorId(empresaId)
                val empresa = empresaResult.getOrNull()

                // Cargar sub-usuarios
                val subUsuarios = subUsuarioRepository.observarSubUsuarios(empresaId)
                    .first()

                // Generar reporte del mes actual
                val now = System.currentTimeMillis()
                val inicioMes = getInicioMes(now)
                val finMes = now

                val reporteResult = reporteRepository.generarReporteEnvios(
                    empresaId = empresaId,
                    fechaInicio = inicioMes,
                    fechaFin = finMes
                )
                val reporte = reporteResult.getOrNull()

                // Calcular estadísticas del mes actual
                val estadisticasMes = calcularEstadisticasMes(reporte, empresaId)

                _uiState.value = DashboardUiState(
                    empresa = empresa,
                    estadisticasMesActual = estadisticasMes,
                    subUsuarios = subUsuarios.filter { it.activo },
                    reporteEnvios = reporte,
                    cargando = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    cargando = false,
                    error = e.message ?: "Error al cargar el dashboard"
                )
            }
        }
    }

    private fun getInicioMes(timestamp: Long): Long {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1)
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private suspend fun calcularEstadisticasMes(
        reporte: ReporteEnvios?,
        empresaId: String
    ): EstadisticaMensual? {
        if (reporte == null) return null

        val now = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = now

        val enviosCompletados = reporte.enviosPorEstado["ENTREGADO"] ?: 0
        val totalEnvios = reporte.totalEnvios
        
        val tasaEntregaATiempo = if (totalEnvios > 0) {
            (enviosCompletados.toDouble() / totalEnvios) * 100
        } else 0.0

        return EstadisticaMensual(
            empresaId = empresaId,
            mes = calendar.get(java.util.Calendar.MONTH) + 1,
            anio = calendar.get(java.util.Calendar.YEAR),
            totalEnvios = reporte.totalEnvios,
            costoTotal = reporte.costoTotal,
            costoPromedioPorEnvio = if (reporte.totalEnvios > 0) {
                reporte.costoTotal / reporte.totalEnvios
            } else 0.0,
            pesoTotalTransportado = reporte.enviosPorTipoCarga.entries.sumOf { 
                // Estimación basada en tipo de carga
                it.value * 100 // promedio 100kg por envío
            },
            rutasUnicas = reporte.rutasMasUsadas.size,
            transportistasUnicos = reporte.transportistasFrecuentes.size,
            tasaEntregaATiempo = tasaEntregaATiempo,
            calificacionPromedio = reporte.transportistasFrecuentes
                .map { it.calificacionPromedio }
                .average()
                .takeIf { !it.isNaN() } ?: 0.0
        )
    }

    fun actualizarGastoMensual(monto: Double) {
        val empresaId = _uiState.value.empresa?.id ?: return
        
        viewModelScope.launch {
            empresaRepository.actualizarGastoMensual(empresaId, monto)
                .onSuccess {
                    cargarDashboard(empresaId)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = "Error al actualizar gasto: ${error.message}"
                    )
                }
        }
    }

    fun exportarReportePDF(mes: Int, anio: Int) {
        val empresaId = _uiState.value.empresa?.id ?: return
        
        viewModelScope.launch {
            reporteRepository.exportarReportePDF(empresaId, mes, anio)
                .onSuccess { url ->
                    // Aquí se abriría el PDF para descarga/visualización
                    _uiState.value = _uiState.value.copy(
                        error = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = "Error al exportar PDF: ${error.message}"
                    )
                }
        }
    }

    fun exportarReporteExcel(mes: Int, anio: Int) {
        val empresaId = _uiState.value.empresa?.id ?: return
        
        viewModelScope.launch {
            reporteRepository.exportarReporteExcel(empresaId, mes, anio)
                .onSuccess { url ->
                    // Aquí se abriría el Excel para descarga/visualización
                    _uiState.value = _uiState.value.copy(
                        error = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = "Error al exportar Excel: ${error.message}"
                    )
                }
        }
    }

    fun agregarSubUsuario(subUsuario: SubUsuario) {
        viewModelScope.launch {
            subUsuarioRepository.crearSubUsuario(subUsuario)
                .onSuccess {
                    val empresaId = _uiState.value.empresa?.id ?: return@launch
                    cargarDashboard(empresaId)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = "Error al agregar sub-usuario: ${error.message}"
                    )
                }
        }
    }

    fun eliminarSubUsuario(subUsuarioId: String) {
        viewModelScope.launch {
            subUsuarioRepository.eliminarSubUsuario(subUsuarioId)
                .onSuccess {
                    val empresaId = _uiState.value.empresa?.id ?: return@launch
                    cargarDashboard(empresaId)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = "Error al eliminar sub-usuario: ${error.message}"
                    )
                }
        }
    }
}
