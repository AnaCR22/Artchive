package com.uo300568.artchive.ui.cuadro

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.uo300568.artchive.R
import androidx.core.graphics.drawable.toDrawable

class ImagenCompletaFragment : DialogFragment() {

    private val args: ImagenCompletaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_imagen_completa, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load(args.imagenUrl)
            .into(view.findViewById<PhotoView>(R.id.imagenCompleta))

        view.setOnClickListener { dismiss() }
    }
}