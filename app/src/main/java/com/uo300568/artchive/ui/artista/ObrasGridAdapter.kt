package com.uo300568.artchive.ui.artista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uo300568.artchive.R
import com.uo300568.artchive.domain.Cuadro

class ObrasGridAdapter(
    private val cuadros: List<Cuadro>,
    private val onItemClick: (Cuadro) -> Unit
) : RecyclerView.Adapter<ObrasGridAdapter.ObraViewHolder>() {

    inner class ObraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cuadro: Cuadro, onItemClick: (Cuadro) -> Unit) {
            Glide.with(itemView.context).load(cuadro.miniaturaUrl)
                .into(itemView.findViewById(R.id.miniaturaObra))
            itemView.setOnClickListener { onItemClick(cuadro) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObraViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_obra_grid, parent, false)
        return ObraViewHolder(view)
    }

    override fun onBindViewHolder(holder: ObraViewHolder, position: Int) {
        holder.bind(cuadros[position], onItemClick)
    }

    override fun getItemCount() = cuadros.size
}