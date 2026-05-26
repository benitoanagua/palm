package com.apay.data.repository

// Firebase (comentado - activar cuando se configuren los servicios)
// import com.apay.domain.model.Ruta
// import com.apay.domain.repository.RutaRepository
// import com.google.firebase.firestore.FirebaseFirestore
// import kotlinx.coroutines.channels.awaitClose
// import kotlinx.coroutines.flow.Flow
// import kotlinx.coroutines.flow.callbackFlow
// import kotlinx.coroutines.tasks.await
// import javax.inject.Inject
// import javax.inject.Singleton

// @Singleton
// class FirestoreRutaRepository @Inject constructor(
//     private val firestore: FirebaseFirestore
// ) : RutaRepository {

//     override fun observarRutasDisponibles(): Flow<List<Ruta>> = callbackFlow {
//         val listener = firestore.collection("rutas")
//             .whereEqualTo("estado", "DISPONIBLE")
//             .addSnapshotListener { snap, error ->
//                 if (error != null) { close(error); return@addSnapshotListener }
//                 val rutas = snap?.documents?.mapNotNull { doc ->
//                     try {
//                         Ruta(
//                             id = doc.id,
//                             transportistaId = doc.getString("transportistaId") ?: "",
//                             transportistaNombre = doc.getString("transportistaNombre") ?: "",
//                             origen = doc.getString("origen") ?: "",
//                             destino = doc.getString("destino") ?: "",
//                             fechaSalida = doc.getLong("fechaSalida") ?: 0L,
//                             capacidadDisponibleKg = doc.getLong("capacidadDisponibleKg")?.toInt() ?: 0,
//                             capacidadDisponibleM3 = doc.getDouble("capacidadDisponibleM3") ?: 0.0,
//                             precioPorKg = doc.getDouble("precioPorKg") ?: 0.0,
//                             tiposCargaAceptados = doc.get("tiposCargaAceptados") as? List<String> ?: emptyList(),
//                             estado = doc.getString("estado") ?: "DISPONIBLE",
//                             timestamp = doc.getLong("timestamp") ?: 0L
//                         )
//                     } catch (e: Exception) { null }
//                 } ?: emptyList()
//                 trySend(rutas)
//             }
//         awaitClose { listener.remove() }
//     }

//     override suspend fun publicarRuta(ruta: Ruta): Result<Unit> = runCatching {
//         firestore.collection("rutas").add(mapOf(
//             "transportistaId" to ruta.transportistaId,
//             "transportistaNombre" to ruta.transportistaNombre,
//             "origen" to ruta.origen,
//             "destino" to ruta.destino,
//             "fechaSalida" to ruta.fechaSalida,
//             "capacidadDisponibleKg" to ruta.capacidadDisponibleKg,
//             "capacidadDisponibleM3" to ruta.capacidadDisponibleM3,
//             "precioPorKg" to ruta.precioPorKg,
//             "tiposCargaAceptados" to ruta.tiposCargaAceptados,
//             "estado" to ruta.estado,
//             "timestamp" to System.currentTimeMillis()
//         )).await()
//     }

//     override suspend fun obtenerRutaPorId(id: String): Result<Ruta> = runCatching {
//         val doc = firestore.collection("rutas").document(id).get().await()
//         Ruta(
//             id = doc.id,
//             transportistaId = doc.getString("transportistaId") ?: "",
//             transportistaNombre = doc.getString("transportistaNombre") ?: "",
//             origen = doc.getString("origen") ?: "",
//             destino = doc.getString("destino") ?: "",
//             fechaSalida = doc.getLong("fechaSalida") ?: 0L,
//             capacidadDisponibleKg = doc.getLong("capacidadDisponibleKg")?.toInt() ?: 0,
//             capacidadDisponibleM3 = doc.getDouble("capacidadDisponibleM3") ?: 0.0,
//             precioPorKg = doc.getDouble("precioPorKg") ?: 0.0,
//             tiposCargaAceptados = doc.get("tiposCargaAceptados") as? List<String> ?: emptyList(),
//             estado = doc.getString("estado") ?: "DISPONIBLE",
//             timestamp = doc.getLong("timestamp") ?: 0L
//         )
//     }
// }
