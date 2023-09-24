package com.tripfy.livecity2.ui.bares

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tripfy.livecity2.R
import com.tripfy.livecity2.adapters.BaresAdapter
import com.tripfy.livecity2.adapters.CiudadesAdapter
import com.tripfy.livecity2.clases.Bar
import com.tripfy.livecity2.clases.Ciudad
import com.tripfy.livecity2.clases.Pais
import com.tripfy.livecity2.databinding.FragmentBaresBinding
import com.tripfy.livecity2.databinding.FragmentSlideshowBinding
import com.tripfy.livecity2.singleton.ConfiguradorCiudad
import com.tripfy.livecity2.ui.slideshow.SlideshowViewModel

class BaresFragment : Fragment() {

    private lateinit var progressDialog: ProgressDialog


    private lateinit var baresAdapter: BaresAdapter
    private lateinit var baresLista:List<Bar>
    private lateinit var rvBares: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bares, container, false)

        rvBares = root.findViewById(R.id.rvBares)
        rvBares.setHasFixedSize(true)
        rvBares.layoutManager = LinearLayoutManager(root.context)
        baresLista = ArrayList()

        inicializarVariables()

        obtenerBaresBD()

        return root
    }

    private fun inicializarVariables() {


    }

    private fun obtenerBaresBD() {
        var confCiudad = ConfiguradorCiudad.configurador
        var ciudad = confCiudad?.nombre
        val reference = FirebaseDatabase.getInstance().reference.child("Bares").orderByChild("ciudad").equalTo(ciudad)
        reference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                (baresLista as ArrayList<Bar>).clear()
                for(sh in snapshot.children){
                    val bar = sh.getValue(Bar::class.java)!!
                    (baresLista as ArrayList<Bar>).add(bar)

                }
                baresAdapter = BaresAdapter(baresLista, "Bares")
                rvBares.adapter = baresAdapter


            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }


}