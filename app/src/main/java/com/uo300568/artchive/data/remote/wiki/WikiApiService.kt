package com.uo300568.artchive.data.remote.wiki

import retrofit2.http.GET
import retrofit2.http.Path

interface WikiApiService {
    @GET("page/summary/{titulo}")
    suspend fun obtenerResumen(
        @Path("titulo") titulo: String
    ): WikiDto
}