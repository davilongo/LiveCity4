package com.tripfy.livecity2

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask

class AddCiudadActivity : AppCompatActivity() {

    var nombrePais : String = ""

    private lateinit var etNombreCiudad: EditText
    private lateinit var etDescripcionCiudad: EditText
    private lateinit var btnAgregarCiudad: Button
    private lateinit var reference: DatabaseReference
    private lateinit var btnUploadPhoto: Button
    private var imageUri : Uri?= null
    private lateinit var url: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ciudad)
        obtenerNombrePais()
        iniciarVariables()
        listeners()
    }

    private fun obtenerNombrePais() {
            intent= intent
            nombrePais = intent.getStringExtra("pais").toString()
    }

    private fun iniciarVariables(){
        etNombreCiudad = findViewById(R.id.etNombreCiudad)
        etDescripcionCiudad = findViewById(R.id.etDescripcionCiudad)
        btnAgregarCiudad = findViewById(R.id.btnAddCiudad)
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto)

    }
    private fun listeners(){
        btnAgregarCiudad.setOnClickListener{
            validarDatos()
        }
        btnUploadPhoto.setOnClickListener{
            abrirGaleria()
        }
    }

    private fun abrirGaleria() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galeriaARL.launch(intent)

    }

    private val galeriaARL = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{ resultado->
            if(resultado.resultCode == RESULT_OK){
                val data = resultado.data
                imageUri = data!!.data
                val cargandoImagen = ProgressDialog(this)
                cargandoImagen.setMessage("Cargando imagen, espere")
                cargandoImagen.setCanceledOnTouchOutside(false)
                cargandoImagen.show()

                var carpetaimagenes = FirebaseStorage.getInstance().reference.child("Imagenes de mensajes")
                val reference = FirebaseDatabase.getInstance().reference
                val idMensaje = reference.push().key
                val nombreImagen = carpetaimagenes.child("$idMensaje")

                val uploadTask: StorageTask<*>
                uploadTask = nombreImagen.putFile(imageUri!!)
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task->
                    if(!task.isSuccessful){
                        task.exception?.let{
                            throw it
                        }
                    }
                    return@Continuation nombreImagen.downloadUrl
                }).addOnCompleteListener{ task->
                    if(task.isSuccessful){
                        cargandoImagen.dismiss()
                        val downloadUrl = task.result
                        url = downloadUrl.toString()

                    }

                }

            }else{
                Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show()
            }

        }
    )
    private fun validarDatos() {
        Toast.makeText(this, "llega", Toast.LENGTH_SHORT).show()
        val nombreCiudad : String = etNombreCiudad.text.toString()
        val descripcionCiudad : String = etDescripcionCiudad.text.toString()

        if(nombreCiudad.isEmpty()){
            Toast.makeText(this, "ingrese un nombre de ciudad", Toast.LENGTH_SHORT)
        }else if(descripcionCiudad.isEmpty()){
            Toast.makeText(this, "ingrese una descripción de la ciudad", Toast.LENGTH_SHORT)
        }
        else{
            registrarCiudad(nombreCiudad, descripcionCiudad, url)
        }
    }

    private fun registrarCiudad(nombreCiudad: String, descripcionCiudad: String, url: String) {
        reference = FirebaseDatabase.getInstance().reference.child("Ciudades")
        val hashMap = HashMap<String, Any>()

        hashMap["nombre"] = nombreCiudad
        hashMap["descripcion"] = descripcionCiudad
        hashMap["pais"] = nombrePais
        hashMap["imagen"] = url



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