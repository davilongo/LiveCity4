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
import com.tripfy.livecity2.singleton.ConfiguradorCiudad

class AddBarActivity : AppCompatActivity() {

    private lateinit var etNombreBar: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etTipoComida: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etCalidad: EditText
    private lateinit var etHoraApertura: EditText
    private lateinit var etHoraCierre: EditText
    private lateinit var etDireccion: EditText
    private lateinit var btnAgregarBar: Button
    private lateinit var btnUploadPhoto: Button
    private lateinit var url: String
    private lateinit var reference: DatabaseReference
    private var imageUri : Uri?= null
    private lateinit var confCiudad: ConfiguradorCiudad
    private var ciudad: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bar)
        iniciarVariables()
        listeners()
    }

    private fun iniciarVariables(){
        etNombreBar = findViewById(R.id.etNombreBar)
        etDescripcion = findViewById(R.id.etDescripcion)
        btnAgregarBar = findViewById(R.id.btnAddBar)
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto)
        etTipoComida = findViewById(R.id.etTipoComida)
        etPrecio = findViewById(R.id.etPrecio)
        etCalidad = findViewById(R.id.etCalidad)
        etHoraApertura = findViewById(R.id.etHoraApertura)
        etHoraCierre = findViewById(R.id.etHoraCierre)
        etDireccion = findViewById(R.id.etDireccion)

    }

    private fun listeners(){
        btnAgregarBar.setOnClickListener{
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
        val nombreBar : String = etNombreBar.text.toString()
        val descripcion : String = etDescripcion.text.toString()
        var tipoComida : String = etTipoComida.text.toString()
        var precio : String = etPrecio.text.toString()
        var calidad : String = etCalidad.text.toString()
        var horaApertura : String = etHoraApertura.text.toString()
        var horaCierre : String = etHoraCierre.text.toString()
        var direccion : String = etDireccion.text.toString()

        if(nombreBar.isEmpty()){
            Toast.makeText(this, "ingrese un nombre del bar o restaurante", Toast.LENGTH_SHORT)
        }else if(descripcion.isEmpty()){
            Toast.makeText(this, "ingrese una descripción del bar o restaurante", Toast.LENGTH_SHORT)
        }
        else{


            registrarBar(nombreBar, descripcion, url, tipoComida, precio, calidad, horaApertura, horaCierre, direccion)
        }
    }


    private fun registrarBar(
        nombreBar: String,
        descripcion: String,
        url: String,
        tipoComida: String,
        precio: String,
        calidad: String,
        horaApertura: String,
        horaCierre: String,
        direccion: String
    ) {
        reference = FirebaseDatabase.getInstance().reference.child("Bares")
        val hashMap = HashMap<String, Any>()

        confCiudad = ConfiguradorCiudad.configurador!!
        ciudad = confCiudad?.nombre.toString()

        hashMap["nombre"] = nombreBar
        hashMap["descripcion"] = descripcion
        hashMap["ciudad"] = ciudad
        hashMap["imagen"] = url
        hashMap["tipoComida"] = tipoComida
        hashMap["precio"] = precio
        hashMap["calidad"] = calidad
        hashMap["horaApertura"] = horaApertura
        hashMap["horaCierre"] = horaCierre
        hashMap["direccion"] = direccion

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