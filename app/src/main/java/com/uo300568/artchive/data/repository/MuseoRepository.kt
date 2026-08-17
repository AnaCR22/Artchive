package com.uo300568.artchive.data.repository

import com.uo300568.artchive.domain.Museo

interface  MuseoRepository {
    suspend fun buscarMuseosCercanos(latitud: Double, longitud: Double): List<Museo>
}