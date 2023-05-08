package COM.HRSTORMDESKTOP.controllers.Evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Evaluation.Employee;
import COM.HRSTORMDESKTOP.models.Evaluation.Evaluation;
import COM.HRSTORMDESKTOP.models.Evaluation.Poste;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceEmployee;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceEvaluation;
import COM.HRSTORMDESKTOP.services.Evaluation.ServicePoste;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import java.util.Date;





//import your.package.name.UserService;

 public  class evaluationInsert implements Initializable {

    @FXML
private DatePicker datePicker;

    @FXML
    private TextField experienceField;

    @FXML
    private TextArea commentaireArea;


   

    @FXML
    private Label errorLabel;
    
    @FXML
    private ComboBox<Poste> posteComboBox;

   @FXML
    private ComboBox<Employee> employeeComboBox;

   
   @FXML
    private ComboBox<String> levelComboBox;
   
    ServiceEvaluation se = new ServiceEvaluation();

    @FXML
     private void handleInsert(ActionEvent event) {
        Poste selectedPoste = posteComboBox.getValue();
        int posteId = selectedPoste.getId();
        LocalDate selectedDate = datePicker.getValue();
        java.sql.Date date = java.sql.Date.valueOf(selectedDate);     
        String commentaire = commentaireArea.getText();
        String level = levelComboBox.getValue();
        int experience = Integer.parseInt(experienceField.getText());
        int employeeId = employeeComboBox.getValue().getId();

        try {
        

            Evaluation newEvaluation = new Evaluation();
            
newEvaluation.setDate(date);
            newEvaluation.setExperience(experience);
            newEvaluation.setCommentaire(commentaire);
            newEvaluation.setLevel(level);
ServicePoste servicePoste = new ServicePoste();
Poste poste = servicePoste.selectOne(posteId);
newEvaluation.setPoste(poste);


ServiceEmployee serviceEmployee = new ServiceEmployee();
Employee employee = serviceEmployee.selectOne(employeeId);
newEvaluation.setEmployee(employee);
            // Insert new evaluation into the database
            se.insertEvaluation(newEvaluation);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Évaluation ajoutée");
            alert.setContentText("L'évaluation a été ajoutée avec succès.");
            alert.showAndWait();

            // Close the window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            // Handle invalid input values
            errorLabel.setText("Veuillez saisir des valeurs numériques valides pour les champs Expérience, Poste ID et Employee ID.");
        } catch (SQLException e) {
            // Handle database errors
            errorLabel.setText("Erreur lors de l'insertion de l'évaluation.");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
           try {
        // Get the list of Postes from the service
        List<Poste> postes = new ServicePoste().selectAll();
        List<Employee> employees = new ServiceEmployee().selectAll();
        List<String> levels = new ArrayList<>();
        levels.add("Stagiaire");
        levels.add("Junior");
        levels.add("Mid-Level");
        levels.add("Senior");
        // Initialize the ComboBox with the list of Postes
        posteComboBox.getItems().addAll(postes);
        employeeComboBox.getItems().addAll(employees);
        levelComboBox.getItems().addAll(levels);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }
    
    
    @FXML
private void handleImport(ActionEvent event) {
    // Open file chooser dialog to allow user to select a file
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Excel or CSV file");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx"),
        new FileChooser.ExtensionFilter("CSV Files", "*.csv")
    );
    File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
    if (file == null) {
        return;
    }
    
    // Parse the file contents and add them to the database
    try {
        List<Evaluation> evaluations = parseCsv(file);
        ServiceEvaluation serviceEvaluation = new ServiceEvaluation();
        for (Evaluation evaluation : evaluations) {
            serviceEvaluation.insertEvaluation(evaluation);
        }
        
        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Import successful");
        alert.setContentText("The file has been imported successfully.");
        alert.showAndWait();
    } catch (Exception e) {
        // Handle errors
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Import failed");
        alert.setContentText("An error occurred while importing the file.");
        alert.showAndWait();
        e.printStackTrace();
    }
}



private List<Evaluation> parseCsv(File file) throws IOException, SQLException, ParseException {
    List<Evaluation> evaluations = new ArrayList<>();
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        br.readLine(); // Skip the first line containing column headers

        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
/*
            if (fields.length != 6) {
                throw new IOException("Invalid file format");
            }
*/
            Evaluation evaluation = new Evaluation();
          String dateString = fields[0];
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
Date date =  dateFormat.parse(dateString);
long time = date.getTime();
java.sql.Date sqlDate = new java.sql.Date(time);
evaluation.setDate(sqlDate);
            evaluation.setCommentaire(fields[1]);
            evaluation.setExperience(Integer.valueOf(fields[2]));
            evaluation.setLevel(fields[3]);
           // Retrieve list of Poste objects from database
            ServicePoste servicePoste = new ServicePoste();
            List<Poste> postes = servicePoste.selectAll();
            // Match poste_id to an existing Poste object
            int posteId = Integer.parseInt(fields[4]);
            Poste poste = postes.stream().filter(p -> p.getId() == posteId).findFirst().orElse(null);
            evaluation.setPoste(poste);
            
            ServiceEmployee se = new ServiceEmployee();
            List<Employee> employees = se.selectAll();
            int empId = Integer.parseInt(fields[5]);
            Employee e = employees.stream().filter(p -> p.getId() == empId).findFirst().orElse(null);
            
            evaluation.setEmployee(e);
            evaluations.add(evaluation);
        }
    }
            System.out.println(evaluations);
    return evaluations;
}

    

}
