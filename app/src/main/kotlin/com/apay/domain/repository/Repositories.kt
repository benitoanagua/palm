package com.apay.domain.repository

import com.apay.domain.model.*
import kotlinx.coroutines.flow.Flow

interface RutaRepository {
    fun observarRutasDisponibles(): Flow<List<Ruta>>
    fun observarRutasPorTransportista(transportistaId: String): Flow<List<Ruta>>
    suspend fun publicarRuta(ruta: Ruta): Result<Unit>
    suspend fun obtenerRutaPorId(id: String): Result<Ruta>
    suspend fun actualizarEstado(rutaId: String, estado: String): Result<Unit>
    suspend fun eliminarRuta(rutaId: String): Result<Unit>
}

interface EnvioRepository {
    fun observarEnvios(usuarioId: String): Flow<List<Envio>>
    fun observarEnvioPorId(envioId: String): Flow<Envio?>
    suspend fun crearEnvio(envio: Envio): Result<Unit>
    suspend fun actualizarEstado(envioId: String, estado: String): Result<Unit>
    suspend fun confirmarEntrega(envioId: String, evidenciaUrl: String, firmaUrl: String?): Result<Unit>
    suspend fun actualizarUbicacion(envioId: String, lat: Double, lng: Double): Result<Unit>
    suspend fun agregarCalificacion(
        envioId: String,
        calificacionTransportista: Int?,
        comentarioTransportista: String?,
        calificacionCargador: Int?,
        comentarioCargador: String?
    ): Result<Unit>
}

interface TransportistaRepository {
    suspend fun obtenerPerfil(uid: String): Result<Transportista>
    suspend fun actualizarPerfil(transportista: Transportista): Result<Unit>
    suspend fun registrarTransportista(transportista: Transportista): Result<Unit>
    fun observarTransportista(uid: String): Flow<Transportista>
    suspend fun actualizarEstadisticas(uid: String, viajesCompletados: Int, ingresosTotales: Double, kmTotales: Double): Result<Unit>
}

interface UsuarioRepository {
    suspend fun obtenerPorId(uid: String): Result<Usuario>
    suspend fun actualizarPerfil(uid: String, datos: Map<String, Any>): Result<Unit>
    suspend fun actualizarPresencia(uid: String, enLinea: Boolean)
    fun observarUsuario(uid: String): Flow<Usuario>
    suspend fun registrarUsuario(usuario: Usuario): Result<Unit>
}

interface DisputaRepository {
    fun observarDisputas(usuarioId: String): Flow<List<Disputa>>
    suspend fun crearDisputa(disputa: Disputa): Result<Unit>
    suspend fun actualizarEstado(disputaId: String, estado: String, resolucion: String): Result<Unit>
}

// US-21 y US-22: Interfaces para Dashboard Empresarial
interface EmpresaRepository {
    suspend fun registrarEmpresa(empresa: Empresa): Result<Unit>
    suspend fun obtenerEmpresaPorId(id: String): Result<Empresa>
    suspend fun obtenerEmpresaPorNIT(nit: String): Result<Empresa?>
    suspend fun actualizarEmpresa(empresa: Empresa): Result<Unit>
    suspend fun actualizarGastoMensual(empresaId: String, monto: Double): Result<Unit>
    fun observarEmpresa(propietarioId: String): Flow<Empresa?>
    suspend fun eliminarEmpresa(empresaId: String): Result<Unit>
}

interface SubUsuarioRepository {
    suspend fun crearSubUsuario(subUsuario: SubUsuario): Result<Unit>
    suspend fun obtenerSubUsuarioPorId(id: String): Result<SubUsuario>
    fun observarSubUsuarios(empresaId: String): Flow<List<SubUsuario>>
    suspend fun actualizarSubUsuario(subUsuario: SubUsuario): Result<Unit>
    suspend fun eliminarSubUsuario(subUsuarioId: String): Result<Unit>
    suspend fun registrarAcceso(subUsuarioId: String): Result<Unit>
    fun contarSubUsuarios(empresaId: String): Flow<Int>
}

interface ReporteRepository {
    suspend fun generarReporteEnvios(
        empresaId: String,
        fechaInicio: Long,
        fechaFin: Long
    ): Result<ReporteEnvios>
    
    fun observarEstadisticasMensuales(empresaId: String, anio: Int): Flow<List<EstadisticaMensual>>
    
    suspend fun guardarEstadisticaMensual(estadistica: EstadisticaMensual): Result<Unit>
    
    suspend fun exportarReportePDF(empresaId: String, mes: Int, anio: Int): Result<String>
    
    suspend fun exportarReporteExcel(empresaId: String, mes: Int, anio: Int): Result<String>
}
