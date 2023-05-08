 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.user;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.Config;

import COM.HRSTORMDESKTOP.models.user.User;
import COM.HRSTORMDESKTOP.services.user.ImpServiceUser;
import COM.HRSTORMDESKTOP.utils.ResetPasswordMail;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Moetez
 */
public class LoginController implements Initializable {



    
    @FXML
    private TextField EmailInput;
    @FXML
    private PasswordField PasswordInput;
    @FXML
    private Button LoginButton;
    @FXML
    private Button ResetPasswordInput;
    String notificationMessage;
        @FXML
    private Rectangle sqsq;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void Login(ActionEvent event) {
        ImpServiceUser su = new ImpServiceUser();
        User u = new User();
        u = su.Login(EmailInput.getText(), PasswordInput.getText());
        System.out.println(u);
        if (u.getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Mail / password.");
            alert.show();
        } else {
            Config.setUserStatic(u);
            redirectToMainDashboard();
        }

    }

    @FXML
    public void ResetPassword(ActionEvent event) throws Exception {
        ImpServiceUser su = new ImpServiceUser();
        User u = new User();
        u = su.GetByMail(EmailInput.getText());
        if (u.getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Mail.");
            alert.show();
        } else {
            ResetPasswordMail.sendMail(u);
            Config.setUserStatic(u);
            redirectToResetPassword();
        }
    }

    @FXML
    private void redirectToMainDashboard() {
     //   Config.showNotification("you are logged in on the " + LocalDate.now() + " \n@ " + LocalDate.now().atTime(LocalTime.now()).getHour()
         //       + ":" + LocalDate.now().atTime(LocalTime.now()).getMinute() + ":" + LocalDate.now().atTime(LocalTime.now()).getSecond(), "Welcome " + Config.UserStatic.getNom()+Config.UserStatic.getPrenom());
    //    if (!Config.UserStatic.getRoles().equals("RH_RESPONSABLE")) {
        //    Config.showNotification(notificationMessage, "Welcome " + Config.UserStatic.getNom()+Config.UserStatic.getPrenom());
       // }
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/MainDashboard.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("bootstrap.css");

            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToResetPassword() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/ResetPassword.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("bootstrap.css");

            Stage stage = (Stage) ResetPasswordInput.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @FXML
    private void redirectToLandingPage(MouseEvent event) {
        Parent root;
        
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/LandingPage.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/COM/HRSTORMDESKTOP/bootstrap.css");

            
            Stage stage = (Stage) sqsq.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

}
