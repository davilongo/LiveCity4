package com.tripfy.livecity2.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tripfy.livecity2.R
import com.tripfy.livecity2.adapters.BaresAdapter
import com.tripfy.livecity2.clases.Bar
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide

import com.tripfy.livecity2.databinding.FragmentHomeBinding
import com.tripfy.livecity2.singleton.ConfiguradorCiudad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var ciudad: String = ""
    private lateinit var baresAdapter: BaresAdapter
    private lateinit var baresLista:List<Bar>
    private lateinit var tvCiudad:TextView
    private lateinit var tvDescripcion:TextView
    private lateinit var imgCiudad: ImageView
    private lateinit var rvBares:RecyclerView
    private lateinit var confCiudad: ConfiguradorCiudad



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //ciudad = getArguments()?.getString("nombre").toString()
        //Toast.makeText(textView.context, "la ciudad esssssss ${ciudad}", Toast.LENGTH_SHORT).show()
        rvBares = root.findViewById(R.id.rvBares)
        rvBares.setHasFixedSize(true)
        rvBares.layoutManager = LinearLayoutManager(root.context)
        baresLista = ArrayList()
        inicializarVariables(root)
        obtenerBaresBD()
        initUI()
        confCiudad = ConfiguradorCiudad.configurador!!
        ciudad = confCiudad?.nombre.toString()
        var descripcion = confCiudad?.descripcion.toString()
        //Toast.makeText(rvBares.context, "la ciudad es:  ${ciudad}", Toast.LENGTH_SHORT).show()
        tvCiudad.text = ciudad
        tvDescripcion.text = descripcion
        Glide.with(tvDescripcion.context).load(confCiudad.imagen).placeholder(R.drawable.sevilla).into(imgCiudad)

       return root
    }

    private fun initUI() {

    }

    private fun inicializarVariables(root:View) {
        tvCiudad = root.findViewById(R.id.tvCiudad)
        tvDescripcion = root.findViewById(R.id.tvDescripcion)
        imgCiudad = root.findViewById(R.id.imgCiudad)
    }


    private fun obtenerBaresBD() {
        confCiudad = ConfiguradorCiudad.configurador!!
        ciudad = confCiudad?.nombre.toString()

        val reference = FirebaseDatabase.getInstance().reference.child("Bares").orderByChild("ciudad").equalTo(ciudad)
        reference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                (baresLista as ArrayList<Bar>).clear()
                for(sh in snapshot.children){
                    val bar = sh.getValue(Bar::class.java)!!
                    bar.setKey(sh.key.toString())
                    (baresLista as ArrayList<Bar>).add(bar)

                }
                baresAdapter = BaresAdapter(baresLista, "Home")
                rvBares.adapter = baresAdapter
                rvBares.layoutManager = LinearLayoutManager(rvBares.context, LinearLayoutManager.HORIZONTAL, false)
                rvBares.adapter = baresAdapter


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}