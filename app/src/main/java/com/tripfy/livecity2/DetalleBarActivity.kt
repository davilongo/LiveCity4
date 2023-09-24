package com.tripfy.livecity2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tripfy.livecity2.singleton.ConfiguradorBar
import com.tripfy.livecity2.singleton.ConfiguradorCiudad
import com.tripfy.livecity2.singleton.ConfiguradorUsuario

class DetalleBarActivity : AppCompatActivity() {

    private lateinit var imgBar: ImageView
    private lateinit var tvCalidad: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvDireccion: TextView
    private lateinit var tvHoraApertura: TextView
    private lateinit var tvHoraCierre: TextView
    private lateinit var tvNombre: TextView
    private lateinit var tvPrecio: TextView
    private lateinit var tvTipoComida: TextView
    private lateinit var btnEditBar: Button

    private lateinit var confBar: ConfiguradorBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_bar)
        inicializarVariables()
        obtenerDatos()
        listeners()

    }

    private fun inicializarVariables() {
        tvCalidad = findViewById(R.id.tvCalidad)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvDireccion = findViewById(R.id.tvDireccion)
        tvHoraApertura = findViewById(R.id.tvHoraApertura)
        tvHoraCierre = findViewById(R.id.tvHoraCierre)
        tvNombre = findViewById(R.id.tvBar)
        tvPrecio = findViewById(R.id.tvPrecio)
        tvTipoComida = findViewById(R.id.tvTipoComida)
        imgBar = findViewById(R.id.imgBar)
        btnEditBar = findViewById(R.id.btnEditarInfoBar)


    }
    private fun listeners(){
        btnEditBar.setOnClickListener {
            var intent = Intent(this, EditarBarActivity::class.java)
/*
            val config: ConfiguradorBar? = ConfiguradorBar.configurador

            config?.calidad = tvCalidad.text.toString()
            config?.descripcion = tvDescripcion.text.toString()
            config?.direccion = tvDireccion.text.toString()
            config?.horaApertura = tvHoraApertura.text.toString()
            config?.horaCierre = tvHoraCierre.text.toString()
            config?.nombre = tvNombre.text.toString()
            config?.precio = tvPrecio.text.toString()
            config?.tipoComida = tvTipoComida.text.toString()
  */
            startActivity(intent)
        }
    }
    private fun obtenerDatos(){
        confBar = ConfiguradorBar.configurador!!
        tvCalidad.text = confBar?.calidad.toString().orEmpty()
        tvDescripcion.text = confBar?.descripcion.toString().orEmpty()
        tvDireccion.text = confBar?.direccion.toString().orEmpty()
        //Toast.makeText(this, "la direccion: ${confBar?.direccion}", Toast.LENGTH_SHORT).show()
        tvHoraApertura.text = confBar?.horaApertura.toString().orEmpty()
        tvHoraCierre.text = confBar?.horaCierre.toString().orEmpty()
        tvNombre.text = confBar?.nombre.toString().orEmpty()
        tvPrecio.text = confBar?.precio.toString().orEmpty()
        tvTipoComida.text = confBar?.tipoComida.toString().orEmpty()
        Glide.with(this).load(confBar.imagen.toString()).placeholder(R.drawable.sevilla).into(imgBar)
    }
}