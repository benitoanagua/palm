package com.apay.data.repository

import com.apay.domain.model.Ruta
import com.apay.domain.repository.RutaRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRutaRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : RutaRepository {

    override fun observarRutasDisponibles(): Flow<List<Ruta>> = callbackFlow {
        val listener = firestore.collection("rutas")
            .whereEqualTo("estado", "DISPONIBLE")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snap, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                val rutas = snap?.documents?.mapNotNull { doc ->
                    try {
                        Ruta(
                            id = doc.id,
                            transportistaId = doc.getString("transportistaId") ?: "",
                            transportistaNombre = doc.getString("transportistaNombre") ?: "",
                            transportistaCalificacion = doc.getDouble("transportistaCalificacion") ?: 0.0,
                            origen = doc.getString("origen") ?: "",
                            destino = doc.getString("destino") ?: "",
                            fechaSalida = doc.getLong("fechaSalida") ?: 0L,
                            horaSalida = doc.getString("horaSalida") ?: "",
                            capacidadDisponibleKg = doc.getLong("capacidadDisponibleKg")?.toInt() ?: 0,
                            capacidadDisponibleM3 = doc.getDouble("capacidadDisponibleM3") ?: 0.0,
                            precioPorKg = doc.getDouble("precioPorKg") ?: 0.0,
                            precioFlete = doc.getDouble("precioFlete") ?: 0.0,
                            tiposCargaAceptados = doc.get("tiposCargaAceptados") as? List<String> ?: emptyList(),
                            paradasIntermedias = doc.get("paradasIntermedias") as? List<String> ?: emptyList(),
                            estado = doc.getString("estado") ?: "DISPONIBLE",
                            descripcion = doc.getString("descripcion") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0L
                        )
                    } catch (e: Exception) { null }
                } ?: emptyList()
                trySend(rutas)
            }
        awaitClose { listener.remove() }
    }

    override fun observarRutasPorTransportista(transportistaId: String): Flow<List<Ruta>> = callbackFlow {
        val listener = firestore.collection("rutas")
            .whereEqualTo("transportistaId", transportistaId)
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snap, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                val rutas = snap?.documents?.mapNotNull { doc ->
                    try {
                        Ruta(
                            id = doc.id,
                            transportistaId = doc.getString("transportistaId") ?: "",
                            transportistaNombre = doc.getString("transportistaNombre") ?: "",
                            transportistaCalificacion = doc.getDouble("transportistaCalificacion") ?: 0.0,
                            origen = doc.getString("origen") ?: "",
                            destino = doc.getString("destino") ?: "",
                            fechaSalida = doc.getLong("fechaSalida") ?: 0L,
                            horaSalida = doc.getString("horaSalida") ?: "",
                            capacidadDisponibleKg = doc.getLong("capacidadDisponibleKg")?.toInt() ?: 0,
                            capacidadDisponibleM3 = doc.getDouble("capacidadDisponibleM3") ?: 0.0,
                            precioPorKg = doc.getDouble("precioPorKg") ?: 0.0,
                            precioFlete = doc.getDouble("precioFlete") ?: 0.0,
                            tiposCargaAceptados = doc.get("tiposCargaAceptados") as? List<String> ?: emptyList(),
                            paradasIntermedias = doc.get("paradasIntermedias") as? List<String> ?: emptyList(),
                            estado = doc.getString("estado") ?: "DISPONIBLE",
                            descripcion = doc.getString("descripcion") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0L
                        )
                    } catch (e: Exception) { null }
                } ?: emptyList()
                trySend(rutas)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun publicarRuta(ruta: Ruta): Result<Unit> = runCatching {
        firestore.collection("rutas").add(mapOf(
            "transportistaId" to ruta.transportistaId,
            "transportistaNombre" to ruta.transportistaNombre,
            "transportistaCalificacion" to ruta.transportistaCalificacion,
            "origen" to ruta.origen,
            "destino" to ruta.destino,
            "fechaSalida" to ruta.fechaSalida,
            "horaSalida" to ruta.horaSalida,
            "capacidadDisponibleKg" to ruta.capacidadDisponibleKg,
            "capacidadDisponibleM3" to ruta.capacidadDisponibleM3,
            "precioPorKg" to ruta.precioPorKg,
            "precioFlete" to ruta.precioFlete,
            "tiposCargaAceptados" to ruta.tiposCargaAceptados,
            "paradasIntermedias" to ruta.paradasIntermedias,
            "estado" to ruta.estado,
            "descripcion" to ruta.descripcion,
            "timestamp" to System.currentTimeMillis()
        )).await()
    }

    override suspend fun obtenerRutaPorId(id: String): Result<Ruta> = runCatching {
        val doc = firestore.collection("rutas").document(id).get().await()
        Ruta(
            id = doc.id,
            transportistaId = doc.getString("transportistaId") ?: "",
            transportistaNombre = doc.getString("transportistaNombre") ?: "",
            transportistaCalificacion = doc.getDouble("transportistaCalificacion") ?: 0.0,
            origen = doc.getString("origen") ?: "",
            destino = doc.getString("destino") ?: "",
            fechaSalida = doc.getLong("fechaSalida") ?: 0L,
            horaSalida = doc.getString("horaSalida") ?: "",
            capacidadDisponibleKg = doc.getLong("capacidadDisponibleKg")?.toInt() ?: 0,
            capacidadDisponibleM3 = doc.getDouble("capacidadDisponibleM3") ?: 0.0,
            precioPorKg = doc.getDouble("precioPorKg") ?: 0.0,
            precioFlete = doc.getDouble("precioFlete") ?: 0.0,
            tiposCargaAceptados = doc.get("tiposCargaAceptados") as? List<String> ?: emptyList(),
            paradasIntermedias = doc.get("paradasIntermedias") as? List<String> ?: emptyList(),
            estado = doc.getString("estado") ?: "DISPONIBLE",
            descripcion = doc.getString("descripcion") ?: "",
            timestamp = doc.getLong("timestamp") ?: 0L
        )
    }

    override suspend fun actualizarEstado(rutaId: String, estado: String): Result<Unit> = runCatching {
        firestore.collection("rutas").document(rutaId)
            .update("estado", estado)
            .await()
    }

    override suspend fun eliminarRuta(rutaId: String): Result<Unit> = runCatching {
        firestore.collection("rutas").document(rutaId).delete().await()
    }
}
