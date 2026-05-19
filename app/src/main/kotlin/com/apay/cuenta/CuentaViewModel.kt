package com.apay.cuenta

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.apay.domain.model.MovimientoCuenta
import com.apay.domain.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class CuentaUiState(
    val nombre: String = "",
    val email: String = "",
    val rol: String = "",
    val deudaBolivianos: Double = 0.0,
    val ultimosMovimientos: List<MovimientoCuenta> = emptyList(),
    val isLoading: Boolean = true,
    val isUploadingAvatar: Boolean = false
)

@HiltViewModel
class CuentaViewModel @Inject constructor(
    private val usuarioRepo: UsuarioRepository,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow(CuentaUiState())
    val uiState: StateFlow<CuentaUiState> = _uiState.asStateFlow()

    init { cargarCuenta() }

    private fun cargarCuenta() = viewModelScope.launch {
        val user = auth.currentUser ?: return@launch
        val nombre = user.displayName
            ?: user.email?.substringBefore("@")
            ?: "Usuario"

        _uiState.value = _uiState.value.copy(
            nombre = nombre,
            email  = user.email ?: "",
            isLoading = false
        )

        // Cargar rol desde Firestore
        runCatching {
            val doc = firestore.collection("usuarios").document(user.uid).get().await()
            val rol = doc.getString("rol") ?: ""
            _uiState.value = _uiState.value.copy(rol = rol)
        }
    }

    fun actualizarAvatar(uri: Uri) = viewModelScope.launch {
        val uid = auth.currentUser?.uid ?: return@launch
        _uiState.value = _uiState.value.copy(isUploadingAvatar = true)
        runCatching {
            val ref = storage.reference.child("usuarios/$uid/avatar_${System.currentTimeMillis()}.jpg")
            ref.putFile(uri).await()
            val url = ref.downloadUrl.await().toString()
            usuarioRepo.actualizarAvatar(uid, url)
        }
        _uiState.value = _uiState.value.copy(isUploadingAvatar = false)
    }
}
