package com.tripfy.livecity2.adapters

import com.tripfy.livecity2.MainActivity
import com.tripfy.livecity2.clases.Ciudad


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.tripfy.livecity2.R
import com.tripfy.livecity2.singleton.ConfiguradorCiudad

class CiudadesAdapter(listaCiudades:List<Ciudad>):  RecyclerView.Adapter<CiudadesAdapter.ViewHolder?>() {


    private val listaCiudades : List<Ciudad>

    init{

        this.listaCiudades = listaCiudades
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var nombreCiudad: TextView
        var imgCiudad: ImageView

        init {
            nombreCiudad = itemView.findViewById(R.id.tvCiudad)
            imgCiudad = itemView.findViewById(R.id.imgCiudad)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_ciudad, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listaCiudades.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ciudad: Ciudad = listaCiudades[position]
        holder.nombreCiudad.text = ciudad.getNombre()
        Glide.with(holder.nombreCiudad.context).load(ciudad.getImagen()).placeholder(R.drawable.sevilla).into(holder.imgCiudad)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MainActivity::class.java)

            //Toast.makeText(holder.itemView.context, "la ciudad seleccionada es: "+ciudad.getKey(), Toast.LENGTH_SHORT).show()
            val config: ConfiguradorCiudad? = ConfiguradorCiudad.configurador
            config?.nombre = ciudad.getNombre()
            config?.descripcion = ciudad.getDescripcion()
            config?.imagen = ciudad.getImagen()
            config?.key = ciudad.getKey()

            holder.itemView.context.startActivity(intent)
        }
    }
}