package com.apay.data.repository

import com.apay.domain.model.Disputa
import com.apay.domain.repository.DisputaRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreDisputaRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : DisputaRepository {

    override fun observarDisputas(usuarioId: String): Flow<List<Disputa>> = callbackFlow {
        val listener = firestore.collection("disputas")
            .where(or(
                whereEqualTo("cargadorId", usuarioId),
                whereEqualTo("transportistaId", usuarioId)
            ))
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snap, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                val disputas = snap?.documents?.mapNotNull { doc ->
                    try {
                        Disputa(
                            id = doc.id,
                            envioId = doc.getString("envioId") ?: "",
                            cargadorId = doc.getString("cargadorId") ?: "",
                            transportistaId = doc.getString("transportistaId") ?: "",
                            motivo = doc.getString("motivo") ?: "",
                            descripcion = doc.getString("descripcion") ?: "",
                            fotosEvidencia = doc.get("fotosEvidencia") as? List<String> ?: emptyList(),
                            estado = doc.getString("estado") ?: "ABIERTA",
                            resolucion = doc.getString("resolucion") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0L
                        )
                    } catch (e: Exception) { null }
                } ?: emptyList()
                trySend(disputas)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun crearDisputa(disputa: Disputa): Result<Unit> = runCatching {
        firestore.collection("disputas").add(mapOf(
            "envioId" to disputa.envioId,
            "cargadorId" to disputa.cargadorId,
            "transportistaId" to disputa.transportistaId,
            "motivo" to disputa.motivo,
            "descripcion" to disputa.descripcion,
            "fotosEvidencia" to disputa.fotosEvidencia,
            "estado" to "ABIERTA",
            "resolucion" to "",
            "timestamp" to System.currentTimeMillis()
        )).await()
    }

    override suspend fun actualizarEstado(disputaId: String, estado: String, resolucion: String): Result<Unit> = runCatching {
        firestore.collection("disputas").document(disputaId)
            .update(mapOf(
                "estado" to estado,
                "resolucion" to resolucion
            )).await()
    }
}

private fun where(vararg conditions: com.google.firebase.firestore.Filter): com.google.firebase.firestore.Filter {
    return com.google.firebase.firestore.Filter.or(*conditions.map { it as com.google.firebase.firestore.Filter }.toTypedArray())
}

private fun whereEqualTo(field: String, value: Any): com.google.firebase.firestore.Filter {
    return com.google.firebase.firestore.Filter.equalTo(field, value)
}
