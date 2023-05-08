/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.controllers.conge;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Conge.Conge;
import COM.HRSTORMDESKTOP.services.conge.ServiceConge;

/**
 *
 * @author conta
 */
public class ModifierConge  implements Initializable  {
    
    
    @FXML
    private TextField id;
    
    @FXML
    private TextField desc;
    @FXML
    private TextField categorie;
    @FXML
        private DatePicker dp_debut;    
    @FXML
    private DatePicker dp_fin;



    
    Conge c =new Conge();
    
     public void showDetails(Conge Conge) {
String cat=Conge.getCategorie();
         System.out.println(cat);
         categorie.setText(Conge.getCategorie());
         desc.setText(Conge.getDescription());
        
        c = Conge;
         System.out.println(c);
         System.out.println("hereeee");
    }
     
     
      @FXML
    private void handleInsert(ActionEvent event) {
        
            String description = desc.getText();
            String categori = categorie.getText();
            
            LocalDate selectedDate = dp_debut.getValue();
            String debut = selectedDate.toString();
            
            LocalDate selectedDateF = dp_debut.getValue();
            String fin = selectedDateF.toString();

     if (categori.isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Controle de saisie");
            al.setContentText("Veuillez remplir tout les champs !!");
            al.show();
            return;
        }    
   
    ServiceConge cp = new ServiceConge();

    c.setCategorie(categori);
    c.setDescription(description);
    c.setDebut(debut);
    c.setFin(fin);

    
    try {
        cp.updateOne(c);
        Alert al = new Alert(Alert.AlertType.INFORMATION);
         al.setTitle("congé Modifié");
            al.setContentText("congé Modifié");
            al.show();
            System.out.println("congé Modifié" + c);
// Close the window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
            return;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erreur lors de l'insertion de la Conge");
    }

}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}
