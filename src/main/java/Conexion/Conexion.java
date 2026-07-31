package Conexion;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conectar = null;

    String usuario = "root"; // Nombre de usuario de la base de datos
    String contrasenia = "1234"; // Contraseña
    String bd = "peluqueria_canina"; // Nombre de la base de datos
    String ip = "localhost";
    String puerto = "3306";

        String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd + "?useSSL=false&serverTimezone=UTC";
    public Connection estableceConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
          
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos, error" + e.toString());
        }
        return conectar;
    }

}