/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.services.budget;

import COM.HRSTORMDESKTOP.models.budget.budget;
import COM.HRSTORMDESKTOP.models.budget.depense;
import java.time.LocalDate;
import java.util.List;
import java.util.List;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
/**
 *
 * @author ihebt
 */
public class DepenseService implements depenseInterface <depense,budget>{
 Connection cnx;
    public DepenseService  ()
    {
        cnx = DBConnector.getInstance().getCnx();
    }
    @Override
    public void ajouter(depense t, budget b) {
          try {
            String sql = "insert into depense(id_budget_id,nom,montant,date,justificatif,categorie)"
                    + "values (?,?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
           ste.setInt(1, b.getId());
            ste.setString(2,t.getNom());
            ste.setFloat(3,t.getMontant());
            ste.setDate(4, Date.valueOf(t.getDate()));
            ste.setString(5,t.getJustificatif() );
             ste.setString(6,t.getCategorie() );
           
            ste.executeUpdate();
            System.out.println("playlist ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<depense> getAll() {
        List<depense> depenses= new ArrayList<>();
        try {
        String sql = "SELECT depense.id, budget.budget  AS budget, depense.nom,depense.montant,depense.date,depense.justificatif,depense.categorie " +
                     "FROM depense " +
                     "JOIN budget ON depense.id_budget_id = budget.id";
        Statement ste = cnx.createStatement();
        ResultSet s = ste.executeQuery(sql);
        
        while (s.next()) {
            depense m = new depense(s.getInt("id"), new budget(s.getFloat("budget")), s.getString("nom"),s.getFloat("montant"),s.getDate("date").toLocalDate(),s.getString("justificatif"),s.getString("categorie"));
            depenses.add(m);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return depenses;
    }

    @Override
    public void supprimer_depense(depense t) {
   
       String sql = "delete from depense where id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier_depense(budget budget, String nom, float montant, LocalDate D, String justificatif, String categorie, depense t) {
  String sql = "update depense set id_budget_id=?,nom=?,montant=?,date=?,justificatif=?,categorie=? where id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, budget.getId());
            ste.setString(2, nom);
            ste.setFloat(3,montant);
            ste.setDate(4, Date.valueOf(D));
            ste.setString(5,justificatif);
            ste.setString(6,categorie);
            ste.setInt(7, t.getId());
            ste.executeUpdate();
            System.out.println("modification effectuée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    }

 


    

