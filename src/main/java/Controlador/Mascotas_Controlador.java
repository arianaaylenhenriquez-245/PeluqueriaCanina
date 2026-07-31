
package Controlador;    
import Conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableModel;


public class Mascotas_Controlador {
    
Conexion conexion = new Conexion();
    
//Guardar Mascotas

public void guardarMascotas(JTextField txtNombre, JTextField txtRaza, JTextField txtTpObser, JTextField txtTam, JTextField txtSex){
        
        if (txtNombre.getText().trim().isEmpty()|| txtRaza.getText().trim().isEmpty() || txtTpObser.getText().trim().isEmpty() || txtTam.getText().isEmpty() ||txtSex.getText().isEmpty()){
             JOptionPane.showMessageDialog(null, "Complete todos los campos.");
             return;
        }
         
        String sql = "INSERT INTO mascotas (nombre_cliente, raza, observaciones_mascotas, tamano) VALUES (?, ?, ? , ? , ?)";

        try {   
          PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
          ps.setString(1,txtNombre.getText());
          ps.setString(2,txtRaza.getText());
          ps.setString(3,txtTam.getText());
          ps.setString(4,txtSex.getText());
          ps.setString(5,txtTpObser.getText());
          ps.executeUpdate();
            
          ps.close();
            
          JOptionPane.showMessageDialog(null, "Cliente guardado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

    }

     //Mostrar Mascotas
public void mostrarMascotas(JTable tabla) {

    DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Raza");
        modelo.addColumn("Tamaño");
        modelo.addColumn("Obesrvaciones");
        tabla.setModel(modelo);
        String sql = "SELECT * FROM mascotas";

        try {
            PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("id_mascota");
                fila[1] = rs.getString("nombre_mascota");
                fila[2] = rs.getString("raza");
                fila[3] = rs.getString("tamano");
                fila[4] = rs.getString("obsservaciones_mascotas");
                modelo.addRow(fila);

            }
               rs.close();
               ps.close();
               
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al mostrar clientes");
        }
    }


  //Seleccionar Fila
public void seleccionarMascota(JTable tabla, JTextField txtId, JTextField txtNombre, JTextField txtRaza, JTextField txtTamaño, JTextPane tpObservaciones) {
    int fila = tabla.getSelectedRow();
    if (fila >= 0) {
        txtId.setText(tabla.getValueAt(fila, 0).toString());
        txtNombre.setText(tabla.getValueAt(fila, 1).toString());
        txtRaza.setText(tabla.getValueAt(fila, 2).toString());
        txtTamaño.setText(tabla.getValueAt(fila, 3).toString());
        Object obs = tabla.getValueAt(fila, 4);
        tpObservaciones.setText(obs != null ? obs.toString() : "");
    }
}

public void modificarMascota(JTextField txtIdMascota, JTextField txtNombreMascota, JTextField txtRaza, JTextField txtTamaño, JTextPane tpObservaciones) {

    // 1. Validar que los campos obligatorios no estén vacíos
    if (txtIdMascota.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Seleccione una mascota de la tabla.");
        return;
    }

    if (txtNombreMascota.getText().trim().isEmpty() || txtRaza.getText().trim().isEmpty() || txtTamaño.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Complete todos los campos obligatorios.");
        return;
    }
    // 2. Extraer el texto del JTextPane
    String observacionesText = tpObservaciones.getText().trim();

    String sql = "UPDATE mascotas SET nombre_mascota = ?, raza = ?, tamano = ?, observaciones_mascotas = ? WHERE id_mascota = ?";

    try {
        PreparedStatement ps = conexion.estableceConexion().prepareStatement(sql);
        ps.setString(1, txtNombreMascota.getText().trim());
        ps.setString(2, txtRaza.getText().trim());
        ps.setString(3, txtTamaño.getText().trim());
        ps.setString(4, observacionesText);
        ps.setInt(5, Integer.parseInt(txtIdMascota.getText().trim()));
        int resultado = ps.executeUpdate();
 
        if (resultado > 0) {
            JOptionPane.showMessageDialog(null, "Mascota modificada con éxito.");
        }
        ps.close();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al modificar mascota: " + e.getMessage());
    }
}

public void eliminarCliente(JTextField txtId) {
         if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente.");
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

    public void limpiarCampos(JTextField txtNombreMascota, JTextField txtRaza, JTextField txtTamaño, JTextPane tpOpservaciones) {
        txtNombreMascota.setText("");
        txtRaza.setText("");
        txtTamaño.setText("");
        tpOpservaciones.setText("");
    }
    
    public void habilitarCampos(JTextField txtNombreMascota, JTextField txtRaza, JTextField txtTamaño, JTextPane tpOpservaciones){
        txtNombreMascota.setEnabled(true);
        txtRaza.setEnabled(true);
        txtTamaño.setEditable(true);
        tpOpservaciones.setEditable(true);
    }
    
    public void inabilitarCompos(JTextField txtNombreMascota, JTextField txtRaza, JTextField txtTamaño, JTextPane tpOpservaciones){
        txtNombreMascota.setEnabled(false);
        txtRaza.setEnabled(false);
        txtTamaño.setEditable(false);
        tpOpservaciones.setEditable(false);
    }
 
}