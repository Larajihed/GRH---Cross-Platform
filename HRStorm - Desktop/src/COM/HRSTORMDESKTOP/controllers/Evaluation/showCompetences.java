package COM.HRSTORMDESKTOP.controllers.Evaluation;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import COM.HRSTORMDESKTOP.controllers.Evaluation.CompetenceDetails;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceCompetence;
import COM.HRSTORMDESKTOP.services.Evaluation.ServicePerson;


/**
 * FXML Controller class
 *
 * @author Jihed
 */



public class showCompetences implements Initializable  {

    @FXML
    private TextField nomField;
    
    @FXML
    private TextField descriptionField;

    @FXML
    private ListView<Competence> competencesList;

      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
       competencesList.setOnMouseClicked(event -> {
    try {
        handleCompetenceClick(event);
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
});

    }    

    @FXML
private void handleCompetenceClick(MouseEvent event) throws IOException {
    if (event.getClickCount() == 2) { // Only handle double-click events
        Competence selectedCompetence = competencesList.getSelectionModel().getSelectedItem();
        if (selectedCompetence != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/competenceDetails.fxml"));
            Parent root = loader.load();
            CompetenceDetails detailsController = loader.getController();
            detailsController.showDetails(selectedCompetence);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Détails de la compétence");
            stage.setScene(scene);
            stage.show();
        }
    }
}

    
    @FXML
    private void showAddCompetence(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/competenceInsert.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ServiceCompetence cp = new ServiceCompetence();
        List<Competence> competences = cp.selectAll();
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des Competences");
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
    
    

  


}
