package com.uo300568.artchive.data.remote.met

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetApiService {
    @GET("search")
    suspend fun buscar(
        @Query("q") query: String,
        @Query("hasImages") tieneImagenes: Boolean = true,
        @Query("medium") tipo: String = "Paintings"
    ): BusquedaDto

    @GET("objects/{objectID}")
    suspend fun obtenerCuadro(
        @Path("objectID") id: Int
    ): CuadroDto
}