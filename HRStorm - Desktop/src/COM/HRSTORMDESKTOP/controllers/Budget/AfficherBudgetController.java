/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.Budget;

import COM.HRSTORMDESKTOP.controllers.Budget.StatController;
import COM.HRSTORMDESKTOP.services.budget.BudgetService;
import COM.HRSTORMDESKTOP.models.budget.budget;
import COM.HRSTORMDESKTOP.models.budget.pdf;
import java.io.IOException;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.NullPointerException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ihebt
 */
public class AfficherBudgetController implements Initializable {

    budget b = new budget();
    BudgetService bs = new BudgetService();
    @FXML
    private TableColumn<budget, Integer> idcol;
    @FXML
    private TableColumn<budget, Float> budgetCol;
    @FXML
    private TableColumn<budget, Float> primeCol;
    @FXML
    private TableColumn<budget, LocalDate> dateCol;
    @FXML
    private TableColumn<budget, Float> budgetColmateriel;
    @FXML
    private TableColumn<budget, Float> budgetSalaireCol;
    @FXML
    private TableColumn<budget, Float> budgetSerciceCol;
    @FXML
    private TextField id;
    @FXML
    private TextField budget;
    @FXML
    private TextField prime;
    @FXML
    private TextField budgetService;
    @FXML
    private TextField budgetMateriel;
    @FXML
    private TextField budgetSalaire;
    @FXML
    private TextField recherche;
    @FXML
    private Button modifier;
    @FXML
    private Button delete;
    @FXML
    private DatePicker date;
    @FXML
    private Button back;
    @FXML
    private Button depense;
    @FXML
    private ComboBox<String> ExporterListe;
    @FXML
    private ComboBox<String> Tri;
    @FXML
    private Button PDF;
    @FXML
    private Button Stat;
    @FXML
    private TableView<budget> tableview;
        @FXML
    private ImageView backButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO   idCol.setCellValueFactory(cellData -> {
        /*idcol.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getId()).asObject();
        });*/

        budgetCol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getBudget()).asObject();
        });

        dateCol.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<LocalDate>(cellData.getValue().getDate());
        });

        primeCol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getPrime()).asObject();
        });
        budgetColmateriel.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getBudget_materiel()).asObject();
        });
        budgetSalaireCol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getBudget_salaire()).asObject();
        });
        budgetSerciceCol.setCellValueFactory(cellData -> {
            return new SimpleFloatProperty(cellData.getValue().getBudget_service()).asObject();
        });

        ObservableList<String> list4 = FXCollections.observableArrayList("PDF", "Excel", "Imprimer");
        ExporterListe.setItems(list4);
        ObservableList<String> list3 = FXCollections.observableArrayList("Budget", "Date", "Prime");
        Tri.setItems(list3);
        List<budget> budgets = bs.getAll();
        //stat();
        ObservableList<budget> observablePlaylists = FXCollections.observableArrayList(budgets);
        tableview.setItems(observablePlaylists);

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
    private void modifierBudget(ActionEvent event) {
        LocalDate d = date.getValue();

        if (budget.getText().isEmpty() || date.getValue() == null || prime.getText().isEmpty() || budgetMateriel.getText().isEmpty() || budgetSalaire.getText().isEmpty() || budgetService.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOT OK");
            alert.setHeaderText("modification non effectue");
            alert.setContentText("Click Cancel to exit.");
            System.out.println(d);
            alert.showAndWait();
        }
        if (!budget.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget.");
            alert.showAndWait();
            return;
        }

        if (!budgetMateriel.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget matériel.");
            alert.showAndWait();
            return;
        }

        if (!budgetSalaire.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget salaire.");
            alert.showAndWait();
            return;
        }

        if (!budgetService.getText().matches("\\d*\\.?\\d*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre réel (ex: 3.14) pour le budget service.");
            alert.showAndWait();
            return;
        }

        float budgetTotal = parseFloat(budgetMateriel.getText()) + parseFloat(budgetSalaire.getText()) + parseFloat(budgetService.getText());

        if (Math.abs(parseFloat(budget.getText()) - budgetTotal) > 0.001) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("La somme des budgets matériels, salaires et services doit être égale au budget total.");
            alert.showAndWait();
            return;
        } else {

            b.setBudget(parseFloat(budget.getText()));
            b.setDate(d);
            b.setPrime(parseFloat(prime.getText()));
            b.setBudget_materiel(parseFloat(budgetMateriel.getText()));
            b.setBudget_salaire(parseFloat(budgetSalaire.getText()));
            b.setBudget_service(parseFloat(budgetService.getText()));
            //b.setId(parseInt(id.getText()));

            bs.modifier_budget(parseFloat(budget.getText()), d, parseFloat(prime.getText()), parseFloat(budgetMateriel.getText()), parseFloat(budgetService.getText()), parseFloat(budgetSalaire.getText()), b);

        }
        ObservableList<budget> budgets = FXCollections.observableList(bs.getAll());
        tableview.setItems(budgets);

    }
 
    @FXML
    private void deleteBudget(ActionEvent event) {
        b = tableview.getSelectionModel().getSelectedItem();

        if (b == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cliquez sur un budget du table!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "voulez Vous  vraiment supprimer cette reclamation :" + b.getId() + " ?");
            alert.setHeaderText(null);
            //Getting Buttons
            Optional<ButtonType> result = alert.showAndWait();
            //Testing if the user clicked OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                bs.supprimer_budget(b);
                //updating user data after closing popup
                ObservableList<budget> budgets = FXCollections.observableList(bs.getAll());
                tableview.setItems(budgets);
            }
        }
    }

    @FXML
    private void backAjout(ActionEvent event) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Budget/AjouterBudget.fxml"));
            back.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
        @FXML
    private void depense(ActionEvent event) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Budget/AfficherDepense.fxml"));
            back.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

   
