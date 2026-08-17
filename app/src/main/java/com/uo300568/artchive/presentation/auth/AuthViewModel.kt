package com.uo300568.artchive.presentation.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    init {
        val usuarioActual = auth.currentUser
        if (usuarioActual != null) {
            _uiState.value = AuthUiState(sesionIniciada = true, userId = usuarioActual.uid)
        }
    }

    fun registrar(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(cargando = true)
            try {
                val resultado = auth.createUserWithEmailAndPassword(email, password).await()
                _uiState.value = AuthUiState(
                    sesionIniciada = true,
                    userId = resultado.user?.uid
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState(error = e.message)
            }
        }
    }

    fun iniciarSesion(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(cargando = true)
            try {
                val resultado = auth.signInWithEmailAndPassword(email, password).await()
                _uiState.value = AuthUiState(
                    sesionIniciada = true,
                    userId = resultado.user?.uid
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState(error = e.message)
            }
        }
    }

    fun cerrarSesion() {
        auth.signOut()
        _uiState.value = AuthUiState(sesionIniciada = false)
    }

    fun cambiarContrasena(contrasenaActual: String, nuevaContrasena: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(cargando = true)
            try {
                val usuario = auth.currentUser ?: throw Exception("No hay usuario")
                val email = usuario.email ?: throw Exception("Sin email")

                // Reautenticar
                val credencial = EmailAuthProvider.getCredential(email, contrasenaActual)
                usuario.reauthenticate(credencial).await()

                auth.currentUser?.updatePassword(nuevaContrasena)?.await()
                _uiState.value = _uiState.value.copy(cargando = false, exito = "Contraseña cambiada correctamente")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(cargando = false, error = e.message)
            }
        }
    }
}