package COM.HRSTORMDESKTOP.services.Evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.models.Evaluation.Poste;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

public class ServicePoste implements IService<Poste> {

    private Connection cnx = DBConnector.getInstance().getCnx();
    

public void insert(Poste t, List<Competence> competences) throws SQLException {
    String req = "INSERT INTO `poste`(`nom`, `missions`, `description`, `salaire_min`, `salaire_max`) "
            + "VALUES (?, ?, ?, ?, ?)";
    PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
    ps.setString(1, t.getNom());
    ps.setString(2, t.getMissions());
    ps.setString(3, t.getDescription());
    ps.setFloat(4, t.getSalaireMin());
    ps.setFloat(5, t.getSalaireMax());
    ps.executeUpdate();

    ResultSet rs = ps.getGeneratedKeys();
    if (rs.next()) {
        int id = rs.getInt(1);
        t.setId(id);
        System.out.println("Poste ajouté avec succès, ID: " + id);

        // Insert the competences for this poste into poste_competence table
        for (Competence competence : competences) {
            String competenceReq = "INSERT INTO `poste_competence`(`poste_id`, `competence_id`) VALUES (?, ?)";
            PreparedStatement competencePs = cnx.prepareStatement(competenceReq);
            competencePs.setInt(1, id);
            competencePs.setInt(2, competence.getId());
            competencePs.executeUpdate();
            System.out.println("Competence " + competence.getNom() + " ajoutée au poste " + t.getNom());
        }
    } else {
        System.err.println("Erreur lors de l'ajout du poste, ID non généré.");
    }
}



    @Override
    public void updateOne(Poste t) throws SQLException {
        String req = "UPDATE `poste` SET `nom`=?,`missions`=?,`description`=?,`salaire_min`=?,`salaire_max`=? "
                + "WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getNom());
        ps.setString(2, t.getMissions());
        ps.setString(3, t.getDescription());
        ps.setFloat(4, t.getSalaireMin());
        ps.setFloat(5, t.getSalaireMax());
        ps.setInt(6, t.getId());
        ps.executeUpdate();
        System.out.println("Poste modifié !");
    }

    @Override
    public void deleteOne(Poste t) throws SQLException {
        String req = "DELETE FROM `poste` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getId());
        ps.executeUpdate();
        System.out.println("Poste supprimé !");
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String req = "DELETE FROM `poste` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Poste supprimé !");
    }

    @Override
    public List<Poste> selectAll() throws SQLException {
        List<Poste> postes = new ArrayList<>();
        String req = "SELECT * FROM `poste` p "
                
               
                + "JOIN `poste_competence` cp ON p.`id`=cp.`poste_id` "
                + "JOIN `competence` c ON cp.`competence_id`=c.`id`";


        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        int prevId = -1;
        Poste p = null;
        while (rs.next()) {
            int id = rs.getInt("id");
            if (id != prevId) {
                if (p!= null) {
postes.add(p);
}
p = new Poste();
p.setId(id);
p.setNom(rs.getString("nom"));
p.setMissions(rs.getString("missions"));
p.setDescription(rs.getString("description"));
p.setSalaireMin(rs.getFloat("salaire_min"));
p.setSalaireMax(rs.getFloat("salaire_max"));

//p.setCompetences(new ArrayList<>());
prevId = id;
}
            
Competence c = new Competence();
c.setId(rs.getInt("competence_id"));
c.setNom(rs.getString("nom"));
c.setDescription(rs.getString("description"));
p.getCompetences().add(c);

}
if (p != null) {
postes.add(p);
}
return postes;
}
    

public Poste selectOne(int id) throws SQLException {
    String req = "SELECT * FROM `poste` p "
            + "JOIN `poste_competence` cp ON p.`id`=cp.`poste_id` "
            + "JOIN `competence` c ON cp.`competence_id`=c.`id` "
            + "WHERE p.`id`=?";
    PreparedStatement ps = cnx.prepareStatement(req);
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    Poste p = null;
    while (rs.next()) {
        if (p == null) {
            p = new Poste();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setMissions(rs.getString("missions"));
            p.setDescription(rs.getString("description"));
            p.setSalaireMin(rs.getFloat("salaire_min"));
            p.setSalaireMax(rs.getFloat("salaire_max"));
            p.setCompetences(new ArrayList<>());
        }
        Competence c = new Competence();
        c.setId(rs.getInt("competence_id"));
        c.setNom(rs.getString("nom"));
        c.setDescription(rs.getString("description"));
        p.getCompetences().add(c);
    }
    return p;
}

    @Override
    public void insertOne(Poste t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
