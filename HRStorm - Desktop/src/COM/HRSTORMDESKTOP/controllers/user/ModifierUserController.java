package COM.HRSTORMDESKTOP.controllers.user;

import COM.HRSTORMDESKTOP.models.user.User;
import COM.HRSTORMDESKTOP.services.user.ImpServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierUserController implements Initializable {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField rolesField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nomsocieteField;

    @FXML
    private CheckBox isVerifiedCheckBox;

    private User user = new User();

    public void showDetails(User user) {
        nomField.setText(user.getNom());
        prenomField.setText(user.getPrenom());
        rolesField.setText(user.getRoles());
        emailField.setText(user.getEmail());
        nomsocieteField.setText(user.getNomsociete());

        this.user = user;
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String roles = rolesField.getText();
        String email = emailField.getText();
        String nomsociete = nomsocieteField.getText();
        boolean isVerified = isVerifiedCheckBox.isSelected();

        if (nom.isEmpty() || prenom.isEmpty() || roles.isEmpty() || email.isEmpty() || nomsociete.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Controle de saisie");
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.show();
            return;
        }

        ImpServiceUser su = new ImpServiceUser();

        user.setNom(nom);
        user.setPrenom(prenom);
        user.setRoles(roles);
        user.setEmail(email);
        user.setNomsociete(nomsociete);
        user.setIs_verified(isVerified);

        su.Update(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Utilisateur modifié");
        alert.setContentText("Utilisateur modifié");
        alert.show();
        // Close the window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
