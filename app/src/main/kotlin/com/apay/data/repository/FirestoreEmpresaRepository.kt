package com.apay.data.repository

import com.apay.domain.model.Empresa
import com.apay.domain.repository.EmpresaRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreEmpresaRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : EmpresaRepository {

    private val collection get() = firestore.collection("empresas")

    override suspend fun registrarEmpresa(empresa: Empresa): Result<Unit> {
        return try {
            // Verificar que no exista otra empresa con el mismo NIT
            val existing = obtenerEmpresaPorNIT(empresa.nit)
            if (existing.getOrNull() != null) {
                return Result.failure(Exception("Ya existe una empresa registrada con este NIT"))
            }

            collection.document(empresa.id).set(empresa).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun obtenerEmpresaPorId(id: String): Result<Empresa> {
        return try {
            val snapshot = collection.document(id).get().await()
            val empresa = snapshot.toObject(Empresa::class.java)
            if (empresa != null) {
                Result.success(empresa)
            } else {
                Result.failure(Exception("Empresa no encontrada"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun obtenerEmpresaPorNIT(nit: String): Result<Empresa?> {
        return try {
            val snapshot = collection.whereEqualTo("nit", nit).limit(1).get().await()
            val empresa = snapshot.documents.firstOrNull()?.toObject(Empresa::class.java)
            Result.success(empresa)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun actualizarEmpresa(empresa: Empresa): Result<Unit> {
        return try {
            collection.document(empresa.id).set(empresa).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun actualizarGastoMensual(empresaId: String, monto: Double): Result<Unit> {
        return try {
            val empresaRef = collection.document(empresaId)
            
            // Obtener la empresa actual
            val snapshot = empresaRef.get().await()
            val empresaActual = snapshot.toObject(Empresa::class.java)
                ?: return Result.failure(Exception("Empresa no encontrada"))

            // Actualizar gasto acumulado del mes
            val nuevoGasto = empresaActual.gastoActualMes + monto
            
            // Validar límite de gasto mensual si está configurado
            if (empresaActual.limiteGastoMensual > 0 && nuevoGasto > empresaActual.limiteGastoMensual) {
                return Result.failure(Exception("Se excedería el límite de gasto mensual configurado"))
            }

            empresaRef.update("gastoActualMes", nuevoGasto).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observarEmpresa(propietarioId: String): Flow<Empresa?> = callbackFlow {
        val subscription = collection
            .whereEqualTo("propietarioId", propietarioId)
            .limit(1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }

                val empresa = snapshot?.documents?.firstOrNull()?.toObject(Empresa::class.java)
                trySend(empresa)
            }

        awaitClose { subscription.remove() }
    }

    override suspend fun eliminarEmpresa(empresaId: String): Result<Unit> {
        return try {
            collection.document(empresaId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
