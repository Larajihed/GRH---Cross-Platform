package COM.HRSTORMDESKTOP.services.Evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

public class ServiceCompetence implements IService<Competence> {
    
    private Connection cnx = DBConnector.getInstance().getCnx();

    @Override
    public void insertOne(Competence competence) throws SQLException {
        String req = "INSERT INTO `competence`(`nom`, `description`) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, competence.getNom());
        ps.setString(2, competence.getDescription());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            competence.setId(rs.getInt(1));
            System.out.println("Competence inserted: " + competence);
        } else {
            System.err.println("Failed to retrieve auto-generated id for competence: " + competence);
        }
    }

    @Override
    public void updateOne(Competence competence) throws SQLException {
        String req = "UPDATE `competence` SET `nom` = ?, `description` = ? WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, competence.getNom());
        ps.setString(2, competence.getDescription());
        ps.setInt(3, competence.getId());
        ps.executeUpdate();
        System.out.println("Competence updated: " + competence);
    }

    @Override
    public void deleteOne(Competence competence) throws SQLException {
        String req = "DELETE FROM `competence` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, competence.getId());
        ps.executeUpdate();
        System.out.println("Competence deleted: " + competence);
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String req = "DELETE FROM `competence` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Competence deleted with id = " + id);
    }

    @Override
    public List<Competence> selectAll() throws SQLException {
        List<Competence> competences = new ArrayList<>();
        String req = "SELECT * FROM `competence`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Competence competence = new Competence();
            competence.setId(rs.getInt("id"));
            competence.setNom(rs.getString("nom"));
            competence.setDescription(rs.getString("description"));
            competences.add(competence);
        }
        return competences;
    }
}
