/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.controllers.conge;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import  COM.HRSTORMDESKTOP.models.Conge.Conge;
import COM.HRSTORMDESKTOP.models.Conge.SoldeConge;
import COM.HRSTORMDESKTOP.services.conge.ServiceConge;
import COM.HRSTORMDESKTOP.services.conge.ServiceSoldeConge;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * @author marwen
 */
public class Dashboard  implements Initializable {
    
        private Stage showCongesStage;
        private Stage showSoldeCongesStage;
            @FXML
    private ImageView backButton;


       @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
   // Navigation 
    
   @FXML
private void showConges(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/ShowCongeFXML.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ListView<Conge> listView = (ListView<Conge>) root.lookup("#CongesList");
        // Set the cell factory to use custom cell rendering
        listView.setCellFactory(param -> new CongeCell());
        // Retrieve the list of Conges
        ServiceConge cp = new ServiceConge();
        List<Conge> Conges = cp.selectAll();
        // Set the items of the ListView
        ObservableList<Conge> items = FXCollections.observableArrayList(Conges);
        listView.setItems(items);
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des Conges");
        st.setScene(scene);
        st.show();
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
}
   @FXML
private void showAdminConges(ActionEvent event) {
    try {
        System.out.println("sss");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/AdminListeCongeFXML.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ListView<Conge> listView = (ListView<Conge>) root.lookup("#CongesList");
        // Set the cell factory to use custom cell rendering
        listView.setCellFactory(param -> new CongeCell());
        // Retrieve the list of Conges
        ServiceConge cp = new ServiceConge();
        List<Conge> Conges = cp.selectAllAdmin();
        // Set the items of the ListView
        ObservableList<Conge> items = FXCollections.observableArrayList(Conges);
        listView.setItems(items);
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des Conges");
        st.setScene(scene);
        st.show();
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    } catch (SQLException ex) {
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


   @FXML
private void showSoldeConges(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/ShowSoldeCongeFXML.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ListView<SoldeConge> listView = (ListView<SoldeConge>) root.lookup("#SoldeCongesList");
        // Set the cell factory to use custom cell rendering
        listView.setCellFactory(param -> new SoldeCongeCell());
        // Retrieve the list of SoldeConges
        ServiceSoldeConge cp = new ServiceSoldeConge();
        List<SoldeConge> SoldeConges = cp.selectAll();
        // Set the items of the ListView
        ObservableList<SoldeConge> items = FXCollections.observableArrayList(SoldeConges);
        listView.setItems(items);
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des SoldeConges");
        st.setScene(scene);
        st.show();
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
}
   

    
public class CongeCell extends ListCell<Conge> {

    @Override
    protected void updateItem(Conge Conge, boolean empty) {
        super.updateItem(Conge, empty);

        if (empty || Conge == null) {
            setText(null);
            setGraphic(null);
        } else {
            VBox vbox = new VBox();
            vbox.setSpacing(5);

            int id = Conge.getId_User();
            System.out.println(id);
            ServiceConge cp = new ServiceConge();
            String email = null;
            /*try {
                email = cp.getEmail(id);
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
            Label emailLabel = new Label("Email: " + email);
            Label categorieLabel = new Label("Catégorie: " + Conge.getCategorie());
            Label descriptionLabel = new Label("Description: " + Conge.getDescription());
            Label debutLabel = new Label("Début: " + Conge.getDebut());
            Label finLabel = new Label("Fin: " + Conge.getFin());
            Label etatLabel = new Label();
            int eta = Conge.getEtat(); 
            if (eta == 0) {
                etatLabel.setText("Etat:En attente");
            } else if (eta == 1) {
                etatLabel.setText("Etat: Accepté");
            } else {
                etatLabel.setText("Etat: Refusé");
            }
            vbox.getChildren().addAll(emailLabel, categorieLabel, descriptionLabel, debutLabel, finLabel ,etatLabel);
            setGraphic(vbox);
        }
    }
}
    
    public class SoldeCongeCell extends ListCell<SoldeConge> {
    
    @Override
    protected void updateItem(SoldeConge SoldeConge, boolean empty) {
        super.updateItem(SoldeConge, empty);
        
        if (empty || SoldeConge == null) {
            setText(null);
            setGraphic(null);
        } else {
            VBox vbox = new VBox();
            vbox.setSpacing(5);
            
            Label nomLabel = new Label("Employé: " + SoldeConge.getId_user());
            Label descriptionLabel = new Label("Description: " + SoldeConge.getSolde());
            
            vbox.getChildren().addAll(nomLabel, descriptionLabel);
            setGraphic(vbox);
        }
    }
    
    
}
}
