package com.uo300568.artchive.ui.configuracion

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.uo300568.artchive.R
import com.uo300568.artchive.presentation.auth.AuthViewModel
import com.uo300568.artchive.ui.auth.AuthActivity
import kotlinx.coroutines.launch

class ConfiguracionFragment : Fragment(R.layout.fragment_configuracion) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val email = auth.currentUser?.email ?: ""

        view.findViewById<TextView>(R.id.textoEmail).text = email

        view.findViewById<MaterialButton>(R.id.botonCerrarSesion).setOnClickListener {
            authViewModel.cerrarSesion()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        view.findViewById<MaterialButton>(R.id.botonCambiarPassword).setOnClickListener {
            mostrarDialogoCambiarContrasena()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.uiState.collect { state ->
                state.error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
                state.exito?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun mostrarDialogoCambiarContrasena() {
        val contrasenaActual = EditText(requireContext()).apply {
            hint = getString(R.string.hint_contrasena_actual)
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val input = EditText(requireContext()).apply {
            hint = getString(R.string.hint_nueva_contrasena)
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 16, 48, 16)
            addView(contrasenaActual)
            addView(input)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_cambiar_password))
            .setView(layout)
            .setPositiveButton(getString(R.string.dialog_confirmar)) { _, _ ->
                val actual = contrasenaActual.text.toString()
                val nueva = input.text.toString()
                cambiarContrasena(actual, nueva)
            }
            .setNegativeButton(getString(R.string.dialog_cancelar), null)
            .show()
    }

    private fun cambiarContrasena(actual: String, nueva: String){
        if (actual.isEmpty() || nueva.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.error_rellena_ambos_campos), Toast.LENGTH_SHORT).show()
        } else if (nueva.length < 6) {
            Toast.makeText(requireContext(), getString(R.string.error_password_corta), Toast.LENGTH_SHORT).show()
        } else {
            authViewModel.cambiarContrasena(actual, nueva)
        }
    }
}