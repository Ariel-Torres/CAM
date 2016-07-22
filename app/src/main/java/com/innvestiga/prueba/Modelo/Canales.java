package com.innvestiga.prueba.Modelo;

/**
 * Created by ariel on 15/07/2016.
 */
public class Canales {

    public Canales(String ID,String NCanal, String Nombre){
        this.ID = ID;
        this.NCanal = NCanal;
        this.Nombre = Nombre;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNCanal() {
        return NCanal;
    }

    public void setNCanal(String NCanal) {
        this.NCanal = NCanal;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    private String ID,NCanal,Nombre;
}
