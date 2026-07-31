package Controlador;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Mascota_Controlador {

    Conexion conexion = new Conexion();

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

                String item = rs.getInt("id_cliente")
                        + " - "
                        + rs.getString("nombre_cliente");

                jcbDueño.addItem(item);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al cargar los clientes: "
                    + e.getMessage()
            );
        }
    }

    // GUARDAR
    public void guardarMascota(
            JTextField txtNombreMascota,
            JTextField txtRaza,
            JComboBox<String> jcbTamano,
            JComboBox<String> jcbDueño,
            JTextArea txtObservaciones) {

        if (txtNombreMascota.getText().trim().isEmpty()
                || txtRaza.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Complete el nombre y la raza."
            );

            return;
        }

        if (jcbTamano.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un tamaño."
            );

            return;
        }

        if (jcbDueño.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un dueño."
            );

            return;
        }

        String dueñoSeleccionado
                = jcbDueño.getSelectedItem().toString();

        int idCliente;

        try {

            idCliente = Integer.parseInt(
                    dueñoSeleccionado.split(" - ")[0]
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "El dueño seleccionado no es válido."
            );

            return;
        }

        String sql = """
                INSERT INTO mascotas
                (
                    nombre_mascota,
                    raza,
                    observaciones_mascotas,
                    tamano,
                    id_cliente
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    txtNombreMascota.getText().trim()
            );

            ps.setString(
                    2,
                    txtRaza.getText().trim()
            );

            ps.setString(
                    3,
                    txtObservaciones.getText().trim()
            );

            ps.setString(
                    4,
                    jcbTamano.getSelectedItem().toString()
            );

            ps.setInt(
                    5,
                    idCliente
            );

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Mascota registrada correctamente."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al registrar la mascota: "
                    + e.getMessage()
            );
        }
    }

    // MOSTRAR
    public void mostrarMascotas(JTable tbMasc) {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{
                    "ID",
                    "Mascota",
                    "Raza",
                    "Tamaño",
                    "Dueño",
                    "Observaciones"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        String sql = """
                SELECT
                    m.id_mascota,
                    m.nombre_mascota,
                    m.raza,
                    m.tamano,
                    c.nombre_cliente,
                    m.observaciones_mascotas
                FROM mascotas m
                INNER JOIN clientes c
                    ON m.id_cliente = c.id_cliente
                ORDER BY m.id_mascota DESC
                """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Object[] fila = {
                    rs.getInt("id_mascota"),
                    rs.getString("nombre_mascota"),
                    rs.getString("raza"),
                    rs.getString("tamano"),
                    rs.getString("nombre_cliente"),
                    rs.getString("observaciones_mascotas")
                };

                modelo.addRow(fila);
            }

            tbMasc.setModel(modelo);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al mostrar mascotas: "
                    + e.getMessage()
            );
        }
    }

    // SELECCIONAR
    public void seleccionarMascota(
            JTable tbMasc,
            JTextField txtIdMascota,
            JTextField txtNombreMascota,
            JTextField txtRaza,
            JComboBox<String> jcbTamano,
            JComboBox<String> jcbDueño,
            JTextArea txtObservaciones) {

        int fila = tbMasc.getSelectedRow();

        if (fila < 0) {
            return;
        }

        txtIdMascota.setText(
                tbMasc.getValueAt(fila, 0).toString()
        );

        txtNombreMascota.setText(
                tbMasc.getValueAt(fila, 1).toString()
        );

        txtRaza.setText(
                tbMasc.getValueAt(fila, 2).toString()
        );

        jcbTamano.setSelectedItem(
                tbMasc.getValueAt(fila, 3).toString()
        );

        String nombreDueño
                = tbMasc.getValueAt(fila, 4).toString();

        for (int i = 0;
                i < jcbDueño.getItemCount();
                i++) {

            String elemento = jcbDueño.getItemAt(i);

            if (elemento.endsWith(
                    " - " + nombreDueño)) {

                jcbDueño.setSelectedIndex(i);
                break;
            }
        }

        Object observaciones
                = tbMasc.getValueAt(fila, 5);

        if (observaciones == null) {

            txtObservaciones.setText("");

        } else {

            txtObservaciones.setText(
                    observaciones.toString()
            );
        }
    }

    // MODIFICAR
    public void modificarMascota(
            JTextField txtIdMascota,
            JTextField txtNombreMascota,
            JTextField txtRaza,
            JComboBox<String> jcbTamano,
            JComboBox<String> jcbDueño,
            JTextArea txtObservaciones) {

        if (txtIdMascota.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione una mascota."
            );

            return;
        }

        if (txtNombreMascota.getText().trim().isEmpty()
                || txtRaza.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Complete el nombre y la raza."
            );

            return;
        }

        if (jcbTamano.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un tamaño."
            );

            return;
        }

        if (jcbDueño.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un dueño."
            );

            return;
        }

        int idMascota;
        int idCliente;

        try {

            idMascota = Integer.parseInt(
                    txtIdMascota.getText().trim()
            );

            String dueñoSeleccionado
                    = jcbDueño.getSelectedItem().toString();

            idCliente = Integer.parseInt(
                    dueñoSeleccionado.split(" - ")[0]
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Los datos seleccionados no son válidos."
            );

            return;
        }

        String sql = """
                UPDATE mascotas
                SET
                    nombre_mascota = ?,
                    raza = ?,
                    observaciones_mascotas = ?,
                    tamano = ?,
                    id_cliente = ?
                WHERE id_mascota = ?
                """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    txtNombreMascota.getText().trim()
            );

            ps.setString(
                    2,
                    txtRaza.getText().trim()
            );

            ps.setString(
                    3,
                    txtObservaciones.getText().trim()
            );

            ps.setString(
                    4,
                    jcbTamano.getSelectedItem().toString()
            );

            ps.setInt(
                    5,
                    idCliente
            );

            ps.setInt(
                    6,
                    idMascota
            );

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Mascota modificada correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "No se encontró la mascota."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al modificar la mascota: "
                    + e.getMessage()
            );
        }
    }

    // ELIMINAR
    public void eliminarMascota(
            JTextField txtIdMascota) {

        if (txtIdMascota.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Seleccione una mascota."
            );

            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Está segura de eliminar esta mascota?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        int idMascota;

        try {

            idMascota = Integer.parseInt(
                    txtIdMascota.getText().trim()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "El ID de la mascota no es válido."
            );

            return;
        }

        String sql = """
                DELETE FROM mascotas
                WHERE id_mascota = ?
                """;

        try (
                Connection con = conexion.estableceConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    idMascota
            );

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Mascota eliminada correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "No se encontró la mascota."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo eliminar la mascota.\n"
                    + "Puede tener turnos relacionados.\n\n"
                    + e.getMessage()
            );
        }
    }

    // LIMPIAR
    public void limpiarCampos(
            JTextField txtIdMascota,
            JTextField txtNombreMascota,
            JTextField txtRaza,
            JComboBox<String> jcbTamano,
            JTextArea txtObservaciones) {

        txtIdMascota.setText("");
        txtNombreMascota.setText("");
        txtRaza.setText("");
        txtObservaciones.setText("");

        if (jcbTamano.getItemCount() > 0) {

            jcbTamano.setSelectedIndex(0);
        }

        txtNombreMascota.requestFocus();
    }
}