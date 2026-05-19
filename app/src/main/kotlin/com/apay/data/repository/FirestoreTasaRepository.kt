package com.apay.data.repository

import com.apay.domain.model.Tasa
import com.apay.domain.repository.TasaRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreTasaRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : TasaRepository {

    override fun observarTasaActual(): Flow<Tasa> = callbackFlow {
        val listener = firestore.document("config/sistema")
            .addSnapshotListener { snap, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                snap?.let {
                    trySend(
                        Tasa(
                            valor        = it.getDouble("tasaActual") ?: 0.0,
                            publicadaPor = it.getString("publicadaPor") ?: "",
                            timestamp    = it.getTimestamp("ultimaActualizacion")?.toDate()?.time ?: 0L
                        )
                    )
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun publicarTasa(tasa: Tasa): Result<Unit> = runCatching {
        firestore.collection("tasas").add(mapOf(
            "valor"        to tasa.valor,
            "publicadaPor" to tasa.publicadaPor,
            "timestamp"    to FieldValue.serverTimestamp()
        )).await()
        firestore.document("config/sistema").update(mapOf(
            "tasaActual"          to tasa.valor,
            "publicadaPor"        to tasa.publicadaPor,
            "ultimaActualizacion" to FieldValue.serverTimestamp()
        )).await()
    }

    override suspend fun obtenerHistorial(): Result<List<Tasa>> = runCatching {
        firestore.collection("tasas")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(50)
            .get().await()
            .documents.map { doc ->
                Tasa(
                    id           = doc.id,
                    valor        = doc.getDouble("valor") ?: 0.0,
                    publicadaPor = doc.getString("publicadaPor") ?: "",
                    timestamp    = doc.getTimestamp("timestamp")?.toDate()?.time ?: 0L
                )
            }
    }
}
