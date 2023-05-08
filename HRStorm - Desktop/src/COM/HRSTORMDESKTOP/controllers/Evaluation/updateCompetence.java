/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.controllers.Evaluation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceCompetence;

/**
 *
 * @author conta
 */
public class updateCompetence  implements Initializable  {
    
    
    @FXML
    private TextField nomField;
    @FXML
    private TextField descriptionField;
    Competence c =new Competence();
    
     public void showDetails(Competence competence) {
        nomField.setText(competence.getNom());
        descriptionField.setText(competence.getDescription());
        c = competence;
         System.out.println(c);
    }
     
      @FXML
    private void handleInsert(ActionEvent event) {
        
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

    c.setNom(nom);
    c.setDescription(description);
    
    try {
        cp.updateOne(c);
        Alert al = new Alert(Alert.AlertType.INFORMATION);
         al.setTitle("Compétence Modifié");
            al.setContentText("Compétence Modifié");
            al.show();
            System.out.println("Compétence Modifié" + c);
// Close the window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
            return;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erreur lors de l'insertion de la competence");
    }

}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}
