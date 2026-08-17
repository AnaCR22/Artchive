package com.uo300568.artchive.presentation.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uo300568.artchive.data.repository.CuadroRepository

class HistorialViewModelFactory(private val repository: CuadroRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistorialViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistorialViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}