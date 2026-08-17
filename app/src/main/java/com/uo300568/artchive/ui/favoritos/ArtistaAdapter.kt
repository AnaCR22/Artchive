package com.uo300568.artchive.ui.favoritos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uo300568.artchive.R
import com.uo300568.artchive.domain.Artista

class ArtistaAdapter(
    private val artistas: List<Artista>,
    private val onItemClick: (Artista) -> Unit
) : RecyclerView.Adapter<ArtistaAdapter.ArtistaViewHolder>() {

    inner class ArtistaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(artista: Artista, onItemClick: (Artista) -> Unit) {
            itemView.findViewById<TextView>(R.id.nombreItemArtista).text = artista.nombre
            artista.fotoUrl?.let {
                Glide.with(itemView.context).load(it)
                    .into(itemView.findViewById(R.id.fotoItemArtista))
            }
            itemView.setOnClickListener { onItemClick(artista) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artista, parent, false)
        return ArtistaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistaViewHolder, position: Int) {
        holder.bind(artistas[position], onItemClick)
    }

    override fun getItemCount() = artistas.size
}