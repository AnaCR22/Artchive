package com.uo300568.artchive.ui.museo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.uo300568.artchive.ArtchiveApp
import com.uo300568.artchive.R
import com.uo300568.artchive.presentation.museo.MuseoViewModelFactory
import kotlinx.coroutines.launch
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.tasks.CancellationTokenSource
import com.uo300568.artchive.presentation.museo.MuseoViewModel

class MuseoFragment : Fragment(R.layout.fragment_museo), OnMapReadyCallback {

    private val museoViewModel: MuseoViewModel by viewModels {
        MuseoViewModelFactory((requireActivity().application as ArtchiveApp).museumRepository)
    }

    private lateinit var mapa: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val permisoUbicacion = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) obtenerUbicacion()
        else Toast.makeText(requireContext(), getString(R.string.error_permiso_denegado), Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        val mapaFragment = childFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapaFragment.getMapAsync(this)

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarMuseo)

        viewLifecycleOwner.lifecycleScope.launch {
            museoViewModel.uiState.collect { state ->
                progressBar.visibility = if (state.cargando) View.VISIBLE else View.GONE

                state.error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }

                if (::mapa.isInitialized) {
                    mapa.clear()
                    state.museos.forEach { museo ->
                        if (museo.latitud != null && museo.longitud != null) {
                            mapa.addMarker(
                                MarkerOptions()
                                    .position(LatLng(museo.latitud, museo.longitud))
                                    .title(museo.nombre)
                                    .snippet(museo.direccion)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap
        comprobarPermisos()
    }

    private fun comprobarPermisos() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            obtenerUbicacion()
        } else {
            permisoUbicacion.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion() {
        val cancellationToken = CancellationTokenSource().token
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationToken
        ).addOnSuccessListener { ubicacion ->
            if (ubicacion != null) {
                val miUbicacion = LatLng(ubicacion.latitude, ubicacion.longitude)
                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 13f))
                mapa.isMyLocationEnabled = true
                museoViewModel.buscarMuseos(ubicacion.latitude, ubicacion.longitude)
            } else {
                Toast.makeText(requireContext(), getString(R.string.error_ubicacion_no_disponible), Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(requireContext(), getString(R.string.error_obtener_ubicacion, e.message), Toast.LENGTH_SHORT).show()
        }
    }
}