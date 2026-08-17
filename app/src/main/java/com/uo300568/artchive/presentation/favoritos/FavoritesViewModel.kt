package com.uo300568.artchive.presentation.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.uo300568.artchive.data.repository.UserRepository
import com.uo300568.artchive.domain.Cuadro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: UserRepository): ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    init {
        recargarFavoritos()
    }

    fun cambiarFiltro(filtro: FiltroFavoritos) {
        _uiState.value = _uiState.value.copy(filtro = filtro)
    }

    fun añadirFavorito(cuadro: Cuadro) {
        viewModelScope.launch {
            if (userId == null) return@launch
            try {
                repository.añadirFavorito(userId, cuadro)
                recargarFavoritos()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Error al añadir favorito")
            }
        }
    }

    private fun recargarFavoritos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(cargando = true)
            if (userId == null) {
                _uiState.value = FavoritesUiState(error = "Usuario no existe")
                return@launch
            }

            try {
                val favoritos = repository.obtenerFavoritos(userId)
                val artistas = repository.obtenerArtistasFavoritos(userId)
                _uiState.value = _uiState.value.copy(
                    cargando = false,
                    favoritos = favoritos,
                    artistasFavoritos = artistas
                )
            } catch (e: Exception) {
                _uiState.value = FavoritesUiState(
                    cargando = false,
                    error =  e.message ?: "Error al cargar favoritos"
                )
            }
        }
    }

    fun eliminarFavorito(cuadroId: String) {
        viewModelScope.launch {
            if (userId == null) return@launch
            try {
                repository.eliminarFavorito(userId, cuadroId)
                recargarFavoritos()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Error al eliminar favorito")
            }
        }
    }

}