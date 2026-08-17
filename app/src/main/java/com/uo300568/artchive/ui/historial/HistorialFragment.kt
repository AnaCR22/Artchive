package com.uo300568.artchive.ui.historial

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uo300568.artchive.ArtchiveApp
import com.uo300568.artchive.R
import com.uo300568.artchive.presentation.historial.HistorialViewModelFactory
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import com.uo300568.artchive.presentation.historial.HistorialViewModel
import android.widget.Toast

class HistorialFragment : Fragment(R.layout.fragment_historial) {
    private val historialViewModel: HistorialViewModel by viewModels {
        HistorialViewModelFactory((requireActivity().application as ArtchiveApp).cuadroRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerHistorial)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            historialViewModel.uiState.collect { state ->
                val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
                val textoVacio = view.findViewById<TextView>(R.id.textoVacio)

                progressBar.visibility = if (state.cargando) View.VISIBLE else View.GONE
                textoVacio.visibility = if (!state.cargando && state.cuadros.isEmpty()) View.VISIBLE else View.GONE

                state.error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }

                recycler.adapter = CuadroAdapter(state.cuadros) { cuadro ->
                    findNavController().navigate(
                        HistorialFragmentDirections.actionHistorialFragmentToCuadroFragment(cuadro)
                    )
                }
            }
        }
    }
}