/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ligamx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author elias-zzz
 */
public class MySQL {
    private Connection connection=null;
    private Statement comand=null;
    private ResultSet row=null;
    private PreparedStatement ps=null;
    
    public void Connect(){
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
    }
    
    private void getData(String com){
        try{
            comand=connection.createStatement();
            row=comand.executeQuery(com);
        }catch(SQLException e){
            row=null;
        }
    }
    
    private void action(String comm){
        try{
        ps=connection.prepareStatement(comm);
        ps.execute();
        }
        catch(SQLException e){
            e.getSQLState();
        }
    }
    
    
    public ResultSet getRows(String table){
        getData("SELECT * FROM "+table+";");
        return row;
    }
    
    public ResultSet getTables(){
        getData("SHOW TABLES;");
        return row;
    }
    
    public ResultSet getDesc(String table){
        getData("DESCRIBE "+table+";");
        return row;
    }
    
    public void deleteRow(String table,int id){
        action("DELETE FROM "+table+" WHERE id"+table+"="+id+";");
    }
    
    public void insertRow(String table,ArrayList data){
        String campos="(";
        String comm;
        ResultSet fields=getDesc(table);
        try{
            while(fields.next()){
                campos+=fields.getString(1)+",";
            }
            campos=campos.substring(0,campos.length()-1);
            campos+=")";
            comm="INSERT INTO "+table+" "+campos+" VALUES (";
            for(int i=0;i<data.size();i++){
                try{
                    Double.parseDouble(data.get(i).toString());
                    comm+=data.get(i).toString()+",";
                }
                catch(Exception e){
                    comm+="'"+data.get(i).toString()+"',";
                }
                
            }
            comm=comm.substring(0,comm.length()-1);
            comm+=");";
            //System.out.println(comm);
            action(comm);
        }
        catch(SQLException e){
            
        }
        
    }
    
    public void updateRow(String table,ArrayList data,int id){
        //UPDATE `partido` SET `golesLocal` = '5', `golesVisitante` = '3', `idJornada` = '17', `TipoPartido_idTipoPartido` = '4' WHERE `partido`.`idPartido` = 10
        String comm="UPDATE "+table+" SET ";
        ResultSet fields=getDesc(table);
        ArrayList campos=new ArrayList();
        try{
            while(fields.next()){
                campos.add(fields.getString(1));
            }
        }
        catch(SQLException e){
            
        }
        for(int i=0;i<data.size();i++){
            try{
            Double.parseDouble(data.get(i).toString());
            comm+=campos.get(i).toString()+"="+data.get(i).toString()+",";
            }
            catch(Exception ex){
                comm+=campos.get(i).toString()+"='"+data.get(i).toString()+"',";
            }
        }
        comm=comm.substring(0, comm.length()-1);
        comm+=" WHERE "+campos.get(0).toString()+"="+id+";";
        //System.out.println(comm);
        action(comm);
    }
    
    public ResultSet getUsers(){
        getRows("usuarios");
        return row;
    }
    
    public void CloseConnection(){
        try{
        connection.close();
        }
        catch(SQLException e){}
    }
    
    /*public static void main(String args[]){
        MySQL mysql=new MySQL();
        mysql.Connect();
        //ResultSet tablas=mysql.getTables();
        //ResultSet desc=mysql.getDesc("partido");
        //ResultSet rows=mysql.getRows("tipopartido");
        //mysql.deleteRow("partido", 17);
        ArrayList data=new ArrayList();
        data.add("017");
        data.add("1");
        data.add("10");
        data.add("3");
        data.add("2");
        data.add("16");
        data.add("2017-08-20");
        data.add("4");
        //mysql.insertRow("partido", data);
        //mysql.updateRow("partido", data, 17);
        try{
            /*while (tablas.next()) {
                System.out.println(""+tablas.getString(1));
            }
            while (rows.next()) {
                System.out.println(""+rows.getString(1)+"\t"+rows.getString(2));
            }
            while (desc.next()) {
                System.out.println(""+desc.getString(1));
            }
            mysql.CloseConnection();
        }catch(Exception e){}
    }*/
}
