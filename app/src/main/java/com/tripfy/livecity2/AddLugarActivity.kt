package com.tripfy.livecity2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddLugarActivity : AppCompatActivity() {

    private lateinit var etNombreLugar: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var btnAgregarLugar: Button
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lugar)
        iniciarVariables()
        listeners()
    }

    private fun iniciarVariables(){
        etNombreLugar = findViewById(R.id.etNombreLugar)
        etDescripcion = findViewById(R.id.etDescripcion)
        btnAgregarLugar = findViewById(R.id.btnAddLugar)

    }
    private fun listeners(){
        btnAgregarLugar.setOnClickListener{
            validarDatos()
        }
    }

    private fun validarDatos() {
        val nombreLugar : String = etNombreLugar.text.toString()
        val descripcion : String = etDescripcion.text.toString()

        if(nombreLugar.isEmpty()){
            Toast.makeText(this, "ingrese el nombre del lugar", Toast.LENGTH_SHORT)
        }else if(descripcion.isEmpty()){
            Toast.makeText(this, "ingrese una descripción del lugar", Toast.LENGTH_SHORT)
        }
        else{
            registrarLugar(nombreLugar, descripcion)
        }
    }

    private fun registrarLugar(nombreLugar: String, descripcion: String) {
        reference = FirebaseDatabase.getInstance().reference.child("Lugares")
        val hashMap = HashMap<String, Any>()

        hashMap["nombre"] = nombreLugar
        hashMap["descripcion"] = descripcion


        reference.push().setValue(hashMap).addOnCompleteListener{task2->
            if(task2.isSuccessful){
                val intent = Intent(this, MainActivity::class.java )
                Toast.makeText(this, "Se ha agregado con éxito", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }

        }.addOnFailureListener{e->
            Toast.makeText(this, "hubo un fallo ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }
}