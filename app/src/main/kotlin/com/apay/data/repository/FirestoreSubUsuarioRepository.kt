package com.apay.data.repository

import com.apay.domain.model.SubUsuario
import com.apay.domain.repository.SubUsuarioRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirestoreSubUsuarioRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : SubUsuarioRepository {

    private val collection get() = firestore.collection("sub_usuarios")

    override suspend fun crearSubUsuario(subUsuario: SubUsuario): Result<Unit> {
        return try {
            // Verificar límite de sub-usuarios
            val empresa = firestore.collection("empresas")
                .document(subUsuario.empresaId)
                .get()
                .await()
                .toObject(com.apay.domain.model.Empresa::class.java)
            
            if (empresa != null && empresa.subUsuariosActivos >= empresa.subUsuariosMaximos) {
                return Result.failure(Exception("Se alcanzó el límite máximo de sub-usuarios para esta empresa"))
            }

            collection.document(subUsuario.id).set(subUsuario).await()
            
            // Actualizar contador de la empresa
            if (empresa != null) {
                firestore.collection("empresas")
                    .document(subUsuario.empresaId)
                    .update("subUsuariosActivos", empresa.subUsuariosActivos + 1)
                    .await()
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun obtenerSubUsuarioPorId(id: String): Result<SubUsuario> {
        return try {
            val snapshot = collection.document(id).get().await()
            val subUsuario = snapshot.toObject(SubUsuario::class.java)
            if (subUsuario != null) {
                Result.success(subUsuario)
            } else {
                Result.failure(Exception("Sub-usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observarSubUsuarios(empresaId: String): Flow<List<SubUsuario>> = callbackFlow {
        val subscription = collection
            .whereEqualTo("empresaId", empresaId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val subUsuarios = snapshot?.documents?.mapNotNull { 
                    it.toObject(SubUsuario::class.java) 
                } ?: emptyList()
                
                trySend(subUsuarios)
            }

        awaitClose { subscription.remove() }
    }

    override suspend fun actualizarSubUsuario(subUsuario: SubUsuario): Result<Unit> {
        return try {
            collection.document(subUsuario.id).set(subUsuario).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun eliminarSubUsuario(subUsuarioId: String): Result<Unit> {
        return try {
            // Obtener el sub-usuario para conocer su empresaId
            val snapshot = collection.document(subUsuarioId).get().await()
            val subUsuario = snapshot.toObject(SubUsuario::class.java)
                ?: return Result.failure(Exception("Sub-usuario no encontrado"))

            // Eliminar el sub-usuario
            collection.document(subUsuarioId).delete().await()

            // Actualizar contador de la empresa
            val empresaRef = firestore.collection("empresas").document(subUsuario.empresaId)
            val empresa = empresaRef.get().await().toObject(com.apay.domain.model.Empresa::class.java)
            
            if (empresa != null && empresa.subUsuariosActivos > 0) {
                empresaRef.update("subUsuariosActivos", empresa.subUsuariosActivos - 1).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registrarAcceso(subUsuarioId: String): Result<Unit> {
        return try {
            val timestamp = System.currentTimeMillis()
            collection.document(subUsuarioId)
                .update("ultimoAcceso", timestamp)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun contarSubUsuarios(empresaId: String): Flow<Int> = flow {
        val snapshot = collection
            .whereEqualTo("empresaId", empresaId)
            .whereEqualTo("activo", true)
            .count()
            .await()
        
        emit(snapshot.count.toInt())
    }
}
