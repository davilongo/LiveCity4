package com.tripfy.livecity2.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripfy.livecity2.DetalleBarActivity
import com.tripfy.livecity2.MainActivity
import com.tripfy.livecity2.R
import com.tripfy.livecity2.clases.Bar
import com.tripfy.livecity2.clases.Ciudad
import com.tripfy.livecity2.singleton.ConfiguradorBar
import com.tripfy.livecity2.singleton.ConfiguradorCiudad

class BaresAdapter(listaBares:List<Bar>, item:String):  RecyclerView.Adapter<BaresAdapter.ViewHolder?>() {


    private val listaBares : List<Bar>
    private val item: String

    init{

        this.listaBares = listaBares
        this.item = item
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var nombreBar: TextView
        var imgBar: ImageView

        init {
            nombreBar = itemView.findViewById(R.id.tvBar)
            imgBar = itemView.findViewById(R.id.imgBar)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        if(item.equals("Home")){
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_bar_home, parent, false)

        }else{
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_bar, parent, false)
        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listaBares.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val bar: Bar = listaBares[position]
        holder.nombreBar.text = bar.getNombre()
        Glide.with(holder.itemView.context).load(bar.getImagen()).placeholder(R.drawable.sevilla).into(holder.imgBar)
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, DetalleBarActivity::class.java)
            val config: ConfiguradorBar? = ConfiguradorBar.configurador
            config?.nombre = bar.getNombre()
            config?.descripcion = bar.getDescripcion()
            config?.calidad = bar.getCalidad()
            config?.ciudad = bar.getCiudad()
            config?.direccion = bar.getDireccion()
            config?.horaApertura = bar.getHoraApertura()
            config?.horaCierre = bar.getHoraCierre()
            config?.imagen = bar.getImagen()
            config?.precio = bar.getPrecio()
            config?.tipoComida = bar.getTipoComida()
            config?.key = bar.getKey()







           // Toast.makeText(holder.itemView.context, "la doreccion es: "+bar.getDireccion(), Toast.LENGTH_SHORT).show()
            holder.itemView.context.startActivity(intent)


        }
    }
}