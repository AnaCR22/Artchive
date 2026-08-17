package com.uo300568.artchive.data.remote.wiki

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class WikiDto(
    @SerialName("title") val titulo: String = "",
    @SerialName("extract") val descripcion: String = "",
    @SerialName("thumbnail") val miniatura: WikiMiniaturaDto? = null
)

@Serializable
data class WikiMiniaturaDto(
    @SerialName("source") val url: String = ""
)