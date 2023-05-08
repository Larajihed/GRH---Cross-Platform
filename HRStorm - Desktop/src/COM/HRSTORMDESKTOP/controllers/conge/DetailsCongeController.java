/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.controllers.conge;

/**
 *
 * @author conta
 */
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Conge.Conge;
import COM.HRSTORMDESKTOP.services.conge.ServiceConge;

public class DetailsCongeController {

  @FXML
    private Label nomLabel;

    @FXML
    private Label descriptionLabel;
        @FXML
    private Label debut;
    @FXML
    private Label fin;
    
    
    private Conge c;

    public void showDetails(Conge Conge) {
        nomLabel.setText(Conge.getCategorie());
        descriptionLabel.setText(Conge.getDescription());
        debut.setText(Conge.getDebut());
        fin.setText(Conge.getFin());
        
         c = Conge;

    }
    
    
    
    @FXML
    private void editConge(ActionEvent event) throws IOException  {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/ModifierCongeFXML.fxml"));
       Parent root = loader.load();
        
       System.out.println("woerkiii");
       
       try {
        ModifierConge modifierConge = loader.getController();
        modifierConge.showDetails(c);
     } catch (Exception e) {
        e.printStackTrace();
}

       Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Mofification de conge");
            stage.setScene(scene);
            stage.show();   
    }

    @FXML
    private void deleteConge(ActionEvent event) throws SQLException {
            ServiceConge cp = new ServiceConge();
        try{
            cp.deleteOne(c);
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Compétence Supprimé");
            al.setContentText("Compétence Supprimé");
            al.show();
            // Close the window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }catch(SQLException e){
            e.printStackTrace();
        System.out.println("Erreur lors de suppersion de la Conge");
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        System.out.println("back");
    }

}
