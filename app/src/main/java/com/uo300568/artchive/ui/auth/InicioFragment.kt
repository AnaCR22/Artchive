package com.uo300568.artchive.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.uo300568.artchive.R


class InicioFragment : Fragment(R.layout.fragment_inicio) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.botonRegistrarse).setOnClickListener {
            findNavController().navigate(
                InicioFragmentDirections.actionInicioFragmentToRegistroFragment()
            )
        }

        view.findViewById<MaterialButton>(R.id.botonIniciarSesion).setOnClickListener {
            findNavController().navigate(
                InicioFragmentDirections.actionInicioFragmentToLoginFragment()
            )
        }
    }
}