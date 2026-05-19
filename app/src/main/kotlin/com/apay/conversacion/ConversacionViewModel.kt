package com.apay.conversacion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.apay.domain.model.Mensaje
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class ConversacionUiState(
    val mensajes: List<Mensaje> = emptyList(),
    val isLoading: Boolean = true,
    val enviando: Boolean = false,
    val error: String? = null,
    val titulo: String = "Chat",
    val escribiendoTexto: String? = null,
    val mensajeReply: Mensaje? = null
)

@HiltViewModel
class ConversacionViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val chatId: String  = savedStateHandle["chatId"] ?: ""
    val titulo: String  = savedStateHandle.get<String>("titulo")
        ?.let { java.net.URLDecoder.decode(it, "UTF-8") } ?: "Chat"
    val otroUid: String = savedStateHandle["otroUid"] ?: ""

    val miUid: String    get() = auth.currentUser?.uid ?: ""
    val miNombre: String get() = auth.currentUser?.displayName
        ?: auth.currentUser?.email?.substringBefore("@") ?: "Yo"

    private val _uiState = MutableStateFlow(ConversacionUiState(titulo = titulo))
    val uiState: StateFlow<ConversacionUiState> = _uiState.asStateFlow()

    init {
        observarMensajes()
        marcarLeido()
    }

    private fun observarMensajes() = viewModelScope.launch {
        if (chatId.isBlank()) {
            _uiState.value = _uiState.value.copy(isLoading = false)
            return@launch
        }
        callbackFlow<List<Mensaje>> {
            val listener = firestore.collection("chats")
                .document(chatId)
                .collection("mensajes")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener { snap, error ->
                    if (error != null) { close(error); return@addSnapshotListener }
                    val lista = snap?.documents?.map { doc ->
                        Mensaje(
                            id              = doc.id,
                            chatId          = chatId,
                            remitenteId     = doc.getString("remitenteId") ?: "",
                            remitenteNombre = doc.getString("remitenteNombre") ?: "",
                            texto           = doc.getString("texto") ?: "",
                            timestamp       = doc.getTimestamp("timestamp")?.toDate()?.time ?: 0L,
                            leido           = doc.getBoolean("leido") ?: false
                        )
                    } ?: emptyList()
                    trySend(lista)
                }
            awaitClose { listener.remove() }
        }.collect { mensajes ->
            _uiState.value = _uiState.value.copy(mensajes = mensajes, isLoading = false)
        }
    }

    fun enviarMensaje(texto: String) {
        if (chatId.isBlank() || texto.isBlank()) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(enviando = true)
            runCatching {
                val batch = firestore.batch()

                // Agregar mensaje a subcolección
                val mensajeRef = firestore.collection("chats")
                    .document(chatId)
                    .collection("mensajes")
                    .document()
                batch.set(mensajeRef, mapOf(
                    "remitenteId"     to miUid,
                    "remitenteNombre" to miNombre,
                    "texto"           to texto,
                    "timestamp"       to FieldValue.serverTimestamp(),
                    "leido"           to false
                ))

                // Actualizar último mensaje en el chat
                val chatRef = firestore.collection("chats").document(chatId)
                batch.update(chatRef, mapOf(
                    "ultimoMensaje"      to texto,
                    "fechaUltimoMensaje" to FieldValue.serverTimestamp()
                ))

                batch.commit().await()
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(error = "Error al enviar: ${e.message}")
            }
            _uiState.value = _uiState.value.copy(enviando = false)
        }
    }

    private fun marcarLeido() {
        if (chatId.isBlank() || miUid.isBlank()) return
        viewModelScope.launch {
            runCatching {
                firestore.collection("chats").document(chatId).update(
                    "noLeidos_$miUid", 0
                ).await()
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
