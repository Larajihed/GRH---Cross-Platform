package COM.HRSTORMDESKTOP.controllers.Evaluation;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import COM.HRSTORMDESKTOP.controllers.Evaluation.CompetenceDetails;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;
import COM.HRSTORMDESKTOP.models.Evaluation.Evaluation;
import COM.HRSTORMDESKTOP.models.Evaluation.Poste;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceCompetence;
import COM.HRSTORMDESKTOP.services.Evaluation.ServiceEvaluation;
import COM.HRSTORMDESKTOP.services.Evaluation.ServicePerson;
import COM.HRSTORMDESKTOP.services.Evaluation.ServicePoste;


/**
 * FXML Controller class
 *
 * @author Jihed
 */



public class showEvaluations implements Initializable  {

    @FXML
    private TextField nomField;
    
    @FXML
    private TextField descriptionField;

    List<Evaluation> evaluations = new ArrayList<Evaluation>();

  
      
    @FXML
private VBox evaluationsContainer;
    
    @FXML
    private DatePicker dateFromPicker;

    @FXML
    private DatePicker dateToPicker;

    // ...
    
    
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ServiceEvaluation se = new ServiceEvaluation();
        try {
            evaluations = se.selectAll();
        } catch (SQLException ex) {
            Logger.getLogger(showEvaluations.class.getName()).log(Level.SEVERE, null, ex);
        }
        renderEvaluationsAsCards();
        
        

   //Create a KeyCodeCombination for the "N" shortcut
KeyCodeCombination nShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
KeyCodeCombination qShortcut = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);

// Add a listener to the list view to detect when the "N" key is pressed
evaluationsContainer.setOnKeyPressed(keyEvent -> {
    if (nShortcut.match(keyEvent)) {
        // Call the showAddPoste() method when the "N" key is pressed
        showAddPosteUsingShortcut();
        keyEvent.consume(); // Prevent the event from being processed further
    }
    
    if(qShortcut.match(keyEvent)){
          ((Stage) evaluationsContainer.getScene().getWindow()).close();
        keyEvent.consume();
    }
});


//Create a KeyCodeCombination for the "Ctrl+Q" shortcut


    } 
    
private void renderEvaluationsAsCards() {
    evaluationsContainer.getChildren().clear();
    System.out.println(evaluations);
    
    Pagination pagination = new Pagination((int) Math.ceil(evaluations.size() / 5.0), 0);
    pagination.setPageFactory(pageIndex -> {
        VBox page = new VBox(10);
        page.setPadding(new Insets(10));
        page.setAlignment(Pos.CENTER);
        
        int start = pageIndex * 5;
        int end = Math.min(start + 12, evaluations.size());
        for (int i = start; i < end; i += 4) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER);
            
            for (int j = i; j < Math.min(i + 4, end); j++) {
                Evaluation evaluation = evaluations.get(j);
                
                Pane card = new Pane();
                card.setPrefHeight(100);
                card.setPrefWidth(200);
                card.setStyle("-fx-background-color: white;");

                Label dateLabel = new Label("Date: " + evaluation.getDate());
                dateLabel.setStyle("-fx-font-weight: bold;");
                dateLabel.setLayoutX(14);
                dateLabel.setLayoutY(14);
                card.getChildren().add(dateLabel);
                
        

                Label commentLabel = new Label("Comment: " + evaluation.getCommentaire());
                commentLabel.setLayoutX(14);
                commentLabel.setLayoutY(40);
                card.getChildren().add(commentLabel);

                Label experienceLabel = new Label("Experience: " + evaluation.getExperience());
                experienceLabel.setLayoutX(14);
                experienceLabel.setLayoutY(65);
                card.getChildren().add(experienceLabel);

                Label levelLabel = new Label("Level: " + evaluation.getLevel());
                levelLabel.setLayoutX(90);
                levelLabel.setLayoutY(65);
                card.getChildren().add(levelLabel);

                Button detailsButton = new Button("Details");
detailsButton.setLayoutX(130);
detailsButton.setLayoutY(8);
detailsButton.setStyle("-fx-font-family: 'Dubai'; -fx-font-size: 12px; -fx-background-color: blue; -fx-text-fill: white;");

detailsButton.setOnAction(event -> {

        });
card.getChildren().add(detailsButton);
                
                row.getChildren().add(card);
            }
            
            page.getChildren().add(row);
        }
        
        return page;
    });
    
    evaluationsContainer.getChildren().add(pagination);
}
   @FXML
private void filterEvaluations(ActionEvent event) throws SQLException {
    ServiceEvaluation se = new ServiceEvaluation();
    LocalDate fromDate = dateFromPicker.getValue();
    LocalDate toDate = dateToPicker.getValue();

    if (fromDate != null && toDate != null) {
        evaluations = se.selectByDate(fromDate, toDate);
        renderEvaluationsAsCards();
    }
}


       
    @FXML
private void handleCompetenceClick(MouseEvent event) throws IOException {

}



    
    @FXML
    private void showAddPoste(ActionEvent event) {
        
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/evaluationInsert.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ServicePoste sp = new ServicePoste();
        List<Poste> postes = sp.selectAll();
        
    
        
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des postes");
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

    private void showAddPoste() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void showAddPosteUsingShortcut() {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/Evaluation/evaluationInsert.fxml"));
        Parent root = loader.load();
        // Get the ListView from the FXML file
        ServicePoste sp = new ServicePoste();
        List<Poste> postes = sp.selectAll();
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des postes");
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
}
