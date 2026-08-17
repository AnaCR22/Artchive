package com.uo300568.artchive.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.uo300568.artchive.MainActivity
import com.uo300568.artchive.R
import com.uo300568.artchive.presentation.auth.AuthViewModel
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<MaterialButton>(R.id.botonIniciarSesion).setOnClickListener {
            val email = view.findViewById<TextInputEditText>(R.id.editEmail).text.toString()
            val password = view.findViewById<TextInputEditText>(R.id.editContrasena).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.error_campos_vacios), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authViewModel.iniciarSesion(email, password)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.uiState.collect { state ->
                when {
                    state.cargando -> {
                        view.findViewById<MaterialButton>(R.id.botonIniciarSesion).isEnabled = false
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                    }
                    state.sesionIniciada -> {
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                    state.error != null -> {
                        view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                        view.findViewById<MaterialButton>(R.id.botonIniciarSesion).isEnabled = true
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        view.findViewById<TextView>(R.id.textoNoTienesCuenta).setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegistroFragment()
            )
        }
    }
}