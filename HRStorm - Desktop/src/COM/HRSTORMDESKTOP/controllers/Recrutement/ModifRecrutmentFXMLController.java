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
import javafx.scene.control.TextField;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

/**
 * FXML Controller class
 *
 * @author usoum
 */
public class ModifRecrutmentFXMLController implements Initializable {
    
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
    
    ListRecrutmentResFXMLController lrr = new ListRecrutmentResFXMLController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        titre.setText(lrr.getTitreRecrutement());
        description.setText(lrr.getDescriptionRecrutement());
        nbrposte.setText(String.valueOf(lrr.getNbPosteRecrutement()));
        salaire.setText(String.valueOf(lrr.getSalaireRecrutement()));
        type.setText(lrr.getTypeRecrutement());
    }    

    @FXML
    private void ModifierRecrutement(ActionEvent event) {
        try {
            String req = "UPDATE recrutement SET titre=?,description=?,nbrposte=?,salaire=?,type=? WHERE id="+lrr.getIdRecrutement();
            PreparedStatement pst = cnx.prepareStatement(req);

            pst.setString(1, titre.getText());
            pst.setString(2, description.getText());
            pst.setString(3, nbrposte.getText());
            pst.setString(4, salaire.getText());
            pst.setString(5, type.getText());

            pst.executeUpdate();
            Alert all = new Alert(Alert.AlertType.CONFIRMATION);
            all.setTitle("Modification Recrutement");
            all.setContentText("Informations modifiées avec succès !!");
            all.show();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
