package Controlador;

import Conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Cliente_Controlador {

    Conexion conexion = new Conexion();

    // GUARDAR CLIENTE
    public void guardarCliente(JTextField txtNombre, JTextField txtTelefono) {
        
        if (txtNombre.getText().trim().isEmpty()|| txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos.");
            return;
        }
         
        String sql = "INSERT INTO clientes(nombre_cliente, telefono) VALUES (?, ?)";

        try {   
          PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
          ps.setString(1,txtNombre.getText());
          ps.setString(2,txtTelefono.getText());
          ps.executeUpdate();
          ps.close();
          JOptionPane.showMessageDialog(null, "Cliente guardado correctamente");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

    }

    // MOSTRAR CLIENTES
    public void mostrarClientes(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Telefono");
        tabla.setModel(modelo);
        String sql = "SELECT * FROM clientes";
        try {
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("id_cliente");
                fila[1] = rs.getString("nombre_cliente");
                fila[2] = rs.getString("telefono");
                modelo.addRow(fila);
            }
               rs.close();
               ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar clientes");
        }
    }

    // SELECCIONAR FILA
    public void seleccionarCliente(JTable tabla, JTextField txtId, JTextField txtNombre, JTextField txtTelefono) {
        
        try {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(tabla.getValueAt(fila, 0).toString());
                txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                txtTelefono.setText(tabla.getValueAt(fila, 2).toString());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla");
        }

    }

    // MODIFICAR
    public void modificarCliente(JTextField txtId, JTextField txtNombre, JTextField txtTelefono) {

        if (txtNombre.getText().trim().isEmpty()|| txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos.");
        return;
        }
        
        String sql = "UPDATE clientes SET nombre_cliente=?, telefono=? WHERE id_cliente=?";

        try {
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtTelefono.getText());
            ps.setInt(3, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            ps.close();
    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: En Modifcar cliente" + e.getMessage());
        }
    }

    // ELIMINAR
    public void eliminarCliente(JTextField txtId) {

         if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Seleccione un cliente.");
         return;
    }
       
        String sql = "DELETE FROM clientes WHERE id_cliente=?";

        try {

            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // LIMPIAR CAMPOS
    public void limpiarCampos(JTextField txtId, JTextField txtNombre, JTextField txtTelefono) {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
    }
    
    public void habilitarCampos(JTextField txtNombre, JTextField txtTelefono){
        txtNombre.setEnabled(true);
        txtTelefono.setEnabled(true);
    }
    
    public void inabilitarCompos(JTextField txtNombre, JTextField txtTelefono){
        txtNombre.setEnabled(false);
        txtTelefono.setEnabled(false);
    }
    
}

    
