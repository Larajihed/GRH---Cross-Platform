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
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ihebt
 */
public class AfficherDepenseController implements Initializable {

    depense d = new depense();
    DepenseService ds = new DepenseService();
    BudgetService bs = new BudgetService();
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private TextField montant;
    @FXML
    private TextField just;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> catCOM;
    @FXML
    private ComboBox<Float> budgetCom;
    @FXML
    private TableColumn<depense, Integer> idCol;
    @FXML
    private TableColumn<depense, Float> budcol;
    @FXML
    private TableColumn<depense, String> nomcol;
    @FXML
    private TableColumn<depense, Float> montantcol;
    @FXML
    private TableColumn<depense, LocalDate> datecol;
    @FXML
    private TableColumn<depense, String> justificatifcol;
    @FXML
    private TableColumn<depense, String> catCol;
    @FXML
    private Button modfier;
    @FXML
    private Button delete;
    @FXML
    private Button back;
    @FXML
    private Button budget;
    @FXML
    private TableView<depense> tableview;
            @FXML
    private ImageView backButton;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*idCol.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getId()).asObject();
        });*/

        budcol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getB().getBudget()).asObject();
        });
        nomcol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getNom());
        });

        montantcol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getMontant()).asObject();
        });

        datecol.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<LocalDate>(cellData.getValue().getDate());
        });
        justificatifcol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getJustificatif());
        });
        catCol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getCategorie());
        });

        List<depense> depenses = ds.getAll();
        ObservableList<depense> observabledepense = FXCollections.observableArrayList(depenses);
        tableview.setItems(observabledepense);
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
    private void pressetab(MouseEvent event) {
        d = tableview.getSelectionModel().getSelectedItem();

        //id.setText("" + d.getId());
        date.setValue(d.getDate());
        nom.setText("" + d.getNom());
        just.setText("" + d.getJustificatif());
        montant.setText("" + d.getMontant());

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
    private void modifierDepense(ActionEvent event) {
        Connection cnx = DBConnector.getInstance().getCnx();
        if (nom.getText().isEmpty() || date.getValue() == null || just.getText().isEmpty() || montant.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Ajout non effectué");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            Float Selectionnee = budgetCom.getValue();
            String sqlGetIdUser = "SELECT id FROM budget WHERE budget='" + Selectionnee + "'";
            Statement statement;
            ObservableList<Integer> idList = FXCollections.observableArrayList();
            try {
                statement = cnx.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlGetIdUser);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    idList.add(id);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            if (idList.isEmpty()) {
                System.out.println(" le budget sélectionne n'existe pas dans la table budget");
                // afficher une erreur et sortir de la méthode
                return;
            }
            int iddep = idList.get(0);
            System.out.println("ID depense: " + iddep);

// insérer la depense en utilisant l'ID budget récupéré
            budget b = new budget();
            //b.setId(iddep);
            LocalDate da = date.getValue();
            depense d = new depense();
            d.setB(b);
            d.setCategorie(catCOM.getValue());
            d.setDate(date.getValue());
            d.setMontant(parseFloat(montant.getText()));
            d.setNom(nom.getText());
            //d.setId(parseInt(id.getText()));
            d.setJustificatif(just.getText());
            ds.modifier_depense(b, nom.getText(), parseFloat(montant.getText()), da, just.getText(), catCOM.getValue(), d);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("mise a jour effectué");
            alert.setContentText("depense modifiée avec succès.");
            alert.showAndWait();
        }

        nom.setText("");
        date.setValue(null);
        just.setText("");
        montant.setText("");
        ObservableList<depense> depenses = FXCollections.observableList(ds.getAll());
        tableview.setItems(depenses);
    }

    @FXML
    private void deleteDepense(ActionEvent event) {
        d = tableview.getSelectionModel().getSelectedItem();

        if (d == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cliquez sur une depense table!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "voulez Vous  vraiment supprimer cette depense :" + d.getId() + " ?");
            alert.setHeaderText(null);
            //Getting Buttons
            Optional<ButtonType> result = alert.showAndWait();
            //Testing if the user clicked OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ds.supprimer_depense(d);
                //updating user data after closing popup
                ObservableList<depense> depenses = FXCollections.observableList(ds.getAll());
                tableview.setItems(depenses);
            }
        }
    }

    @FXML
    private void backAjout(ActionEvent event) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Budget/AjouterDepense.fxml"));
            back.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void budget(ActionEvent event) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Budget/AfficherBudget.fxml"));
            back.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
