package com.apay.comprobante

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed class ComprobanteUiState {
    data object Idle       : ComprobanteUiState()
    data object Capturando : ComprobanteUiState()
    data class  Preview(val uri: Uri) : ComprobanteUiState()
    data object Enviando   : ComprobanteUiState()
    data object Enviado    : ComprobanteUiState()
    data class  Error(val mensaje: String) : ComprobanteUiState()
}

@HiltViewModel
class ComprobanteViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<ComprobanteUiState>(ComprobanteUiState.Idle)
    val uiState: StateFlow<ComprobanteUiState> = _uiState.asStateFlow()

    fun iniciarCaptura()              { _uiState.value = ComprobanteUiState.Capturando }
    fun onFotoCapturada(uri: Uri)     { _uiState.value = ComprobanteUiState.Preview(uri) }
    fun cancelar()                    { _uiState.value = ComprobanteUiState.Idle }

    fun enviarComprobante(context: Context, imageUri: Uri) {
        val socioId = auth.currentUser?.uid ?: run {
            _uiState.value = ComprobanteUiState.Error("Sesión expirada")
            return
        }
        _uiState.value = ComprobanteUiState.Enviando
        WorkManager.getInstance(context)
            .enqueue(UploadWorker.construir(imageUri, socioId))
        _uiState.value = ComprobanteUiState.Enviado
    }
}
