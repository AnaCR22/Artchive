package com.uo300568.artchive.data.remote.places

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PlacesApiService {
    @POST("https://places.googleapis.com/v1/places:searchNearby")
    suspend fun buscarMuseosCercanos(
        @Header("X-Goog-Api-Key") apiKey: String,
        @Header("X-Goog-FieldMask") fieldMask: String = "places.displayName,places.location,places.formattedAddress",
        @Body peticion: PlacesRequest
    ): PlacesRespuesta
}