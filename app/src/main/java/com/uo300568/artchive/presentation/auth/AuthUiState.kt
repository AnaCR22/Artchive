package com.uo300568.artchive.presentation.auth

data class AuthUiState(
    val cargando: Boolean = false,
    val userId: String? = null,
    val sesionIniciada: Boolean = false,
    val exito: String? = null,
    val error: String? = null
)