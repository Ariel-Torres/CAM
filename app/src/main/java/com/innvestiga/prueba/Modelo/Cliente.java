package com.innvestiga.prueba.Modelo;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by ariel on 14/07/2016.
 */
public class Cliente {

    private String IDUSUARIO;
    private String USUARIO;
    private String ACTIVO;
    private String ID_PRODUCTO;
    private String NOMBRE_PRODUCTO;
    private String BASE;
    private String LOGO;
    private String CLIENTE;

    public Cliente(String IDUSUARIO, String USUARIO, String ACTIVO,String ID_PRODUCTO,String NOMBRE_PRODUCTO,String BASE,String LOGO,String CLIENTE){
        this.IDUSUARIO          = IDUSUARIO         ;
        this.USUARIO            = USUARIO           ;
        this.ACTIVO             = ACTIVO            ;
        this.ID_PRODUCTO        = ID_PRODUCTO       ;
        this.NOMBRE_PRODUCTO    = NOMBRE_PRODUCTO   ;
        this.BASE               = BASE              ;
        this.LOGO               = LOGO              ;
        this.CLIENTE            = CLIENTE           ;
    }
    public String getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(String CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public String getLOGO() {
        return LOGO;
    }

    public void setLOGO(String LOGO) {
        this.LOGO = LOGO;
    }

    public String getBASE() {
        return BASE;
    }

    public void setBASE(String BASE) {
        this.BASE = BASE;
    }

    public String getNOMBRE_PRODUCTO() {
        return NOMBRE_PRODUCTO;
    }

    public void setNOMBRE_PRODUCTO(String NOMBRE_PRODUCTO) {
        this.NOMBRE_PRODUCTO = NOMBRE_PRODUCTO;
    }

    public String getID_PRODUCTO() {
        return ID_PRODUCTO;
    }

    public void setID_PRODUCTO(String ID_PRODUCTO) {
        this.ID_PRODUCTO = ID_PRODUCTO;
    }

    public String getACTIVO() {
        return ACTIVO;
    }

    public void setACTIVO(String ACTIVO) {
        this.ACTIVO = ACTIVO;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getIDUSUARIO() {
        return IDUSUARIO;
    }

    public void setIDUSUARIO(String IDUSUARIO) {
        this.IDUSUARIO = IDUSUARIO;
    }



}
