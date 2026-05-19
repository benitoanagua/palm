package com.apay.tasa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apay.domain.model.Tasa
import com.apay.domain.repository.TasaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TasaUiState(
    val tasa: Tasa? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class TasaViewModel @Inject constructor(
    private val tasaRepo: TasaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasaUiState())
    val uiState: StateFlow<TasaUiState> = _uiState.asStateFlow()

    init { observarTasa() }

    private fun observarTasa() = viewModelScope.launch {
        runCatching {
            tasaRepo.observarTasaActual().collect { tasa ->
                _uiState.value = TasaUiState(tasa = tasa, isLoading = false)
            }
        }.onFailure { e ->
            _uiState.value = TasaUiState(isLoading = false, error = e.message)
        }
    }
}
