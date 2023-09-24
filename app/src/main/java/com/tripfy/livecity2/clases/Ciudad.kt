package com.tripfy.livecity2.clases

class Ciudad {

    private var nombre: String = ""
    private var descripcion: String = ""
    private var imagen: String = ""
    private var key:String = ""


    constructor()

    constructor(nombre: String, descripcion: String, imagen: String){
        this.nombre = nombre
        this.descripcion = descripcion
        this.imagen = imagen
    }

    public fun setKey(key: String){
        this.key =  key

    }
    public fun getKey():String{
        return key
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

    public fun setImagen(imagen: String){
        this.imagen =  imagen

    }
    public fun getImagen():String{
        return imagen
    }





}