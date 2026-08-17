package com.uo300568.artchive.ui.cuadro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.uo300568.artchive.R
import com.uo300568.artchive.presentation.cuadro.CuadroViewModel
import com.uo300568.artchive.presentation.cuadro.CuadroViewModelFactory
import kotlinx.coroutines.launch
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uo300568.artchive.ArtchiveApp
import com.uo300568.artchive.presentation.favoritos.FavoritesViewModel
import com.uo300568.artchive.presentation.favoritos.FavoritesViewModelFactory
import android.widget.Toast
import java.time.LocalDate

class CuadroFragment : Fragment(R.layout.fragment_cuadro) {
    private val args: CuadroFragmentArgs by navArgs()

    private val cuadroViewModel: CuadroViewModel by viewModels {
        CuadroViewModelFactory(
            args.cuadro,
            (requireActivity().application as ArtchiveApp).cuadroRepository,
            (requireActivity().application as ArtchiveApp).userRepository
        )
    }
     private val favoritosViewModel: FavoritesViewModel by activityViewModels {
         FavoritesViewModelFactory((requireActivity().application as ArtchiveApp).userRepository)
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         val botonLike = view.findViewById<FloatingActionButton>(R.id.botonLike)
         configurarBotones(view, botonLike)


        viewLifecycleOwner.lifecycleScope.launch {
            cuadroViewModel.uiState.collect { state ->
                view.findViewById<ProgressBar>(R.id.progressBar).visibility =
                    if (state.cargando) View.VISIBLE else View.GONE

                state.cuadro?.let { cuadro ->
                    Glide.with(requireContext()).load(cuadro.imagenUrl).into(view.findViewById(R.id.imagenCuadro))
                    view.findViewById<TextView>(R.id.textoTitulo).text = cuadro.titulo
                    view.findViewById<TextView>(R.id.textoAutor).text = cuadro.autor
                    view.findViewById<TextView>(R.id.textoFecha).text = cuadro.fecha?.replaceFirstChar { it.uppercase() }
                    view.findViewById<TextView>(R.id.textoGenero).text = cuadro.genero
                    view.findViewById<TextView>(R.id.textoDescripcion).text = cuadro.descripcion

                    actualizarEtiquetaFecha(view, cuadro.fechaMostrado)
                }

                state.error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }

                val icono = if (state.esFavorito) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_outline
                botonLike.setImageResource(icono)
            }

        }
    }

    private fun actualizarEtiquetaFecha(view: View, fechaMostrado: String?) {
        val hoy = LocalDate.now().toString()
        val ayer = LocalDate.now().minusDays(1).toString()
        val etiqueta = view.findViewById<TextView>(R.id.etiquetaFecha)
        when (fechaMostrado) {
            hoy -> {
                etiqueta.text = getString(R.string.etiqueta_hoy)
                etiqueta.visibility = View.VISIBLE
            }
            ayer -> {
                etiqueta.text = getString(R.string.etiqueta_ayer)
                etiqueta.visibility = View.VISIBLE
            }
            else -> etiqueta.visibility = View.GONE
        }
    }

    private fun configurarBotones(view: View, botonLike: FloatingActionButton) {
        botonLike.setOnClickListener { toggleFavorito() }
        view.findViewById<TextView>(R.id.textoAutor).setOnClickListener { navegarAArtista() }
        view.findViewById<FloatingActionButton>(R.id.botonCompartir).setOnClickListener { compartirCuadro() }
        view.findViewById<ImageView>(R.id.imagenCuadro).setOnClickListener { verImagenCompleta() }
    }

    private fun verImagenCompleta() {
        val cuadro = cuadroViewModel.uiState.value.cuadro ?: return
        cuadro.imagenUrl?.let { url ->
            findNavController().navigate(
                CuadroFragmentDirections.actionCuadroFragmentToImagenCompletaFragment(url)
            )
        }
    }

    private fun toggleFavorito() {
        val cuadro = cuadroViewModel.uiState.value.cuadro ?: return
        if (cuadroViewModel.uiState.value.esFavorito) {
            favoritosViewModel.eliminarFavorito(cuadro.id)
        } else {
            favoritosViewModel.añadirFavorito(cuadro)
        }
        cuadroViewModel.modficarBotonFavorito()
    }

    private fun navegarAArtista() {
        cuadroViewModel.uiState.value.cuadro?.autor?.let { autor ->
            findNavController().navigate(
                CuadroFragmentDirections.actionCuadroFragmentToArtistaFragment(autor)
            )
        }
    }

    private fun compartirCuadro() {
        val cuadro = cuadroViewModel.uiState.value.cuadro ?: return
        val texto = "${cuadro.titulo} - ${cuadro.autor}\n${cuadro.imagenUrl}"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, texto)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.compartir)))
    }

}