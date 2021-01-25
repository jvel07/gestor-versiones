/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.reportes;

import ec.fe.gv.dao.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;


/**
 *
 * @author Jose07
 */
public class Conn {

    public static Connection conexion;

    public static Connection Conectar() {
        conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost/Gestor_Versiones";
            String usuarioDB = "root";
            String passwordDB = "1234";
            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            System.out.println("Conexión con la base realizada!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error 1 en la conexión con la base de datos Gestor de Versiones " + ex.getMessage());
            conexion = null;
        } catch (SQLException ex) {
            System.out.println("Error 2 en la conexión con la BD " + ex.getMessage());
            conexion = null;
        } catch (Exception ex) {
            System.out.println("Error 3 en la conexión con la BD " + ex.getMessage());
            conexion = null;
        } finally {
            return conexion;
        }
    }
   

}
