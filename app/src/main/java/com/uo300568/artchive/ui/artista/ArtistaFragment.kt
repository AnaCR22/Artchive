package com.uo300568.artchive.ui.artista

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.uo300568.artchive.ArtchiveApp
import com.uo300568.artchive.R
import com.uo300568.artchive.presentation.artista.ArtistaViewModel
import com.uo300568.artchive.presentation.artista.ArtistaViewModelFactory
import kotlinx.coroutines.launch

class ArtistaFragment : Fragment(R.layout.fragment_artista) {

    private val args: ArtistaFragmentArgs by navArgs()

    private val artistaViewModel: ArtistaViewModel by viewModels {
        ArtistaViewModelFactory(
            (requireActivity().application as ArtchiveApp).cuadroRepository,
            (requireActivity().application as ArtchiveApp).userRepository,
            args.nombreArtista
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerObras)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val botonLike = view.findViewById<FloatingActionButton>(R.id.botonLikeArtista)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarArtista)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutArtista)
        val biografiaArtista  = view.findViewById<TextView>(R.id.biografiaArtista)

        botonLike.setOnClickListener {
            artistaViewModel.modficarBotonFavorito()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        biografiaArtista.visibility = View.VISIBLE
                        recycler.visibility = View.GONE
                    }
                    1 -> {
                        biografiaArtista.visibility = View.GONE
                        recycler.visibility = View.VISIBLE
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewLifecycleOwner.lifecycleScope.launch {
            artistaViewModel.uiState.collect { state ->
                progressBar.visibility = if (state.cargando) View.VISIBLE else View.GONE

                view.findViewById<TextView>(R.id.nombreArtista).text = state.nombreArtista
                biografiaArtista.text = state.biografia

                state.fotoUrl?.let {
                    Glide.with(requireContext()).load(it)
                        .into(view.findViewById(R.id.fotoArtista))
                }

                recycler.adapter = ObrasGridAdapter(state.cuadros) { cuadro ->
                    findNavController().navigate(
                        ArtistaFragmentDirections.actionArtistaFragmentToCuadroFragment(cuadro)
                    )
                }

                val icono = if (state.esFavorito) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_outline
                botonLike.setImageResource(icono)
            }
        }
    }
}