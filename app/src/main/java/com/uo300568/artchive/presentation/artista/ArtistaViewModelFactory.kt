package com.uo300568.artchive.presentation.artista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uo300568.artchive.data.repository.CuadroRepository
import com.uo300568.artchive.data.repository.UserRepository

class ArtistaViewModelFactory (
    private val cuadroRepository: CuadroRepository,
    private val userRepository: UserRepository,
    private val nombreArtista: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtistaViewModel(cuadroRepository, userRepository, nombreArtista) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}