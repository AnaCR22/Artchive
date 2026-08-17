package com.uo300568.artchive.data.repository

import com.uo300568.artchive.domain.Cuadro
import com.uo300568.artchive.domain.DataResult
import kotlinx.coroutines.flow.Flow

interface CuadroRepository {
    fun obtenerHistorial(): Flow<DataResult<List<Cuadro>>>
    fun obtenerCuadroDelDia(): Flow<DataResult<Cuadro?>>
    fun buscarPorArtista(artista: String): Flow<DataResult<List<Cuadro>>>
    suspend fun obtenerInfoArtista(nombreArtista: String): Pair<String?, String?>
}