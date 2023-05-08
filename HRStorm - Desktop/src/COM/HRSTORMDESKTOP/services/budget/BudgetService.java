/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.services.budget;

/**
 *
 * @author ihebt
 */
import java.util.List;
import COM.HRSTORMDESKTOP.models.budget.budget;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

public class BudgetService implements BudgetInterface<budget> {

    Connection cnx;

    public BudgetService() {
        cnx = DBConnector.getInstance().getCnx();
    }

    @Override
    public void ajouter(budget t) {
        try {
            String sql = "insert into budget(id,budget,date,prime,budget_materiel,budget_salaire,budget_service)"
                    + "values (?,?,?,?,?,?,?)";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getId());
            ste.setFloat(2, t.getBudget());
            ste.setDate(3, Date.valueOf(t.getDate()));
            ste.setFloat(4, t.getPrime());
            ste.setFloat(5, t.getBudget_materiel());
            ste.setFloat(6, t.getBudget_salaire());
            ste.setFloat(7, t.getBudget_service());

            ste.executeUpdate();
            System.out.println("budget ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<budget> getAll() {

        List<budget> budgets = new ArrayList<>();
        try {
            String sql = "select * from budget ";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);

            while (s.next()) {
                budget m = new budget(s.getInt("id"), s.getFloat("budget"), s.getDate("date").toLocalDate(), s.getFloat("prime"), s.getFloat("budget_materiel"), s.getFloat("budget_salaire"), s.getFloat("budget_service"));
                budgets.add(m);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return budgets;

    }

    @Override
    public void supprimer_budget(budget t) {
        String sql = "delete from budget where id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setInt(1, t.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier_budget(float budget, LocalDate d, float prime, float budget_materiel, float budget_Service, float budget_salaire, budget t) {
        String sql = "update budget set budget=?,date=?,prime=?,budget_materiel=?,budget_salaire=?,budget_service=? where id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setFloat(1, budget);
            ste.setDate(2, Date.valueOf(d));
            ste.setFloat(3, prime);
            ste.setFloat(4, budget_materiel);
            ste.setFloat(5, budget_salaire);
            ste.setFloat(6, budget_Service);
            ste.setInt(7, t.getId());

            ste.executeUpdate();
            System.out.println("modification effectuée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /* public List<budget> rechercherParBudget(double budget) throws SQLException {
    String query = "SELECT * FROM budget WHERE budget = ?";
    List<budget> list = new ArrayList<>();
    try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
        preparedStatement.setDouble(1, budget);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                //double budget = resultSet.getDouble("budget");
                float categorie = resultSet.getString("categorie");
                String description = resultSet.getString("description");
                budget b = new budget(id, date, montant, categorie, description);
                list.add(b);
            }
        }
    }
    return list;
}*/
    public List<budget> RechercheBudget(float budget) {
        List<budget> bud = new ArrayList<>();
        try {
            String req = "select * from budget WHERE budget = '" + budget + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                budget b = new budget();
                b.setId(rs.getInt("id"));
                b.setBudget(rs.getFloat("budget"));
                b.setDate(rs.getDate("date").toLocalDate());
                b.setPrime(rs.getFloat("prime"));
                b.setBudget_materiel(rs.getFloat("budget_materiel"));
                b.setBudget_salaire(rs.getFloat("Budget_salaire"));
                b.setBudget_service(rs.getFloat("Budget_service"));
                bud.add(b);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return bud;
    }

    public ObservableList<PieChart.Data> getPieChart() throws SQLException {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String query = "SELECT SUM(budget), date FROM budget GROUP BY date ASC;";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                Double budget = rs.getDouble("SUM(budget)");
                data.add(new PieChart.Data(date.toString(), budget));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des données de la base de données : " + ex.getMessage());
            throw ex;
        }

        return data;
    }

    public ObservableList<budget> tri(String colone, boolean ASC) {
        String sql = String.format("SELECT * FROM budget order by %s ", colone);
        Connection cnx = DBConnector.getInstance().getCnx();
        ObservableList<budget> myList = FXCollections.observableArrayList();

        try {
            Statement ste = cnx.createStatement();
            PreparedStatement stee = cnx.prepareStatement(sql);
            //stee.setString(1, colone);
            ResultSet rs = stee.executeQuery();

            while (rs.next()) {
                budget b = new budget();
                b.setId(rs.getInt("id"));
                b.setBudget(rs.getFloat("budget"));
                b.setDate(rs.getDate("date").toLocalDate());
                b.setPrime(rs.getFloat("prime"));
                b.setBudget_materiel(rs.getFloat("budget_materiel"));
                b.setBudget_salaire(rs.getFloat("Budget_salaire"));
                b.setBudget_service(rs.getFloat("Budget_service"));

                myList.add(b);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myList;

    }

    public ObservableList<budget> chercher(String chaine) {
        String sql = "SELECT * FROM budget WHERE (budget LIKE ? or date LIKE ? or budget_materiel LIKE ? or budget_salaire LIKE ? or budget_service = ? )";

        Connection cnx = DBConnector.getInstance().getCnx();
        String ch = "" + chaine + "%";
        System.out.println(sql);
        ObservableList<budget> myList = FXCollections.observableArrayList();
        try {

            Statement ste = cnx.createStatement();
            // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
            PreparedStatement stee = cnx.prepareStatement(sql);
            stee.setString(1, ch);
            stee.setString(2, ch);
            stee.setString(3, ch);
            stee.setString(4, ch);
            stee.setString(5, ch);
            System.out.println(stee);

            ResultSet rs = stee.executeQuery();
            while (rs.next()) {
                budget b = new budget();
                b.setId(rs.getInt("id"));
                b.setBudget(rs.getFloat("budget"));
                b.setDate(rs.getDate("date").toLocalDate());
                b.setPrime(rs.getFloat("prime"));
                b.setBudget_materiel(rs.getFloat("budget_materiel"));
                b.setBudget_salaire(rs.getFloat("Budget_salaire"));
                b.setBudget_service(rs.getFloat("Budget_service"));

                myList.add(b);
                System.out.println("titre trouvé! ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public File exportExcel() throws IOException, SQLException {
    ObservableList<budget> list = (ObservableList<budget>) getAll();
    if (list == null || list.isEmpty()) {
        return null;
    }
    File file = new File("budget.csv");
    Writer writer = new BufferedWriter(new FileWriter(file));

    for (budget b : list) {
        String text = b.getBudget() + "," + b.getDate() + "," + b.getPrime() + ","
                + b.getBudget_materiel() + "," + b.getBudget_salaire() + "," + b.getBudget_service() + "\n";
        writer.write(text);
    }

    writer.flush();
    writer.close();
    return file;
}


    @Override
    public List<Float> getBudget() {
        List<Float> budgets = new ArrayList<>();
        try {
            String sql = "select budget from budget ";
            Statement ste = cnx.createStatement();
            ResultSet s = ste.executeQuery(sql);

            while (s.next()) {
                budgets.add(s.getFloat("budget"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return budgets;
    }

    public String getDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getPrime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getBudget_materiel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getBudget_salaire() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getBudget_service() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
