package COM.HRSTORMDESKTOP.services.Evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.models.Evaluation.Employee;
import COM.HRSTORMDESKTOP.models.Evaluation.Evaluation;
import COM.HRSTORMDESKTOP.models.Evaluation.Poste;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

public class ServiceEvaluation implements IService<Evaluation> {

    private Connection cnx = DBConnector.getInstance().getCnx();

public void insertEvaluation(Evaluation t) throws SQLException {
    String req = "INSERT INTO `evaluation`(`date`, `commentaire`, `experience`, `level`, `poste_id`, `employee_id`) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
    ps.setDate(1, new java.sql.Date(t.getDate().getTime()));
    ps.setString(2, t.getCommentaire());
    ps.setInt(3, t.getExperience());
    ps.setString(4, t.getLevel());
    ps.setInt(5, t.getPoste().getId());
    ps.setInt(6, t.getEmployee().getId()); // pass the employee_id as a parameter
    ps.executeUpdate();

    ResultSet rs = ps.getGeneratedKeys();
    if (rs.next()) {
        int id = rs.getInt(1);
        t.setId(id);
        System.out.println("Evaluation ajoutée avec succès, ID: " + id);
    } else {
        System.err.println("Erreur lors de l'ajout de l'évaluation, ID non généré.");
    }
}



    @Override
    public void updateOne(Evaluation t) throws SQLException {
        String req = "UPDATE `evaluation` SET `date`=?,`commentaire`=?,`experience`=?,`level`=?,`poste_id`=? "
                + "WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setDate(1, new java.sql.Date(t.getDate().getTime()));
        ps.setString(2, t.getCommentaire());
        ps.setInt(3, t.getExperience());
        ps.setString(4, t.getLevel());
        ps.setInt(5, t.getPoste().getId());
        ps.setInt(6, t.getId());
        ps.executeUpdate();
        System.out.println("Evaluation modifiée !");
    }

    
    @Override
    public void deleteOne(Evaluation t) throws SQLException {
        String req = "DELETE FROM `evaluation` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getId());
        ps.executeUpdate();
        System.out.println("Evaluation supprimée !");
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String req = "DELETE FROM `evaluation` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Evaluation supprimée !");
    }
@Override
public List<Evaluation> selectAll() throws SQLException {
    List<Evaluation> evaluations = new ArrayList<>();
    String req = "SELECT * FROM `evaluation` ev";
    Statement st = cnx.createStatement();
    ResultSet rs = st.executeQuery(req);
    int prevId = -1;
    Evaluation e = null;
        
    while (rs.next()) {
        int id = rs.getInt("id");
        if (id != prevId) {
            if (e != null) {
                evaluations.add(e);
            }
            e = new Evaluation();
            e.setId(id);
            e.setDate(rs.getDate("date"));
            e.setCommentaire(rs.getString("commentaire"));
            e.setExperience(rs.getInt("experience"));
            e.setLevel(rs.getString("level"));
            Poste p = new Poste();
            p.setId(rs.getInt("poste_id"));
            e.setPoste(p);
            e.setCompetences(new ArrayList<>());
            prevId = id;
        }
    }
    
    if (e != null) {
        evaluations.add(e);
    }
    
    
    return evaluations;
}

public Collection<Evaluation> selectByPoste(Poste poste) throws SQLException {
    Collection<Evaluation> evaluations = new ArrayList<>();
    String req = "SELECT * FROM `evaluation` ev "
            + "JOIN `poste` p ON ev.`poste_id`=p.`id` "
            + "JOIN `competence_evaluation` ce ON ev.`id`=ce.`evaluation_id` "
            + "JOIN `competence` c ON ce.`competence_id`=c.`id` "
            + "WHERE ev.`poste_id`=?";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, poste.getId());
    ResultSet rs = ps.executeQuery();
    int prevId = -1;
    Evaluation e = null;
    while (rs.next()) {
        int id = rs.getInt("id");
        if (id != prevId) {
            if (e != null) {
                evaluations.add(e);
            }
            e = new Evaluation();
            e.setId(id);
            e.setDate(rs.getDate("date"));
            e.setCommentaire(rs.getString("commentaire"));
            e.setExperience(rs.getInt("experience"));
            e.setLevel(rs.getString("level"));
            e.setPoste(poste);
            e.setCompetences(new ArrayList<>());
            prevId = id;
        }
        Competence c = new Competence();
        c.setId(rs.getInt("competence_id"));
        c.setNom(rs.getString("nom"));
        c.setDescription(rs.getString("description"));
        e.getCompetences().add(c);
    }
    if (e != null) {
        evaluations.add(e);
    }
    return evaluations;
}

    @Override
    public void insertOne(Evaluation t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int countEmployeesByLevel(String level) throws SQLException {
    int count = 0;
    String req = "SELECT COUNT(*) AS count FROM `evaluation` WHERE level = ?";
    PreparedStatement st = cnx.prepareStatement(req);
    st.setString(1, level);
    ResultSet rs = st.executeQuery();
    if (rs.next()) {
        count = rs.getInt("count");
    }
    return count;
}
    
    
public Map<String, Integer> countEmployeesByLevel() throws SQLException {
    Map<String, Integer> counts = new HashMap<>();
    String req = "SELECT level, COUNT(*) as count FROM `evaluation` GROUP BY level";
    Statement st = cnx.createStatement();
    ResultSet rs = st.executeQuery(req);
    while (rs.next()) {
        String level = rs.getString("level");
        int count = rs.getInt("count");
        counts.put(level, count);
    }
    return counts;
}

public List<Evaluation> selectByDate(LocalDate startDate, LocalDate endDate) throws SQLException {
    String query = "SELECT * FROM evaluation WHERE date BETWEEN ? AND ?";
    PreparedStatement st = cnx.prepareStatement(query);
    st.setDate(1, java.sql.Date.valueOf(startDate));
    st.setDate(2, java.sql.Date.valueOf(endDate));
    ResultSet rs = st.executeQuery();

    List<Evaluation> evaluations = new ArrayList<>();
    while (rs.next()) {
        Evaluation evaluation = new Evaluation();
        evaluation.setId(rs.getInt("id"));
evaluation.setDate(rs.getDate("date"));
        evaluation.setCommentaire(rs.getString("commentaire"));
        evaluation.setExperience(rs.getInt("experience"));
        evaluation.setLevel(rs.getString("level"));

      

 
        evaluations.add(evaluation);
    }
    
    // Close resources
    rs.close();
    st.close();
    cnx.close();
    
    return evaluations;
}

    
    
}