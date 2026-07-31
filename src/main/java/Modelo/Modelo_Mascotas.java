package Modelo;

public class Modelo_Mascotas {

    private int idMascota;
    private String nombreMascota;
    private String raza;
    private String tamano;
    private String observaciones;
    private int idCliente;

    public Modelo_Mascotas() {
    }

    public Modelo_Mascotas(int idMascota, String nombreMascota, String raza,
            String tamano, String observaciones, int idCliente) {

        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.raza = raza;
        this.tamano = tamano;
        this.observaciones = observaciones;
        this.idCliente = idCliente;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}