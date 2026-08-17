package com.uo300568.artchive.data.repository

import com.uo300568.artchive.data.remote.places.PlacesApiService
import com.uo300568.artchive.domain.Museo
import com.uo300568.artchive.data.remote.places.PlacesRequest
import com.uo300568.artchive.data.remote.places.RestriccionUbicacion
import com.uo300568.artchive.data.remote.places.Circulo
import com.uo300568.artchive.data.remote.places.Coordenadas

class MuseoRepositoryImpl(
    private val apiService: PlacesApiService,
    private val apiKey: String
) : MuseoRepository {
    override suspend fun buscarMuseosCercanos(latitud: Double, longitud: Double): List<Museo> {
        return try {
            val peticion = PlacesRequest(
                restriccionUbicacion = RestriccionUbicacion(
                    circulo = Circulo(centro = Coordenadas(latitud, longitud))
                )
            )
            val respuesta = apiService.buscarMuseosCercanos(apiKey, peticion = peticion)
            respuesta.lugares?.map { lugar ->
                Museo(
                    nombre = lugar.nombre?.texto ?: "",
                    latitud = lugar.ubicacion?.latitud,
                    longitud = lugar.ubicacion?.longitud,
                    direccion = lugar.direccion
                )
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}