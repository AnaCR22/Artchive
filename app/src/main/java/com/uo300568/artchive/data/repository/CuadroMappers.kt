package com.uo300568.artchive.data.repository

import com.uo300568.artchive.domain.Cuadro
import com.uo300568.artchive.domain.Museo
import com.uo300568.artchive.data.remote.met.CuadroDto

fun CuadroDto.toCuadro(): Cuadro = Cuadro(
    id = id.toString(),
    titulo = titulo.ifEmpty { "Sin título" },
    autor = autor.ifEmpty { "Anónimo" },
    fecha = fecha.ifEmpty { "Fecha desconocida" },
    descripcion = null,
    genero = genero.ifEmpty { "Sin género" },
    miniaturaUrl = miniaturaUrl.ifEmpty { null },
    imagenUrl = imagenUrl.ifEmpty { null },
    museo = Museo(nombre = "Metropolitan Museum of Art")
)

