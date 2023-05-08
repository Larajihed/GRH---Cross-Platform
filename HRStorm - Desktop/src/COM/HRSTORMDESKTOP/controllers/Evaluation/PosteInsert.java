package COM.HRSTORMDESKTOP.controllers.Evaluation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.models.Evaluation.Poste;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceCompetence;
import COM.HRSTORMDESKTOP.services.Evaluation.ServicePoste;

public class PosteInsert implements Initializable {

    @FXML
    private TextField nomField;

    @FXML
    private TextField missionsArea;

    @FXML
    private TextField descriptionArea;

    @FXML
    private TextField salaireMinField;

    @FXML
    private TextField salaireMaxField;

    @FXML
    private ListView<Competence> competencesList;

private ObservableList<Competence> initialCompetencesList = FXCollections.observableArrayList();

    private List<Competence> competences = new ArrayList<>();
   

    @FXML
    private void handleInsert(ActionEvent event) {
        
                 String nom = nomField.getText();

        if (nom.isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Controle de saisie");
            al.setContentText("Veuillez remplir tout les champs !!");
            al.show();
            return;
        }   
        
        
        String missions = missionsArea.getText();
        String description = descriptionArea.getText();
        Float salaireMin = Float.parseFloat(salaireMinField.getText());
        Float salaireMax = Float.parseFloat(salaireMaxField.getText());
        List<Competence> competences = competencesList.getSelectionModel().getSelectedItems();
        if (nom.isEmpty() || missions.isEmpty() || description.isEmpty() || salaireMin < 0 || salaireMax < 0 || salaireMin > salaireMax || competences.isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Controle de saisie");
            al.setContentText("Veuillez remplir tous les champs correctement !!");
            al.show();
            return;
        }

        ServicePoste sp = new ServicePoste();

        Poste newPoste = new Poste(nom, missions, description, competences, salaireMin, salaireMax);

        try {
            sp.insert(newPoste,competences);
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Poste Ajouté");
            al.setContentText("Poste Ajouté");
            al.show();
            System.out.println("Poste inserted: " + newPoste);

            // Close the window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion du poste");
        }

    }

    @FXML
    private void showAddCompetence(ActionEvent event) {
     /*
        Competence selectedCompetence = AddCompetenceDialog.showAddCompetenceDialog();
        if (selectedCompetence != null) {
            competences.add(selectedCompetence);
            initialCompetencesList.add(selectedCompetence);
        }
*/
        
        
        

    }

    @FXML
    private void showDeleteCompetence(ActionEvent event) {
        Competence selectedCompetence = competencesList.getSelectionModel().getSelectedItem();

        if (selectedCompetence != null) {
            competences.remove(selectedCompetence); 
            initialCompetencesList.remove(selectedCompetence);
        }
    }

@Override
public void initialize(URL url, ResourceBundle rb) {
    ServiceCompetence cp = new ServiceCompetence();
    initialCompetencesList = FXCollections.observableArrayList();
    try {
        initialCompetencesList.addAll(cp.selectAll());
    } catch (SQLException ex) {
        Logger.getLogger(PosteInsert.class.getName()).log(Level.SEVERE, null, ex);
    }

    competencesList.setItems(initialCompetencesList);
    competencesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    competencesList.setCellFactory(new Callback<ListView<Competence>, ListCell<Competence>>() {
        @Override
        public ListCell<Competence> call(ListView<Competence> param) {
            return new ListCell<Competence>() {
                @Override
                protected void updateItem(Competence competence, boolean empty) {
                    super.updateItem(competence, empty);
                    if (empty || competence == null) {
                        setText("");
                    } else {
                        setText("Nom: " + competence.getNom() + ", Description: " + competence.getDescription());
                    }
                }
            };
        }
    });
    initialCompetencesList = FXCollections.observableArrayList();
    competencesList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            competences.add(newSelection);
            initialCompetencesList.setAll(competences);
        }
    });
}




}
