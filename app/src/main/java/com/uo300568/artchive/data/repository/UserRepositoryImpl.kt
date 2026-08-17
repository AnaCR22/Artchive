package com.uo300568.artchive.data.repository

import com.uo300568.artchive.data.firebase.FirestoreService
import com.uo300568.artchive.domain.Artista
import com.uo300568.artchive.domain.Cuadro

class UserRepositoryImpl (
    private val firestoreService: FirestoreService,
) : UserRepository{
    override suspend fun obtenerFavoritos(userId: String): List<Cuadro> {
        return firestoreService.obtenerFavoritos(userId);
    }

    override suspend fun añadirFavorito(userId: String, cuadro: Cuadro) {
        firestoreService.añadirFavorito(userId, cuadro);
    }

    override suspend fun eliminarFavorito(userId: String, cuadroId: String) {
        firestoreService.eliminarFavorito(userId, cuadroId);
    }

    override suspend fun esFavorito(userId: String, cuadroId: String): Boolean {
        return firestoreService.esFavorito(userId, cuadroId);
    }

    override suspend fun añadirArtistaFavorito(userId: String, artista: Artista) {
        firestoreService.añadirArtistaFavorito(userId, artista)
    }

    override suspend fun eliminarArtistaFavorito(userId: String, nombreArtista: String) {
        firestoreService.eliminarArtistaFavorito(userId, nombreArtista)
    }

    override suspend fun esArtistaFavorito(userId: String, nombreArtista: String): Boolean {
        return firestoreService.esArtistaFavorito(userId, nombreArtista)
    }

    override suspend fun obtenerArtistasFavoritos(userId: String): List<Artista> {
        return firestoreService.obtenerArtistasFavoritos(userId)
    }
}