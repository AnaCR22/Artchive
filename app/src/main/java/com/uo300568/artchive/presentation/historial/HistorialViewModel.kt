package com.uo300568.artchive.presentation.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uo300568.artchive.data.repository.CuadroRepository
import com.uo300568.artchive.domain.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistorialViewModel (private val repository: CuadroRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HistorialUiState())
    val uiState: StateFlow<HistorialUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.obtenerHistorial().collect { cuadros ->
                when (cuadros) {
                    is DataResult.Cargando -> _uiState.value = _uiState.value.copy(cargando = true)
                    is DataResult.Exito -> {
                        _uiState.value = _uiState.value.copy(cargando = false, cuadros = cuadros.datos)
                    }
                    is DataResult.Error -> _uiState.value = _uiState.value.copy(cargando = false, error = cuadros.mensaje)
                }
            }
        }
    }
}