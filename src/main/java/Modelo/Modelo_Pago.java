package Modelo;

public class Modelo_Pago {

    private int idPago;
    private int idTurno;
    private double montoTotal;
    private String metodoPago;
    private String estadoPago;
    private String fechaPago;
    private String horaPago;

    public Modelo_Pago() {
    }

    public Modelo_Pago(
            int idPago,
            int idTurno,
            double montoTotal,
            String metodoPago,
            String estadoPago,
            String fechaPago,
            String horaPago) {

        this.idPago = idPago;
        this.idTurno = idTurno;
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
        this.estadoPago = estadoPago;
        this.fechaPago = fechaPago;
        this.horaPago = horaPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getHoraPago() {
        return horaPago;
    }

    public void setHoraPago(String horaPago) {
        this.horaPago = horaPago;
    }
}