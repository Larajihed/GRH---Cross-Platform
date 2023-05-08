package COM.HRSTORMDESKTOP.controllers.Budget;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
import javafx.scene.chart.*;

public class StatController implements Initializable {

    @FXML
    private PieChart stat;

    private Connection cnx;
    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cnx = DBConnector.getInstance().getCnx();
            stat();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
    }

    public void stat() throws SQLException {
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

        System.out.println("Données de la PieChart: " + data);
        stat.setTitle("**Statistiques des Budgets Par Date**");
        stat.setLegendSide(Side.LEFT);
        stat.setData(data);
    }
}
