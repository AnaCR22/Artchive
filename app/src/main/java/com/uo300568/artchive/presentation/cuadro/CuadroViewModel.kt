package com.uo300568.artchive.presentation.cuadro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.uo300568.artchive.data.repository.CuadroRepository
import com.uo300568.artchive.data.repository.UserRepository
import com.uo300568.artchive.domain.Cuadro
import com.uo300568.artchive.domain.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CuadroViewModel(
    private val cuadroInicial: Cuadro?,
    private val cuadroRepository: CuadroRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CuadroUiState())
    val uiState: StateFlow<CuadroUiState> = _uiState

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    init {
        if (cuadroInicial != null) {
            // Viene de Historial o Favoritos
            _uiState.value = _uiState.value.copy(cuadro = cuadroInicial)
            comprobarFavorito(cuadroInicial.id)
        } else {
            // Viene de DailyArt
            cargarCuadroDelDia()
        }
    }

    private fun cargarCuadroDelDia() {
        viewModelScope.launch {
            cuadroRepository.obtenerCuadroDelDia().collect { result ->
                when (result) {
                    is DataResult.Cargando -> _uiState.value = _uiState.value.copy(cargando = true)
                    is DataResult.Exito -> {
                        _uiState.value = _uiState.value.copy(cargando = false, cuadro = result.datos)
                        result.datos?.let { comprobarFavorito(it.id) }
                    }
                    is DataResult.Error -> _uiState.value = _uiState.value.copy(cargando = false, error = result.mensaje)
                }
            }
        }
    }

    private fun comprobarFavorito(cuadroId: String) {
        viewModelScope.launch {
            if (userId != null) {
                val esFavorito = userRepository.esFavorito(userId, cuadroId)
                _uiState.value = _uiState.value.copy(esFavorito = esFavorito)
            }
        }
    }

    fun modficarBotonFavorito() {
        _uiState.value = _uiState.value.copy(esFavorito = !_uiState.value.esFavorito)
    }
}