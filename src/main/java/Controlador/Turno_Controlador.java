
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
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

public class Turno_Controlador {

    private final Conexion conexion = new Conexion();

    // CARGAR MASCOTAS
  
    public void cargarMascotas(JComboBox<String> jcbMascota) {

        jcbMascota.removeAllItems();

        String sql = """
                     SELECT id_mascota, nombre_mascota
                     FROM mascotas
                     ORDER BY nombre_mascota
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                String mascota = rs.getInt("id_mascota")
                        + " - "
                        + rs.getString("nombre_mascota");

                jcbMascota.addItem(mascota);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar mascotas:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // CARGAR SERVICIOS

    public void cargarServicios(JComboBox<String> jcbServicio) {

        jcbServicio.removeAllItems();

        String sql = """
                     SELECT id_servicio, nombre_servicio
                     FROM servicios
                     ORDER BY nombre_servicio
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                String servicio = rs.getInt("id_servicio")
                        + " - "
                        + rs.getString("nombre_servicio");

                jcbServicio.addItem(servicio);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar servicios:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
    // CARGAR ESTADOS

    public void cargarEstados(JComboBox<String> jcbEstado) {

        jcbEstado.removeAllItems();

        jcbEstado.addItem("Pendiente");
        jcbEstado.addItem("Confirmado");
        jcbEstado.addItem("Finalizado");
        jcbEstado.addItem("Cancelado");
    }

    
    // GUARDAR TURNO
  
    public void guardarTurno(
            JTextField txtFecha,
            JTextField txtHora,
            JComboBox<String> jcbMascota,
            JComboBox<String> jcbServicio,
            JComboBox<String> jcbEstado,
            JTextPane jtaObservaciones) {

        if (!validarCampos(
                txtFecha,
                txtHora,
                jcbMascota,
                jcbServicio,
                jcbEstado)) {

            return;
        }

        int idMascota;
        int idServicio;

        try {

            idMascota = obtenerIdCombo(jcbMascota);
            idServicio = obtenerIdCombo(jcbServicio);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo obtener el ID de la mascota o del servicio.",
                    "Datos inválidos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql = """
                     INSERT INTO turnos
                     (
                         fecha,
                         hora,
                         estado,
                         observaciones,
                         id_mascota,
                         id_servicio
                     )
                     VALUES (?, ?, ?, ?, ?, ?)
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, txtFecha.getText().trim());
            ps.setString(2, txtHora.getText().trim());
            ps.setString(3, jcbEstado.getSelectedItem().toString());
            ps.setString(4, jtaObservaciones.getText().trim());
            ps.setInt(5, idMascota);
            ps.setInt(6, idServicio);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Turno registrado correctamente."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al registrar el turno:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
    // MOSTRAR TURNOS
 
    public void mostrarTurnos(JTable jtTurno) {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{
                    "ID",
                    "Mascota",
                    "Servicio",
                    "Fecha",
                    "Hora",
                    "Estado",
                    "Observaciones"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        jtTurno.setModel(modelo);

        String sql = """
                     SELECT
                         t.id_turno,
                         m.nombre_mascota,
                         s.nombre_servicio,
                         t.fecha,
                         t.hora,
                         t.estado,
                         t.observaciones
                     FROM turnos AS t
                     INNER JOIN mascotas AS m
                         ON t.id_mascota = m.id_mascota
                     INNER JOIN servicios AS s
                         ON t.id_servicio = s.id_servicio
                     ORDER BY t.fecha DESC, t.hora DESC
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Object[] fila = new Object[7];

                fila[0] = rs.getInt("id_turno");
                fila[1] = rs.getString("nombre_mascota");
                fila[2] = rs.getString("nombre_servicio");
                fila[3] = rs.getDate("fecha");
                fila[4] = rs.getTime("hora");
                fila[5] = rs.getString("estado");
                fila[6] = rs.getString("observaciones");

                modelo.addRow(fila);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar turnos:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

  
    // SELECCIONAR TURNO DESDE LA TABLA

    public void seleccionarTurno(
            JTable jtTurno,
            JTextField txtFecha,
            JTextField txtHora,
            JComboBox<String> jcbMascota,
            JComboBox<String> jcbServicio,
            JComboBox<String> jcbEstado,
            JTextPane jtaObservaciones) {

        int filaSeleccionada = jtTurno.getSelectedRow();

        if (filaSeleccionada < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un turno de la tabla."
            );

            return;
        }

        String nombreMascota = valorTabla(jtTurno, filaSeleccionada, 1);
        String nombreServicio = valorTabla(jtTurno, filaSeleccionada, 2);
        String fecha = valorTabla(jtTurno, filaSeleccionada, 3);
        String hora = valorTabla(jtTurno, filaSeleccionada, 4);
        String estado = valorTabla(jtTurno, filaSeleccionada, 5);
        String observaciones = valorTabla(jtTurno, filaSeleccionada, 6);

        txtFecha.setText(fecha);
        txtHora.setText(hora);
        jcbEstado.setSelectedItem(estado);
        jtaObservaciones.setText(observaciones);

        seleccionarComboPorNombre(jcbMascota, nombreMascota);
        seleccionarComboPorNombre(jcbServicio, nombreServicio);
    }


    // MODIFICAR TURNO
 
    public void modificarTurno(
            JTable jtTurno,
            JTextField txtFecha,
            JTextField txtHora,
            JComboBox<String> jcbMascota,
            JComboBox<String> jcbServicio,
            JComboBox<String> jcbEstado,
            JTextPane jtaObservaciones) {

        int filaSeleccionada = jtTurno.getSelectedRow();

        if (filaSeleccionada < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione el turno que desea modificar."
            );

            return;
        }

        if (!validarCampos(
                txtFecha,
                txtHora,
                jcbMascota,
                jcbServicio,
                jcbEstado)) {

            return;
        }

        int idTurno;
        int idMascota;
        int idServicio;

        try {

            idTurno = Integer.parseInt(
                    jtTurno.getValueAt(filaSeleccionada, 0).toString()
            );

            idMascota = obtenerIdCombo(jcbMascota);
            idServicio = obtenerIdCombo(jcbServicio);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se pudieron obtener los identificadores del turno.",
                    "Datos inválidos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql = """
                     UPDATE turnos
                     SET
                         fecha = ?,
                         hora = ?,
                         estado = ?,
                         observaciones = ?,
                         id_mascota = ?,
                         id_servicio = ?
                     WHERE id_turno = ?
                     """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, txtFecha.getText().trim());
            ps.setString(2, txtHora.getText().trim());
            ps.setString(3, jcbEstado.getSelectedItem().toString());
            ps.setString(4, jtaObservaciones.getText().trim());
            ps.setInt(5, idMascota);
            ps.setInt(6, idServicio);
            ps.setInt(7, idTurno);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Turno modificado correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "No se encontró el turno que desea modificar."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al modificar el turno:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ELIMINAR TURNO

    public void eliminarTurno(JTable jtTurno) {

        int filaSeleccionada = jtTurno.getSelectedRow();

        if (filaSeleccionada < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione el turno que desea eliminar."
            );

            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de eliminar este turno?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        int idTurno;

        try {

            idTurno = Integer.parseInt(
                    jtTurno.getValueAt(filaSeleccionada, 0).toString()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "El ID del turno no es válido."
            );

            return;
        }

        String sql = "DELETE FROM turnos WHERE id_turno = ?";

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idTurno);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Turno eliminado correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "No se encontró el turno seleccionado."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo eliminar el turno.\n"
                    + "Compruebe que no tenga un pago relacionado.\n\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // LIMPIAR CAMPOS
   
    public void limpiarCampos(
            JTable jtTurno,
            JTextField txtFecha,
            JTextField txtHora,
            JComboBox<String> jcbMascota,
            JComboBox<String> jcbServicio,
            JComboBox<String> jcbEstado,
            JTextPane jtaObservaciones) {

        jtTurno.clearSelection();

        txtFecha.setText("");
        txtHora.setText("");
        jtaObservaciones.setText("");

        if (jcbMascota.getItemCount() > 0) {
            jcbMascota.setSelectedIndex(0);
        }

        if (jcbServicio.getItemCount() > 0) {
            jcbServicio.setSelectedIndex(0);
        }

        if (jcbEstado.getItemCount() > 0) {
            jcbEstado.setSelectedIndex(0);
        }

        txtFecha.requestFocus();
    }

    // VALIDAR CAMPOS
  
    private boolean validarCampos(
            JTextField txtFecha,
            JTextField txtHora,
            JComboBox<String> jcbMascota,
            JComboBox<String> jcbServicio,
            JComboBox<String> jcbEstado) {

        if (txtFecha.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese la fecha del turno.\nFormato: AAAA-MM-DD"
            );

            txtFecha.requestFocus();
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

        if (txtHora.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese la hora del turno.\nFormato: HH:MM"
            );

            txtHora.requestFocus();
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

        if (jcbMascota.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione una mascota."
            );

            return false;
        }

        if (jcbServicio.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un servicio."
            );

            return false;
        }

        if (jcbEstado.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione el estado del turno."
            );

            return false;
        }

        return true;
    }

    
    // OBTENER ID DEL COMBO
  
    private int obtenerIdCombo(JComboBox<String> combo) {

        String elemento = combo.getSelectedItem().toString();

        return Integer.parseInt(
                elemento.split(" - ")[0].trim()
        );
    }

    
    // SELECCIONAR COMBO MEDIANTE EL NOMBRE
   
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
                break;
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