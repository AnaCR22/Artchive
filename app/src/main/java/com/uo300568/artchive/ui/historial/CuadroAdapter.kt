package com.uo300568.artchive.ui.historial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uo300568.artchive.R
import com.uo300568.artchive.domain.Cuadro
import java.time.LocalDate

class CuadroAdapter (
    private val cuadros: List<Cuadro>,
    private val onItemClick: (Cuadro) -> Unit
) : RecyclerView.Adapter<CuadroAdapter.CuadroViewHolder>() {

    inner class CuadroViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cuadro: Cuadro, onItemClick: (Cuadro) -> Unit) {
            Glide.with(itemView.context).load(cuadro.miniaturaUrl).into(itemView.findViewById(R.id.miniaturaCuadro))
            itemView.findViewById<TextView>(R.id.itemTitulo).text = cuadro.titulo
            itemView.findViewById<TextView>(R.id.itemAutor).text = cuadro.autor

            val hoy = LocalDate.now().toString()
            val ayer = LocalDate.now().minusDays(1).toString()
            itemView.findViewById<TextView>(R.id.itemFecha).text = when (cuadro.fechaMostrado) {
                hoy -> itemView.context.getString(R.string.etiqueta_hoy)
                ayer -> itemView.context.getString(R.string.etiqueta_ayer)
                else -> cuadro.fechaMostrado
            }
            itemView.setOnClickListener { onItemClick(cuadro) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuadroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cuadro, parent, false)
        return CuadroViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuadroViewHolder, position: Int) {
        holder.bind(cuadros[position], onItemClick)
    }

    override fun getItemCount() = cuadros.size
}