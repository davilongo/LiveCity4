package com.tripfy.livecity2

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.tripfy.livecity2.databinding.ActivityMainBinding
import com.tripfy.livecity2.singleton.ConfiguradorCiudad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name="CIUDAD")
class MainActivity : AppCompatActivity() {

    var nombreCiudad : String = ""
    lateinit var ciudadDbHelper: SQLiteOpenHelper

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var confCiudad: ConfiguradorCiudad
    private lateinit var imgCiudadPeque: ImageView
    private lateinit var tvCiudadPortada: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //imgCiudadPeque = findViewById(R.id.imgCiudadPeque)

        confCiudad = ConfiguradorCiudad.configurador!!
        //Glide.with(this).load(R.drawable.sevilla).placeholder(R.drawable.sevilla).into(imgCiudadPeque)

        lifecycleScope.launch (Dispatchers.IO){
            obtenerNombreCiudad()
        }





        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            mostrarDialog()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        var hView = navView.getHeaderView(0)
        imgCiudadPeque = hView.findViewById(R.id.imgCiudadPeque)
        tvCiudadPortada = hView.findViewById(R.id.tvCiudadPortada)
        Glide.with(this).load(confCiudad.imagen).placeholder(R.drawable.sevilla).into(imgCiudadPeque)
        tvCiudadPortada.text = confCiudad.nombre
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_queVer, R.id.nav_bares, R.id.nav_dondeDormir, R.id.nav_miPerfil
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        //Toast.makeText(this, "la ciudad es: ${nombreCiudad}", Toast.LENGTH_SHORT).show()


        //val bundle = bundleOf("nombre" to nombreCiudad)
        //navController.navigate(R.id.nav_home, bundle)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private suspend fun obtenerNombreCiudad() {
        intent= intent
        nombreCiudad = intent.getStringExtra("ciudad").toString()
        dataStore.edit{ preferences ->

            preferences[stringPreferencesKey("name")] = nombreCiudad

        }


    }
    private fun mostrarDialog() {

        val btnEditar: Button
        val btnBar: Button
        val btnLugar: Button
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_element)
        btnEditar = dialog.findViewById(R.id.btnEditarInfo)
        btnBar = dialog.findViewById(R.id.btnBar)
        btnLugar = dialog.findViewById(R.id.btnLugarTuristico)

        btnEditar.setOnClickListener {
            //Toast.makeText(this, "pais", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, EditarCiudadActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        btnBar.setOnClickListener {
            //Toast.makeText(this, "ciudad", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AddBarActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        btnLugar.setOnClickListener {
            //Toast.makeText(this, "ciudad", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AddLugarActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()

    }
}