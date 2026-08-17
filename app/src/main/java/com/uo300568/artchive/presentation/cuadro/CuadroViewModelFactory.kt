package com.uo300568.artchive.presentation.cuadro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uo300568.artchive.data.repository.CuadroRepository
import com.uo300568.artchive.data.repository.UserRepository
import com.uo300568.artchive.domain.Cuadro

class CuadroViewModelFactory (
        private val cuadro: Cuadro?,
        private val cuadroRepository: CuadroRepository,
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CuadroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CuadroViewModel(cuadro, cuadroRepository, userRepository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}