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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import  COM.HRSTORMDESKTOP.models.Evaluation.Poste;
import  COM.HRSTORMDESKTOP.services.Evaluation.ServiceCompetence;
import COM.HRSTORMDESKTOP.services.Evaluation.ServicePerson;
import COM.HRSTORMDESKTOP.services.Evaluation.ServicePoste;


/**
 * FXML Controller class
 *
 * @author Jihed
 */



public class showPostes implements Initializable  {

    @FXML
    private TextField nomField;
    
    @FXML
    private TextField descriptionField;

    @FXML
    private ListView<Poste> PostesList;

      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
       PostesList.setOnMouseClicked(event -> {
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
    /*
    if (event.getClickCount() == 2) { // Only handle double-click events
        Poste selectedPoste = postesList.getSelectionModel().getSelectedItem();
        if (selectedPoste != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/CompetenceDetails.fxml"));
            Parent root = loader.load();
            Poste detailsController = loader.getController();
            detailsController.showDetails(selectedPoste);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Détails de la compétence");
            stage.setScene(scene);
            stage.show();
        }
    }
*/
}

    
    @FXML
    private void showAddPoste(ActionEvent event) {
        
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/posteInsert.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ServicePoste sp = new ServicePoste();
        List<Poste> postes = sp.selectAll();
        
    
        
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des postes");
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
    private void handleInsert(ActionEvent event) {
        /*
         String nom = nomField.getText();
    String description = descriptionField.getText();
     if (nom.isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Controle de saisie");
            al.setContentText("Veuillez remplir tout les champs !!");
            al.show();
            return;
        }    
   
    ServiceCompetence cp = new ServiceCompetence();

    Competence newCompetence = new Competence(nom, description);
    
    try {
        cp.insertOne(newCompetence);
        Alert al = new Alert(Alert.AlertType.INFORMATION);
         al.setTitle("Compétence Ajouté");
            al.setContentText("Compétence Ajouté");
            al.show();
            System.out.println("Competence inserted: " + newCompetence);
// Close the window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
            return;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erreur lors de l'insertion de la competence");
    }
*/
}

}
