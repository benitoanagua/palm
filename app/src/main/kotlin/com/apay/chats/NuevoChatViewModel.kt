package com.apay.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.apay.domain.model.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class NuevoChatUiState(
    val query: String = "",
    val usuarios: List<Usuario> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class NuevoChatViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = MutableStateFlow(NuevoChatUiState())
    val uiState: StateFlow<NuevoChatUiState> = _uiState.asStateFlow()

    fun buscar(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
        if (query.length < 2) {
            _uiState.value = _uiState.value.copy(usuarios = emptyList())
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            runCatching {
                val snap = firestore.collection("usuarios")
                    .orderBy("nombre")
                    .startAt(query)
                    .endAt(query + "\uf8ff")
                    .limit(20)
                    .get().await()
                snap.documents
                    .filter { it.id != auth.currentUser?.uid }
                    .map { doc ->
                        Usuario(
                            uid    = doc.id,
                            nombre = doc.getString("nombre") ?: "",
                            email  = doc.getString("email") ?: ""
                        )
                    }
            }.onSuccess { usuarios ->
                _uiState.value = _uiState.value.copy(usuarios = usuarios, isLoading = false)
            }.onFailure {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun iniciarChat(otroUid: String, otroNombre: String, onCreado: (String) -> Unit) {
        viewModelScope.launch {
            val miUid = auth.currentUser?.uid ?: return@launch
            runCatching {
                // Buscar chat existente entre los dos usuarios
                val existente = firestore.collection("chats")
                    .whereArrayContains("participantes", miUid)
                    .get().await()
                    .documents
                    .firstOrNull { doc ->
                        val participantes = (doc.get("participantes") as? List<*>)
                            ?.filterIsInstance<String>() ?: emptyList()
                        participantes.contains(otroUid) && participantes.size == 2
                    }

                if (existente != null) {
                    existente.id
                } else {
                    val miNombre = auth.currentUser?.displayName
                        ?: auth.currentUser?.email?.substringBefore("@")
                        ?: "Usuario"
                    val ref = firestore.collection("chats").add(
                        mapOf(
                            "participantes"      to listOf(miUid, otroUid),
                            "titulo"             to otroNombre,
                            "ultimoMensaje"      to "",
                            "fechaUltimoMensaje" to FieldValue.serverTimestamp(),
                            "creadoPor"          to miUid
                        )
                    ).await()
                    ref.id
                }
            }.onSuccess { chatId ->
                onCreado(chatId)
            }
        }
    }
}
