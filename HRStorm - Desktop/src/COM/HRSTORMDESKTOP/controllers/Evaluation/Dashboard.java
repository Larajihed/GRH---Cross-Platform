/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.controllers.Evaluation;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.models.Evaluation.Evaluation;
import COM.HRSTORMDESKTOP.models.Evaluation.Poste;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceCompetence;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceEmployee;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceEvaluation;
import COM.HRSTORMDESKTOP.services.Evaluation.ServicePoste;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * @author conta
 */
public class Dashboard  implements Initializable {
    
        private Stage showCompetencesStage;
        
        @FXML
        private Text totEval;
            @FXML
    private ImageView backButton;
        
        
        
        @FXML
        private Text totemp;
        
        
        
        @FXML
        private Text totcomp;
        
        
        @FXML
        private Text totpos;
          
        @FXML
private BarChart<String, Number> barChart;
@FXML
private AnchorPane barChartPane;

@FXML
private AnchorPane pieChartPane;

@Override
public void initialize(URL url, ResourceBundle rb) {
    ServiceEvaluation se = new ServiceEvaluation();
    ServiceCompetence sc = new ServiceCompetence();
    ServiceEmployee see = new ServiceEmployee();
    ServicePoste sp = new ServicePoste();

    
            try {
               
                String totalEvaluations = String.valueOf(se.selectAll().size());
                String totalPostes = String.valueOf(sp.selectAll().size());
                String totalemp = String.valueOf(see.selectAll().size());
                String totalCompetences = String.valueOf(sc.selectAll().size());
                totEval.setText(totalEvaluations);
                totcomp.setText(totalCompetences);
                totemp.setText(totalemp);
                totpos.setText(totalPostes);

            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
              int numberOfEvaluations= se.selectAll().size();
                System.out.println(numberOfEvaluations);
            } catch (SQLException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
    try {
        int seniorCount = se.countEmployeesByLevel("senior");
        int stagaireCount = se.countEmployeesByLevel("Intern");
        int midLevelCount = se.countEmployeesByLevel("mid-level");
        int juniorCount = se.countEmployeesByLevel("junior");
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Senior", "Intern", "Mid-Level", "Junior")));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Employee Count");

        BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Senior", seniorCount));
        series.getData().add(new XYChart.Data<>("Intern", stagaireCount));
        series.getData().add(new XYChart.Data<>("Mid-Level", midLevelCount));
        series.getData().add(new XYChart.Data<>("Junior", juniorCount));
        barChart.getData().add(series);
        barChart.setLegendVisible(false);

        barChartPane.getChildren().add(barChart);

    } catch (SQLException ex) {
        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
    }

    try {
      PieChart pieChart = new PieChart();
pieChart.setTitle("Niveau des Employés");
Map<String, Integer> counts = se.countEmployeesByLevel();
ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
counts.forEach((level, count) -> pieChartData.add(new PieChart.Data(level, count)));
pieChart.setData(pieChartData);
pieChartPane.getChildren().add(pieChart);
    } catch (SQLException ex) {
        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
    }
}

@FXML
private void showLevelBarChart(ActionEvent event) {
    try {
        // Retrieve the data for the bar chart
        ServiceEvaluation se = new ServiceEvaluation();
        int seniorCount = se.countEmployeesByLevel("senior");
        int stagiaireCount = se.countEmployeesByLevel("intern");
        int midLevelCount = se.countEmployeesByLevel("mid-level");
        int juniorCount = se.countEmployeesByLevel("junior");

        // Create the bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Set the data for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Niveau des employés");
        series.getData().add(new XYChart.Data<>("Senior", seniorCount));
        series.getData().add(new XYChart.Data<>("Stagiaire", stagiaireCount));
        series.getData().add(new XYChart.Data<>("Mid-level", midLevelCount));
        series.getData().add(new XYChart.Data<>("Junior", juniorCount));
        barChart.getData().add(series);

        // Add the bar chart to the dashboard
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().add(barChart);
        Scene scene = new Scene(vbox, 600, 400);
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        st.setScene(scene);
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
}


   // Navigation 
    
   @FXML
private void showCompetences(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/showCompetences.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ListView<Competence> listView = (ListView<Competence>) root.lookup("#competencesList");
        // Set the cell factory to use custom cell rendering
        listView.setCellFactory(param -> new CompetenceCell());
        // Retrieve the list of competences
        ServiceCompetence cp = new ServiceCompetence();
        List<Competence> competences = cp.selectAll();
        // Set the items of the ListView
        ObservableList<Competence> items = FXCollections.observableArrayList(competences);
        listView.setItems(items);
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des Competences");
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
private void showPostes(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/showPostes.fxml"));
        Parent root = loader.load();
        
        ListView<Poste> listView = (ListView<Poste>) root.lookup("#PostesList");
        ServicePoste cp = new ServicePoste();
        List<Poste> postes = cp.selectAll();
        
        listView.setCellFactory(param -> new ListCell<Poste>() {
            @Override
            protected void updateItem(Poste poste, boolean empty) {
                super.updateItem(poste, empty);
                
                if (empty || poste == null) {
                    setText(null);
                } else {
                    String text = String.format("%s\n%s\n%s\n%s - %s\nCompétences: %s",
                            poste.getNom(), poste.getMissions(), poste.getDescription(), 
                            poste.getSalaireMin(), poste.getSalaireMax(), poste.getCompetences());
                    setText(text);
                }
            }
        });
        
        ObservableList<Poste> items = FXCollections.observableArrayList(postes);
        listView.setItems(items);
        
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des Postes");
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
    private void showEvaluations(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/showEvaluations.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des Competences");
        st.setScene(scene);
        st.show();
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
}

    
    public class CompetenceCell extends ListCell<Competence> {
    
    @Override
    protected void updateItem(Competence competence, boolean empty) {
        super.updateItem(competence, empty);
        
        if (empty || competence == null) {
            setText(null);
            setGraphic(null);
        } else {
            VBox vbox = new VBox();
            vbox.setSpacing(5);
            
            Label nomLabel = new Label("Nom: " + competence.getNom());
            Label descriptionLabel = new Label("Description: " + competence.getDescription());
            
            vbox.getChildren().addAll(nomLabel, descriptionLabel);
            setGraphic(vbox);
        }
    }
    
}
    
  @FXML
private void showLevelPieChart(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/levelPieChart.fxml"));
        Parent root = loader.load();

        PieChart pieChart = (PieChart) root.lookup("#levelPieChart");

        ServiceEvaluation evaluationService = new ServiceEvaluation();
        Map<String, Integer> counts = evaluationService.countEmployeesByLevel();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        counts.forEach((level, count) -> pieChartData.add(new PieChart.Data(level, count)));

        pieChart.setData(pieChartData);

        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Niveau des Employés");
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

    
}
