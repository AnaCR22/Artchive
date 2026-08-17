package com.uo300568.artchive.data.repository

import com.uo300568.artchive.domain.Artista
import com.uo300568.artchive.domain.Cuadro

interface UserRepository {
    suspend fun obtenerFavoritos(userId: String): List<Cuadro>
    suspend fun añadirFavorito(userId: String, cuadro: Cuadro)
    suspend fun eliminarFavorito(userId: String, cuadroId: String)
    suspend fun esFavorito(userId: String, cuadroId: String): Boolean

    suspend fun añadirArtistaFavorito(userId: String, artista: Artista)
    suspend fun eliminarArtistaFavorito(userId: String, nombreArtista: String)
    suspend fun esArtistaFavorito(userId: String, nombreArtista: String): Boolean
    suspend fun obtenerArtistasFavoritos(userId: String): List<Artista>
}