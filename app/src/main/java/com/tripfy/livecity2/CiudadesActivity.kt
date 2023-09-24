package com.tripfy.livecity2

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tripfy.livecity2.adapters.CiudadesAdapter

import com.tripfy.livecity2.clases.Ciudad
import com.tripfy.livecity2.clases.Pais

class CiudadesActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    //private lateinit var fab: FloatingActionButton

    private lateinit var ciudadesAdapter: CiudadesAdapter
    private lateinit var ciudadesLista:List<Ciudad>
    private lateinit var rvCiudades: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudades)

        rvCiudades = findViewById(R.id.rvCiudades)
        rvCiudades.setHasFixedSize(true)
        rvCiudades.layoutManager = LinearLayoutManager(this)
        ciudadesLista = ArrayList()

        inicializarVariables()
        listeners()
        obtenerCiudadesBD()

    }





    private fun inicializarVariables() {
        progressDialog = ProgressDialog(this)
        //fab = findViewById(R.id.fabCiudades)
    }

    private fun listeners() {
        /*
        fab.setOnClickListener {

            mostrarDialog()

        }

         */
    }
    private fun mostrarDialog() {

        val btnPais: Button
        val btnCiudad: Button
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_elegir_pais_ciudad)
        btnPais = dialog.findViewById(R.id.btnPais)
        btnCiudad = dialog.findViewById(R.id.btnCiudad)

        btnPais.setOnClickListener {
            //Toast.makeText(this, "pais", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AddPaisActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        btnCiudad.setOnClickListener {
            //Toast.makeText(this, "ciudad", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SeleccionarPais::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun obtenerCiudadesBD() {

        val reference = FirebaseDatabase.getInstance().reference.child("Ciudades").orderByChild("nombre")
        reference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                (ciudadesLista as ArrayList<Pais>).clear()
                for(sh in snapshot.children){
                    //Toast.makeText(rvCiudades.context, "la clave es:${sh.key}", Toast.LENGTH_SHORT).show()
                    val ciudad = sh.getValue(Ciudad::class.java)!!
                    ciudad.setKey(sh.key.toString())
                    (ciudadesLista as ArrayList<Ciudad>).add(ciudad)

                }
                ciudadesAdapter = CiudadesAdapter(ciudadesLista)
                rvCiudades.adapter = ciudadesAdapter


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }

}