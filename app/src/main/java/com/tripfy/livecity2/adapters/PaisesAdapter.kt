package com.tripfy.livecity2.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tripfy.livecity2.AddCiudadActivity
import com.tripfy.livecity2.R
import com.tripfy.livecity2.clases.Pais

class PaisesAdapter(listaPaises:List<Pais>):  RecyclerView.Adapter<PaisesAdapter.ViewHolder?>() {


    private val listaPaises : List<Pais>

    init{

        this.listaPaises = listaPaises
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var nombrePais: TextView

        init {
            nombrePais = itemView.findViewById(R.id.tvPais)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_pais, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listaPaises.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pais: Pais = listaPaises[position]
        holder.nombrePais.text = pais.getNombre()
        //holder.email.text = usuario.getEmail()
        //Glide.with(context).load(usuario.getImagen()).placeholder(R.drawable.ic_item_user).into(holder.image)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddCiudadActivity::class.java)
            intent.putExtra("pais", pais.getNombre())
            Toast.makeText(holder.itemView.context, "el país seleccionado es: "+pais.getNombre(), Toast.LENGTH_SHORT).show()
            holder.itemView.context.startActivity(intent)
        }
    }
}