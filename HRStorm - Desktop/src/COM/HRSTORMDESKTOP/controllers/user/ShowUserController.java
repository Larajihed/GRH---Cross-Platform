package COM.HRSTORMDESKTOP.controllers.user;

import COM.HRSTORMDESKTOP.controllers.user.DetailsUserController;
import COM.HRSTORMDESKTOP.models.user.User;
import COM.HRSTORMDESKTOP.services.user.ImpServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

public class ShowUserController implements Initializable {

    @FXML
    private TextField nomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ListView<User> usersList;
                @FXML
    private ImageView backButton;
    
    @Override
public void initialize(URL url, ResourceBundle rb) {
    ImpServiceUser su = new ImpServiceUser();
    List<User> users = su.Getall();
    ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
    usersList.setItems(observableUsers);

    usersList.setOnMouseClicked(event -> {
        try {
            handleUserClick(event);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    });
}

    @FXML
    private void handleUserClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) { // Only handle double-click events

            User selectedUser = usersList.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/DetailsUserFXML.fxml"));
                Parent root = loader.load();
                DetailsUserController detailsController = loader.getController();
                detailsController.showDetails(selectedUser);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Détails de l'utilisateur");
                stage.setScene(scene);
                stage.show();

            }
        }
    }

    @FXML
    private void showAddUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/AjouterUserFXML.fxml"));
            Parent root = loader.load();
            // Get the ListView from the FXML file
            ImpServiceUser su = new ImpServiceUser();
            List<User> users = su.Getall();
            Scene scene = new Scene(root);
            Stage st = new Stage();
            st.setTitle("Liste des utilisateurs");
            st.setScene(scene);
            st.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
      @FXML
void redirectToDash(MouseEvent event) {
                Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/MainDashboard.fxml"));
            Scene c = new Scene(root);
                        Stage stage = (Stage) backButton.getScene().getWindow();
             stage.setScene(c);
           
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }}
}
