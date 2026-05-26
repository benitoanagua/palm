package com.apay.data.repository

import com.apay.domain.model.Usuario
import com.apay.domain.repository.UsuarioRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreUsuarioRepository @Inject constructor(
    private val db: FirebaseFirestore
) : UsuarioRepository {

    private val usuarios = db.collection("usuarios")

    override suspend fun obtenerPorId(uid: String): Result<Usuario> = runCatching {
        val doc = usuarios.document(uid).get().await()
        Usuario(
            uid = doc.id,
            nombre = doc.getString("nombre") ?: "",
            email = doc.getString("email") ?: "",
            rol = doc.getString("rol") ?: "",
            avatarUrl = doc.getString("avatarUrl"),
            enLinea = doc.getBoolean("enLinea") ?: false,
            telefono = doc.getString("telefono") ?: "",
            calificacion = doc.getDouble("calificacion") ?: 0.0,
            viajesCompletados = doc.getLong("viajesCompletados")?.toInt() ?: 0,
            nit = doc.getString("nit"),
            empresa = doc.getString("empresa"),
            fechaRegistro = doc.getLong("fechaRegistro") ?: System.currentTimeMillis()
        )
    }

    override suspend fun actualizarPerfil(uid: String, datos: Map<String, Any>): Result<Unit> = runCatching {
        usuarios.document(uid).update(datos).await()
    }

    override suspend fun actualizarPresencia(uid: String, enLinea: Boolean) {
        runCatching {
            usuarios.document(uid).set(
                mapOf("enLinea" to enLinea),
                SetOptions.merge()
            ).await()
        }
    }

    override fun observarUsuario(uid: String): Flow<Usuario> = callbackFlow {
        val listener = usuarios.document(uid)
            .addSnapshotListener { snap, error ->
                if (error != null) { close(error); return@addSnapshotListener }
                snap?.let {
                    trySend(
                        Usuario(
                            uid = it.id,
                            nombre = it.getString("nombre") ?: "",
                            email = it.getString("email") ?: "",
                            rol = it.getString("rol") ?: "",
                            avatarUrl = it.getString("avatarUrl"),
                            enLinea = it.getBoolean("enLinea") ?: false,
                            telefono = it.getString("telefono") ?: "",
                            calificacion = it.getDouble("calificacion") ?: 0.0,
                            viajesCompletados = it.getLong("viajesCompletados")?.toInt() ?: 0,
                            nit = it.getString("nit"),
                            empresa = it.getString("empresa"),
                            fechaRegistro = it.getLong("fechaRegistro") ?: System.currentTimeMillis()
                        )
                    )
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun registrarUsuario(usuario: Usuario): Result<Unit> = runCatching {
        usuarios.document(usuario.uid)
            .set(mapOf(
                "uid" to usuario.uid,
                "nombre" to usuario.nombre,
                "email" to usuario.email,
                "rol" to usuario.rol,
                "telefono" to usuario.telefono,
                "avatarUrl" to usuario.avatarUrl,
                "enLinea" to false,
                "calificacion" to 0.0,
                "viajesCompletados" to 0,
                "nit" to usuario.nit,
                "empresa" to usuario.empresa,
                "fechaRegistro" to System.currentTimeMillis()
            )).await()
    }
}
