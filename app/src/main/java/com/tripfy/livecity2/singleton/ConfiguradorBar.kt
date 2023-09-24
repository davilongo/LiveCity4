package com.tripfy.livecity2.singleton

class ConfiguradorBar

private constructor() {
    var calidad: String? = null
    var ciudad: String? = null
    var descripcion: String? = null
    var direccion: String? = null
    var horaApertura: String? = null
    var horaCierre: String? = null
    var imagen: String? = null
    var nombre: String? = null
    var precio: String? = null
    var tipoComida: String? = null
    var key: String? = null


    fun clean() {
        if (nombre != null) {
            nombre = null
        }
        if (descripcion != null) {
            descripcion = null
        }
        if (calidad != null) {
            calidad = null
        }
        if (direccion != null) {
            direccion = null
        }
        if (horaApertura != null) {
            horaApertura = null
        }
        if (horaCierre != null) {
            horaCierre = null
        }
        if (imagen != null) {
            imagen = null
        }
        if (precio != null) {
            precio = null
        }
        if (tipoComida != null) {
            tipoComida = null
        }
        if (key != null) {
            key = null
        }


    }

    companion object {
        private var miconfigurador: ConfiguradorBar? = null
        val configurador: ConfiguradorBar?
            get() {
                if (miconfigurador == null) {
                    miconfigurador = ConfiguradorBar()
                }
                return miconfigurador
            }
    }
}
