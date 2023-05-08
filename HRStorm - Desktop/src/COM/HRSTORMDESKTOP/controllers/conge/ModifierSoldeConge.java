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
import COM.HRSTORMDESKTOP.models.Conge.SoldeConge;
import COM.HRSTORMDESKTOP.services.conge.ServiceSoldeConge;
        

/**
 *
 * @author conta
 */
public class ModifierSoldeConge  implements Initializable  {
    
    
    @FXML
    private TextField id;

    @FXML
    private TextField solde;



    
    SoldeConge c =new SoldeConge();
    
     public void showDetails(SoldeConge SoldeConge) {

solde.setText(String.valueOf(SoldeConge.getSolde()));
        
        c = SoldeConge;
         System.out.println(c);
         System.out.println("hereeee");
    }
     
     
      @FXML
    private void handleInsert(ActionEvent event) {
        
            String sold = solde.getText();
            int x = Integer.parseInt(sold);
            


     if (sold.isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Controle de saisie");
            al.setContentText("Veuillez remplir tout les champs !!");
            al.show();
            return;
        }    
   
    ServiceSoldeConge cp = new ServiceSoldeConge();

    c.setSolde(x);
 

    
    try {
        cp.updateOne(c);
        Alert al = new Alert(Alert.AlertType.INFORMATION);
         al.setTitle("Solde Modifié");
            al.setContentText("Solde Modifié");
            al.show();
            System.out.println("Solde Modifié" + c);
// Close the window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
            return;
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erreur lors de l'insertion de la SoldeConge");
    }

}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}
