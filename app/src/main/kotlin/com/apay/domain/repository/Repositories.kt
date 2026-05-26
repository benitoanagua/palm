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
