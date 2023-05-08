/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.user;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
public  class AjoutUserController implements Initializable {

    @FXML
    private TextField Nom;
    @FXML
    private TextArea Nomsociete;
    @FXML
    private TextField Prenom;
    @FXML
    private DatePicker dateNaiss;
    @FXML
    private TextField Email;
    @FXML
    private TextField password;
    @FXML
    private Button Ajouter;
    User UserManagedAdd = new User();
    Config c = new Config();
    private ImpServiceUser imps = new ImpServiceUser();
    @FXML
    private ToggleButton Employe;
    @FXML
    private ToggleButton EmployeRH;
    @FXML
    private ToggleButton RespRH;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
        @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    private void Ajouter(ActionEvent event) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {

        //User statique
     
        User responsable = Config.UserStatic;
        //User statique

        User u1 = new User();
        u1 = imps.GetByMail(Email.getText());
        if (u1.getId() == 0) {{
                if ((isFullname(Nom.getText())) && (Prenom.getText().length() != 0) && (Nomsociete.getText().length()!=0)
                     && (isEmailAdress(Email.getText())) && (password.getText().length() != 0)) {
                    PasswordEncryption pe = new PasswordEncryption();
                    String salt = pe.getSalt();
                    String passwordHashed = pe.Encrypt(password.getText(), salt);
                    UserManagedAdd.setPrenom(Prenom.getText());
                    UserManagedAdd.setNom(Nom.getText());
                    UserManagedAdd.setNomsociete(Nomsociete.getText());
                    UserManagedAdd.setRoles("HREMPLOYE");
                    UserManagedAdd.setEmail(Email.getText());
                    UserManagedAdd.setPassword(passwordHashed);
                    imps.Add(UserManagedAdd);

                    Config.setUserManagedAdd(UserManagedAdd);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ajout d'utilisateur(s)");
                    alert.setContentText("Utilisateur ajouté avec succées!");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ajout d'utilisateurs");
                    alert.setContentText("Champ(s) invalide(s)");
                    alert.show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ajout d'utilisateurs");
            alert.setContentText("Mail déja utilisé!!");
            alert.show();
        }

    }

   


    @FXML
    private void ImportCSV(ActionEvent event) {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Importer un fichier csv");
        Stage stage = (Stage) Email.getScene().getWindow();
        File fileCSV = filechooser.showOpenDialog(stage);
        if (fileCSV != null) {
            imps.AddFromCSV(fileCSV.getPath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout d'utilisateur(s)");
            alert.setContentText("Liste d'utilisateurs importée avec succées.");
            alert.show();

        }
    }

    public static boolean isEmailAdress(String email) {
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }

    public static boolean isFullname(String str) {
        String expression = "^[a-zA-Z\\s]+";
        return str.matches(expression);
    }
    public static boolean isNum(String str) {
        String expression = "\\d+";
        return str.matches(expression);
    }

    @FXML
    private void redirectHome(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/MainDashboard.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) Email.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    @FXML
    private void redirectToDisplayUser(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/DisplayUser.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) Email.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
