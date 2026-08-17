package com.uo300568.artchive.presentation.historial

import com.uo300568.artchive.domain.Cuadro

data class HistorialUiState(
    val cargando: Boolean = false,
    val cuadros: List<Cuadro> = emptyList(),
    val error: String? = null
)