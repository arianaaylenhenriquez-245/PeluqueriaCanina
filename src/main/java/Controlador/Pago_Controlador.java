package Controlador;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Pago_Controlador {

    private final Conexion conexion = new Conexion();

    // CARGAR TURNOS
   
    public void cargarTurnos(JComboBox<String> jcbTurno) {

        jcbTurno.removeAllItems();

        String sql = """
                     SELECT
                         t.id_turno,
                         m.nombre_mascota,
                         s.nombre_servicio
                     FROM turnos t
                     INNER JOIN mascotas m
                         ON t.id_mascota = m.id_mascota
                     INNER JOIN servicios s
                         ON t.id_servicio = s.id_servicio
                     ORDER BY t.id_turno DESC
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                String turno = rs.getInt("id_turno")
                        + " - "
                        + rs.getString("nombre_mascota")
                        + " - "
                        + rs.getString("nombre_servicio");

                jcbTurno.addItem(turno);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar los turnos:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
    // CARGAR DUEÑOS
   
    public void cargarDueños(JComboBox<String> jcbDueño) {

        jcbDueño.removeAllItems();

        String sql = """
                     SELECT id_cliente, nombre_cliente
                     FROM clientes
                     ORDER BY nombre_cliente
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                String dueño = rs.getInt("id_cliente")
                        + " - "
                        + rs.getString("nombre_cliente");

                jcbDueño.addItem(dueño);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar los dueños:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
    // SELECCIONAR DUEÑO SEGÚN EL TURNO
   
    public void seleccionarDueñoDelTurno(
            JComboBox<String> jcbTurno,
            JComboBox<String> jcbDueño) {

        if (jcbTurno.getSelectedItem() == null) {
            return;
        }

        int idTurno;

        try {

            idTurno = obtenerIdCombo(jcbTurno);

        } catch (NumberFormatException e) {
            return;
        }

        String sql = """
                     SELECT c.id_cliente
                     FROM turnos t
                     INNER JOIN mascotas m
                         ON t.id_mascota = m.id_mascota
                     INNER JOIN clientes c
                         ON m.id_cliente = c.id_cliente
                     WHERE t.id_turno = ?
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idTurno);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int idCliente = rs.getInt("id_cliente");
                    seleccionarComboPorId(jcbDueño, idCliente);
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al obtener el dueño del turno:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

  
    // CARGAR MÉTODOS DE PAGO

    public void cargarMetodosPago(
            JComboBox<String> jcbMetodoPago) {

        jcbMetodoPago.removeAllItems();

        jcbMetodoPago.addItem("Efectivo");
        jcbMetodoPago.addItem("Transferencia");
        jcbMetodoPago.addItem("Tarjeta de Débito");
        jcbMetodoPago.addItem("Tarjeta de Crédito");
        jcbMetodoPago.addItem("Mercado Pago");
    }


    // CARGAR ESTADOS DE PAGO

    public void cargarEstadosPago(
            JComboBox<String> jcbEstadoPago) {

        jcbEstadoPago.removeAllItems();

        jcbEstadoPago.addItem("Pendiente");
        jcbEstadoPago.addItem("Pagado");
        jcbEstadoPago.addItem("Anulado");
    }

    // GUARDAR PAGO

    public void guardarPago(
            JComboBox<String> jcbTurno,
            JTextField txtTotal,
            JComboBox<String> jcbMetodoPago,
            JComboBox<String> jcbEstadoPago,
            JTextField txtFecha,
            JTextField txtHora) {

        if (!validarCampos(
                jcbTurno,
                txtTotal,
                jcbMetodoPago,
                jcbEstadoPago,
                txtFecha,
                txtHora)) {

            return;
        }

        int idTurno;
        double montoTotal;

        try {

            idTurno = obtenerIdCombo(jcbTurno);

            montoTotal = Double.parseDouble(
                    txtTotal.getText()
                            .trim()
                            .replace(",", ".")
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "El monto total ingresado no es válido.",
                    "Datos inválidos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql = """
                     INSERT INTO pagos
                     (
                         id_turno,
                         monto_total,
                         metodo_pago,
                         estado_pago,
                         fecha_pago,
                         hora_pago
                     )
                     VALUES (?, ?, ?, ?, ?, ?)
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idTurno);
            ps.setDouble(2, montoTotal);

            ps.setString(
                    3,
                    jcbMetodoPago.getSelectedItem().toString()
            );

            ps.setString(
                    4,
                    jcbEstadoPago.getSelectedItem().toString()
            );

            ps.setString(5, txtFecha.getText().trim());
            ps.setString(6, txtHora.getText().trim());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Pago registrado correctamente."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al registrar el pago:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

  
    // MOSTRAR PAGOS
 
    public void mostrarPagos(JTable jtPagos) {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{
                    "ID Pago",
                    "ID Turno",
                    "Dueño",
                    "Mascota",
                    "Servicio",
                    "Total",
                    "Método",
                    "Estado",
                    "Fecha",
                    "Hora"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int fila,
                    int columna) {

                return false;
            }
        };

        jtPagos.setModel(modelo);

        String sql = """
                     SELECT
                         p.id_pago,
                         p.id_turno,
                         c.nombre_cliente,
                         m.nombre_mascota,
                         s.nombre_servicio,
                         p.monto_total,
                         p.metodo_pago,
                         p.estado_pago,
                         p.fecha_pago,
                         p.hora_pago
                     FROM pagos p
                     INNER JOIN turnos t
                         ON p.id_turno = t.id_turno
                     INNER JOIN mascotas m
                         ON t.id_mascota = m.id_mascota
                     INNER JOIN clientes c
                         ON m.id_cliente = c.id_cliente
                     INNER JOIN servicios s
                         ON t.id_servicio = s.id_servicio
                     ORDER BY p.id_pago DESC
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                modelo.addRow(new Object[]{

                    rs.getInt("id_pago"),
                    rs.getInt("id_turno"),
                    rs.getString("nombre_cliente"),
                    rs.getString("nombre_mascota"),
                    rs.getString("nombre_servicio"),
                    rs.getDouble("monto_total"),
                    rs.getString("metodo_pago"),
                    rs.getString("estado_pago"),
                    rs.getDate("fecha_pago"),
                    rs.getTime("hora_pago")
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar los pagos:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

  
    // SELECCIONAR PAGO
   
    public void seleccionarPago(
            JTable jtPagos,
            JComboBox<String> jcbTurno,
            JComboBox<String> jcbDueño,
            JTextField txtTotal,
            JComboBox<String> jcbMetodoPago,
            JComboBox<String> jcbEstadoPago,
            JTextField txtFecha,
            JTextField txtHora) {

        int fila = jtPagos.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un pago de la tabla."
            );

            return;
        }

        int idTurno = Integer.parseInt(
                jtPagos.getValueAt(fila, 1).toString()
        );

        String nombreDueño = valorTabla(jtPagos, fila, 2);
        String monto = valorTabla(jtPagos, fila, 5);
        String metodoPago = valorTabla(jtPagos, fila, 6);
        String estadoPago = valorTabla(jtPagos, fila, 7);
        String fecha = valorTabla(jtPagos, fila, 8);
        String hora = valorTabla(jtPagos, fila, 9);

        seleccionarComboPorId(jcbTurno, idTurno);
        seleccionarComboPorNombre(jcbDueño, nombreDueño);

        txtTotal.setText(monto);
        jcbMetodoPago.setSelectedItem(metodoPago);
        jcbEstadoPago.setSelectedItem(estadoPago);
        txtFecha.setText(fecha);
        txtHora.setText(hora);
    }

    // MODIFICAR PAGO
   
    public void modificarPago(
            JTable jtPagos,
            JComboBox<String> jcbTurno,
            JTextField txtTotal,
            JComboBox<String> jcbMetodoPago,
            JComboBox<String> jcbEstadoPago,
            JTextField txtFecha,
            JTextField txtHora) {

        int fila = jtPagos.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione el pago que desea modificar."
            );

            return;
        }

        if (!validarCampos(
                jcbTurno,
                txtTotal,
                jcbMetodoPago,
                jcbEstadoPago,
                txtFecha,
                txtHora)) {

            return;
        }

        int idPago;
        int idTurno;
        double montoTotal;

        try {

            idPago = Integer.parseInt(
                    jtPagos.getValueAt(fila, 0).toString()
            );

            idTurno = obtenerIdCombo(jcbTurno);

            montoTotal = Double.parseDouble(
                    txtTotal.getText()
                            .trim()
                            .replace(",", ".")
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Los datos numéricos no son válidos."
            );

            return;
        }

        String sql = """
                     UPDATE pagos
                     SET
                         id_turno = ?,
                         monto_total = ?,
                         metodo_pago = ?,
                         estado_pago = ?,
                         fecha_pago = ?,
                         hora_pago = ?
                     WHERE id_pago = ?
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idTurno);
            ps.setDouble(2, montoTotal);

            ps.setString(
                    3,
                    jcbMetodoPago.getSelectedItem().toString()
            );

            ps.setString(
                    4,
                    jcbEstadoPago.getSelectedItem().toString()
            );

            ps.setString(5, txtFecha.getText().trim());
            ps.setString(6, txtHora.getText().trim());
            ps.setInt(7, idPago);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Pago modificado correctamente."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al modificar el pago:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
    // ELIMINAR PAGO
  
    public void eliminarPago(JTable jtPagos) {

        int fila = jtPagos.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione el pago que desea eliminar."
            );

            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de eliminar este pago?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        int idPago = Integer.parseInt(
                jtPagos.getValueAt(fila, 0).toString()
        );

        String sql = """
                     DELETE FROM pagos
                     WHERE id_pago = ?
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idPago);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Pago eliminado correctamente."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al eliminar el pago:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // LIMPIAR CAMPOS

    public void limpiarCampos(
            JTable jtPagos,
            JComboBox<String> jcbTurno,
            JComboBox<String> jcbDueño,
            JTextField txtTotal,
            JComboBox<String> jcbMetodoPago,
            JComboBox<String> jcbEstadoPago,
            JTextField txtFecha,
            JTextField txtHora) {

        jtPagos.clearSelection();

        txtTotal.setText("");
        txtFecha.setText("");
        txtHora.setText("");

        if (jcbTurno.getItemCount() > 0) {
            jcbTurno.setSelectedIndex(0);
        }

        if (jcbDueño.getItemCount() > 0) {
            jcbDueño.setSelectedIndex(0);
        }

        if (jcbMetodoPago.getItemCount() > 0) {
            jcbMetodoPago.setSelectedIndex(0);
        }

        if (jcbEstadoPago.getItemCount() > 0) {
            jcbEstadoPago.setSelectedIndex(0);
        }

        txtTotal.requestFocus();
    }


    // VALIDACIONES

    private boolean validarCampos(
            JComboBox<String> jcbTurno,
            JTextField txtTotal,
            JComboBox<String> jcbMetodoPago,
            JComboBox<String> jcbEstadoPago,
            JTextField txtFecha,
            JTextField txtHora) {

        if (jcbTurno.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un turno."
            );

            return false;
        }

        if (txtTotal.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese el monto total."
            );

            txtTotal.requestFocus();
            return false;
        }

        try {

            double total = Double.parseDouble(
                    txtTotal.getText()
                            .trim()
                            .replace(",", ".")
            );

            if (total <= 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "El monto debe ser mayor que cero."
                );

                return false;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese un monto válido.\nEjemplo: 15000.50"
            );

            txtTotal.requestFocus();
            return false;
        }

        if (jcbMetodoPago.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un método de pago."
            );

            return false;
        }

        if (jcbEstadoPago.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un estado de pago."
            );

            return false;
        }

        if (!txtFecha.getText().trim().matches(
                "\\d{4}-\\d{2}-\\d{2}")) {

            JOptionPane.showMessageDialog(
                    null,
                    "La fecha debe tener el formato AAAA-MM-DD.\n"
                    + "Ejemplo: 2026-07-31"
            );

            txtFecha.requestFocus();
            return false;
        }

        if (!txtHora.getText().trim().matches(
                "([01]\\d|2[0-3]):[0-5]\\d(:[0-5]\\d)?")) {

            JOptionPane.showMessageDialog(
                    null,
                    "La hora debe tener el formato HH:MM.\n"
                    + "Ejemplo: 14:30"
            );

            txtHora.requestFocus();
            return false;
        }

        return true;
    }

  
    // OBTENER ID DE UN COMBO

    private int obtenerIdCombo(JComboBox<String> combo) {

        String elemento = combo.getSelectedItem().toString();

        return Integer.parseInt(
                elemento.split(" - ")[0].trim()
        );
    }

  
    // SELECCIONAR COMBO POR ID
  
    private void seleccionarComboPorId(
            JComboBox<String> combo,
            int idBuscado) {

        for (int i = 0; i < combo.getItemCount(); i++) {

            String elemento = combo.getItemAt(i);
            String primerValor = elemento.split(" - ")[0];

            try {

                int idElemento = Integer.parseInt(
                        primerValor.trim()
                );

                if (idElemento == idBuscado) {

                    combo.setSelectedIndex(i);
                    return;
                }

            } catch (NumberFormatException e) {
                // Continúa buscando.
            }
        }
    }


    // SELECCIONAR COMBO POR NOMBRE

    private void seleccionarComboPorNombre(
            JComboBox<String> combo,
            String nombreBuscado) {

        for (int i = 0; i < combo.getItemCount(); i++) {

            String elemento = combo.getItemAt(i);
            String[] partes = elemento.split(" - ", 2);

            if (partes.length == 2
                    && partes[1].trim().equalsIgnoreCase(
                            nombreBuscado.trim())) {

                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    // OBTENER VALOR SEGURO DE LA TABLA

    private String valorTabla(
            JTable tabla,
            int fila,
            int columna) {

        Object valor = tabla.getValueAt(fila, columna);

        return valor == null ? "" : valor.toString();
    }
}
