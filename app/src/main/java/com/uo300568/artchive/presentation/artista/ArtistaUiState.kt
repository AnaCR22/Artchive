package com.uo300568.artchive.presentation.artista

import com.uo300568.artchive.domain.Cuadro

data class ArtistaUiState(
    val cargando: Boolean = false,
    val nombreArtista: String = "",
    val fotoUrl: String? = null,
    val biografia: String? = null,
    val cuadros: List<Cuadro> = emptyList(),
    val esFavorito: Boolean = false,
    val error: String? = null
)