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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Conge.Conge;
import COM.HRSTORMDESKTOP.services.conge.ServiceConge;


/**
 * FXML Controller class
 *
 * @author Jihed
 */



public class AfficherCongeController implements Initializable  {

   @FXML
    private TextField nomField;
    
    @FXML
    private TextField descriptionField;
    
    
      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
private void showConges(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/HRSTORMDESKTOP/views/conge/AfficherCongeFXML.fxml"));
        Parent root = loader.load();

        // Get the ListView from the FXML file
        ListView<Conge> listView = (ListView<Conge>) root.lookup("#lvAffiche");
        // Retrieve the list of Conges
        ServiceConge cp = new ServiceConge();
        List<Conge> Conges = cp.selectAll();
        // Set the items of the ListView
        System.out.println(Conges);
        ObservableList<Conge> items = FXCollections.observableArrayList(Conges);
        System.out.println(" here");
        listView.setItems(items);
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

