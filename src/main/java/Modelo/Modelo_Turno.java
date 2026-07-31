package Modelo;

public class Modelo_Turno {

    private int fecha;
    private int hora;
    private String estadoTurno;
    private String observacionTurno;
    private String servicioTurno;

    public Modelo_Turno() {
    }

    public Modelo_Turno(int fecha, int hora, String estadoTurno, String observacionTurno, String servicioTurno) {
        this.fecha = fecha;
        this.hora = hora;
        this.estadoTurno = estadoTurno;
        this.observacionTurno = observacionTurno;
        this.servicioTurno = servicioTurno;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public String getEstadoTurno() {
        return estadoTurno;
    }

    public void setEstadoTurno(String estadoTurno) {
        this.estadoTurno = estadoTurno;
    }

    public String getObservacionTurno() {
        return observacionTurno;
    }

    public void setObservacionTurno(String observacionTurno) {
        this.observacionTurno = observacionTurno;
    }

    public String getServicioTurno() {
        return servicioTurno;
    }

    public void setServicioTurno(String servicioTurno) {
        this.servicioTurno = servicioTurno;
    }

}
