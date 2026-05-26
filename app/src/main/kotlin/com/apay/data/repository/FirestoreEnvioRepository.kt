package com.apay.data.repository

import com.apay.domain.model.Envio
import com.apay.domain.repository.EnvioRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreEnvioRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : EnvioRepository {

    override fun observarEnvios(usuarioId: String): Flow<List<Envio>> = callbackFlow {
        val listener = firestore.collection("envios")
            .where(or(
                whereEqualTo("cargadorId", usuarioId),
                whereEqualTo("transportistaId", usuarioId)
            ))
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snap, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                val envios = snap?.documents?.mapNotNull { doc ->
                    try {
                        Envio(
                            id = doc.id,
                            rutaId = doc.getString("rutaId") ?: "",
                            cargadorId = doc.getString("cargadorId") ?: "",
                            cargadorNombre = doc.getString("cargadorNombre") ?: "",
                            cargadorEmpresa = doc.getString("cargadorEmpresa"),
                            transportistaId = doc.getString("transportistaId") ?: "",
                            transportistaNombre = doc.getString("transportistaNombre") ?: "",
                            origen = doc.getString("origen") ?: "",
                            destino = doc.getString("destino") ?: "",
                            fechaSalida = doc.getLong("fechaSalida") ?: 0L,
                            fechaEntregaEstimada = doc.getLong("fechaEntregaEstimada") ?: 0L,
                            pesoKg = doc.getLong("pesoKg")?.toInt() ?: 0,
                            dimensiones = doc.getString("dimensiones") ?: "",
                            tipoCarga = doc.getString("tipoCarga") ?: "",
                            valorDeclarado = doc.getDouble("valorDeclarado") ?: 0.0,
                            precioTotal = doc.getDouble("precioTotal") ?: 0.0,
                            metodoPago = doc.getString("metodoPago") ?: "",
                            estado = doc.getString("estado") ?: "PENDIENTE",
                            evidenciaEntregaUrl = doc.getString("evidenciaEntregaUrl"),
                            firmaReceptorUrl = doc.getString("firmaReceptorUrl"),
                            calificacionTransportista = doc.getLong("calificacionTransportista")?.toInt(),
                            comentarioTransportista = doc.getString("comentarioTransportista"),
                            calificacionCargador = doc.getLong("calificacionCargador")?.toInt(),
                            comentarioCargador = doc.getString("comentarioCargador"),
                            codigoQR = doc.getString("codigoQR"),
                            ubicacionActualLat = doc.getDouble("ubicacionActualLat"),
                            ubicacionActualLng = doc.getDouble("ubicacionActualLng"),
                            ultimaUbicacionTimestamp = doc.getLong("ultimaUbicacionTimestamp"),
                            timestamp = doc.getLong("timestamp") ?: 0L
                        )
                    } catch (e: Exception) { null }
                } ?: emptyList()
                trySend(envios)
            }
        awaitClose { listener.remove() }
    }

    override fun observarEnvioPorId(envioId: String): Flow<Envio?> = callbackFlow {
        val listener = firestore.collection("envios").document(envioId)
            .addSnapshotListener { doc, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                val envio = doc?.let { documento ->
                    try {
                        Envio(
                            id = documento.id,
                            rutaId = documento.getString("rutaId") ?: "",
                            cargadorId = documento.getString("cargadorId") ?: "",
                            cargadorNombre = documento.getString("cargadorNombre") ?: "",
                            cargadorEmpresa = documento.getString("cargadorEmpresa"),
                            transportistaId = documento.getString("transportistaId") ?: "",
                            transportistaNombre = documento.getString("transportistaNombre") ?: "",
                            origen = documento.getString("origen") ?: "",
                            destino = documento.getString("destino") ?: "",
                            fechaSalida = documento.getLong("fechaSalida") ?: 0L,
                            fechaEntregaEstimada = documento.getLong("fechaEntregaEstimada") ?: 0L,
                            pesoKg = documento.getLong("pesoKg")?.toInt() ?: 0,
                            dimensiones = documento.getString("dimensiones") ?: "",
                            tipoCarga = documento.getString("tipoCarga") ?: "",
                            valorDeclarado = documento.getDouble("valorDeclarado") ?: 0.0,
                            precioTotal = documento.getDouble("precioTotal") ?: 0.0,
                            metodoPago = documento.getString("metodoPago") ?: "",
                            estado = documento.getString("estado") ?: "PENDIENTE",
                            evidenciaEntregaUrl = documento.getString("evidenciaEntregaUrl"),
                            firmaReceptorUrl = documento.getString("firmaReceptorUrl"),
                            calificacionTransportista = documento.getLong("calificacionTransportista")?.toInt(),
                            comentarioTransportista = documento.getString("comentarioTransportista"),
                            calificacionCargador = documento.getLong("calificacionCargador")?.toInt(),
                            comentarioCargador = documento.getString("comentarioCargador"),
                            codigoQR = documento.getString("codigoQR"),
                            ubicacionActualLat = documento.getDouble("ubicacionActualLat"),
                            ubicacionActualLng = documento.getDouble("ubicacionActualLng"),
                            ultimaUbicacionTimestamp = documento.getLong("ultimaUbicacionTimestamp"),
                            timestamp = documento.getLong("timestamp") ?: 0L
                        )
                    } catch (e: Exception) { null }
                }
                trySend(envio)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun crearEnvio(envio: Envio): Result<Unit> = runCatching {
        firestore.collection("envios").add(mapOf(
            "rutaId" to envio.rutaId,
            "cargadorId" to envio.cargadorId,
            "cargadorNombre" to envio.cargadorNombre,
            "cargadorEmpresa" to envio.cargadorEmpresa,
            "transportistaId" to envio.transportistaId,
            "transportistaNombre" to envio.transportistaNombre,
            "origen" to envio.origen,
            "destino" to envio.destino,
            "fechaSalida" to envio.fechaSalida,
            "fechaEntregaEstimada" to envio.fechaEntregaEstimada,
            "pesoKg" to envio.pesoKg,
            "dimensiones" to envio.dimensiones,
            "tipoCarga" to envio.tipoCarga,
            "valorDeclarado" to envio.valorDeclarado,
            "precioTotal" to envio.precioTotal,
            "metodoPago" to envio.metodoPago,
            "estado" to envio.estado,
            "codigoQR" to envio.codigoQR,
            "timestamp" to System.currentTimeMillis()
        )).await()
    }

    override suspend fun actualizarEstado(envioId: String, estado: String): Result<Unit> = runCatching {
        firestore.collection("envios").document(envioId)
            .update("estado", estado).await()
    }

    override suspend fun confirmarEntrega(envioId: String, evidenciaUrl: String, firmaUrl: String?): Result<Unit> = runCatching {
        firestore.collection("envios").document(envioId)
            .update(mapOf(
                "estado" to "ENTREGADO",
                "evidenciaEntregaUrl" to evidenciaUrl,
                "firmaReceptorUrl" to firmaUrl
            )).await()
    }

    override suspend fun actualizarUbicacion(envioId: String, lat: Double, lng: Double): Result<Unit> = runCatching {
        firestore.collection("envios").document(envioId)
            .update(mapOf(
                "ubicacionActualLat" to lat,
                "ubicacionActualLng" to lng,
                "ultimaUbicacionTimestamp" to System.currentTimeMillis()
            )).await()
    }

    override suspend fun agregarCalificacion(
        envioId: String,
        calificacionTransportista: Int?,
        comentarioTransportista: String?,
        calificacionCargador: Int?,
        comentarioCargador: String?
    ): Result<Unit> = runCatching {
        val updates = mutableMapOf<String, Any>()
        calificacionTransportista?.let { updates["calificacionTransportista"] = it }
        comentarioTransportista?.let { updates["comentarioTransportista"] = it }
        calificacionCargador?.let { updates["calificacionCargador"] = it }
        comentarioCargador?.let { updates["comentarioCargador"] = it }
        
        firestore.collection("envios").document(envioId)
            .update(updates).await()
    }
}

private fun where(vararg conditions: com.google.firebase.firestore.Filter): com.google.firebase.firestore.Filter {
    return com.google.firebase.firestore.Filter.or(*conditions.map { it as com.google.firebase.firestore.Filter }.toTypedArray())
}

private fun whereEqualTo(field: String, value: Any): com.google.firebase.firestore.Filter {
    return com.google.firebase.firestore.Filter.equalTo(field, value)
}
