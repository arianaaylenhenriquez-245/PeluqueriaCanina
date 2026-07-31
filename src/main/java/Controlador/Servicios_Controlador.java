package Controlador;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Servicios_Controlador {

    private final Conexion conexion = new Conexion();

    // GUARDAR
    public void guardarServicio(
            JTextField txtServicios,
            JTextField txtPrecio) {

        String nombre = txtServicios.getText().trim();
        String precioTexto = txtPrecio.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Complete el nombre y el precio."
            );

            return;
        }

        double precio;

        try {

            precio = Double.parseDouble(
                    precioTexto.replace(",", ".")
            );

            if (precio <= 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "El precio debe ser mayor que cero."
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese un precio válido."
            );

            return;
        }

        String sql = """
                INSERT INTO servicios
                (nombre_servicio, descripcion, precio)
                VALUES (?, ?, ?)
                """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, nombre);
            ps.setString(2, "");
            ps.setDouble(3, precio);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Servicio registrado correctamente."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar el servicio:\n"
                    + e.getMessage()
            );
        }
    }

    // MOSTRAR
    public void mostrarServicios(JTable jtServicios) {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{
                    "ID",
                    "Nombre",
                    "Precio"
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

        String sql = """
                SELECT
                    id_servicio,
                    nombre_servicio,
                    precio
                FROM servicios
                ORDER BY id_servicio DESC
                """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Object[] fila = {
                    rs.getInt("id_servicio"),
                    rs.getString("nombre_servicio"),
                    rs.getDouble("precio")
                };

                modelo.addRow(fila);
            }

            jtServicios.setModel(modelo);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar los servicios:\n"
                    + e.getMessage()
            );
        }
    }

    // SELECCIONAR
    public void seleccionarServicio(
            JTable jtServicios,
            JTextField txtServicios,
            JTextField txtPrecio) {

        int fila = jtServicios.getSelectedRow();

        if (fila < 0) {
            return;
        }

        txtServicios.setText(
                jtServicios.getValueAt(fila, 1).toString()
        );

        txtPrecio.setText(
                jtServicios.getValueAt(fila, 2).toString()
        );
    }

    // MODIFICAR
    public void modificarServicio(
            JTable jtServicios,
            JTextField txtServicios,
            JTextField txtPrecio) {

        int fila = jtServicios.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un servicio de la tabla."
            );

            return;
        }

        String nombre = txtServicios.getText().trim();
        String precioTexto = txtPrecio.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Complete el nombre y el precio."
            );

            return;
        }

        int idServicio = Integer.parseInt(
                jtServicios.getValueAt(fila, 0).toString()
        );

        double precio;

        try {

            precio = Double.parseDouble(
                    precioTexto.replace(",", ".")
            );

            if (precio <= 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "El precio debe ser mayor que cero."
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Ingrese un precio válido."
            );

            return;
        }

        String sql = """
                UPDATE servicios
                SET nombre_servicio = ?,
                    precio = ?
                WHERE id_servicio = ?
                """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, idServicio);

            int resultado = ps.executeUpdate();

            if (resultado > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Servicio modificado correctamente."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al modificar el servicio:\n"
                    + e.getMessage()
            );
        }
    }

    // ELIMINAR
    public void eliminarServicio(JTable jtServicios) {

        int fila = jtServicios.getSelectedRow();

        if (fila < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un servicio de la tabla."
            );

            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Está segura de eliminar este servicio?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        int idServicio = Integer.parseInt(
                jtServicios.getValueAt(fila, 0).toString()
        );

        String sql = """
                DELETE FROM servicios
                WHERE id_servicio = ?
                """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idServicio);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Servicio eliminado correctamente."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo eliminar el servicio.\n"
                    + "Puede tener turnos relacionados.\n\n"
                    + e.getMessage()
            );
        }
    }

    // LIMPIAR
    public void limpiarCampos(
            JTextField txtServicios,
            JTextField txtPrecio,
            JTable jtServicios) {

        txtServicios.setText("");
        txtPrecio.setText("");
        jtServicios.clearSelection();
        txtServicios.requestFocus();
    }
}
