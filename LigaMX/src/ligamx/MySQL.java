/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ligamx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author elias-zzz
 */
public class MySQL {
    private Connection connection=null;
    private Statement comand=null;
    private ResultSet row=null;
    
    public Connection Connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost:3306/ligaMX";
            String usuario = "root";
            String pass = "1234";
            //Se inicia la conexión
            connection = DriverManager.getConnection(servidor, usuario, pass);
        }
        catch(ClassNotFoundException ex){
        JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            connection = null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            connection = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            connection = null;
        }
        if(connection != null){
            System.out.print("Conexion exitosa");
        }
        return connection;
    }
    public static void main(String args[]){
        MySQL mysql=new MySQL();
        mysql.Connect();
    }
}
