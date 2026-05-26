package com.apay.domain.repository

import com.apay.domain.model.*
import kotlinx.coroutines.flow.Flow

interface RutaRepository {
    fun observarRutasDisponibles(): Flow<List<Ruta>>
    suspend fun publicarRuta(ruta: Ruta): Result<Unit>
    suspend fun obtenerRutaPorId(id: String): Result<Ruta>
}

interface EnvioRepository {
    fun observarEnvios(usuarioId: String): Flow<List<Envio>>
    suspend fun crearEnvio(envio: Envio): Result<Unit>
    suspend fun actualizarEstado(envioId: String, estado: String): Result<Unit>
    suspend fun confirmarEntrega(envioId: String, evidenciaUrl: String): Result<Unit>
}

interface TransportistaRepository {
    suspend fun obtenerPerfil(uid: String): Result<Transportista>
    suspend fun actualizarPerfil(transportista: Transportista): Result<Unit>
    fun observarTransportista(uid: String): Flow<Transportista>
}

interface UsuarioRepository {
    suspend fun obtenerPorId(uid: String): Result<Usuario>
    suspend fun actualizarPerfil(uid: String, datos: Map<String, Any>): Result<Unit>
    suspend fun actualizarPresencia(uid: String, enLinea: Boolean)
    fun observarUsuario(uid: String): Flow<Usuario>
}
