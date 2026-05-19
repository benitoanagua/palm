package com.apay.domain.repository

import com.apay.domain.model.*
import kotlinx.coroutines.flow.Flow

interface TasaRepository {
    fun observarTasaActual(): Flow<Tasa>
    suspend fun publicarTasa(tasa: Tasa): Result<Unit>
    suspend fun obtenerHistorial(): Result<List<Tasa>>
}

interface UsuarioRepository {
    suspend fun obtenerPorId(uid: String): Result<Usuario>
    suspend fun actualizarAvatar(uid: String, url: String): Result<Unit>
    suspend fun actualizarPresencia(uid: String, enLinea: Boolean)
    fun observarUsuario(uid: String): Flow<Usuario>
}

interface ChatRepository {
    fun observarChats(uid: String): Flow<List<Chat>>
    suspend fun crearChat(participantes: List<String>, titulo: String): Result<String>
    suspend fun marcarComoLeido(chatId: String, uid: String): Result<Unit>
}

interface MensajeRepository {
    fun observarMensajes(chatId: String): Flow<List<Mensaje>>
    suspend fun enviarMensaje(mensaje: Mensaje): Result<Unit>
    suspend fun enviarMensajeConImagen(mensaje: Mensaje, imagenUrl: String): Result<Unit>
}
