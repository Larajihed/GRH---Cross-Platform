package COM.HRSTORMDESKTOP.controllers.conge;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Conge.Conge;
import COM.HRSTORMDESKTOP.services.conge.ServiceConge;
import javafx.scene.control.Button;


/**
 * FXML Controller class
 *
 * @author Marwen
 */



public class AdminListeCongeController implements Initializable  {

    
    
    @FXML
    private TextField recherche;
    

    @FXML
    private ListView<Conge> CongesList;
    @FXML
    private Button chartButton;

      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
       CongesList.setOnMouseClicked(event -> {
    try {
        handleCongeClick(event);
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
});

    }    

private void handleCongeClick(MouseEvent event) throws IOException {
    if (event.getClickCount() == 2) { // Only handle double-click events
        Conge selectedConge = CongesList.getSelectionModel().getSelectedItem();
        if (selectedConge != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/AdminCongeDetailFXML.fxml"));
            Parent root = loader.load();
            AdminCongeDetailController detailsController = loader.getController();
            detailsController.showDetails(selectedConge);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Détails de conge");
            stage.setScene(scene);
            stage.show();
        }
    }
}

@FXML
private void showChart(ActionEvent event) {
    try {
        ServiceConge serviceConge = new ServiceConge();
        List<Conge> Conges = serviceConge.selectAllAdmin();
        int countAttente = 0;
        int countAccepte = 0;
        int countRefuse = 0;
        for (Conge conge : Conges) {
            switch (conge.getEtat()) {
                case 0:
                    countAttente++;
                    break;
                case 1:
                    countAccepte++;
                    break;
                case 2:
                    countRefuse++;
                    break;
            }
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("En attente", countAttente),
                new PieChart.Data("Accepté", countAccepte),
                new PieChart.Data("Refusé", countRefuse));
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Statistiques de Congés");
        Group root = new Group(chart);
        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Graphique de Congés");
        stage.show();
    } catch (SQLException ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Erreur lors du chargement des Congés.");
        alert.showAndWait();
        ex.printStackTrace();
    }
}


    
    private void showAddConge(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AjouterCongeFXML.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ServiceConge cp = new ServiceConge();
        List<Conge> Conges = cp.selectAllAdmin();
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des Conges");
        st.setScene(scene);
        st.show();
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
}
    @FXML
    private void Recherche(ActionEvent event) {
        ServiceConge se = new ServiceConge();
        String chaine = recherche.getText();
        populateTable(se.chercher(chaine));
    }

    private void populateTable(ObservableList<Conge> branlist) {
        CongesList.setItems(branlist);

    }
    
  


}
