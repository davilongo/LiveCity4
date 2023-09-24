package com.tripfy.livecity2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tripfy.livecity2.adapters.PaisesAdapter
import com.tripfy.livecity2.clases.Pais

class SeleccionarPais : AppCompatActivity() {

    private lateinit var paisesAdapter: PaisesAdapter
    private lateinit var paisesLista:List<Pais>
    private lateinit var rvPaises: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_pais)

        rvPaises = findViewById(R.id.rvPaises)
        rvPaises.setHasFixedSize(true)
        rvPaises.layoutManager = LinearLayoutManager(this)
        paisesLista = ArrayList()
        obtenerPaisesBD()
    }

    private fun obtenerPaisesBD() {

        val reference = FirebaseDatabase.getInstance().reference.child("Paises").orderByChild("nombre")
        reference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                (paisesLista as ArrayList<Pais>).clear()
                    for(sh in snapshot.children){
                        val pais = sh.getValue(Pais::class.java)!!
                            (paisesLista as ArrayList<Pais>).add(pais)

                    }
                    paisesAdapter = PaisesAdapter(paisesLista)
                    rvPaises.adapter = paisesAdapter


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }
}