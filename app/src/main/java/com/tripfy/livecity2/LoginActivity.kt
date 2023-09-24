package com.tripfy.livecity2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnAcceder: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var tvRegistro: TextView
    private var firebaseUser: FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        iniciarVariables()
        listeners()

    }
    private fun iniciarVariables(){
        etEmail = findViewById(R.id.etUsuario)
        etPass = findViewById(R.id.etPass)
        btnAcceder = findViewById(R.id.btnAcceder)
        auth = FirebaseAuth.getInstance()
        tvRegistro = findViewById(R.id.tvRegistro)
    }
    private fun listeners(){
        btnAcceder.setOnClickListener{
            validarDatos()
        }

        tvRegistro.setOnClickListener{
            val intent = Intent(this, RegistroActivity::class.java)
            Toast.makeText(this, "registro", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

    }

    private fun validarDatos() {
        val email:String = etEmail.text.toString()
        val pass:String =etPass.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this, "ingrese su email", Toast.LENGTH_SHORT)
        }else if(pass.isEmpty()){
            Toast.makeText(this, "ingrese su contraseña", Toast.LENGTH_SHORT)
        }else{
            loginUsuario(email, pass)
        }

    }

    private fun loginUsuario(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    val intent= Intent(this, CiudadesActivity::class.java)
                    Toast.makeText(this, "ha iniciado sesión", Toast.LENGTH_SHORT)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this, "ha ocurrido un error", Toast.LENGTH_SHORT)

                }
            }.addOnFailureListener{e->
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT)
            }

    }
    private fun comprobarSesion(){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if(firebaseUser!=null){
            val intent = Intent(this, CiudadesActivity::class.java)
            Toast.makeText(this, "La sesión está activa", Toast.LENGTH_SHORT)
            startActivity(intent)
            finish()
        }
    }
    override fun onStart() {
        comprobarSesion()
        super.onStart()

    }
}