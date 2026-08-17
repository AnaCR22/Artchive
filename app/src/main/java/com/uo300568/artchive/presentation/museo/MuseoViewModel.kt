package com.uo300568.artchive.presentation.museo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uo300568.artchive.data.repository.MuseoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MuseoViewModel(
    private val repository: MuseoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MuseoUiState())
    val uiState: StateFlow<MuseoUiState> = _uiState

    fun buscarMuseos(latitud: Double, longitud: Double) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(cargando = true)

            try {
                val museos = repository.buscarMuseosCercanos(latitud, longitud)
                _uiState.value = _uiState.value.copy(cargando = false, museos = museos)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(cargando = false, error = e.message ?: "Error al buscar museos")
            }
        }
    }
}