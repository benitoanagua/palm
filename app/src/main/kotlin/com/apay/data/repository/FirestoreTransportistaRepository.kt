package com.apay.data.repository

import com.apay.domain.model.Transportista
import com.apay.domain.repository.TransportistaRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreTransportistaRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : TransportistaRepository {

    override suspend fun obtenerPerfil(uid: String): Result<Transportista> = runCatching {
        val doc = firestore.collection("transportistas").document(uid).get().await()
        Transportista(
            uid = doc.id,
            nombre = doc.getString("nombre") ?: "",
            email = doc.getString("email") ?: "",
            telefono = doc.getString("telefono") ?: "",
            placa = doc.getString("placa") ?: "",
            tipoVehiculo = doc.getString("tipoVehiculo") ?: "",
            capacidadMaximaKg = doc.getLong("capacidadMaximaKg")?.toInt() ?: 0,
            capacidadMaximaM3 = doc.getDouble("capacidadMaximaM3") ?: 0.0,
            calificacion = doc.getDouble("calificacion") ?: 0.0,
            viajesCompletados = doc.getLong("viajesCompletados")?.toInt() ?: 0,
            miembroDesde = doc.getLong("miembroDesde") ?: 0L,
            verificado = doc.getBoolean("verificado") ?: false,
            rutasActivas = doc.getLong("rutasActivas")?.toInt() ?: 0,
            fotoVehiculoUrl = doc.getString("fotoVehiculoUrl"),
            documentoHabilitacionUrl = doc.getString("documentoHabilitacionUrl"),
            ingresosTotales = doc.getDouble("ingresosTotales") ?: 0.0,
            kmTotales = doc.getDouble("kmTotales") ?: 0.0
        )
    }

    override suspend fun actualizarPerfil(transportista: Transportista): Result<Unit> = runCatching {
        firestore.collection("transportistas").document(transportista.uid)
            .set(mapOf(
                "nombre" to transportista.nombre,
                "email" to transportista.email,
                "telefono" to transportista.telefono,
                "placa" to transportista.placa,
                "tipoVehiculo" to transportista.tipoVehiculo,
                "capacidadMaximaKg" to transportista.capacidadMaximaKg,
                "capacidadMaximaM3" to transportista.capacidadMaximaM3,
                "calificacion" to transportista.calificacion,
                "viajesCompletados" to transportista.viajesCompletados,
                "verificado" to transportista.verificado,
                "rutasActivas" to transportista.rutasActivas,
                "fotoVehiculoUrl" to transportista.fotoVehiculoUrl,
                "documentoHabilitacionUrl" to transportista.documentoHabilitacionUrl,
                "ingresosTotales" to transportista.ingresosTotales,
                "kmTotales" to transportista.kmTotales
            )).await()
    }

    override suspend fun registrarTransportista(transportista: Transportista): Result<Unit> = runCatching {
        firestore.collection("transportistas").document(transportista.uid)
            .set(mapOf(
                "uid" to transportista.uid,
                "nombre" to transportista.nombre,
                "email" to transportista.email,
                "telefono" to transportista.telefono,
                "placa" to transportista.placa,
                "tipoVehiculo" to transportista.tipoVehiculo,
                "capacidadMaximaKg" to transportista.capacidadMaximaKg,
                "capacidadMaximaM3" to transportista.capacidadMaximaM3,
                "calificacion" to transportista.calificacion,
                "viajesCompletados" to transportista.viajesCompletados,
                "miembroDesde" to System.currentTimeMillis(),
                "verificado" to false,
                "rutasActivas" to 0,
                "fotoVehiculoUrl" to transportista.fotoVehiculoUrl,
                "documentoHabilitacionUrl" to transportista.documentoHabilitacionUrl,
                "ingresosTotales" to 0.0,
                "kmTotales" to 0.0
            )).await()
    }

    override fun observarTransportista(uid: String): Flow<Transportista> = callbackFlow {
        val listener = firestore.collection("transportistas").document(uid)
            .addSnapshotListener { snap, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                snap?.let {
                    trySend(
                        Transportista(
                            uid = it.id,
                            nombre = it.getString("nombre") ?: "",
                            email = it.getString("email") ?: "",
                            telefono = it.getString("telefono") ?: "",
                            placa = it.getString("placa") ?: "",
                            tipoVehiculo = it.getString("tipoVehiculo") ?: "",
                            capacidadMaximaKg = it.getLong("capacidadMaximaKg")?.toInt() ?: 0,
                            capacidadMaximaM3 = it.getDouble("capacidadMaximaM3") ?: 0.0,
                            calificacion = it.getDouble("calificacion") ?: 0.0,
                            viajesCompletados = it.getLong("viajesCompletados")?.toInt() ?: 0,
                            miembroDesde = it.getLong("miembroDesde") ?: 0L,
                            verificado = it.getBoolean("verificado") ?: false,
                            rutasActivas = it.getLong("rutasActivas")?.toInt() ?: 0,
                            fotoVehiculoUrl = it.getString("fotoVehiculoUrl"),
                            documentoHabilitacionUrl = it.getString("documentoHabilitacionUrl"),
                            ingresosTotales = it.getDouble("ingresosTotales") ?: 0.0,
                            kmTotales = it.getDouble("kmTotales") ?: 0.0
                        )
                    )
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun actualizarEstadisticas(uid: String, viajesCompletados: Int, ingresosTotales: Double, kmTotales: Double): Result<Unit> = runCatching {
        firestore.collection("transportistas").document(uid)
            .update(mapOf(
                "viajesCompletados" to viajesCompletados,
                "ingresosTotales" to ingresosTotales,
                "kmTotales" to kmTotales
            )).await()
    }
}
