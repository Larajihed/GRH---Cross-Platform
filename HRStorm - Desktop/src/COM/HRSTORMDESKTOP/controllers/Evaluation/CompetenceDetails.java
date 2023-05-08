/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.controllers.Evaluation;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceCompetence;

public class CompetenceDetails {

    @FXML
    private Label nomLabel;

    @FXML
    private Label descriptionLabel;
    
    
    private Competence c;

    public void showDetails(Competence competence) {
        nomLabel.setText(competence.getNom());
        descriptionLabel.setText(competence.getDescription());
         c = competence;
    }
    
    
    @FXML
    private void editCompetence(ActionEvent event) throws IOException  {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/updateCompetence.fxml"));
       Parent root = loader.load();
        
       
       try {
        updateCompetence updateCompetence = loader.getController();
        updateCompetence.showDetails(c);
     } catch (Exception e) {
        e.printStackTrace();
}

       Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Mofification de comptence");
            stage.setScene(scene);
            stage.show();   
    }

    @FXML
    private void deleteCompetence(ActionEvent event) throws SQLException {
            ServiceCompetence cp = new ServiceCompetence();
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
        System.out.println("Erreur lors de suppersion de la competence");
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        System.out.println("back");
    }
}
