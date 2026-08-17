package com.uo300568.artchive.presentation.museo

import com.uo300568.artchive.domain.Museo

data class MuseoUiState(
    val cargando: Boolean = false,
    val museos: List<Museo> = emptyList(),
    val error: String? = null
)