/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.Recrutement;

import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import COM.HRSTORMDESKTOP.services.Recrutement.SendEmailMailtrap;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

/**
 * FXML Controller class
 *
 * @author usoum
 */
public class AjoutCondidatFXMLController implements Initializable {
    
    private Connection cnx = DBConnector.getInstance().getCnx();

    @FXML
    private DatePicker dateN;
    @FXML
    private TextField prenom;
    @FXML
    private TextField lettreM;
    @FXML
    private TextField nom;
    @FXML
    private TextField tel;
    @FXML
    private TextField email;
    
    private static String emailCan;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterCondidat(ActionEvent event) throws UnknownHostException {
        try {
                String req = "INSERT INTO candidat(idrecrutement_id, nom, prenom, datenaissance, tel, email, lettremotivation, etat) VALUES (?,?,?,?,?,?,?,0)";
                PreparedStatement pst = cnx.prepareStatement(req);
                ListRecrutmentCondFXMLController lrc = new ListRecrutmentCondFXMLController();

                pst.setInt(1, lrc.getIdRecrutement());
                pst.setString(2, nom.getText());
                pst.setString(3, prenom.getText());
                pst.setString(4, String.valueOf(dateN.getValue()));
                pst.setString(5, tel.getText());
                pst.setString(6, email.getText());
                pst.setString(7, lettreM.getText());
                
                emailCan = email.getText();

                pst.executeUpdate();
                
                SendEmailMailtrap.getSendEmail();
                
                Alert all = new Alert(Alert.AlertType.CONFIRMATION);
                all.setTitle("Condidat");
                all.setContentText("Condidat ajouté !!");
                all.show();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    

    public static String getEmailCan() {
        return emailCan;
    }

    public static void setEmailCan(String emailCan) {
        AjoutCondidatFXMLController.emailCan = emailCan;
    }
    
    
    
}
