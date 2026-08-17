package com.uo300568.artchive.data.remote.met

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class BusquedaDto(
    val total: Int = 0,
    val objectIDs: List<Int>? = null
)

@Serializable
data class CuadroDto(
    @SerialName("objectID") val id: Int = 0,
    @SerialName("title") val titulo: String = "",
    @SerialName("artistDisplayName") val autor: String = "",
    @SerialName("objectDate") val fecha: String = "",
    @SerialName("medium") val genero: String = "",
    @SerialName("primaryImage") val imagenUrl: String = "",
    @SerialName("primaryImageSmall") val miniaturaUrl: String = "",
    @SerialName("department") val departamento: String = ""
)