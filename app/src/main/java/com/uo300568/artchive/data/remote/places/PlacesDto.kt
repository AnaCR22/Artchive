package com.uo300568.artchive.data.remote.places

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PlacesRequest(
    @SerialName("includedTypes") val tipos: List<String> = listOf("museum"),
    @SerialName("maxResultCount") val maxResultados: Int = 20,
    @SerialName("locationRestriction") val restriccionUbicacion: RestriccionUbicacion
)

@Serializable
data class RestriccionUbicacion(
    @SerialName("circle") val circulo: Circulo
)

@Serializable
data class Circulo(
    @SerialName("center") val centro: Coordenadas,
    @SerialName("radius") val radio: Double = 5000.0
)

@Serializable
data class Coordenadas(
    @SerialName("latitude") val latitud: Double,
    @SerialName("longitude") val longitud: Double
)

@Serializable
data class PlacesRespuesta(
    @SerialName("places") val lugares: List<PlaceDto>? = null
)

@Serializable
data class PlaceDto(
    @SerialName("displayName") val nombre: NombreLugar? = null,
    @SerialName("location") val ubicacion: Coordenadas? = null,
    @SerialName("formattedAddress") val direccion: String? = null
)

@Serializable
data class NombreLugar(
    @SerialName("text") val texto: String = ""
)