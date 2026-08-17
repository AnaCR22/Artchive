package com.uo300568.artchive.presentation.favoritos

import com.uo300568.artchive.domain.Artista
import com.uo300568.artchive.domain.Cuadro

enum class FiltroFavoritos {
    OBRA, ARTISTA
}

data class FavoritesUiState(
    val cargando: Boolean = false,
    val favoritos: List<Cuadro> = emptyList(),
    val artistasFavoritos: List<Artista> = emptyList(),
    val filtro: FiltroFavoritos = FiltroFavoritos.OBRA,
    val error: String? = null
)