package com.apay.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.apay.domain.model.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatsUiState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatsUiState())
    val uiState: StateFlow<ChatsUiState> = _uiState.asStateFlow()

    init { cargarChats() }

    private fun cargarChats() = viewModelScope.launch {
        val uid = auth.currentUser?.uid ?: run {
            _uiState.value = ChatsUiState(isLoading = false)
            return@launch
        }

        callbackFlow<List<Chat>> {
            val listener = firestore.collection("chats")
                .whereArrayContains("participantes", uid)
                .orderBy("fechaUltimoMensaje", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener { snap, error ->
                    if (error != null) { close(error); return@addSnapshotListener }
                    val lista = snap?.documents?.map { doc ->
                        Chat(
                            id                 = doc.id,
                            titulo             = doc.getString("titulo") ?: "",
                            ultimoMensaje      = doc.getString("ultimoMensaje") ?: "",
                            fechaUltimoMensaje = doc.getTimestamp("fechaUltimoMensaje")?.toDate()?.time ?: 0L,
                            participantes      = (doc.get("participantes") as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                            noLeidos           = (doc.get("noLeidos_$uid") as? Long)?.toInt() ?: 0
                        )
                    } ?: emptyList()
                    trySend(lista)
                }
            awaitClose { listener.remove() }
        }.collect { chats ->
            _uiState.value = ChatsUiState(chats = chats, isLoading = false)
        }
    }

    fun abrirChat(chatId: String) {
        // Navigation handled externally via NavController
    }
}
