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
import com.tripfy.livecity2.singleton.ConfiguradorCiudad

class EditarCiudadActivity : AppCompatActivity() {

    private lateinit var imgCiudad : ImageView
    private lateinit var etDescripcionCiudad : EditText
    private lateinit var btnGuardar : Button

    var reference: DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_ciudad)

        inicializarVariables()
        listeners()
    }

    private fun inicializarVariables() {
        imgCiudad = findViewById(R.id.imgCiudad)
        etDescripcionCiudad = findViewById(R.id.etDescripcionCiudad)
        btnGuardar = findViewById(R.id.btnGuardar)
        val config: ConfiguradorCiudad? = ConfiguradorCiudad.configurador

        reference = FirebaseDatabase.getInstance().reference.child("Ciudades").child(config?.key!!)

        Glide.with(this).load(config?.imagen).placeholder(R.drawable.sevilla).into(imgCiudad)
        etDescripcionCiudad.setText(config?.descripcion)

    }
    private fun listeners() {
        btnGuardar.setOnClickListener {
            actualizarInfo()
        }
    }
    private fun actualizarInfo(){
        var descripcion = etDescripcionCiudad.text.toString()

        val hashMap = HashMap<String, Any>()
        hashMap["descripcion"] = descripcion

        reference!!.updateChildren(hashMap).addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(this, "se han actualizado los datos", Toast.LENGTH_SHORT).show()
                val config: ConfiguradorCiudad? = ConfiguradorCiudad.configurador
                config?.descripcion = descripcion
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "No se han actualizado los datos", Toast.LENGTH_SHORT).show()
            }


        }.addOnFailureListener { e->
            Toast.makeText(this, "error: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }


}
