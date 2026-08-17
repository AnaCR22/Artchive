package com.uo300568.artchive.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cuadro(
    val id: String = "",
    val titulo: String = "",
    val autor: String? = null,
    val fecha: String? = null,
    val fechaMostrado: String = "",
    val descripcion: String? = null,
    val genero: String? = null,
    val miniaturaUrl: String? = null,
    val imagenUrl: String? = null,
    val museo: Museo? = null
) : Parcelable
