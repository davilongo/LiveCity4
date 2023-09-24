package com.tripfy.livecity2.singleton

import com.firebase.ui.auth.data.model.User


class ConfiguradorCiudad

private constructor() {
    var key:String? = null
    var nombre: String? = null
    var descripcion: String? = null
    var imagen: String? = null


    fun clean() {
        if (nombre != null) {
            nombre = null
        }
        if (descripcion != null) {
            descripcion = null
        }
        if (imagen != null) {
            imagen = null
        }
    }

    companion object {
        private var miconfigurador: ConfiguradorCiudad? = null
        val configurador: ConfiguradorCiudad?
            get() {
                if (miconfigurador == null) {
                    miconfigurador = ConfiguradorCiudad()
                }
                return miconfigurador
            }
    }
}
