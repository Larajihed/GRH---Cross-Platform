/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.services.conge;

/**
 *
 * @author Marwen
 */
import COM.HRSTORMDESKTOP.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import COM.HRSTORMDESKTOP.models.Conge.Conge;



import COM.HRSTORMDESKTOP.models.Conge.SoldeConge;
import COM.HRSTORMDESKTOP.models.user.User;

import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

public class ServiceConge implements IService<Conge> {

    private Connection cnx = DBConnector.getInstance().getCnx();

    @Override
    public void insertOne(Conge conge) throws SQLException {
        String req = "INSERT INTO `conge`(`id_user_id`,`categorie`, `description`, `etat`, `debut`, `fin`) "
                + "VALUES ( ?, ?, ?, 0, ?, ?)";
        
        System.out.println("hello");
        PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
                System.out.println("hello");

        int id = Config.UserStatic.getId();
        System.out.println(id);
    ps.setInt(1, id);
    ps.setString(2, conge.getCategorie());
    ps.setString(3, conge.getDescription());
    ps.setString(4, conge.getDebut());
    ps.setString(5, conge.getFin());
        System.out.println(ps);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            conge.setId(rs.getInt(1));
            System.out.println("Conge inserted: " + conge);
        } else {
            System.err.println("Failed to retrieve auto-generated id for conge: " + conge);
        }
    }

    @Override
    public void updateOne(Conge conge) throws SQLException {
        String req = "UPDATE `conge` SET  `categorie` = ?, `description` = ?, `etat` = 0, `debut` = ?, `fin` = ? WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, conge.getCategorie());
        ps.setString(2, conge.getDescription());
        ps.setObject(3, conge.getDebut());
        ps.setObject(4, conge.getFin());
        ps.setInt(5, conge.getId());
        ps.executeUpdate();
        System.out.println("Conge updated: " + conge);
    }

    @Override
    public void deleteOne(Conge conge) throws SQLException {
        String req = "DELETE FROM `conge` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, conge.getId());
        ps.executeUpdate();
        System.out.println("Conge deleted: " + conge);
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String req = "DELETE FROM `conge` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Conge deleted with id = " + id);
    }
@Override
public List<Conge> selectAll() throws SQLException {
    List<Conge> conges = new ArrayList<>();
    int userId = Config.UserStatic.getId();

    String req = "SELECT * FROM `conge` WHERE `id_user_id` = ?";
    PreparedStatement st = cnx.prepareStatement(req);
    st.setInt(1, userId);

    ResultSet rs = st.executeQuery();

    while (rs.next()) {
        Conge conge = new Conge();
        conge.setId(rs.getInt("id"));
        conge.setId_User(rs.getInt("id_user_id"));
        conge.setCategorie(rs.getString("categorie"));
        conge.setDescription(rs.getString("description"));
        conge.setEtat(rs.getInt("etat"));
        conge.setDebut(rs.getString("debut"));
        conge.setFin(rs.getString("fin"));

        conges.add(conge);
    }

    return conges;
}


public List<Conge> selectAllAdmin() throws SQLException {
    List<Conge> conges = new ArrayList<>();

    String req = "SELECT * FROM `conge`";
    PreparedStatement st = cnx.prepareStatement(req);

    ResultSet rs = st.executeQuery();

    while (rs.next()) {
        Conge conge = new Conge();
        conge.setId(rs.getInt("id"));
        conge.setId_User(rs.getInt("id_user_id"));
        conge.setCategorie(rs.getString("categorie"));
        conge.setDescription(rs.getString("description"));
        conge.setEtat(rs.getInt("etat"));
        conge.setDebut(rs.getString("debut"));
        conge.setFin(rs.getString("fin"));

        conges.add(conge);
    }

    return conges;
}

    


    public void AccepteConge(Conge c) throws SQLException {

    String req = "UPDATE `conge` SET `etat` = 1 WHERE `id_user_id` = ?";
        System.out.println(c);  

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, c.getId_User());

        System.out.println("Conge updated: " + c);  
       int days= countDays(c.getDebut(),c.getFin());
               System.out.println(days); 
               int id=c.getId_User();
               System.out.println(id);
               
               
String reqSolde = "UPDATE `solde_conge` SET `solde` = ? WHERE `id_user_id` = ?";
PreparedStatement ps2 = cnx.prepareStatement(reqSolde);
ps2.setInt(1, getSolde(id) - days);
ps2.setInt(2, c.getId_User());
int rowsUpdated = ps2.executeUpdate();
        ps.executeUpdate(); 


if (rowsUpdated > 0) {
    System.out.println("Solde updated successfully.");
} else {
    System.out.println("Failed to update solde.");
}
    }
    
    
    
public int countDays(String startDateStr, String endDateStr) {
    LocalDate startDate = LocalDate.parse(startDateStr);
    LocalDate endDate = LocalDate.parse(endDateStr);
    long days = ChronoUnit.DAYS.between(startDate, endDate);
    return (int) days;
    
    }






public void RefuseConge(Conge c) throws SQLException {
String req = "UPDATE `conge` SET   `etat` = 2";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.executeUpdate(); 
        System.out.println("Conge Refusé: " + c);  
      

}
    









public int getSolde(int id_user) throws SQLException {
    String req = "SELECT `solde` FROM `solde_conge` WHERE `id_user_id` = ?";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, id_user);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getInt("solde");
    } else {
        throw new SQLException("User not found");
    }
}



public String getEmail(int id_user) throws SQLException {
    String req = "SELECT `email` FROM `user` WHERE `id` = ?";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, id_user);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getString("email");
    } else {
        throw new SQLException("User not found");
    }
}

public ObservableList<Conge> chercher(String chaine) {
    String sql = "SELECT * FROM conge WHERE (categorie LIKE ? OR description LIKE ?  OR debut LIKE ? OR fin LIKE ?)";

    Connection cnx = DBConnector.getInstance().getCnx();
    String ch = "%" + chaine + "%";
    ObservableList<Conge> myList = FXCollections.observableArrayList();
    try {
        PreparedStatement stee = cnx.prepareStatement(sql);
        stee.setString(1, ch);
        stee.setString(2, ch);
        stee.setString(3, ch);
        stee.setString(4, ch);

        ResultSet rs = stee.executeQuery();
        while (rs.next()) {
            Conge c = new Conge();
            c.setId(rs.getInt("id"));
            c.setCategorie(rs.getString("categorie"));
            c.setDescription(rs.getString("description"));
            c.setEtat(rs.getInt("etat"));
            c.setDebut(rs.getString("debut"));
            c.setFin(rs.getString("fin"));

            myList.add(c);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return myList;
}
}


    
