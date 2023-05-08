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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Conge.Conge;
import COM.HRSTORMDESKTOP.services.conge.ServiceConge;


/**
 * FXML Controller class
 *
 * @author Marwen
 */



public class ShowCongeController implements Initializable  {

    @FXML
    private TextField nomField;
    
    @FXML
    private TextField descriptionField;

    @FXML
    private ListView<Conge> CongesList;

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

    @FXML
private void handleCongeClick(MouseEvent event) throws IOException {
    if (event.getClickCount() == 2) { // Only handle double-click events
        

        Conge selectedConge = CongesList.getSelectionModel().getSelectedItem();
        if (selectedConge != null) {
                                         System.out.println("fafa");
                                         System.out.println(selectedConge);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/DetailsCongeFXML.fxml"));
            Parent root = loader.load();
            DetailsCongeController detailsController = loader.getController();
            detailsController.showDetails(selectedConge);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Détails de conge");
            stage.setScene(scene);
            stage.show();
                             System.out.println("fafa");

        }
    }
}

    
    @FXML
    private void showAddConge(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/AjouterCongeFXML.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ServiceConge cp = new ServiceConge();
        List<Conge> Conges = cp.selectAll();
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
    
  


}
