package Modelo;

public class Modelo_Cliente {

    private int idCliente;
    private String nombreDueño;
    private String telefono;

    public Modelo_Cliente() {
    }

    public Modelo_Cliente(int idCliente, String nombreDueño, String telefono) {
        this.idCliente = idCliente;
        this.nombreDueño = nombreDueño;
        this.telefono = telefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreDueño() {
        return nombreDueño;
    }

    public void setNombreDueño(String nombreDueño) {
        this.nombreDueño = nombreDueño;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}