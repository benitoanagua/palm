package com.apay.empresa.subusuarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apay.domain.model.SubUsuario
import com.apay.domain.repository.SubUsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SubUsuariosUiState(
    val empresaId: String? = null,
    val subUsuarios: List<SubUsuario> = emptyList(),
    val cargando: Boolean = true,
    val error: String? = null,
    val mostrandoDialogoAgregar: Boolean = false,
    val subUsuarioEliminadoExitosamente: Boolean = false
)

@HiltViewModel
class SubUsuariosViewModel @Inject constructor(
    private val subUsuarioRepository: SubUsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubUsuariosUiState())
    val uiState: StateFlow<SubUsuariosUiState> = _uiState.asStateFlow()

    fun cargarSubUsuarios(empresaId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(cargando = true)
            
            subUsuarioRepository.observarSubUsuarios(empresaId)
                .collect { lista ->
                    _uiState.value = _uiState.value.copy(
                        subUsuarios = lista,
                        empresaId = empresaId,
                        cargando = false
                    )
                }
        }
    }

    fun mostrarDialogoAgregar() {
        _uiState.value = _uiState.value.copy(mostrandoDialogoAgregar = true)
    }

    fun ocultarDialogoAgregar() {
        _uiState.value = _uiState.value.copy(mostrandoDialogoAgregar = false)
    }

    fun agregarSubUsuario(subUsuario: SubUsuario) {
        viewModelScope.launch {
            subUsuarioRepository.crearSubUsuario(subUsuario)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        mostrandoDialogoAgregar = false,
                        error = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = "Error al agregar sub-usuario: ${error.message}"
                    )
                }
        }
    }

    fun eliminarSubUsuario(subUsuarioId: String) {
        viewModelScope.launch {
            subUsuarioRepository.eliminarSubUsuario(subUsuarioId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        subUsuarioEliminadoExitosamente = true,
                        error = null
                    )
                    // Resetear flag después de un tiempo
                    kotlinx.coroutines.delay(2000)
                    _uiState.value = _uiState.value.copy(subUsuarioEliminadoExitosamente = false)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = "Error al eliminar sub-usuario: ${error.message}"
                    )
                }
        }
    }

    fun actualizarSubUsuario(subUsuario: SubUsuario) {
        viewModelScope.launch {
            subUsuarioRepository.actualizarSubUsuario(subUsuario)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(error = null)
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = "Error al actualizar sub-usuario: ${error.message}"
                    )
                }
        }
    }

    fun registrarAcceso(subUsuarioId: String) {
        viewModelScope.launch {
            subUsuarioRepository.registrarAcceso(subUsuarioId)
        }
    }

    fun limpiarError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
