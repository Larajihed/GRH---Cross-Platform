/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.Recrutement;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

/**
 * FXML Controller class
 *
 * @author usoum
 */
public class AjoutRecrutmentFXMLController implements Initializable {
    
    private Connection cnx = DBConnector.getInstance().getCnx();

    @FXML
    private TextField titre;
    @FXML
    private TextField description;
    @FXML
    private TextField nbrposte;
    @FXML
    private TextField salaire;
    @FXML
    private TextField type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterRecrutement(ActionEvent event) {
        try {
                String req = "INSERT INTO recrutement(titre, description, nbrposte, salaire, type) VALUES (?,?,?,?,?)";
                PreparedStatement pst = cnx.prepareStatement(req);

                pst.setString(1, titre.getText());
                pst.setString(2, description.getText());
                pst.setString(3, nbrposte.getText());
                pst.setString(4, salaire.getText());
                pst.setString(5, type.getText());

                pst.executeUpdate();
                Alert all = new Alert(Alert.AlertType.CONFIRMATION);
                all.setTitle("Rendez-Vous");
                all.setContentText("Recrutement ajouté !!");
                all.show();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
}
