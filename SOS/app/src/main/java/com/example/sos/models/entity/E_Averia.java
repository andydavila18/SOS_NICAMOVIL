package com.example.sos.models.entity;

public class E_Averia {

    private int ID;
    private String Tipo;
    private String Fecha;
    private String Estado;
    private String EsMecanico;
    private String ID_USUARIO;

    public E_Averia(int ID,String Tipo, String Fecha,String Estado,String EsMecanico,String ID_USUARIO) {

        this.ID = ID;
        this.Tipo= Tipo;
        this.Fecha = Fecha;
        this.Estado = Estado;
        this.EsMecanico = EsMecanico;
        this.ID_USUARIO = ID_USUARIO;
    }

    public int getID() { return ID; }
    public String getTipo() { return Tipo; }
    public  String getFecha(){return Fecha;}
    public  String getEstado(){return Estado;}
    public  String getMecanico(){return EsMecanico;}
    public  String getID_USUARIO(){return ID_USUARIO;}
}
