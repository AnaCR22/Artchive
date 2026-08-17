package com.uo300568.artchive.data.repository

import com.uo300568.artchive.data.firebase.FirestoreService
import com.uo300568.artchive.data.remote.met.MetApiService
import com.uo300568.artchive.data.remote.wiki.WikiApiService
import com.uo300568.artchive.domain.Cuadro
import com.uo300568.artchive.domain.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class CuadroRepositoryImpl (
    private val firestoreService: FirestoreService,
    private val apiService: MetApiService,
    private val wikiApi: WikiApiService
    ) : CuadroRepository {

    override fun obtenerHistorial(): Flow<DataResult<List<Cuadro>>> = flow {
        emit(DataResult.Cargando)
        try {
            firestoreService.obtenerHistorial().collect { cuadros ->
                emit(DataResult.Exito(cuadros))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message ?: "Error al cargar el historial"))
        }
    }

    override fun obtenerCuadroDelDia(): Flow<DataResult<Cuadro?>> = flow {
        emit(DataResult.Cargando)
        try{
            val hoy = LocalDate.now().toString()
            val ultimo = firestoreService.obtenerCuadroPorFecha(hoy)
            //si ya hay un cuadro del día, lo devolvemos
            if (ultimo != null) {
                emit(DataResult.Exito(ultimo))
                return@flow
            }

            // Buscar IDs de cuadros
            val busqueda = apiService.buscar(query = "painting")
            val ids = busqueda.objectIDs ?: run {
                emit(DataResult.Error("No se encontraron cuadros"))
                return@flow
            }

            // Buscar cuadro con descripción válida
            var cuadro: Cuadro? = null
            val idsBarajados = ids.shuffled()

            for (id in idsBarajados) {
                try {
                    val cuadroDto = apiService.obtenerCuadro(id)
                    val tituloWiki = cuadroDto.titulo.replace(" ", "_")
                    val descripcion = try {
                        val resultado = wikiApi.obtenerResumen(tituloWiki).descripcion
                        if (resultado.contains("may refer to") || resultado.length < 100) null
                        else resultado
                    } catch (e: Exception) { null }

                    if (descripcion != null && cuadroDto.imagenUrl.isNotEmpty() && cuadroDto.miniaturaUrl.isNotEmpty()) {
                        cuadro = cuadroDto.toCuadro().copy(descripcion = descripcion)
                        break
                    }
                } catch (e: Exception) {
                    continue
                }
            }


            // Guardar cuadro en Firestore con la fecha de hoy para el historial
            cuadro?.let { c ->
                val cuadroConFecha = c.copy(fechaMostrado = hoy)
                firestoreService.guardarCuadroDelDia(hoy, cuadroConFecha)
                emit(DataResult.Exito(cuadroConFecha))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message ?: "Error desconocido"))
        }
    }

    override fun buscarPorArtista(artista: String): Flow<DataResult<List<Cuadro>>>  = flow {
        emit(DataResult.Cargando)
        try {
            val busqueda = apiService.buscar(query = artista)
            val ids = busqueda.objectIDs?.take(10) ?: run {
                emit(DataResult.Exito(emptyList()))
                return@flow
            }

            val cuadros = ids.mapNotNull { id ->
                try {
                    apiService.obtenerCuadro(id).toCuadro()
                } catch (e: Exception) { null } //ignorar id que fallen
            }

            emit(DataResult.Exito(cuadros))
        } catch (e: Exception) {
            emit(DataResult.Error(e.message ?: "Error desconocido"))

        }
    }

    override suspend fun obtenerInfoArtista(nombreArtista: String): Pair<String?, String?> {
        return try {
            val tituloWiki = nombreArtista.replace(" ", "_")
            val respuesta = wikiApi.obtenerResumen(tituloWiki)
            Pair(respuesta.descripcion, respuesta.miniatura?.url)
        } catch (e: Exception) {
            Pair(null, null)
        }
    }

}