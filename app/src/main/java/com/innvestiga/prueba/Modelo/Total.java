package com.innvestiga.prueba.Modelo;

/**
 * Created by ariel on 22/07/2016.
 */
public class Total {

    public  Total(String Total, String Año, String visitas){
        this.Total= Total;
        this.año = Año;
        this.visitas =  visitas;
    }
    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getVisitas() {
        return visitas;
    }

    public void setVisitas(String visitas) {
        this.visitas = visitas;
    }

    String Total;
    String año;
    String visitas;
}
