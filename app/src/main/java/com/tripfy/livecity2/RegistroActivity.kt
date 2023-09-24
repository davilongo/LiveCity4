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

class RegistroActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etEmail: EditText
    private lateinit var etpass: EditText
    private lateinit var etRePass: EditText
    private lateinit var btnRegistro: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        iniciarVariables()
        listeners()

    }

    private fun iniciarVariables(){
        etUsuario = findViewById(R.id.etUsuario)
        etEmail = findViewById(R.id.etEmail)
        etpass = findViewById(R.id.etpass)
        etRePass = findViewById(R.id.etRePass)
        btnRegistro = findViewById(R.id.btnRegistrar)
        auth = FirebaseAuth.getInstance()
    }

    private fun listeners(){
        btnRegistro.setOnClickListener{
            validarDatos()
        }
    }

    private fun validarDatos() {
        val nombreusuario : String = etUsuario.text.toString()
        val email : String = etEmail.text.toString()
        val pass : String = etpass.text.toString()
        val repass : String = etRePass.text.toString()

        if(nombreusuario.isEmpty()){
            Toast.makeText(this, "ingrese un nombre de usuario", Toast.LENGTH_SHORT)
        }else if(email.isEmpty()){
            Toast.makeText(this, "ingrese un email", Toast.LENGTH_SHORT)
        }else if(pass.isEmpty()){
            Toast.makeText(this, "ingrese una contraseña", Toast.LENGTH_SHORT)
        }else if(email.isEmpty()){
            Toast.makeText(this, "por favor, repita su cpntraseña", Toast.LENGTH_SHORT)
        }else if(!pass.equals(repass)){
            Toast.makeText(this, "las contraseñas no coinciden", Toast.LENGTH_SHORT)
        }
        else{
            registrarUsuario(email, pass)
        }
    }

    private fun registrarUsuario(email: String, pass: String) {

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this){ task->

                //Toast.makeText(this, "entra", Toast.LENGTH_SHORT).show()
                if(task.isSuccessful){






                    var uid : String = "pepe"



                    uid = auth.currentUser!!.uid
                    //Toast.makeText(this, "exitoso $uid", Toast.LENGTH_SHORT).show()

                    reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                    val hashMap = HashMap<String, Any>()
                    val usuario:String = etUsuario.text.toString()
                    hashMap["uid"] = uid
                    hashMap["usuario"] = usuario
                    hashMap["email"] = email
                    hashMap["imagen"] = ""
                    hashMap["buscar"] = usuario.lowercase()
                    hashMap["nombres"] = ""
                    hashMap["apellidos"] = ""
                    hashMap["edad"] = ""
                    hashMap["profesion"] = ""
                    hashMap["domicilio"] = ""
                    hashMap["estado"] = "offline"








                    reference.updateChildren(hashMap).addOnCompleteListener{task2->
                        if(task2.isSuccessful){
                            val intent = Intent(this, MainActivity::class.java )
                            Toast.makeText(this, "Se ha registrado con éxito", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        }

                    }.addOnFailureListener{e->
                        Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(this, "Ha ocurrido un error desconocido", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{e->
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()

            }

    }

}