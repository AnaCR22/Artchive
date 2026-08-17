package com.uo300568.artchive.data.firebase

import com.uo300568.artchive.domain.Cuadro
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.uo300568.artchive.domain.Artista
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirestoreService {
    private val db = FirebaseFirestore.getInstance()

    // Historial
    suspend fun obtenerCuadroPorFecha(fecha: String): Cuadro? {
        return db.collection("cuadros")
            .document(fecha)
            .get()
            .await()
            .toObject(Cuadro::class.java)
    }

    suspend fun guardarCuadroDelDia(fecha: String, cuadro: Cuadro) {
        db.collection("cuadros")
            .document(fecha)
            .set(cuadro)
            .await()
    }

    fun obtenerHistorial(): Flow<List<Cuadro>> = flow {
        val cuadros = db.collection("cuadros")
            .orderBy("fechaMostrado", Query.Direction.DESCENDING)
            .get()
            .await()
            .map { it.toObject(Cuadro::class.java) }
        emit(cuadros)
    }

    // Favoritos
    suspend fun obtenerFavoritos(userId: String): List<Cuadro> {
        return db.collection("favoritos")
            .document(userId)
            .collection("cuadros")
            .get()
            .await()
            .map { it.toObject(Cuadro::class.java) }
    }

    suspend fun añadirFavorito(userId: String, cuadro: Cuadro) {
        db.collection("favoritos")
            .document(userId)
            .collection("cuadros")
            .document(cuadro.id)
            .set(cuadro)
            .await()
    }

    suspend fun eliminarFavorito(userId: String, cuadroId: String) {
        db.collection("favoritos")
            .document(userId)
            .collection("cuadros")
            .document(cuadroId)
            .delete()
            .await()
    }

    suspend fun esFavorito(userId: String, cuadroId: String): Boolean {
        return db.collection("favoritos")
            .document(userId)
            .collection("cuadros")
            .document(cuadroId)
            .get()
            .await()
            .exists()
    }

    suspend fun añadirArtistaFavorito(userId: String, artista: Artista) {
        db.collection("favoritos")
            .document(userId)
            .collection("artistas")
            .document(artista.nombre)
            .set(artista)
            .await()
    }

    suspend fun eliminarArtistaFavorito(userId: String, nombreArtista: String) {
        db.collection("favoritos")
            .document(userId)
            .collection("artistas")
            .document(nombreArtista)
            .delete()
            .await()
    }

    suspend fun esArtistaFavorito(userId: String, nombreArtista: String): Boolean {
        return db.collection("favoritos")
            .document(userId)
            .collection("artistas")
            .document(nombreArtista)
            .get()
            .await()
            .exists()
    }

    suspend fun obtenerArtistasFavoritos(userId: String): List<Artista> {
        return db.collection("favoritos")
            .document(userId)
            .collection("artistas")
            .get()
            .await()
            .map { it.toObject(Artista::class.java) }
    }
}