@FXML
private void Stat(ActionEvent event) {
    try {
        BudgetService bs = new BudgetService();
        PieChart pieChart = new PieChart();
        pieChart.setData(bs.getPieChart());

        Stage stage = new Stage();
        Scene scene = new Scene(new Group(pieChart), 500, 400);
        stage.setScene(scene);
        stage.setTitle("**Statistiques des Budgets Par Date**");
        stage.show();
    } catch (SQLException ex) {
        Logger.getLogger(AfficherBudgetController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    public void Stat() {
        this.Stat.setVisible(false);
    }

    /*private void hideStatButton() {
    this.Stat.setDisable(true);
}*/

    @FXML
    private void Recherche(ActionEvent event) {
        BudgetService se = new BudgetService();
        String chaine = recherche.getText();
        populateTable(se.chercher(chaine));
    }

    private void populateTable(ObservableList<budget> branlist) {
        tableview.setItems(branlist);

    }
    @FXML
    private void Tri(ActionEvent event){
                String N = (String) Tri.getValue();
 BudgetService se = new BudgetService();

        populateTable(se.tri(N,true));
        
    }

    @FXML
    private void PDF(MouseEvent event) {
        budget bud = tableview.getSelectionModel().getSelectedItem();

        pdf pd = new pdf();
        try {
            pd.GeneratePdf("Gestion du Budget", bud, bud.getId());

            System.out.println("impression done");
        } catch (Exception ex) {
            Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
private void Excel(ActionEvent event) throws IOException {
    BudgetService bs = new BudgetService();
    List<budget> list = bs.getAll();
    
    File file = new File("C:\\Users\\ihebt\\Documents\\NetBeansProjects\\HRstorm2\\budget.csv");
    writeCsvFile(list, file);
    
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Excel");
    alert.setHeaderText(null);
    alert.setContentText("Excel exported!");
    alert.showAndWait();
}

private void writeCsvFile(List<budget> budgetList, File file) throws IOException {
    try (Writer writer = new BufferedWriter(new FileWriter(file))) {
        for (budget b : budgetList) {
            String text = b.getBudget() + "," + b.getDate() + "," + b.getPrime() + ","
                    + b.getBudget_materiel() + "," + b.getBudget_salaire() + "," + b.getBudget_service() + "\n";
            writer.write(text);
        }
    }
}


    public static void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
        PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        node.getTransforms().add(scale);

        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();

            }
        }
        node.getTransforms().remove(scale);

    }

    private void ImprimerAction(ActionEvent event) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        printNode(tableview);
    }

    @FXML
    private void ExporterListe(ActionEvent event) throws IOException, NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException, SQLException {
        String N = (String) ExporterListe.getValue();

        if (N == "PDF") {
            ExporterListe.setValue("Exporter");
            budget bud = tableview.getSelectionModel().getSelectedItem();

            pdf pd = new pdf();
            try {
                pd.GeneratePdf("" + bud.getBudget() + "", bud, bud.getId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF");
                alert.setHeaderText(null);
                alert.setContentText("!!!PDF exported!!!");
                alert.showAndWait();
                System.out.println("impression done");
            } catch (Exception ex) {
                Logger.getLogger(BudgetService.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("!!!Selectioner un budget!!!");
                alert.showAndWait();
            }
        }
        if (N == "Excel") {
            //ExporterListe.setValue("Exporter");

           BudgetService bs = new BudgetService();
try {
    File file = bs.exportExcel();

    if (file != null) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Excel");
        alert.setHeaderText(null);
        alert.setContentText("Le fichier Excel a été exporté avec succès!");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier Excel");
        fileChooser.setInitialFileName("budget.csv");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Fichiers CSV", "*.csv"));

        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            FileUtils.copyFile(file, selectedFile);
            alert.showAndWait();
        } else {
            file.delete();
        }
    } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Export Excel");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur est survenue lors de l'exportation du fichier Excel!");
        alert.showAndWait();
    }
} catch (IOException | SQLException ex) {
    ex.printStackTrace();
}
        }

        if (N == "Imprimer") {
            ExporterListe.setValue("Exporter");
            printNode(tableview);
        }
    }

    @FXML
    private void pressTABLE(MouseEvent event) {
        b = tableview.getSelectionModel().getSelectedItem();

        budget.setText("" + b.getBudget());
        prime.setText("" + b.getPrime());
        date.setValue(b.getDate());
        budgetSalaire.setText("" + b.getBudget_service());
        budgetMateriel.setText("" + b.getBudget_materiel());
        budgetService.setText("" + b.getBudget_salaire());
        //id.setText("" + b.getId());

    }

    private static class FileUtils {

        private static void copyFile(File file, File selectedFile) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public FileUtils() {
        }
    }

}
