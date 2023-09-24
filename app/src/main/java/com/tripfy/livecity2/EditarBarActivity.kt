package com.tripfy.livecity2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tripfy.livecity2.singleton.ConfiguradorBar
import com.tripfy.livecity2.singleton.ConfiguradorCiudad

class EditarBarActivity : AppCompatActivity() {

    private lateinit var imgBar: ImageView
    private lateinit var imgEditarImagen: ImageView
    private lateinit var etNombreBar: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etTipoComida: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etCalidad: EditText
    private lateinit var etHoraApertura: EditText
    private lateinit var etHoraCierre: EditText
    private lateinit var etDireccion: EditText
    private lateinit var btnGuardar: Button
    var reference: DatabaseReference?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_bar)
        iniciarvariables()
        listeners()
    }



    private fun iniciarvariables() {
        etNombreBar = findViewById(R.id.etNombreBar)
        etDescripcion = findViewById(R.id.etDescripcion)
        etTipoComida = findViewById(R.id.etTipoComida)
        etPrecio = findViewById(R.id.etPrecio)
        etCalidad = findViewById(R.id.etCalidad)
        etHoraApertura = findViewById(R.id.etHoraCierre)
        etHoraCierre = findViewById(R.id.etHoraCierre)
        etDireccion = findViewById(R.id.etDireccion)
        btnGuardar = findViewById(R.id.btnGuardar)
        imgBar = findViewById(R.id.imgBar)
        //imgEditarImagen = findViewById(R.id.imgEditarImagen)
        val config: ConfiguradorBar? = ConfiguradorBar.configurador
        etNombreBar.setText(config?.nombre.orEmpty())
        etDescripcion.setText(config?.descripcion.orEmpty())
        etTipoComida.setText(config?.tipoComida.orEmpty())
        etPrecio.setText(config?.precio.orEmpty())
        etCalidad.setText(config?.calidad.orEmpty())
        etHoraApertura.setText(config?.horaApertura.orEmpty())
        etHoraCierre.setText(config?.horaCierre.orEmpty())
        etDireccion.setText(config?.direccion.orEmpty())
        Glide.with(this).load(config?.imagen).placeholder(R.drawable.sevilla).into(imgBar)

        reference = FirebaseDatabase.getInstance().reference.child("Bares").child(config?.key!!)

    }

    private fun listeners() {
        btnGuardar.setOnClickListener {
            guardarInfo()
        }
        /*
        imgEditarImagen.setOnClickListener{
            var intent = Intent(this, EditarImagenBarActivity::class.java)
            startActivity(intent)
        }

         */
    }

    private fun guardarInfo() {

        var nombreBar = etNombreBar.text.toString()
        var descripcion = etDescripcion.text.toString()
        var tipoComida = etTipoComida.text.toString()
        var precio = etPrecio.text.toString()
        var calidad = etCalidad.text.toString()
        var horaApertura = etHoraApertura.text.toString()
        var horaCierre = etHoraCierre.text.toString()
        var direccion = etDireccion.text.toString()

        val hashMap = HashMap<String, Any>()

        hashMap["calidad"] = calidad
        hashMap["descripcion"] = descripcion
        hashMap["direccion"] = direccion
        hashMap["horaApertura"] = horaApertura
        hashMap["horaCierre"] = horaCierre
        hashMap["nombre"] = nombreBar
        hashMap["precio"] = precio
        hashMap["tipoComida"] = tipoComida

        reference!!.updateChildren(hashMap).addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(this, "se han actualizado los datos", Toast.LENGTH_SHORT).show()
                val config: ConfiguradorBar? = ConfiguradorBar.configurador
                config?.calidad = calidad
                config?.descripcion = descripcion
                config?.direccion = direccion
                config?.horaCierre = horaCierre
                config?.horaApertura = horaApertura
                config?.nombre = nombreBar
                config?.precio = precio
                config?.tipoComida = tipoComida

                var intent = Intent(this, DetalleBarActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this, "No se han actualizado los datos", Toast.LENGTH_SHORT).show()
            }


        }.addOnFailureListener { e->
            Toast.makeText(this, "error: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

}