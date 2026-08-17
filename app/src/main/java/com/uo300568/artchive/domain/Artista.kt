package com.uo300568.artchive.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artista(
    val nombre: String = "",
    val fotoUrl: String? = null,
    val biografia: String? = null
) : Parcelable