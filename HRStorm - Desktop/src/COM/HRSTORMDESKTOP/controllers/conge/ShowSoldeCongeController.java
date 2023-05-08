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
import COM.HRSTORMDESKTOP.models.Conge.SoldeConge;
import COM.HRSTORMDESKTOP.services.conge.ServiceSoldeConge;


/**
 * FXML Controller class
 *
 * @author Marwen
 */



public class ShowSoldeCongeController implements Initializable  {

    @FXML
    private TextField nomField;
    
    @FXML
    private TextField descriptionField;

    @FXML
    private ListView<SoldeConge> SoldeCongesList;

      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
       SoldeCongesList.setOnMouseClicked(event -> {
    try {
        handleSoldeCongeClick(event);
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
});

    }    

    @FXML
private void handleSoldeCongeClick(MouseEvent event) throws IOException {
    if (event.getClickCount() == 2) { // Only handle double-click events
        SoldeConge selectedSoldeConge = SoldeCongesList.getSelectionModel().getSelectedItem();
        if (selectedSoldeConge != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/DetailsSoldeCongeFXML.fxml"));
            Parent root = loader.load();
            DetailsSoldeCongeController detailsController = loader.getController();
            detailsController.showDetails(selectedSoldeConge);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Détails de SoldeConge");
            stage.setScene(scene);
            stage.show();
        }
    }
}

    
    @FXML
    private void showAddSoldeConge(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/AjouterSoldeCongeFXML.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ServiceSoldeConge cp = new ServiceSoldeConge();
        List<SoldeConge> SoldeConges = cp.selectAll();
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des SoldeConges");
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
