package com.uo300568.artchive.domain

sealed class DataResult<out T> {
    object Cargando : DataResult<Nothing>()
    data class Exito<T>(val datos: T) : DataResult<T>()
    data class Error(val mensaje: String) : DataResult<Nothing>()
}