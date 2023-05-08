/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.services.Planning;

import COM.HRSTORMDESKTOP.models.Planning.Tache;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
import java.sql.Connection;
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
public class ServiceTache implements IService<Tache>{
    Connection conn;
    PreparedStatement ste;

    public ServiceTache() {
        conn= DBConnector.getInstance().getCnx();
    }
    
    
    

    @Override
    public void ajout(Tache entity) {
        String sql = "insert into tache(planning_id,nom,description,priorite) Values(?,?,?,?)";
        try {
            ste=conn.prepareStatement(sql);
            
            ste.setInt(1, entity.getPlanning().getId());
            ste.setString(2, entity.getNom().toString());
            ste.setString(3, entity.getDescription());
            ste.setString(4, entity.getPriorite());
            
            ste.executeUpdate();
            System.out.println("tache Ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    @Override
    public void modifier(Tache entity) {
        String sql = "update  tache set planning_id=?,nom=?,description=?,priorite=? where id=?";
        try {
            ste=conn.prepareStatement(sql);
            ste.setInt(1, entity.getPlanning().getId());
            ste.setString(2, entity.getNom().toString());
            ste.setString(3, entity.getDescription());
            ste.setString(4, entity.getPriorite());
            ste.setInt(5, entity.getId());
               
            
            ste.executeUpdate();
            System.out.println("tache modifie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    @Override
    public void supprimer(int id) {
        String sql = "DELETE from tache where id= '"+id+"' "; 
        try{

            
           Statement st= conn.createStatement();       
           st.executeUpdate(sql);
           System.out.println("tache supprimé avec succés !");
       }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
    }

    @Override
    public List<Tache> afficher() {
        ServicePlanning sr=new ServicePlanning();
        ObservableList<Tache> Tachelist = FXCollections.observableArrayList();
        
        try{
        Statement st= conn.createStatement();
        String query = "select * from Tache";
        
            ResultSet rs;
        rs = st.executeQuery(query);
        Tache eq;
        while (rs.next()) {
           eq = new Tache(rs.getInt("id"),rs.getString("nom"),rs.getString("description"),rs.getString("priorite"),sr.getById(rs.getInt("planning_id"))); 
            eq.setPlanning_nom(eq.getPlanning().getNom());
            Tachelist.add(eq);

        }
         return Tachelist;    
         }catch(SQLException ex){
                         System.out.println(ex.getMessage());

         }
        return Tachelist;
    }
    
    
    public Tache getById(int id) {
        ServicePlanning sr=new ServicePlanning();
        Tache eq=new Tache();
        try{
        Statement st= conn.createStatement();
        String query = "select * from Tache where id="+id+"";
        
            ResultSet rs;
        rs = st.executeQuery(query);
        while (rs.next()) {
            eq = new Tache(rs.getInt("id"),rs.getString("nom"),rs.getString("description"),rs.getString("priorite"),sr.getById(rs.getInt("planning_id"))); 
            eq.setPlanning_nom(eq.getPlanning().getNom());

        }
         return eq;    
         }catch(SQLException ex){
                         System.out.println(ex.getMessage());

         }
        return eq;
    }
}
