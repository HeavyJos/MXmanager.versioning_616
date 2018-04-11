/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ligamx;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mshvc2
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    
    private String nombre = "Liga Mx";
    private Color backgroundColor = new Color(235, 235, 235);
    private Color menuBarColor = new Color(25, 20, 74);
    
    private boolean isInsertMode=false;
    private boolean isEditMode=false;
    
    //Metodo para establecer nombres más normales para el usuario
    private String elegantTableName(String tableName, boolean status){
        String[][] tables={
            {"dt","Directores técnicos"},
            {"equipo","Equipos"},
            {"jornada","Jornadas"},
            {"jugador","Jugadores"},
            {"partido","Partidos"},
            {"temporada","Temporadas"},
            {"tipopartido","Tipos de partido"}
        };
        if(status==true){
            for(byte i=0;i<tables.length;i++){
                if(tableName.equals(tables[i][0]))
                    return tables[i][1];
            }
        }
        else{
            for(byte i=0;i<tables.length;i++){
                if(tableName.equals(tables[i][1]))
                    return tables[i][0];
            }
        }
        return null;
    }
    
    //Metodo para establecer inserciones más normales para el usuario
    private void elegantInsertName(){
        String tableSelect=elegantTableName(
                filterTables.getSelectedItem().toString(),false);
        String name="";
        String data="";
        String nameValue="";
        if(tableSelect==null)
            tableSelect="";
        visibleData(true);
        
        String[] tables={
            "equipo",
            "jornada",
            "jugador",
            "partido",
            "dt",
            "temporada",
            "tipopartido"
        };
        
        String[] altTables={
            "dt",
            "temporada",
            "equipo"
        };
        
        int n=1;
        int nMax=InDataExtra.getItemCount();
        
        while(n<nMax){
            InDataExtra.removeItemAt(1);
            n++;
        }
        
        String altTable="";
        int id=0;

        if(tableSelect.equals(tables[0])){
            altTable=altTables[0];
            name="Nombre del equipo:";
            data="Director técnico:";
        }
        if(tableSelect.equals(tables[1])){
            altTable=altTables[1];
            name="N° de Jornada:";
            data="Temporada:";
        }
        if(tableSelect.equals(tables[2])){
            altTable=altTables[2];
            name="Nombre del jugador:";
            data="Equipo:";
        }
        if(tableSelect.equals(tables[3])){
            try{
                MySQL mysql=new MySQL();
                mysql.Connect();
                ResultSet record = mysql.getRows("equipo");
                while(record.next()){
                    PInLocal1.addItem(record.getString(2));
                    PInVisit1.addItem(record.getString(2));
                }
                record = mysql.getRows("jornada");
                while(record.next()){
                    PinJornada1.addItem(record.getString(1));
                }
                record = mysql.getRows("tipopartido");
                while(record.next()){
                    PInPartido1.addItem(record.getString(2));
                }
                mysql.CloseConnection();
            }catch(Exception Ex){

            }
            
            PinJornada1.setSelectedItem("-Selecciona un valor-");
            PInPartido1.setSelectedItem("-Selecciona un valor-");
            PInLocal1.setSelectedItem("-Selecciona un valor-");
            PInVisit1.setSelectedItem("-Selecciona un valor-");
        }
        if(tableSelect.equals(tables[4])){
            name="Nombre del director técnico:";
        }
        if(tableSelect.equals(tables[5])){
            name="Nombre de la temporada:";
        }
        if(tableSelect.equals(tables[6])){
            name="Nombre del tipo de partido:";
        }
        
        if(!altTable.equals("")){
            try{
                MySQL mysql=new MySQL();
                mysql.Connect();
                ResultSet record = mysql.getRows(altTable);
                while(record.next()){
                    InDataExtra.addItem(record.getString(2));
                }
                mysql.CloseConnection();
            }catch(SQLException e){}
        }
        else{
            InDataExtra.setVisible(false);
            jLabelInData.setVisible(false);
        }
        InDataExtra.setSelectedItem("-Seleccione un valor-");
        jLabelInData.setText(data);
        jLabelInName.setText(name);
        InDataName.setText(nameValue);
    }
    
    //Metodo para establecer registros más normales para el usuario
    private void elegantRecordName(){
        String recordSelect=filterRecords.getSelectedItem().toString();
        String tableSelect=elegantTableName(
                filterTables.getSelectedItem().toString(),false);
        
        String name="";
        String data="";
        String valueName="";
        String valueData="";
        
        visibleData(true);
        
        
        int n=1;
        int nMax=ModDataExtra.getItemCount();
        while(n<nMax){
            ModDataExtra.removeItemAt(1);
            n++;
        }
        
        int nLocal=1;
        int nMaxLocal=ModPLocal.getItemCount();
        while(nLocal<nMaxLocal){
            ModPLocal.removeItemAt(1);
            nLocal++;
        }

        int nVisit=1;
        int nMaxVisit=ModPVisit.getItemCount();
        while(nVisit<nMaxVisit){
            ModPVisit.removeItemAt(1);
            nVisit++;
        }

        int nJornada=1;
        int nMaxJornada=ModPJornada.getItemCount();
        while(nJornada<nMaxJornada){
            ModPJornada.removeItemAt(1);
            nJornada++;
        }

        int nPartido=1;
        int nMaxPartido=ModPPartido.getItemCount();
        while(nPartido<nMaxPartido){
            ModPPartido.removeItemAt(1);
            nPartido++;
        }
        
        int id = 0;
        
        String[] tables={
            "equipo",
            "jornada",
            "jugador",
            "partido",
            "dt",
            "temporada",
            "tipopartido"
        };
        try{
        MySQL mysql=new MySQL();
        mysql.Connect();
        
        //Datos de equipo
        if(tableSelect.equals(tables[0])){
            ResultSet record = mysql.getRows(tableSelect);
            while(record.next()){
                if(recordSelect.equals(record.getString(2))){
                    name="Nombre del equipo:";
                    valueName=record.getString(2);
                    id = record.getInt(3);
                }
            }
            record = mysql.getRows("dt");
            while(record.next()){
                if(id == record.getInt(1)){
                    data="Director tećnico:";
                    valueData = record.getString(2);
                }
                ModDataExtra.addItem(record.getString(2));
            }
        }
        
        //Datos de jornada
        if(tableSelect.equals(tables[1])){
            ResultSet record = mysql.getRows(tableSelect);
            while(record.next()){
                if(recordSelect.equals(record.getString(1))){
                    name="N° de jornada:";
                    valueName=record.getString(1);
                    id = record.getInt(2);
                }
            }
            record = mysql.getRows("temporada");
            while(record.next()){
                if(id == record.getInt(1)){
                    data="Temporada:";
                    valueData = record.getString(2);
                }
                ModDataExtra.addItem(record.getString(2));
            }
        }
        
        //Datos del jugador
        if(tableSelect.equals(tables[2])){
            ResultSet record = mysql.getRows(tableSelect);
            while(record.next()){
                if(recordSelect.equals(record.getString(2))){
                    name="Nombre del jugador:";
                    valueName=record.getString(2);
                    id = record.getInt(3);
                }
            }
            record = mysql.getRows("equipo");
            while(record.next()){
                if(id == record.getInt(1)){
                    data="Equipo:";
                    valueData = record.getString(2);
                }
                ModDataExtra.addItem(record.getString(2));
            }
        }
        
        //Datos del partido
        if(tableSelect.equals(tables[3])){
            int idLocal=0;
            int idVisit=0;
            int idJornada=0;
            int idPartido=0;
           
            ResultSet record = mysql.getRows(tableSelect);
            
            while(record.next()){
                if(recordSelect.equals(record.getString(1))){
                    
                    PLocalGol.setText(" "+record.getString(4));
                    PVisitGol.setText(" "+record.getString(5));
                    PDate.setText(" "+record.getString(7));
                    
                    PModLocalGol.setText(record.getString(4));
                    PModVisitGol.setText(record.getString(5));
                    PModDate.setText(record.getString(7));
                    
                    idLocal = record.getInt(2);
                    idVisit = record.getInt(3);
                    idJornada = record.getInt(6);
                    idPartido = record.getInt(8);
                }
            }
            record = mysql.getRows("equipo");
            while(record.next()){
                System.out.println(record.getString(2));
                if(idLocal == record.getInt(1)){
                    PLocal.setText(" "+record.getString(2));
                }
                if(idVisit == record.getInt(1)){
                    PVisit.setText(" "+record.getString(2));
                }
                ModPLocal.addItem(record.getString(2));
                ModPVisit.addItem(record.getString(2));
            }
            record = mysql.getRows("jornada");
            while(record.next()){
                if(idJornada == record.getInt(1)){
                    PJornada.setText(" "+record.getString(1));
                }
                ModPJornada.addItem(record.getString(1));
            }
            record = mysql.getRows("tipopartido");
            while(record.next()){
                if(idPartido == record.getInt(1)){
                    PPartido.setText(" "+record.getString(2));
                }
                ModPPartido.addItem(record.getString(2));
            }
            try{
                ModPJornada.setSelectedItem(PJornada.getText()
                    .substring(1,PJornada.getText().length()));
                ModPPartido.setSelectedItem(PPartido.getText()
                    .substring(1,PPartido.getText().length()));
                ModPLocal.setSelectedItem(PLocal.getText()
                    .substring(1,PLocal.getText().length()));
                ModPVisit.setSelectedItem(PVisit.getText()
                    .substring(1,PVisit.getText().length()));
            }catch(Exception ex){
                
            }
        }
        
        
        //Datos del director tecnico
        if(tableSelect.equals(tables[4])){
            ResultSet record = mysql.getRows(tableSelect);
            while(record.next()){
                if(recordSelect.equals(record.getString(2))){
                    name="Nombre del director técnico:";
                    valueName=record.getString(2);
                }
            }
        }
        //Datos de la temporada
        if(tableSelect.equals(tables[5])){
            ResultSet record = mysql.getRows(tableSelect);
            while(record.next()){
                if(recordSelect.equals(record.getString(2))){
                    name="Nombre de la temporada:";
                    valueName=record.getString(2);
                }
            }
        }
        //Datos del tipo de partido
        if(tableSelect.equals(tables[6])){
            ResultSet record = mysql.getRows(tableSelect);
            while(record.next()){
                if(recordSelect.equals(record.getString(2))){
                    name="Nombre del tipo de partido:";
                    valueName=record.getString(2);
                }
            }
        }
        mysql.CloseConnection();
        }catch(SQLException e){
            
        }
        jLabelExData.setText(data);
        jLabelExName.setText(name);
        jLabelModData.setText(data);
        jLabelModName.setText(name);
        ExDataName.setText(" "+valueName);
        ExDataExtra.setText(" "+valueData);
        ModDataName.setText(valueName);
        
        ModDataExtra.setSelectedItem(valueData);
        
        if(data.equals("")==true){
            jLabelExData.setVisible(false);
            jLabelModData.setVisible(false);
            ExDataExtra.setVisible(false);
            ModDataExtra.setVisible(false);
        }
            
    }
    public void visibleData(boolean isVisible){
        jLabelExData.setVisible(isVisible);
        jLabelExName.setVisible(isVisible);
        ExDataName.setVisible(isVisible);
        ExDataExtra.setVisible(isVisible);
        
        jLabelModData.setVisible(isVisible);
        jLabelModName.setVisible(isVisible);
        ModDataExtra.setVisible(isVisible);
        ModDataName.setVisible(isVisible);
        
        jLabelInData.setVisible(isVisible);
        jLabelInName.setVisible(isVisible);
        InDataExtra.setVisible(isVisible);
        InDataName.setVisible(isVisible);
        
        jLabelPDate.setVisible(isVisible);
        jLabelPDate1.setVisible(isVisible);
        jLabelPDate2.setVisible(isVisible);
        jLabelPJornada.setVisible(isVisible);
        jLabelPJornada1.setVisible(isVisible);
        jLabelPJornada2.setVisible(isVisible);
        jLabelPLocal.setVisible(isVisible);
        jLabelPLocal1.setVisible(isVisible);
        jLabelPLocal2.setVisible(isVisible);
        jLabelPLocalGol.setVisible(isVisible);
        jLabelPLocalGol1.setVisible(isVisible);
        jLabelPLocalGol2.setVisible(isVisible);
        jLabelPPartido.setVisible(isVisible);
        jLabelPPartido1.setVisible(isVisible);
        jLabelPPartido2.setVisible(isVisible);
        jLabelPVisit.setVisible(isVisible);
        jLabelPVisit1.setVisible(isVisible);
        jLabelPVisit2.setVisible(isVisible);
        jLabelPVisitGol.setVisible(isVisible);
        jLabelPVisitGol1.setVisible(isVisible);
        jLabelPVisitGol2.setVisible(isVisible);
        
        PDate.setVisible(isVisible);
        PInDate1.setVisible(isVisible);
        PInLocal1.setVisible(isVisible);
        PInLocalGol1.setVisible(isVisible);
        PInPartido1.setVisible(isVisible);
        PInVisit1.setVisible(isVisible);
        PInVisitGol1.setVisible(isVisible);
        PJornada.setVisible(isVisible);
        PLocal.setVisible(isVisible);
        PLocalGol.setVisible(isVisible);
        PModDate.setVisible(isVisible);
        PModLocalGol.setVisible(isVisible);
        PModVisitGol.setVisible(isVisible);
        PPartido.setVisible(isVisible);
        PVisit.setVisible(isVisible);
        PVisitGol.setVisible(isVisible);
        PinJornada1.setVisible(isVisible);
        ModPJornada.setVisible(isVisible);
        ModPLocal.setVisible(isVisible);
        ModPPartido.setVisible(isVisible);
        ModPVisit.setVisible(isVisible);
    }
    
    //Metodo recopilatorio para actualizar
    public void updateData(){
        
        String tableSelect=elegantTableName(
                filterTables.getSelectedItem().toString(),false);
        int Superid=0;
        
        ArrayList dataRaw = new ArrayList();
        String[] tables={
            "equipo",
            "jornada",
            "jugador",
            "partido",
            "dt",
            "temporada",
            "tipopartido"
        };
        
        String[] altTables={
            "dt",
            "temporada",
            "equipo"
        };
        
        try{
            MySQL mysql=new MySQL();
            mysql.Connect();
            ResultSet record = mysql.getRows(tableSelect);
            while(record.next()){
                if(filterRecords.getSelectedItem().toString()
                    .equals(record.getString(2))){
                    dataRaw.add(record.getInt(1));
                    Superid=record.getInt(1);
                }
            }
        }catch(Exception e){}
        
        if(tableSelect.equals(tables[4])||
           tableSelect.equals(tables[5])||
           tableSelect.equals(tables[6])){
           dataRaw.add(ModDataName.getText());
        }
        if(tableSelect.equals(tables[0])||
            tableSelect.equals(tables[1])||
            tableSelect.equals(tables[2])){
            
            dataRaw.add(ModDataName.getText());
            
            if(tableSelect.equals(tables[1]))
                Superid=Integer.parseInt(ModDataName.getText());
            
            String altTable="";
            int id=0;
            
            if(tableSelect.equals(tables[0]))
                altTable=altTables[0];
            if(tableSelect.equals(tables[1]))
                altTable=altTables[1];
            if(tableSelect.equals(tables[2]))
                altTable=altTables[2];
            
            try{
                MySQL mysql=new MySQL();
                mysql.Connect();
                ResultSet record = mysql.getRows(altTable);
                while(record.next()){
                    if(ModDataExtra.getSelectedItem().toString()
                        .equals(record.getString(2))){
                        dataRaw.add(record.getInt(1));
                    }
                }
            }catch(Exception e){}
        }
        
        if(tableSelect.equals(tables[3])){
            dataRaw=new ArrayList();
            Superid=Integer.parseInt(filterRecords.getSelectedItem().toString());
            dataRaw.add(filterRecords.getSelectedItem().toString());
            try{
                MySQL mysql=new MySQL();
                mysql.Connect();
                ResultSet record = mysql.getRows("equipo");
                while(record.next()){
                    if(ModPLocal.getSelectedItem().toString()
                        .equals(record.getString(2))){
                        dataRaw.add(record.getInt(1));
                    }
                }
                record = mysql.getRows("equipo");
                while(record.next()){
                    if(ModPVisit.getSelectedItem().toString()
                        .equals(record.getString(2))){
                        dataRaw.add(record.getInt(1));
                    }
                }
                dataRaw.add(PModLocalGol.getText());
                dataRaw.add(PModVisitGol.getText());
                
                record = mysql.getRows("jornada");
                while(record.next()){
                    if(ModPJornada.getSelectedItem().toString()
                        .equals(record.getString(1))){
                        dataRaw.add(record.getInt(1));
                    }
                }
                dataRaw.add(PModDate.getText());
                record = mysql.getRows("tipopartido");
                while(record.next()){
                    if(ModPPartido.getSelectedItem().toString()
                        .equals(record.getString(2))){
                        dataRaw.add(record.getInt(1));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try{
            MySQL mysql=new MySQL();
            mysql.Connect();
            //mysql.insertRow(tableSelect, dataRaw);
            mysql.updateRow(tableSelect, dataRaw, Superid);
            mysql.CloseConnection();
        }catch(Exception ex){}
        
        System.out.println(dataRaw+" - "+Superid);
    }
    
    //Metodo recopilatorio para insertar
    public void inserData(){
        
        String tableSelect=elegantTableName(
                filterTables.getSelectedItem().toString(),false);
        
        ArrayList dataRaw = new ArrayList();
        String[] tables={
            "equipo",
            "jornada",
            "jugador",
            "partido",
            "dt",
            "temporada",
            "tipopartido"
        };
        
        String[] altTables={
            "dt",
            "temporada",
            "equipo"
        };
        
        if(tableSelect.equals(tables[4])||
           tableSelect.equals(tables[5])||
           tableSelect.equals(tables[6])){
           dataRaw.add(InDataName.getText());
        }
        if(tableSelect.equals(tables[0])||
            tableSelect.equals(tables[1])||
            tableSelect.equals(tables[2])){
            
            dataRaw.add(InDataName.getText());
            
            String altTable="";
            int id=0;
            
            if(tableSelect.equals(tables[0]))
                altTable=altTables[0];
            if(tableSelect.equals(tables[1]))
                altTable=altTables[1];
            if(tableSelect.equals(tables[2]))
                altTable=altTables[2];
            
            try{
                MySQL mysql=new MySQL();
                mysql.Connect();
                ResultSet record = mysql.getRows(altTable);
                while(record.next()){
                    if(InDataExtra.getSelectedItem().toString()
                        .equals(record.getString(2))){
                        dataRaw.add(record.getInt(1));
                    }
                }
            }catch(Exception e){}
        }
        
        if(tableSelect.equals(tables[3])){
            try{
                MySQL mysql=new MySQL();
                mysql.Connect();
                ResultSet record = mysql.getRows("equipo");
                dataRaw.add("null");
                while(record.next()){
                    if(PInLocal1.getSelectedItem().toString()
                        .equals(record.getString(2))){
                        dataRaw.add(record.getInt(1));
                    }
                }
                record = mysql.getRows("equipo");
                while(record.next()){
                    if(PInVisit1.getSelectedItem().toString()
                        .equals(record.getString(2))){
                        dataRaw.add(record.getInt(1));
                    }
                }
                dataRaw.add(PInLocalGol1.getText());
                dataRaw.add(PInVisitGol1.getText());
                
                record = mysql.getRows("jornada");
                while(record.next()){
                    if(PinJornada1.getSelectedItem().toString()
                        .equals(record.getString(1))){
                        dataRaw.add(record.getInt(1));
                    }
                }
                dataRaw.add(PInDate1.getText());/*
                record = mysql.getRows("jornada");
                while(record.next()){
                    if(PinJornada1.getSelectedItem().toString()
                        .equals(record.getString(1))){
                        dataRaw.add(record.getInt(1));
                    }
                }*/
                record = mysql.getRows("tipopartido");
                while(record.next()){
                    if(PInPartido1.getSelectedItem().toString()
                        .equals(record.getString(2))){
                        dataRaw.add(record.getInt(1));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        MySQL mysql=new MySQL();
        mysql.Connect();
        mysql.insertRow(tableSelect, dataRaw);
        mysql.CloseConnection();
        
        System.out.println(dataRaw);
    }
    
    //Metodo para borrar datos
    public void deleteData(){
        String recordSelect=filterRecords.getSelectedItem().toString();
        String tableSelect=elegantTableName(
                filterTables.getSelectedItem().toString(),false);
        int Superid=0;
        try{
            MySQL mysql=new MySQL();
            mysql.Connect();
            ResultSet record = mysql.getRows(tableSelect);
            while(record.next()){
                if(filterRecords.getSelectedItem().toString()
                    .equals(record.getString(2))){
                    Superid=record.getInt(1);
                }
                if(tableSelect.equals("jornada")||tableSelect.equals("partido")){
                    if(filterRecords.getSelectedItem().toString()
                    .equals(record.getString(1))){
                        Superid=record.getInt(1);
                    }
                }
            }
        }catch(Exception e){
            
        }
        try{
            MySQL mysql=new MySQL();
            mysql.Connect();
            mysql.deleteRow(tableSelect, Superid);
            mysql.CloseConnection();
        }catch(Exception ex){}
        filterRecords.setSelectedIndex(0);
        visibleData(false);
    }
    
    //Metodo para insertar imagen de usuario
    public void setUserPhoto(String userName, int w, int h){
        String[][] logins ={
            {"Martin","/ligamx/Icons/Login/Martin.jpg"},
            {"Elias_ZZZ",""},
            {"Evaluador",""},
            {"Default","/ligamx/Icons/LigaMx.png"}
        };
        try{
            if(userName.equals(logins[0][0])){
                this.userProfile.setIcon(resizeIcon(logins[0][1], w, h));
            }
            if(userName.equals(logins[1][0])){
                this.userProfile.setIcon(resizeIcon(logins[1][1], w, h));
            }
            if(userName.equals(logins[2][0])){
                this.userProfile.setIcon(resizeIcon(logins[2][1], w, h));
            }
        }catch(Exception e){
            this.userProfile.setIcon(resizeIcon(logins[3][1], w, h));
        }
    }
    
    public GUI(String userName) {
        initComponents();
        setPreferredSize(new Dimension(800,600));
        //setLocationRelativeTo(null);
        this.userProfile.setText(userName);
        setUserPhoto(userName, 27, 27);
        visibleData(false);
        bmainData.setEnabled(false);
        bModData.setEnabled(true);
        bNewData.setEnabled(true);
        try {
            filterTablesStatus(true);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelList = new javax.swing.JPanel();
        jPanelMainList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelFiltros = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filterTables = new javax.swing.JComboBox<>();
        filterRecords = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelMenu = new javax.swing.JPanel();
        jLabelMainListExp1 = new javax.swing.JLabel();
        bmainData = new javax.swing.JButton();
        bModData = new javax.swing.JButton();
        bNewData = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanelExtra = new javax.swing.JPanel();
        jLabelMainListExp2 = new javax.swing.JLabel();
        bExtraData = new javax.swing.JButton();
        jPanelMain = new javax.swing.JPanel();
        jScrollPaneExploreData = new javax.swing.JScrollPane();
        jPanelexploreData = new javax.swing.JPanel();
        jLabelExName = new javax.swing.JLabel();
        jLabelExData = new javax.swing.JLabel();
        ExDataName = new javax.swing.JLabel();
        ExDataExtra = new javax.swing.JLabel();
        jScrollPaneModData = new javax.swing.JScrollPane();
        jPanelModData = new javax.swing.JPanel();
        jLabelModName = new javax.swing.JLabel();
        jLabelModData = new javax.swing.JLabel();
        ModDataName = new javax.swing.JTextField();
        ModDataExtra = new javax.swing.JComboBox<>();
        jScrollPaneInData = new javax.swing.JScrollPane();
        jPanelInData = new javax.swing.JPanel();
        jLabelInName = new javax.swing.JLabel();
        jLabelInData = new javax.swing.JLabel();
        InDataName = new javax.swing.JTextField();
        InDataExtra = new javax.swing.JComboBox<>();
        jScrollPaneExploreDataP = new javax.swing.JScrollPane();
        jPanelexploreDataP = new javax.swing.JPanel();
        jLabelPLocal = new javax.swing.JLabel();
        jLabelPVisit = new javax.swing.JLabel();
        PLocal = new javax.swing.JLabel();
        PVisit = new javax.swing.JLabel();
        jLabelPLocalGol = new javax.swing.JLabel();
        jLabelPVisitGol = new javax.swing.JLabel();
        PVisitGol = new javax.swing.JLabel();
        PLocalGol = new javax.swing.JLabel();
        jLabelPJornada = new javax.swing.JLabel();
        PJornada = new javax.swing.JLabel();
        PPartido = new javax.swing.JLabel();
        jLabelPPartido = new javax.swing.JLabel();
        jLabelPDate = new javax.swing.JLabel();
        PDate = new javax.swing.JLabel();
        jScrollPaneModDataP = new javax.swing.JScrollPane();
        jPanelexploreModP = new javax.swing.JPanel();
        jLabelPLocal1 = new javax.swing.JLabel();
        jLabelPVisit1 = new javax.swing.JLabel();
        jLabelPLocalGol1 = new javax.swing.JLabel();
        jLabelPVisitGol1 = new javax.swing.JLabel();
        jLabelPJornada1 = new javax.swing.JLabel();
        jLabelPPartido1 = new javax.swing.JLabel();
        jLabelPDate1 = new javax.swing.JLabel();
        PModLocalGol = new javax.swing.JTextField();
        PModVisitGol = new javax.swing.JTextField();
        PModDate = new javax.swing.JTextField();
        ModPVisit = new javax.swing.JComboBox<>();
        ModPPartido = new javax.swing.JComboBox<>();
        ModPLocal = new javax.swing.JComboBox<>();
        ModPJornada = new javax.swing.JComboBox<>();
        jScrollPaneInDataP = new javax.swing.JScrollPane();
        jPanelInP = new javax.swing.JPanel();
        jLabelPLocal2 = new javax.swing.JLabel();
        jLabelPVisit2 = new javax.swing.JLabel();
        jLabelPLocalGol2 = new javax.swing.JLabel();
        jLabelPVisitGol2 = new javax.swing.JLabel();
        jLabelPJornada2 = new javax.swing.JLabel();
        jLabelPPartido2 = new javax.swing.JLabel();
        jLabelPDate2 = new javax.swing.JLabel();
        PInLocalGol1 = new javax.swing.JTextField();
        PInVisitGol1 = new javax.swing.JTextField();
        PInDate1 = new javax.swing.JTextField();
        PInVisit1 = new javax.swing.JComboBox<>();
        PInPartido1 = new javax.swing.JComboBox<>();
        PInLocal1 = new javax.swing.JComboBox<>();
        PinJornada1 = new javax.swing.JComboBox<>();
        jPanelBar = new javax.swing.JPanel();
        userProfile = new javax.swing.JLabel();
        jLabelInfoBar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Liga Mx");
        setBackground(new java.awt.Color(235, 235, 235));
        setPreferredSize(new java.awt.Dimension(638, 377));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanelList.setBackground(new java.awt.Color(235, 235, 235));
        jPanelList.setPreferredSize(new java.awt.Dimension(192, 313));
        jPanelList.setLayout(new java.awt.CardLayout());

        jPanelMainList.setBackground(new java.awt.Color(235, 235, 235));
        jPanelMainList.setLayout(null);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(192, 95));

        jPanelFiltros.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFiltros.setPreferredSize(new java.awt.Dimension(192, 99));
        jPanelFiltros.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(25, 20, 74));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Filtro de busqueda");
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(192, 33));
        jLabel1.setRequestFocusEnabled(false);
        jPanelFiltros.add(jLabel1);
        jLabel1.setBounds(-3, -2, 192, 33);

        filterTables.setBackground(new java.awt.Color(255, 255, 255));
        filterTables.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        filterTables.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione una tabla-" }));
        filterTables.setEnabled(false);
        filterTables.setPreferredSize(new java.awt.Dimension(192, 33));
        filterTables.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterTablesItemStateChanged(evt);
            }
        });
        filterTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterTablesActionPerformed(evt);
            }
        });
        jPanelFiltros.add(filterTables);
        filterTables.setBounds(-3, 29, 192, 33);

        filterRecords.setBackground(new java.awt.Color(255, 255, 255));
        filterRecords.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        filterRecords.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione un registro-" }));
        filterRecords.setEnabled(false);
        filterRecords.setPreferredSize(new java.awt.Dimension(192, 33));
        filterRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterRecordsActionPerformed(evt);
            }
        });
        jPanelFiltros.add(filterRecords);
        filterRecords.setBounds(-3, 58, 192, 33);

        jScrollPane1.setViewportView(jPanelFiltros);

        jPanelMainList.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 192, 95);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(192, 125));

        jPanelMenu.setLayout(null);

        jLabelMainListExp1.setBackground(new java.awt.Color(25, 20, 74));
        jLabelMainListExp1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabelMainListExp1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMainListExp1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMainListExp1.setText("Menu");
        jLabelMainListExp1.setOpaque(true);
        jLabelMainListExp1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelMenu.add(jLabelMainListExp1);
        jLabelMainListExp1.setBounds(-3, 0, 192, 33);

        bmainData.setBackground(new java.awt.Color(255, 255, 255));
        bmainData.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bmainData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ligamx/Icons/searchB.png"))); // NOI18N
        bmainData.setText("Modo de exploración");
        bmainData.setPreferredSize(new java.awt.Dimension(200, 33));
        bmainData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bmainDataActionPerformed(evt);
            }
        });
        jPanelMenu.add(bmainData);
        bmainData.setBounds(-7, 30, 200, 33);

        bModData.setBackground(new java.awt.Color(255, 255, 255));
        bModData.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bModData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ligamx/Icons/editB.png"))); // NOI18N
        bModData.setText("Modo de edición");
        bModData.setPreferredSize(new java.awt.Dimension(200, 33));
        bModData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bModDataActionPerformed(evt);
            }
        });
        jPanelMenu.add(bModData);
        bModData.setBounds(-7, 59, 200, 33);

        bNewData.setBackground(new java.awt.Color(255, 255, 255));
        bNewData.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bNewData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ligamx/Icons/addB.png"))); // NOI18N
        bNewData.setText("Modo de inserción");
        bNewData.setPreferredSize(new java.awt.Dimension(200, 33));
        bNewData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewDataActionPerformed(evt);
            }
        });
        jPanelMenu.add(bNewData);
        bNewData.setBounds(-7, 88, 200, 33);

        jScrollPane2.setViewportView(jPanelMenu);

        jPanelMainList.add(jScrollPane2);
        jScrollPane2.setBounds(0, 103, 192, 125);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(192, 66));

        jPanelExtra.setLayout(null);

        jLabelMainListExp2.setBackground(new java.awt.Color(25, 20, 74));
        jLabelMainListExp2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabelMainListExp2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMainListExp2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMainListExp2.setText("Acciones");
        jLabelMainListExp2.setOpaque(true);
        jLabelMainListExp2.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelExtra.add(jLabelMainListExp2);
        jLabelMainListExp2.setBounds(-3, 0, 192, 33);

        bExtraData.setBackground(new java.awt.Color(255, 255, 255));
        bExtraData.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bExtraData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ligamx/Icons/deleteB.png"))); // NOI18N
        bExtraData.setText("Eliminar registro");
        bExtraData.setPreferredSize(new java.awt.Dimension(200, 33));
        bExtraData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExtraDataActionPerformed(evt);
            }
        });
        jPanelExtra.add(bExtraData);
        bExtraData.setBounds(-7, 30, 200, 33);

        jScrollPane3.setViewportView(jPanelExtra);

        jPanelMainList.add(jScrollPane3);
        jScrollPane3.setBounds(0, 232, 192, 66);

        jPanelList.add(jPanelMainList, "card2");

        getContentPane().add(jPanelList);
        jPanelList.setBounds(8, 56, 192, 313);

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMain.setPreferredSize(new java.awt.Dimension(422, 313));
        jPanelMain.setLayout(new java.awt.CardLayout());

        jPanelexploreData.setBackground(new java.awt.Color(255, 255, 255));
        jPanelexploreData.setLayout(null);

        jLabelExName.setText("Nombre: ");
        jLabelExName.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreData.add(jLabelExName);
        jLabelExName.setBounds(8, 8, 192, 33);

        jLabelExData.setText("Dato :");
        jLabelExData.setToolTipText("");
        jLabelExData.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreData.add(jLabelExData);
        jLabelExData.setBounds(8, 82, 192, 33);

        ExDataName.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        ExDataName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        ExDataName.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreData.add(ExDataName);
        ExDataName.setBounds(8, 41, 192, 33);

        ExDataExtra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        ExDataExtra.setToolTipText("");
        ExDataExtra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        ExDataExtra.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreData.add(ExDataExtra);
        ExDataExtra.setBounds(8, 115, 192, 33);

        jScrollPaneExploreData.setViewportView(jPanelexploreData);

        jPanelMain.add(jScrollPaneExploreData, "exploredata");

        jPanelModData.setBackground(new java.awt.Color(255, 255, 255));
        jPanelModData.setLayout(null);

        jLabelModName.setText("Nombre: ");
        jLabelModName.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelModData.add(jLabelModName);
        jLabelModName.setBounds(8, 8, 192, 33);

        jLabelModData.setText("Dato:");
        jLabelModData.setToolTipText("");
        jLabelModData.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelModData.add(jLabelModData);
        jLabelModData.setBounds(8, 82, 192, 33);

        ModDataName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        ModDataName.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelModData.add(ModDataName);
        ModDataName.setBounds(8, 41, 192, 33);

        ModDataExtra.setBackground(new java.awt.Color(255, 255, 255));
        ModDataExtra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        ModDataExtra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione un valor-" }));
        ModDataExtra.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelModData.add(ModDataExtra);
        ModDataExtra.setBounds(8, 115, 192, 33);

        jScrollPaneModData.setViewportView(jPanelModData);

        jPanelMain.add(jScrollPaneModData, "moddata");

        jPanelInData.setBackground(new java.awt.Color(255, 255, 255));
        jPanelInData.setLayout(null);

        jLabelInName.setText("Nombre: ");
        jLabelInName.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInData.add(jLabelInName);
        jLabelInName.setBounds(8, 8, 192, 33);

        jLabelInData.setText("Dato:");
        jLabelInData.setToolTipText("");
        jLabelInData.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInData.add(jLabelInData);
        jLabelInData.setBounds(8, 82, 192, 33);

        InDataName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        InDataName.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInData.add(InDataName);
        InDataName.setBounds(8, 41, 192, 33);

        InDataExtra.setBackground(new java.awt.Color(255, 255, 255));
        InDataExtra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        InDataExtra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Seleccione un valor-" }));
        InDataExtra.setPreferredSize(new java.awt.Dimension(192, 33));
        InDataExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InDataExtraActionPerformed(evt);
            }
        });
        jPanelInData.add(InDataExtra);
        InDataExtra.setBounds(8, 115, 192, 33);

        jScrollPaneInData.setViewportView(jPanelInData);

        jPanelMain.add(jScrollPaneInData, "adddata");

        jPanelexploreDataP.setBackground(new java.awt.Color(255, 255, 255));
        jPanelexploreDataP.setLayout(null);

        jLabelPLocal.setText("Equipo Local: ");
        jLabelPLocal.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(jLabelPLocal);
        jLabelPLocal.setBounds(8, 8, 192, 33);

        jLabelPVisit.setText("Equipo Visitante:");
        jLabelPVisit.setToolTipText("");
        jLabelPVisit.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(jLabelPVisit);
        jLabelPVisit.setBounds(8, 82, 192, 33);

        PLocal.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PLocal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PLocal.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(PLocal);
        PLocal.setBounds(8, 41, 192, 33);

        PVisit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PVisit.setToolTipText("");
        PVisit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PVisit.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(PVisit);
        PVisit.setBounds(8, 115, 192, 33);

        jLabelPLocalGol.setText("N° Goles:");
        jLabelPLocalGol.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(jLabelPLocalGol);
        jLabelPLocalGol.setBounds(216, 8, 192, 33);

        jLabelPVisitGol.setText("N° Goles:");
        jLabelPVisitGol.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(jLabelPVisitGol);
        jLabelPVisitGol.setBounds(216, 82, 192, 33);

        PVisitGol.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PVisitGol.setToolTipText("");
        PVisitGol.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PVisitGol.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(PVisitGol);
        PVisitGol.setBounds(216, 115, 192, 33);

        PLocalGol.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PLocalGol.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PLocalGol.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(PLocalGol);
        PLocalGol.setBounds(216, 41, 192, 33);

        jLabelPJornada.setText("Jornada:");
        jLabelPJornada.setToolTipText("");
        jLabelPJornada.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(jLabelPJornada);
        jLabelPJornada.setBounds(8, 156, 192, 33);

        PJornada.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PJornada.setToolTipText("");
        PJornada.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PJornada.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(PJornada);
        PJornada.setBounds(8, 190, 192, 33);

        PPartido.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PPartido.setToolTipText("");
        PPartido.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PPartido.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(PPartido);
        PPartido.setBounds(216, 189, 192, 33);

        jLabelPPartido.setText("Tipo de partido:");
        jLabelPPartido.setToolTipText("");
        jLabelPPartido.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(jLabelPPartido);
        jLabelPPartido.setBounds(216, 156, 192, 33);

        jLabelPDate.setText("Fecha:");
        jLabelPDate.setToolTipText("");
        jLabelPDate.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(jLabelPDate);
        jLabelPDate.setBounds(8, 230, 192, 33);

        PDate.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PDate.setToolTipText("");
        PDate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        PDate.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreDataP.add(PDate);
        PDate.setBounds(8, 263, 192, 33);

        jScrollPaneExploreDataP.setViewportView(jPanelexploreDataP);

        jPanelMain.add(jScrollPaneExploreDataP, "exploredataP");

        jPanelexploreModP.setBackground(new java.awt.Color(255, 255, 255));
        jPanelexploreModP.setLayout(null);

        jLabelPLocal1.setText("Equipo Local: ");
        jLabelPLocal1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(jLabelPLocal1);
        jLabelPLocal1.setBounds(8, 8, 192, 33);

        jLabelPVisit1.setText("Equipo Visitante:");
        jLabelPVisit1.setToolTipText("");
        jLabelPVisit1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(jLabelPVisit1);
        jLabelPVisit1.setBounds(8, 82, 192, 33);

        jLabelPLocalGol1.setText("N° Goles:");
        jLabelPLocalGol1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(jLabelPLocalGol1);
        jLabelPLocalGol1.setBounds(216, 8, 192, 33);

        jLabelPVisitGol1.setText("N° Goles:");
        jLabelPVisitGol1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(jLabelPVisitGol1);
        jLabelPVisitGol1.setBounds(216, 82, 192, 33);

        jLabelPJornada1.setText("Jornada:");
        jLabelPJornada1.setToolTipText("");
        jLabelPJornada1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(jLabelPJornada1);
        jLabelPJornada1.setBounds(8, 156, 192, 33);

        jLabelPPartido1.setText("Tipo de partido:");
        jLabelPPartido1.setToolTipText("");
        jLabelPPartido1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(jLabelPPartido1);
        jLabelPPartido1.setBounds(216, 156, 192, 33);

        jLabelPDate1.setText("Fecha:");
        jLabelPDate1.setToolTipText("");
        jLabelPDate1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(jLabelPDate1);
        jLabelPDate1.setBounds(8, 230, 192, 33);

        PModLocalGol.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        PModLocalGol.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(PModLocalGol);
        PModLocalGol.setBounds(216, 41, 192, 33);

        PModVisitGol.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        PModVisitGol.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(PModVisitGol);
        PModVisitGol.setBounds(216, 115, 192, 33);

        PModDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        PModDate.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(PModDate);
        PModDate.setBounds(8, 263, 192, 33);

        ModPVisit.setBackground(new java.awt.Color(255, 255, 255));
        ModPVisit.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        ModPVisit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona un valor-" }));
        ModPVisit.setToolTipText("");
        ModPVisit.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(ModPVisit);
        ModPVisit.setBounds(8, 115, 192, 33);

        ModPPartido.setBackground(new java.awt.Color(255, 255, 255));
        ModPPartido.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        ModPPartido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona un valor-" }));
        ModPPartido.setToolTipText("");
        ModPPartido.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(ModPPartido);
        ModPPartido.setBounds(216, 189, 192, 33);

        ModPLocal.setBackground(new java.awt.Color(255, 255, 255));
        ModPLocal.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        ModPLocal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona un valor-" }));
        ModPLocal.setToolTipText("");
        ModPLocal.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(ModPLocal);
        ModPLocal.setBounds(8, 41, 192, 33);

        ModPJornada.setBackground(new java.awt.Color(255, 255, 255));
        ModPJornada.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        ModPJornada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona un valor-" }));
        ModPJornada.setToolTipText("");
        ModPJornada.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelexploreModP.add(ModPJornada);
        ModPJornada.setBounds(8, 189, 192, 33);

        jScrollPaneModDataP.setViewportView(jPanelexploreModP);

        jPanelMain.add(jScrollPaneModDataP, "moddataP");

        jPanelInP.setBackground(new java.awt.Color(255, 255, 255));
        jPanelInP.setLayout(null);

        jLabelPLocal2.setText("Equipo Local: ");
        jLabelPLocal2.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(jLabelPLocal2);
        jLabelPLocal2.setBounds(8, 8, 192, 33);

        jLabelPVisit2.setText("Equipo Visitante:");
        jLabelPVisit2.setToolTipText("");
        jLabelPVisit2.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(jLabelPVisit2);
        jLabelPVisit2.setBounds(8, 82, 192, 33);

        jLabelPLocalGol2.setText("N° Goles:");
        jLabelPLocalGol2.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(jLabelPLocalGol2);
        jLabelPLocalGol2.setBounds(216, 8, 192, 33);

        jLabelPVisitGol2.setText("N° Goles:");
        jLabelPVisitGol2.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(jLabelPVisitGol2);
        jLabelPVisitGol2.setBounds(216, 82, 192, 33);

        jLabelPJornada2.setText("Jornada:");
        jLabelPJornada2.setToolTipText("");
        jLabelPJornada2.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(jLabelPJornada2);
        jLabelPJornada2.setBounds(8, 156, 192, 33);

        jLabelPPartido2.setText("Tipo de partido:");
        jLabelPPartido2.setToolTipText("");
        jLabelPPartido2.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(jLabelPPartido2);
        jLabelPPartido2.setBounds(216, 156, 192, 33);

        jLabelPDate2.setText("Fecha:");
        jLabelPDate2.setToolTipText("");
        jLabelPDate2.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(jLabelPDate2);
        jLabelPDate2.setBounds(8, 230, 192, 33);

        PInLocalGol1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        PInLocalGol1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(PInLocalGol1);
        PInLocalGol1.setBounds(216, 41, 192, 33);

        PInVisitGol1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        PInVisitGol1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(PInVisitGol1);
        PInVisitGol1.setBounds(216, 115, 192, 33);

        PInDate1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        PInDate1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(PInDate1);
        PInDate1.setBounds(8, 263, 192, 33);

        PInVisit1.setBackground(new java.awt.Color(255, 255, 255));
        PInVisit1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PInVisit1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona un valor-" }));
        PInVisit1.setToolTipText("");
        PInVisit1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(PInVisit1);
        PInVisit1.setBounds(8, 115, 192, 33);

        PInPartido1.setBackground(new java.awt.Color(255, 255, 255));
        PInPartido1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PInPartido1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona un valor-" }));
        PInPartido1.setToolTipText("");
        PInPartido1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(PInPartido1);
        PInPartido1.setBounds(216, 189, 192, 33);

        PInLocal1.setBackground(new java.awt.Color(255, 255, 255));
        PInLocal1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PInLocal1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona un valor-" }));
        PInLocal1.setToolTipText("");
        PInLocal1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(PInLocal1);
        PInLocal1.setBounds(8, 41, 192, 33);

        PinJornada1.setBackground(new java.awt.Color(255, 255, 255));
        PinJornada1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        PinJornada1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona un valor-" }));
        PinJornada1.setToolTipText("");
        PinJornada1.setPreferredSize(new java.awt.Dimension(192, 33));
        jPanelInP.add(PinJornada1);
        PinJornada1.setBounds(8, 189, 192, 33);

        jScrollPaneInDataP.setViewportView(jPanelInP);

        jPanelMain.add(jScrollPaneInDataP, "adddataP");

        getContentPane().add(jPanelMain);
        jPanelMain.setBounds(208, 56, 422, 313);

        jPanelBar.setBackground(new java.awt.Color(235, 235, 235));
        jPanelBar.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanelBar.setLayout(null);

        userProfile.setBackground(new java.awt.Color(255, 255, 255));
        userProfile.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        userProfile.setForeground(new java.awt.Color(255, 255, 255));
        userProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ligamx/Icons/home2.png"))); // NOI18N
        userProfile.setText("Usuario");
        userProfile.setPreferredSize(new java.awt.Dimension(186, 33));
        jPanelBar.add(userProfile);
        userProfile.setBounds(10, 10, 186, 33);

        jLabelInfoBar.setBackground(new java.awt.Color(25, 20, 74));
        jLabelInfoBar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabelInfoBar.setForeground(new java.awt.Color(255, 255, 255));
        jLabelInfoBar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelInfoBar.setOpaque(true);
        jLabelInfoBar.setPreferredSize(new java.awt.Dimension(800, 48));
        jPanelBar.add(jLabelInfoBar);
        jLabelInfoBar.setBounds(0, 0, 800, 48);

        getContentPane().add(jPanelBar);
        jPanelBar.setBounds(0, 0, 800, 600);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited

    }//GEN-LAST:event_formMouseExited
    
    private void filterTablesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterTablesItemStateChanged
        
    }//GEN-LAST:event_filterTablesItemStateChanged

    private void filterTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterTablesActionPerformed
        if(filterTables.getSelectedItem().equals(filterTables.getItemAt(0))){
            filterRecords.setSelectedIndex(0);
            filterRecords.setEnabled(false);
        }
        else{
            try {
                filterRecordsStatus(true);
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(filterTables.getSelectedItem().equals("Partidos")){
            if(isInsertMode==false){
                if(isEditMode==true)
                    chargePanel("mode");
                else
                    chargePanel("exploredata");
            }
            else{
                chargePanel("adddata");
            }
        }
        else{
            if(isInsertMode==false){
                if(isEditMode==true)
                    chargePanel("mode");
                else
                    chargePanel("exploredata");
            }
            else{
                chargePanel("adddata");
            }
        }
        visibleData(false);
        if(isInsertMode==true){
            filterRecords.setSelectedIndex(0);
            filterRecords.setEnabled(false);
            elegantInsertName();
            if(filterTables.getSelectedItem().equals(filterTables.getItemAt(0))){
                InDataName.setVisible(false);
            }
        }
    }//GEN-LAST:event_filterTablesActionPerformed

    private void filterRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterRecordsActionPerformed
        if(filterRecords.getSelectedItem().equals(filterRecords.getItemAt(0))){
            visibleData(false);
        }
        else{
            elegantRecordName();
        }
    }//GEN-LAST:event_filterRecordsActionPerformed

    private void InDataExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InDataExtraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InDataExtraActionPerformed

    private void bExtraDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExtraDataActionPerformed
        if(isInsertMode==true){
            inserData();
            filterTables.setSelectedIndex(0);
        }
        else{
            if(isEditMode==true){
                updateData();
            }
            else{
                deleteData();
            }
            if(filterTables.getSelectedItem().equals(filterTables.getItemAt(0))){
                filterRecords.setSelectedIndex(0);
                filterRecords.setEnabled(false);
            }
            else{
                try {
                    filterRecordsStatus(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        visibleData(false);
    }//GEN-LAST:event_bExtraDataActionPerformed

    private void bmainDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bmainDataActionPerformed
        isEditMode=false;
        chargePanel("exploredata");
        if(isInsertMode==true){
        filterRecords.setEnabled(true);
            if(filterTables.getSelectedItem().equals(filterTables.getItemAt(0))){
                filterRecords.setSelectedIndex(0);
                filterRecords.setEnabled(false);
            }
            else{
                try {
                    filterRecordsStatus(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            visibleData(false);
            isInsertMode=false;
            
        }
        dataExtra();
        bmainData.setEnabled(false);
        bModData.setEnabled(true);
        bNewData.setEnabled(true);
    }//GEN-LAST:event_bmainDataActionPerformed

    private void bModDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bModDataActionPerformed
        isEditMode=true;
        chargePanel("moddata");
        if(isInsertMode==true){
            filterRecords.setEnabled(true);
            if(filterTables.getSelectedItem().equals(filterTables.getItemAt(0))){
                filterRecords.setSelectedIndex(0);
                filterRecords.setEnabled(false);
            }
            else{
                try {
                    filterRecordsStatus(true);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            visibleData(false);
            isInsertMode=false;
        }
        dataExtra();
        bmainData.setEnabled(true);
        bModData.setEnabled(false);
        bNewData.setEnabled(true);
    }//GEN-LAST:event_bModDataActionPerformed

    private void bNewDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewDataActionPerformed
        chargePanel("adddata");
        filterRecords.setSelectedIndex(0);
        filterRecords.setEnabled(false);
        if(filterTables.getSelectedItem().equals(filterTables.getItemAt(0))){
            visibleData(false);
        }
        else{
            visibleData(true);
            elegantInsertName();
        }
        isInsertMode=true;
        isEditMode=false;
        dataExtra();
        bmainData.setEnabled(true);
        bModData.setEnabled(true);
        bNewData.setEnabled(false);
    }//GEN-LAST:event_bNewDataActionPerformed
    
    //Modificar el boton extra
    public void dataExtra(){
        if(isEditMode==true){
            bExtraData.setText("Actualizar registro");
            bExtraData.setIcon(new javax.swing.ImageIcon(getClass()
                    .getResource("/ligamx/Icons/saveB.png")));
        }
        else{
            if(isInsertMode==true){
                bExtraData.setText("Guardar registro");
                bExtraData.setIcon(new javax.swing.ImageIcon(getClass()
                    .getResource("/ligamx/Icons/saveB.png")));
            }
            else{
                bExtraData.setText("Eliminar registro");
                bExtraData.setIcon(new javax.swing.ImageIcon(getClass()
                    .getResource("/ligamx/Icons/deleteB.png")));
            }
        }
    }
    //Activar filtro de registros
    public void filterRecordsStatus(boolean statusB) throws SQLException{
        int n=1;
        int nMax=filterRecords.getItemCount();
        filterRecords.setSelectedIndex(0);
        while(n<nMax){
            filterRecords.removeItemAt(1);
            n++;
        }
        
        String tableName=elegantTableName(filterTables.getSelectedItem()
                .toString(), false);
        if(tableName!=null){
            MySQL mysql=new MySQL();
            mysql.Connect();
            ResultSet records = mysql.getRows(tableName);
            while(records.next()){
                if(tableName.equals("jornada")||tableName.equals("partido"))
                    filterRecords.addItem(records.getString(1));
                else
                    filterRecords.addItem(records.getString(2));
            }
            mysql.CloseConnection();
        }
        filterRecords.setEnabled(statusB);
    }
    
    //Activar filtro de tablas
    public void filterTablesStatus(boolean statusA) throws SQLException{
        int n=1;
        int nMax=filterTables.getItemCount();
        while(n<nMax){
            filterTables.removeItemAt(1);
            n++;
        }
        MySQL mysql=new MySQL();
        mysql.Connect();
        if(statusA == true){
            ResultSet tables = mysql.getTables();
            while(tables.next()){
                String tableName=elegantTableName(tables.getString(1), true);
                if(tableName!=null)
                    filterTables.addItem(tableName);
            }
        }
        mysql.CloseConnection();
        filterTables.setEnabled(statusA);
    }
    
    //Cambiar paneles principales
    public void chargePanel(String panelName){
        if(!filterRecords.getSelectedItem().equals(filterRecords.getItemAt(0)))
            elegantRecordName();
        if(filterTables.getSelectedItem().equals("Partidos"))
            panelName+="P";
        CardLayout card = (CardLayout)jPanelMain.getLayout();
        card.show(jPanelMain, panelName);
    }
    
    //redimesionar imagenes
    public ImageIcon resizeIcon(String ruta, int h, int w){
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(ruta)); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(h, w,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);
        return imageIcon;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ExDataExtra;
    private javax.swing.JLabel ExDataName;
    private javax.swing.JComboBox<String> InDataExtra;
    private javax.swing.JTextField InDataName;
    private javax.swing.JComboBox<String> ModDataExtra;
    private javax.swing.JTextField ModDataName;
    private javax.swing.JComboBox<String> ModPJornada;
    private javax.swing.JComboBox<String> ModPLocal;
    private javax.swing.JComboBox<String> ModPPartido;
    private javax.swing.JComboBox<String> ModPVisit;
    private javax.swing.JLabel PDate;
    private javax.swing.JTextField PInDate1;
    private javax.swing.JComboBox<String> PInLocal1;
    private javax.swing.JTextField PInLocalGol1;
    private javax.swing.JComboBox<String> PInPartido1;
    private javax.swing.JComboBox<String> PInVisit1;
    private javax.swing.JTextField PInVisitGol1;
    private javax.swing.JLabel PJornada;
    private javax.swing.JLabel PLocal;
    private javax.swing.JLabel PLocalGol;
    private javax.swing.JTextField PModDate;
    private javax.swing.JTextField PModLocalGol;
    private javax.swing.JTextField PModVisitGol;
    private javax.swing.JLabel PPartido;
    private javax.swing.JLabel PVisit;
    private javax.swing.JLabel PVisitGol;
    private javax.swing.JComboBox<String> PinJornada1;
    private javax.swing.JButton bExtraData;
    private javax.swing.JButton bModData;
    private javax.swing.JButton bNewData;
    private javax.swing.JButton bmainData;
    private javax.swing.JComboBox<String> filterRecords;
    private javax.swing.JComboBox<String> filterTables;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelExData;
    private javax.swing.JLabel jLabelExName;
    private javax.swing.JLabel jLabelInData;
    private javax.swing.JLabel jLabelInName;
    private javax.swing.JLabel jLabelInfoBar;
    private javax.swing.JLabel jLabelMainListExp1;
    private javax.swing.JLabel jLabelMainListExp2;
    private javax.swing.JLabel jLabelModData;
    private javax.swing.JLabel jLabelModName;
    private javax.swing.JLabel jLabelPDate;
    private javax.swing.JLabel jLabelPDate1;
    private javax.swing.JLabel jLabelPDate2;
    private javax.swing.JLabel jLabelPJornada;
    private javax.swing.JLabel jLabelPJornada1;
    private javax.swing.JLabel jLabelPJornada2;
    private javax.swing.JLabel jLabelPLocal;
    private javax.swing.JLabel jLabelPLocal1;
    private javax.swing.JLabel jLabelPLocal2;
    private javax.swing.JLabel jLabelPLocalGol;
    private javax.swing.JLabel jLabelPLocalGol1;
    private javax.swing.JLabel jLabelPLocalGol2;
    private javax.swing.JLabel jLabelPPartido;
    private javax.swing.JLabel jLabelPPartido1;
    private javax.swing.JLabel jLabelPPartido2;
    private javax.swing.JLabel jLabelPVisit;
    private javax.swing.JLabel jLabelPVisit1;
    private javax.swing.JLabel jLabelPVisit2;
    private javax.swing.JLabel jLabelPVisitGol;
    private javax.swing.JLabel jLabelPVisitGol1;
    private javax.swing.JLabel jLabelPVisitGol2;
    private javax.swing.JPanel jPanelBar;
    private javax.swing.JPanel jPanelExtra;
    private javax.swing.JPanel jPanelFiltros;
    private javax.swing.JPanel jPanelInData;
    private javax.swing.JPanel jPanelInP;
    private javax.swing.JPanel jPanelList;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelMainList;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelModData;
    private javax.swing.JPanel jPanelexploreData;
    private javax.swing.JPanel jPanelexploreDataP;
    private javax.swing.JPanel jPanelexploreModP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneExploreData;
    private javax.swing.JScrollPane jScrollPaneExploreDataP;
    private javax.swing.JScrollPane jScrollPaneInData;
    private javax.swing.JScrollPane jScrollPaneInDataP;
    private javax.swing.JScrollPane jScrollPaneModData;
    private javax.swing.JScrollPane jScrollPaneModDataP;
    private javax.swing.JLabel userProfile;
    // End of variables declaration//GEN-END:variables
}
