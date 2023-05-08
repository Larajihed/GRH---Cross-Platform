package COM.HRSTORMDESKTOP.controllers.user;

import COM.HRSTORMDESKTOP.controllers.user.ModifierUserController;
import COM.HRSTORMDESKTOP.models.user.User;
import COM.HRSTORMDESKTOP.services.user.ImpServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DetailsUserController {

    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label rolesLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label nomsocieteLabel;

    @FXML
    private Label isVerifiedLabel;

    private User user;

    public void showDetails(User user) {
        nomLabel.setText(user.getNom());
        prenomLabel.setText(user.getPrenom());
        rolesLabel.setText(user.getRoles());
        emailLabel.setText(user.getEmail());
        nomsocieteLabel.setText(user.getNomsociete());

        this.user = user;
    }

    @FXML
    private void editUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/ModifierUserFXML.fxml"));
        Parent root = loader.load();

        ModifierUserController modifierUserController = loader.getController();
        modifierUserController.showDetails(user);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Modification de l'utilisateur");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteUser(ActionEvent event) throws SQLException {
        ImpServiceUser su = new ImpServiceUser();
        su.Delete(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Utilisateur supprimé");
        alert.setContentText("Utilisateur supprimé");
        alert.show();
        // Close the window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        System.out.println("back");
    }
}
