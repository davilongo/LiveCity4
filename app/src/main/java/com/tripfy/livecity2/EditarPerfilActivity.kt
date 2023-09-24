package com.tripfy.livecity2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tripfy.livecity2.singleton.ConfiguradorUsuario

class EditarPerfilActivity : AppCompatActivity() {
    private lateinit var tvUsuario : TextView
    private lateinit var tvEmail : TextView
    private lateinit var imgPerfil : ImageView
    private lateinit var imgEditarImagen : ImageView
    private lateinit var etApellidos : EditText
    private lateinit var etProfesion : EditText
    private lateinit var etDomicilio : EditText
    private lateinit var etEdad : EditText

    private lateinit var btnGuardar : Button


    var user: FirebaseUser?=null
    var reference: DatabaseReference?=null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        inicializarVariables()
        listeners()
    }

    private fun listeners() {
        btnGuardar.setOnClickListener {
            GuardarInfo()
        }
        imgEditarImagen.setOnClickListener{
            var intent = Intent(this, EditarImagenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inicializarVariables() {
        tvUsuario = findViewById(R.id.tvUsuario)
        tvEmail = findViewById(R.id.tvEmail)
        imgPerfil = findViewById(R.id.imgPerfil)
        imgEditarImagen = findViewById(R.id.imgEditarImagen)
        etApellidos = findViewById(R.id.etApellidos)
        etProfesion = findViewById(R.id.etProfesion)
        etDomicilio = findViewById(R.id.etDomicilio)
        etEdad = findViewById(R.id.etEdad)

        btnGuardar = findViewById(R.id.btnGuardar)
        user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(user!!.uid)



        val config: ConfiguradorUsuario? = ConfiguradorUsuario.configurador

        tvUsuario.text = config?.usuario
        tvEmail.text = config?.email
        etApellidos.setText(config?.apellidos.orEmpty())
        etProfesion.setText(config?.profesion.orEmpty())
        etDomicilio.setText(config?.domicilio.orEmpty())
        etEdad.setText(config?.edad.orEmpty())
        Glide.with(this).load(config?.imagen).placeholder(R.drawable.sevilla).into(imgPerfil)


    }

    private fun GuardarInfo() {

        var apellidos = etApellidos.text.toString()
        var profesion = etProfesion.text.toString()
        var domicilio = etDomicilio.text.toString()
        var edad = etEdad.text.toString()

        val hashMap = HashMap<String, Any>()

        hashMap["apellidos"] = apellidos
        hashMap["profesion"] = profesion
        hashMap["dmicilio"] = domicilio
        hashMap["edad"] = edad

        reference!!.updateChildren(hashMap).addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(this, "se han actualizado los datos", Toast.LENGTH_SHORT).show()
                finish()

            }else{
                Toast.makeText(this, "No se han actualizado los datos", Toast.LENGTH_SHORT).show()
            }


        }.addOnFailureListener { e->
            Toast.makeText(this, "error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


}