/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.Budget;

import COM.HRSTORMDESKTOP.services.budget.BudgetService;
import COM.HRSTORMDESKTOP.models.budget.budget;
import java.io.IOException;
import static java.lang.Float.parseFloat;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ihebt
 */
public class AjouterBudgetController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private Button ajoutButton;
    @FXML
    private Button afficher;
    @FXML
    private TextField budget;
    @FXML
    private TextField prime;
    @FXML
    private TextField budget_materiel;
    @FXML
    private TextField budget_salaire;
    @FXML
    private TextField budget_service;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
   private void Ajouter_Budget(ActionEvent event) {

    if (budget.getText().isEmpty() || date.getValue() == null || prime.getText().isEmpty() || budget_materiel.getText().isEmpty() || budget_salaire.getText().isEmpty() || budget_service.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Ajout non effectué");
        alert.setContentText("Veuillez remplir tous les champs.");
        alert.showAndWait();
        return;
    }

    if (!budget.getText().matches("\\d*\\.?\\d*")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget.");
        alert.showAndWait();
        return;
    }

    if (!budget_materiel.getText().matches("\\d*\\.?\\d*")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget matériel.");
        alert.showAndWait();
        return;
    }

    if (!budget_salaire.getText().matches("\\d*\\.?\\d*")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget salaire.");
        alert.showAndWait();
        return;
    }

    if (!budget_service.getText().matches("\\d*\\.?\\d*")) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget service.");
        alert.showAndWait();
        return;
    }

    float budgetTotal = parseFloat(budget_materiel.getText()) + parseFloat(budget_salaire.getText()) + parseFloat(budget_service.getText());

    if (Math.abs(parseFloat(budget.getText()) - budgetTotal) > 0.001) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText("La somme des budgets matériels, salaires et services doit être égale au budget total.");
        alert.showAndWait();
        return;
    }

    budget b = new budget();
    b.setBudget(parseFloat(budget.getText()));
    b.setPrime(parseFloat(prime.getText()));
    b.setBudget_materiel(parseFloat(budget_materiel.getText()));
    b.setBudget_salaire(parseFloat(budget_salaire.getText()));
    b.setBudget_service(parseFloat(budget_service.getText()));
    b.setDate(date.getValue());

    BudgetService Bs = new BudgetService();
    Bs.ajouter(b);

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Succès");
    alert.setHeaderText("Ajout effectué");
    alert.setContentText("Budget ajouté avec succès.");
    alert.showAndWait();

    budget.setText("");
    prime.setText("");
    budget_materiel.setText("");
    budget_salaire.setText("");
    budget_service.setText("");
    

}

    @FXML
    private void goToDisplay(ActionEvent event) {
             try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Budget/AfficherBudget.fxml"));
            afficher.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}