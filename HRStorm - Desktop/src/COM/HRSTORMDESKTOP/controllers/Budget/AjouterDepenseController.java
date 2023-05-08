/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.Budget;

import COM.HRSTORMDESKTOP.services.budget.BudgetService;
import COM.HRSTORMDESKTOP.services.budget.DepenseService;
import COM.HRSTORMDESKTOP.models.budget.budget;
import COM.HRSTORMDESKTOP.models.budget.depense;
import java.io.IOException;
import static java.lang.Float.parseFloat;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;

/**
 * FXML Controller class
 *
 * @author ihebt
 */
public class AjouterDepenseController implements Initializable {

    depense d = new depense();
    DepenseService ds = new DepenseService();
    BudgetService bs = new BudgetService();
    @FXML
    private TextField nom;
    @FXML
    private TextField montant;
    @FXML
    private TextField just;
    @FXML
    private ComboBox<Float> budgetCom;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> catCOM;
    @FXML
    private Button Ajout;
    @FXML
    private Button aff;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<  Float> o = FXCollections.observableArrayList(bs.getBudget());
        budgetCom.setItems(o);
        Float selected = budgetCom.getValue();
        if (!o.isEmpty()) {
            budgetCom.setValue(o.get(0));
        }
        catCOM.getItems().addAll("budget_materiel", "budget_service", "budget_salaire");
        if (!catCOM.getItems().isEmpty()) {
            catCOM.setValue(catCOM.getItems().get(0));
        }

    }

    @FXML
    private void AjouterDep(ActionEvent event) {
        montant.textProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si la nouvelle valeur saisie est un nombre réel (float)
            if (newValue != null && !newValue.matches("\\d*\\.?\\d*")) {
                // Afficher une alerte si la saisie n'est pas valide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14)");
                alert.showAndWait();

            }
        });
        
        Connection cnx = DBConnector.getInstance().getCnx();
        if (nom.getText().isEmpty() || date.getValue() == null || just.getText().isEmpty() || montant.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Ajout non effectué");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }
        if (!montant.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le montant.");
            alert.showAndWait();

        } else {
            Float Selectionnee = budgetCom.getValue();
            String sqlGetIdUser = "SELECT id FROM budget WHERE budget='" + Selectionnee + "'";
            Statement statement;
            ObservableList<Integer> idList = FXCollections.observableArrayList();
            try {
                statement = cnx.createStatement();  //exécuter une requête SQL
                ResultSet resultSet = statement.executeQuery(sqlGetIdUser);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    idList.add(id);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            if (idList.isEmpty()) {
                System.out.println(" le budget sélectionné n'existe pas dans la table budget");
                // afficher une erreur et sortir de la méthode
                return;
            }
            int id = idList.get(0);
            System.out.println("ID budget: " + id);
            

// insérer la depense en utilisant l'ID budget récupéré
            budget b = new budget();
            b.setId(id);

            depense d = new depense();
            d.setB(b);
            d.setCategorie(catCOM.getValue());
            d.setDate(date.getValue());
            d.setMontant(parseFloat(montant.getText()));
            d.setNom(nom.getText());
            d.setJustificatif(just.getText());
            ds.ajouter(d, b);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Ajout effectué");
            alert.setContentText("depense ajoutée avec succès.");
            alert.showAndWait();
        }

        nom.setText("");
        date.setValue(null);
        just.setText("");
        montant.setText("");
        

    }

    @FXML
    private void gotoDisplay(ActionEvent event) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Budget/AfficherDepense.fxml"));
            aff.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
