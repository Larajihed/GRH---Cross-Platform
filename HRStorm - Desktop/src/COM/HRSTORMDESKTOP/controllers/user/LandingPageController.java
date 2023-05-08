/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.user;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import com.client.login.*;

/**
 * FXML Controller class
 *
 * @author Moetez
 */
public class LandingPageController implements Initializable {


    @FXML
    private Button signin;
        @FXML
    private Button ButtPostuler;
            @FXML
    private Button ChatButton;

    /**
     * Initializes the controller class.
     */
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   //@FXML
  /*  private void RedirectToChat(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("views/user/AchatPack.fxm"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/COM/HRSTORMDESKTOP/bootstrap.css");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    } */

 @FXML
    void RedirectToChat(ActionEvent event) {

                try {
   Parent parent = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/LoginView.fxml"));
                    Scene c = new Scene(parent);
             Stage s = (Stage) ChatButton.getScene().getWindow();
       s.setScene(c);
        s.setTitle("Socket Chat : HRSTORM version 0.3");
        s.setResizable(false);
       s.setScene(c);
        s.show();
        s.setOnCloseRequest(e -> Platform.exit());
    } catch (IOException ex){
                    System.err.println(ex.getMessage());
    }
    }
    
        @FXML
    private void RedirectToPostuler(ActionEvent event) {
                Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Recrutement/ListRecrutmentCondFXML.fxml"));
            Scene c = new Scene(root);
                        Stage stage = (Stage) ButtPostuler.getScene().getWindow();
             stage.setScene(c);
           
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @FXML
    private void RedirectToLogin(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/Login.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("bootstrap.css");
            
            Stage stage = (Stage) signin.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
}

