package com.uo300568.artchive.presentation.artista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.uo300568.artchive.data.repository.CuadroRepository
import com.uo300568.artchive.data.repository.UserRepository
import com.uo300568.artchive.domain.Artista
import com.uo300568.artchive.domain.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistaViewModel (
    private val cuadroRepository: CuadroRepository,
    private val userRepository: UserRepository,
    private val nombreArtista: String
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArtistaUiState(nombreArtista = nombreArtista))
    val uiState: StateFlow<ArtistaUiState> = _uiState

    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    init {
        cargarArtista()
    }

    private fun cargarArtista() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(cargando = true)

            // Info de Wikipedia
            val (biografia, fotoUrl) = cuadroRepository.obtenerInfoArtista(nombreArtista)

            // Comprobar si es favorito
            val esFavorito = if (userId != null) {
                userRepository.esArtistaFavorito(userId, nombreArtista)
            } else false

            // Cuadros del artista
            cuadroRepository.buscarPorArtista(nombreArtista).collect { result ->
                when (result) {
                    is DataResult.Cargando -> {}
                    is DataResult.Exito -> {
                        _uiState.value = _uiState.value.copy(
                            cargando = false,
                            biografia = biografia,
                            fotoUrl = fotoUrl,
                            cuadros = result.datos,
                            esFavorito = esFavorito
                        )
                    }
                    is DataResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            cargando = false,
                            error = result.mensaje
                        )
                    }
                }
            }
        }
    }

    fun modficarBotonFavorito() {
        viewModelScope.launch {
            if (userId == null) return@launch
            try {
                if (_uiState.value.esFavorito) {
                    userRepository.eliminarArtistaFavorito(userId, nombreArtista)
                } else {
                    val artista = Artista(
                        nombre = nombreArtista,
                        fotoUrl = _uiState.value.fotoUrl,
                        biografia = _uiState.value.biografia
                    )
                    userRepository.añadirArtistaFavorito(userId, artista)
                }
                _uiState.value = _uiState.value.copy(esFavorito = !_uiState.value.esFavorito)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Error al modificar favorito")
            }
        }
    }
}