package com.tripfy.livecity2.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tripfy.livecity2.EditarImagenActivity
import com.tripfy.livecity2.EditarPerfilActivity
import com.tripfy.livecity2.LoginActivity
import com.tripfy.livecity2.R
import com.tripfy.livecity2.clases.Usuario
import com.tripfy.livecity2.singleton.ConfiguradorCiudad
import com.tripfy.livecity2.singleton.ConfiguradorUsuario


/**
 * A simple [Fragment] subclass.
 * Use the [PerfilUsuarioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilUsuarioFragment : Fragment() {

    private lateinit var editImage : ImageView
    private lateinit var imagenPerfil : ImageView
    private lateinit var tvUsuario : TextView
    private lateinit var tvApellidos : TextView
    private lateinit var tvEmail : TextView
    private lateinit var tvProfesion : TextView
    private lateinit var tvDomicilio : TextView
    private lateinit var tvEdad : TextView
    private lateinit var tvTelefono : TextView
    private lateinit var btnEditProfile : Button
    private lateinit var btnCerrarSesion : Button



    var user: FirebaseUser?=null
    var reference: DatabaseReference?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        var root: View = inflater.inflate(R.layout.fragment_perfil_usuario, container, false)
        inicializarVariables(root)
        obtenerDatos()
        listeners()


        return root
    }

    private fun listeners() {

    }

    private fun inicializarVariables(root: View) {

        imagenPerfil = root.findViewById(R.id.imgPerfil)
        editImage = root.findViewById(R.id.imgEditarImagen)
        tvUsuario = root.findViewById(R.id.tvUsuario)
        tvApellidos = root.findViewById(R.id.tvApellidos)
        tvEmail = root.findViewById(R.id.tvEmail)
        tvProfesion = root.findViewById(R.id.tvProfesion)
        tvDomicilio = root.findViewById(R.id.tvDomicilio)
        tvEdad = root.findViewById(R.id.tvEdad)
        tvTelefono = root.findViewById(R.id.tvTelefono)
        btnEditProfile = root.findViewById(R.id.btneditarPerfil)
        btnCerrarSesion = root.findViewById(R.id.btnCerrarSesion)


        user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(user!!.uid)


    }
    private fun obtenerDatos(){
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    //Datos de Firebase
                    val usuario: Usuario?= snapshot.getValue(Usuario::class.java)
                    val strUsuario = usuario!!.getUsuario()
                    val apellidos = usuario!!.getApellidos()
                    val email = usuario!!.getEmail()
                    val profesion = usuario!!.getProfesion()
                    val domicilio = usuario!!.getDomicilio()
                    val edad = usuario!!.getEdad()
                    val telefono = usuario!!.getTelefono()

                    tvUsuario.text = strUsuario
                    tvApellidos.text = apellidos
                    tvEmail.text = email
                    tvProfesion.text = profesion
                    tvDomicilio.text = domicilio
                    tvEdad.text = edad
                    tvTelefono.text = telefono
                    Glide.with(tvTelefono.context).load(usuario.getImagen()).placeholder(R.drawable.ic_person).into(imagenPerfil)


                    btnEditProfile.setOnClickListener {

                        val config: ConfiguradorUsuario? = ConfiguradorUsuario.configurador
                        config?.usuario = strUsuario
                        config?.email = email
                        config?.apellidos = apellidos
                        config?.domicilio = domicilio
                        config?.edad = edad
                        config?.imagen = usuario.getImagen()
                        config?.profesion = profesion


                        val intent = Intent(btnEditProfile.context, EditarPerfilActivity::class.java)
                        startActivity(intent)

                    }

                    editImage.setOnClickListener{
                        val intent = Intent(editImage.context, EditarImagenActivity::class.java)
                        //Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT)
                        startActivity(intent)

                    }
                    btnCerrarSesion.setOnClickListener {
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(btnCerrarSesion.context, LoginActivity::class.java)
                        //Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT)
                        startActivity(intent)

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}