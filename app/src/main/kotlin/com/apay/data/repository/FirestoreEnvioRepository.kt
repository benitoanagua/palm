package com.apay.data.repository

// Firebase (comentado - activar cuando se configuren los servicios)
// import com.apay.domain.model.Envio
// import com.apay.domain.repository.EnvioRepository
// import com.google.firebase.firestore.FirebaseFirestore
// import kotlinx.coroutines.channels.awaitClose
// import kotlinx.coroutines.flow.Flow
// import kotlinx.coroutines.flow.callbackFlow
// import kotlinx.coroutines.tasks.await
// import javax.inject.Inject
// import javax.inject.Singleton

// @Singleton
// class FirestoreEnvioRepository @Inject constructor(
//     private val firestore: FirebaseFirestore
// ) : EnvioRepository {

//     override fun observarEnvios(usuarioId: String): Flow<List<Envio>> = callbackFlow {
//         val listener = firestore.collection("envios")
//             .whereEqualTo("cargadorId", usuarioId)
//             .addSnapshotListener { snap, error ->
//                 if (error != null) { close(error); return@addSnapshotListener }
//                 val envios = snap?.documents?.mapNotNull { doc ->
//                     try {
//                         Envio(
//                             id = doc.id,
//                             rutaId = doc.getString("rutaId") ?: "",
//                             cargadorId = doc.getString("cargadorId") ?: "",
//                             cargadorNombre = doc.getString("cargadorNombre") ?: "",
//                             transportistaId = doc.getString("transportistaId") ?: "",
//                             transportistaNombre = doc.getString("transportistaNombre") ?: "",
//                             origen = doc.getString("origen") ?: "",
//                             destino = doc.getString("destino") ?: "",
//                             fechaSalida = doc.getLong("fechaSalida") ?: 0L,
//                             fechaEntregaEstimada = doc.getLong("fechaEntregaEstimada") ?: 0L,
//                             pesoKg = doc.getLong("pesoKg")?.toInt() ?: 0,
//                             dimensiones = doc.getString("dimensiones") ?: "",
//                             tipoCarga = doc.getString("tipoCarga") ?: "",
//                             precioTotal = doc.getDouble("precioTotal") ?: 0.0,
//                             estado = doc.getString("estado") ?: "PENDIENTE",
//                             evidenciaEntregaUrl = doc.getString("evidenciaEntregaUrl"),
//                             calificacionTransportista = doc.getLong("calificacionTransportista")?.toInt(),
//                             calificacionCargador = doc.getLong("calificacionCargador")?.toInt(),
//                             timestamp = doc.getLong("timestamp") ?: 0L
//                         )
//                     } catch (e: Exception) { null }
//                 } ?: emptyList()
//                 trySend(envios)
//             }
//         awaitClose { listener.remove() }
//     }

//     override suspend fun crearEnvio(envio: Envio): Result<Unit> = runCatching {
//         firestore.collection("envios").add(mapOf(
//             "rutaId" to envio.rutaId,
//             "cargadorId" to envio.cargadorId,
//             "cargadorNombre" to envio.cargadorNombre,
//             "transportistaId" to envio.transportistaId,
//             "transportistaNombre" to envio.transportistaNombre,
//             "origen" to envio.origen,
//             "destino" to envio.destino,
//             "fechaSalida" to envio.fechaSalida,
//             "fechaEntregaEstimada" to envio.fechaEntregaEstimada,
//             "pesoKg" to envio.pesoKg,
//             "dimensiones" to envio.dimensiones,
//             "tipoCarga" to envio.tipoCarga,
//             "precioTotal" to envio.precioTotal,
//             "estado" to envio.estado,
//             "timestamp" to System.currentTimeMillis()
//         )).await()
//     }

//     override suspend fun actualizarEstado(envioId: String, estado: String): Result<Unit> = runCatching {
//         firestore.collection("envios").document(envioId)
//             .update("estado", estado).await()
//     }

//     override suspend fun confirmarEntrega(envioId: String, evidenciaUrl: String): Result<Unit> = runCatching {
//         firestore.collection("envios").document(envioId)
//             .update(mapOf(
//                 "estado" to "ENTREGADO",
//                 "evidenciaEntregaUrl" to evidenciaUrl
//             )).await()
//     }
// }
