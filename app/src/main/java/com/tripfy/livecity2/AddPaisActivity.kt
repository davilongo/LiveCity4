package com.tripfy.livecity2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPaisActivity : AppCompatActivity() {


    private lateinit var etNombrePais: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var btnAgregarPais: Button
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pais)
        iniciarVariables()
        listeners()
    }

    private fun iniciarVariables(){
        etNombrePais = findViewById(R.id.etNombrePais)
        etDescripcion = findViewById(R.id.etDescripcion)
        btnAgregarPais = findViewById(R.id.btnAddPais)

    }
    private fun listeners(){
        btnAgregarPais.setOnClickListener{
            validarDatos()
        }
    }

    private fun validarDatos() {
        val nombrePais : String = etNombrePais.text.toString()
        val descripcion : String = etDescripcion.text.toString()

        if(nombrePais.isEmpty()){
            Toast.makeText(this, "ingrese un nombre de país", Toast.LENGTH_SHORT)
        }else if(descripcion.isEmpty()){
            Toast.makeText(this, "ingrese una descripción del país", Toast.LENGTH_SHORT)
        }
        else{
            registrarPais(nombrePais, descripcion)
        }
    }

    private fun registrarPais(nombrePais: String, descripcion: String) {
        reference = FirebaseDatabase.getInstance().reference.child("Paises")
        val hashMap = HashMap<String, Any>()

        hashMap["nombre"] = nombrePais
        hashMap["descripcion"] = descripcion


        reference.push().setValue(hashMap).addOnCompleteListener{task2->
            if(task2.isSuccessful){
                val intent = Intent(this, CiudadesActivity::class.java )
                Toast.makeText(this, "Se ha agregado con éxito", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }

        }.addOnFailureListener{e->
            Toast.makeText(this, "hubo un fallo ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }


}