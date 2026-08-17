package com.uo300568.artchive.presentation.cuadro

import com.uo300568.artchive.domain.Cuadro

data class CuadroUiState(
    val cargando: Boolean = false,
    val cuadro: Cuadro? = null,
    val esFavorito : Boolean = false,
    val error: String? = null
)