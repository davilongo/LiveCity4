package com.tripfy.livecity2.clases

class Bar {

    private var nombre: String = ""
    private var descripcion: String = ""
    private var imagen: String = ""
    private var tipoComida: String =""
    private var precio: String = ""
    private var calidad: String = ""
    private var horaApertura = ""
    private var horaCierre = ""
    private var direccion = ""
    private var ciudad = ""
    private var key = ""

    constructor()

    constructor(nombre: String, descripcion: String, imagen: String, tipoComida: String, precio: String, calidad: String, horaCierre: String, horaApertura: String, direccion:String, ciudad: String){
        this.nombre = nombre
        this.descripcion = descripcion
        this.imagen = imagen
        this.tipoComida = tipoComida
        this.precio = precio
        this.calidad = calidad
        this.horaApertura = horaApertura
        this.horaCierre = horaCierre
        this.direccion = direccion
        this.ciudad = ciudad
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

    public fun setTipoComida(tipoComida: String){
        this.tipoComida =  tipoComida

    }
    public fun getTipoComida():String{
        return tipoComida
    }

    public fun setPrecio(precio: String){
        this.precio =  precio

    }
    public fun getPrecio():String{
        return precio
    }
    public fun setCalidad(calidad: String){
        this.calidad =  calidad

    }
    public fun getCalidad():String{
        return calidad
    }

    public fun setHoraApertura(horaApertura: String){
        this.horaApertura =  horaApertura

    }
    public fun getHoraApertura():String{
        return horaApertura
    }
    public fun setHoraCierre(horaCierre: String){
        this.horaCierre =  horaCierre

    }
    public fun getHoraCierre():String{
        return horaCierre
    }
    public fun setDireccion(imagen: String){
        this.direccion =  direccion

    }
    public fun getDireccion():String{
        return direccion
    }

    public fun setCiudad(ciudad: String){
        this.ciudad =  ciudad

    }
    public fun getCiudad():String{
        return ciudad
    }


}