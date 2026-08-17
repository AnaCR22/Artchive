package com.uo300568.artchive.ui.favoritos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.uo300568.artchive.ArtchiveApp
import com.uo300568.artchive.R
import com.uo300568.artchive.presentation.favoritos.FavoritesViewModel
import com.uo300568.artchive.presentation.favoritos.FavoritesViewModelFactory
import com.uo300568.artchive.presentation.favoritos.FiltroFavoritos
import com.uo300568.artchive.ui.artista.ObrasGridAdapter
import kotlinx.coroutines.launch
import android.widget.Toast
import android.widget.TextView

class FavoritosFragment : Fragment(R.layout.fragment_favoritos) {

    private val favoritosViewModel: FavoritesViewModel by viewModels {
        FavoritesViewModelFactory((requireActivity().application as ArtchiveApp).userRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> favoritosViewModel.cambiarFiltro(FiltroFavoritos.OBRA)
                    1 -> favoritosViewModel.cambiarFiltro(FiltroFavoritos.ARTISTA)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewLifecycleOwner.lifecycleScope.launch {
            favoritosViewModel.uiState.collect { state ->
                val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
                val textoVacio = view.findViewById<TextView>(R.id.textoVacio)

                progressBar.visibility = if (state.cargando) View.VISIBLE else View.GONE

                state.error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }

                val estaVacio = when (state.filtro) {
                    FiltroFavoritos.OBRA -> state.favoritos.isEmpty()
                    FiltroFavoritos.ARTISTA -> state.artistasFavoritos.isEmpty()
                }
                textoVacio.visibility = if (!state.cargando && estaVacio) View.VISIBLE else View.GONE

                when (state.filtro) {
                    FiltroFavoritos.OBRA -> {
                        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
                        recycler.adapter = ObrasGridAdapter(state.favoritos) { cuadro ->
                            findNavController().navigate(
                                FavoritosFragmentDirections.actionFavoritosFragmentToCuadroFragment(cuadro)
                            )
                        }
                    }
                    FiltroFavoritos.ARTISTA -> {
                        recycler.layoutManager = LinearLayoutManager(requireContext())
                        recycler.adapter = ArtistaAdapter(state.artistasFavoritos) { artista ->
                            findNavController().navigate(
                                FavoritosFragmentDirections.actionFavoritosFragmentToArtistaFragment(artista.nombre)
                            )
                        }
                    }
                }
            }
        }
    }
}