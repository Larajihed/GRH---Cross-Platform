/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.services.Planning;

import COM.HRSTORMDESKTOP.models.Planning.Planning;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author houss
 */
public class ServicePlanning implements IService<Planning>{
    Connection conn;
    PreparedStatement ste;

    public ServicePlanning() {
        conn=DBConnector.getInstance().getCnx();
    }
    
    

    @Override
    public void ajout(Planning r) {
String sql = "insert into planning(nom,description,date_debut,date_fin) Values(?,?,?,?)";
        try {
            ste=conn.prepareStatement(sql);
            ste.setString(2, r.getDescription());
            ste.setString(1, r.getNom().toString());
            ste.setDate(3, r.getDate_debut());
            ste.setDate(4, r.getDatefin());
            
               
            
            ste.executeUpdate();
            System.out.println("planning Ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    @Override
    public void modifier(Planning entity) {
        String sql = "update  planning set nom= ? ,description= ? ,date_debut= ?,date_fin=? where id= ?";
        try {
            ste=conn.prepareStatement(sql);
            ste.setString(2, entity.getDescription());
            ste.setString(1, entity.getNom().toString());
            ste.setDate(3, entity.getDate_debut());
            ste.setDate(4, entity.getDatefin());
            ste.setInt(5, entity.getId());
               
            
            ste.executeUpdate();
            System.out.println("planning Updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    @Override
    public void supprimer(int id) {
       String sql = "DELETE from planning where id= '"+id+"' "; 
        String sql1="DELETE from tache where planning_id= '"+id+"' "; 
        try{

            
           Statement st= conn.createStatement();
           st.executeUpdate(sql1);        
           st.executeUpdate(sql);
           System.out.println("planning supprimé avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
    }

    @Override
    public List<Planning> afficher() {
       ObservableList<Planning> Planninglist = FXCollections.observableArrayList();
        try{
        Statement st= conn.createStatement();
        String query = "select * from Planning";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Planning comp;
        while (rs.next()) {
           comp = new Planning(rs.getInt("id"),rs.getString("nom"),rs.getString("description")
                   ,rs.getDate("date_debut"),rs.getDate("date_fin")); 
            Planninglist.add(comp);

        }
         return Planninglist;    
         }catch(SQLException ex){
                         System.out.println(ex.getMessage());

         }
        return Planninglist;
    }
    
    public List<Planning> afficherBydate(Date debut,Date fin,int type) {
       ObservableList<Planning> Planninglist = FXCollections.observableArrayList();
        try{
        Statement st= conn.createStatement();
        String query = "";
        if(type==1){
            
         query = "select * from Planning where date_debut>='"+debut+"'";
        }else if(type==2){
            
        query = "select * from Planning where date_fin <='"+fin+"'";
            System.out.println(query);
        }else{
             query = "select * from Planning where date_debut>='"+debut+"' and date_fin <='"+fin+"'";  
                }
        
        ResultSet rs;
        rs = st.executeQuery(query);
        Planning comp;
        while (rs.next()) {
           comp = new Planning(rs.getInt("id"),rs.getString("nom"),rs.getString("description")
                   ,rs.getDate("date_debut"),rs.getDate("date_fin")); 
            Planninglist.add(comp);

        }
         return Planninglist;    
         }catch(SQLException ex){
                         System.out.println(ex.getMessage());

         }
        return Planninglist;
    }
    
    
    public Planning getById(int id) {
        Planning comp=new Planning();
        try{
        Statement st= conn.createStatement();
        String query = "select * from Planning where id="+id+"";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        
        while (rs.next()) {
           comp = new Planning(rs.getInt("id"),rs.getString("nom"),rs.getString("description")
                   ,rs.getDate("date_debut"),rs.getDate("date_fin")); 
            

        }
         return comp;    
         }catch(SQLException ex){
                         System.out.println(ex.getMessage());

         }
        return comp;
    }
    
    
    public Planning getByNom(String id) {
        Planning comp=new Planning();
        try{
        Statement st= conn.createStatement();
        String query = "select * from Planning where nom='"+id+"'";
        
        ResultSet rs;
        rs = st.executeQuery(query);
        
        while (rs.next()) {
           comp = new Planning(rs.getInt("id"),rs.getString("nom"),rs.getString("description")
                   ,rs.getDate("date_debut"),rs.getDate("date_fin")); 
            
        }
         return comp;    
         }catch(SQLException ex){
                         System.out.println(ex.getMessage());

         }
        return comp;
    }
    
}
