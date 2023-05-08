/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.user;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.Config;
import COM.HRSTORMDESKTOP.models.user.User;
import COM.HRSTORMDESKTOP.services.user.ImpServiceUser;
import COM.HRSTORMDESKTOP.utils.PasswordEncryption;

/**
 * FXML Controller class
 *
 * @author Moetez
 */
public class ResetPasswordController implements Initializable {

    @FXML
    private TextField VerifCode;
    @FXML
    private Button changePwdButton;
    @FXML
    private PasswordField NewPassword;
    @FXML
    private PasswordField ConfirmedPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    private User ResetPassword (ActionEvent event) throws IOException, NoSuchAlgorithmException, NoSuchProviderException{
        
        
        
        ImpServiceUser su = new ImpServiceUser();
        User u = Config.getUserStatic();
        User u1 = new User();
        
        if(!VerifCode.getText().equals(Config.getVerificationCode())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Verification Code is invalid!!");
            alert.show();
        }else {
            if(NewPassword.getText().equals(ConfirmedPassword.getText())&&(ConfirmedPassword.getText().length()!=0)&&(NewPassword.getText().length()!=0)){
            u1 = su.GetByMail(u.getEmail());
            PasswordEncryption ipe= new PasswordEncryption();
            String salt = ipe.getSalt();
            u1.setPassword(ipe.Encrypt(NewPassword.getText(),salt));
            
            
            su.Update(u1);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setContentText("Password is updated successfully./nRedirecting to login page.");
            alert.show();
            redirectToLogin();
            
            }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Enter / confirm your new password!!");
            alert.show();
            
            }
      
        }
        
    return u;
    }
    
    @FXML
    private void redirectToLogin() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/Login.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/COM/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) VerifCode.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
}
