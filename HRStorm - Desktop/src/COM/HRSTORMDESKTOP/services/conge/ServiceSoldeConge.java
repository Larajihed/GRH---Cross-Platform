/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.services.conge;

/**
 *
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import COM.HRSTORMDESKTOP.models.Conge.SoldeConge;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

public class ServiceSoldeConge implements IService<SoldeConge> {

    private Connection cnx = DBConnector.getInstance().getCnx();

    @Override
    public void insertOne(SoldeConge soldeConge) throws SQLException {
        String req = "INSERT INTO `solde_conge`(`solde`) VALUES (?)";

        PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);

 
        ps.setInt(1, soldeConge.getSolde());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            soldeConge.setId(rs.getInt(1));
            System.out.println("SoldeConge inserted: " + soldeConge);
        } else {
            System.err.println("Failed to retrieve auto-generated id for SoldeConge: " + soldeConge);
        }
    }

    @Override
    public void updateOne(SoldeConge soldeConge) throws SQLException {
        String req = "UPDATE `solde_conge` SET `solde` = ? WHERE `id_user_id` = ?";
        System.out.println(req);
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, soldeConge.getSolde());
        ps.setInt(2, soldeConge.getId_user());
        ps.executeUpdate();
        System.out.println("SoldeConge updated: " + soldeConge);

    }

    @Override
    public void deleteOne(SoldeConge soldeConge) throws SQLException {
        String req = "DELETE FROM `solde_conge` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, soldeConge.getId());
        ps.executeUpdate();
        System.out.println("SoldeConge deleted: " + soldeConge);
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String req = "DELETE FROM `solde_conge` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("SoldeConge deleted with id = " + id);
    }

   @Override
public List<SoldeConge> selectAll() throws SQLException {
    List<SoldeConge> soldeConges = new ArrayList<>();
    String req = "SELECT * FROM `solde_conge`";
    Statement st = cnx.createStatement();
    ResultSet rs = st.executeQuery(req);
    while (rs.next()) {
        SoldeConge soldeConge = new SoldeConge();
        soldeConge.setId_user(rs.getInt("id_user_id"));
        System.out.println(rs.getInt("id_user_id"));
        soldeConge.setSolde(rs.getInt("solde"));
        soldeConges.add(soldeConge);
    }
    return soldeConges;
}
}