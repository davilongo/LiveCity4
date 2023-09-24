package com.tripfy.livecity2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class InicioActivity : AppCompatActivity() {

    private var firebaseUser: FirebaseUser?=null
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
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