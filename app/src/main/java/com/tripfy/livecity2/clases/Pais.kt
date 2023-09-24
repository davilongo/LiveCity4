package com.tripfy.livecity2.clases

class Pais {

    private var nombre: String = ""
    private var descripcion: String = ""


    constructor()

    constructor(nombre: String, descripcion: String){
        this.nombre = nombre
        this.descripcion = descripcion
    }

    public fun setNombre(nombre: String){
        this.nombre =  nombre

    }
    public fun getNombre():String{
        return nombre
    }

    public fun setDescripcion(descripcion: String){
        this.descripcion =  descripcion

    }
    public fun getDescripcion():String{
        return descripcion
    }



}