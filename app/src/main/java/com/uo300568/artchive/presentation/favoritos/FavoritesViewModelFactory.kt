package com.uo300568.artchive.presentation.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uo300568.artchive.data.repository.UserRepository

class FavoritesViewModelFactory (private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}