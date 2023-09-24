package com.tripfy.livecity2

import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.tripfy.livecity2.singleton.ConfiguradorCiudad
import com.tripfy.livecity2.singleton.ConfiguradorUsuario
import com.tripfy.livecity2.ui.perfil.PerfilUsuarioFragment

class EditarImagenActivity : AppCompatActivity() {

    private lateinit var imgPerfil : ImageView
    private lateinit var btnElegirImagen: Button
    private lateinit var btnActualizarImagen: Button
    private var imageuri : Uri?= null

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_imagen)

        inicializarVariables()
        listeners()

    }

    private fun inicializarVariables() {
        btnElegirImagen = findViewById(R.id.btnElegirImagen)
        btnActualizarImagen = findViewById(R.id.btnActualizarImagen)
        imgPerfil = findViewById(R.id.imgPerfil)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("espere, por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

    }
    private fun listeners() {
        btnElegirImagen.setOnClickListener {
            //Toast.makeText(this, "elegir imagen", Toast.LENGTH_SHORT).show()
            mostrarDialog()

        }
        btnActualizarImagen.setOnClickListener {
            //Toast.makeText(this, "actualizar imagen", Toast.LENGTH_SHORT).show()
            validarImagen()

        }

    }private fun mostrarDialog(){
        val btnAbrirGaleria:Button
        val btnAbrirCamara:Button
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.imagen_dialog)
        btnAbrirGaleria = dialog.findViewById(R.id.btnGaleria)
        btnAbrirCamara = dialog.findViewById(R.id.btnCamara)

        btnAbrirGaleria.setOnClickListener {
            //Toast.makeText(this, "abrir galeria", Toast.LENGTH_SHORT).show()
            abrirGaleria()
            dialog.dismiss()
        }
        btnAbrirCamara.setOnClickListener {
            //Toast.makeText(this, "abrir camara", Toast.LENGTH_SHORT).show()
            abrirCamara()
            dialog.dismiss()
        }
        dialog.show()



    }

    private fun abrirGaleria(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        galeriaActivityResultLauncher.launch(intent)
    }

    private val galeriaActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{ resultado->
            if(resultado.resultCode== RESULT_OK){
                val data = resultado.data
                imageuri = data!!.data
                imgPerfil.setImageURI(imageuri)

            }else{
                Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show()
            }

        }
    )

    private fun abrirCamara(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Título")
        values.put(MediaStore.Images.Media.DESCRIPTION, "descripción")
        imageuri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
        camaraActivityResultLauncher.launch(intent)
    }

    private val camaraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ resultado_camara->
        if(resultado_camara.resultCode == RESULT_OK){
            imgPerfil.setImageURI(imageuri)
        }else{
            Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show()

        }
    }

    private fun validarImagen(){
        if(imageuri== null){
            Toast.makeText(this, "Es necesario una imagen", Toast.LENGTH_SHORT).show()
        }else{
            subirImagen()
        }
    }

    private fun subirImagen() {
        progressDialog.setMessage("Actualizando imagen")
        progressDialog.show()
        val rutaImagen = "Perfil_usuario/"+firebaseAuth.uid
        val referenceStorage = FirebaseStorage.getInstance().getReference(rutaImagen)
        referenceStorage.putFile(imageuri!!).addOnSuccessListener { tarea->
            val uriTarea: Task<Uri> = tarea.storage.downloadUrl
            while(!uriTarea.isSuccessful);
            val urlImagen = "${uriTarea.result}"
            actualizarImagen(urlImagen)

        }.addOnFailureListener{e->
            Toast.makeText(this, "No se ha podido subir la imagen debido a: ${e.message}", Toast.LENGTH_SHORT).show()

        }
    }

    private fun actualizarImagen(urlImagen: String) {

        progressDialog.setMessage("actualizando imagen")
        val hashmap : HashMap<String, Any> = HashMap()
        if(imageuri!= null){
            hashmap["imagen"] = urlImagen
        }

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(firebaseAuth.uid!!).updateChildren(hashmap).addOnSuccessListener {
            progressDialog.dismiss()
            val config: ConfiguradorUsuario? = ConfiguradorUsuario.configurador
            config?.imagen = urlImagen
            val intent = Intent(this, PerfilUsuarioFragment::class.java)
            startActivity(intent)

            Toast.makeText(this, "Su imagen ha sido actualizada", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {e->
            Toast.makeText(this, "No se ha actualizado su imagen debido a: ${e.message}", Toast.LENGTH_SHORT).show()

        }


    }


}