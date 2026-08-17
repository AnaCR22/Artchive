package com.uo300568.artchive.presentation.museo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uo300568.artchive.data.repository.MuseoRepository

class MuseoViewModelFactory(private val repository: MuseoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MuseoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MuseoViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}
