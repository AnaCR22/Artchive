package com.uo300568.artchive.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Museo(
    val nombre: String = "",
    val latitud: Double? = null,
    val longitud: Double? = null,
    val direccion: String? = null
) : Parcelable