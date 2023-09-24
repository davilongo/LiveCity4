package com.tripfy.livecity2.singleton

class ConfiguradorUsuario

    private constructor() {

        var usuario: String? = null
        var email: String? = null
        var apellidos: String? = null
        var domicilio: String? = null
        var edad: String? = null
        var imagen: String? = null
        var profesion: String? = null



        fun clean() {
            if (apellidos != null) {
                apellidos = null
            }
            if (domicilio != null) {
                domicilio = null
            }
            if (edad != null) {
                edad = null
            }
            if (imagen != null) {
                profesion = null
            }


        }

        companion object {
            private var miconfigurador: ConfiguradorUsuario? = null
            val configurador: ConfiguradorUsuario?
                get() {
                    if (miconfigurador == null) {
                        miconfigurador = ConfiguradorUsuario()
                    }
                    return miconfigurador
                }
        }
    }
