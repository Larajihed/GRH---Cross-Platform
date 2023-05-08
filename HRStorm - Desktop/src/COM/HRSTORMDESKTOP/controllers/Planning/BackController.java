/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.Planning;

import COM.HRSTORMDESKTOP.models.Planning.Planning;
import COM.HRSTORMDESKTOP.models.Planning.Tache;
import COM.HRSTORMDESKTOP.services.Planning.ServicePlanning;
import COM.HRSTORMDESKTOP.services.Planning.ServiceTache;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import jfxtras.scene.control.agenda.Agenda;


/**
 * FXML Controller class
 *
 * @author houss
 */
public class BackController implements Initializable {

    @FXML
    private TableColumn<Planning, String> colNom;
    @FXML
    private TableColumn<Planning, String> colDesc;
    @FXML
    private TableColumn<Planning, Date> colDebut;
    @FXML
    private TableColumn<Planning, Date> colFin;
    @FXML
    private Button btnMenuEvent;
    @FXML
    private Button btnMenuSponsor;
    @FXML
    private TableView<Tache> tvTache;
    @FXML
    private TableView<Planning> tvPlanning;
    @FXML
    private TextField tfNomPlan;
    @FXML
    private TextField tfDescPlan;
    @FXML
    private DatePicker tfDebutPlan;
    @FXML
    private DatePicker tfFinPlan;
    @FXML
    private Button btnConfPlan;
    @FXML
    private Pane pnPlan;
    @FXML
    private Button btnAjoutPlan;
    @FXML
    private Button btnModifyPlan;
    @FXML
    private Button btnSupprimerPlan;
    @FXML
    private Label lbIdPlan;
    @FXML
    private Pane pnFormTache;
    @FXML
    private TextField tfNomTache;
    @FXML
    private TextField tfDescripTache;
    @FXML
    private Slider tfPrioriteTache;
    @FXML
    private ComboBox<String> tfPlanningTache;
    @FXML
    private Label lbTitleAjoutTache;
    @FXML
    private Label lbTitleModifyTache;
    @FXML
    private Button btnConfTache;
    @FXML
    private Pane pnTache;
    @FXML
    private TableColumn<Tache, String> colNomTache;
    @FXML
    private TableColumn<Tache, String> colPlanningTache;
    @FXML
    private TableColumn<Tache, String> colDescriptionTache;
    @FXML
    private TableColumn<Tache, String> colPrioriteTache;
    @FXML
    private Button btnAjoutTache;
    @FXML
    private Button btnModifyTache;
    @FXML
    private Button btnSupprimerTache;
    @FXML
    private Label lbidTache;
    @FXML
    private Label lbTitleAjoutPlanning;
    @FXML
    private Label lbTitleModifyPlanning;
    @FXML
    private Pane pnFormPlanning;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnFilter;
    @FXML
    private DatePicker tfFinFilter;
    @FXML
    private DatePicker tfDebutFilter;
    @FXML
    private TextField tfRechercherPlqnnig;
    @FXML
    private TextField tfRechercherTache;
    @FXML
    private Button btnMenuAgenda;
    @FXML
    private Pane pnAgenda;
    @FXML
    private Agenda PlanningAgenda;
                @FXML
    private ImageView backButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnPlan.toFront();
        fnEventShow();
        // TODO
        fnSpShow();
        ObservableList<String> valuesList = FXCollections.observableArrayList();
        COM.HRSTORMDESKTOP.services.Planning.ServicePlanning sr=new ServicePlanning();
        for (Planning comp : sr.afficher()) {
            valuesList.add(comp.getNom());
        }
        tfPlanningTache.setItems(valuesList);
    }    
    
    public void fnEventShow(){
        ServicePlanning sr=new ServicePlanning();
         ObservableList<Planning> list = FXCollections.observableArrayList(sr.afficher());;
    
     colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));       
     colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));        
     colDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));   
     colFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));   

        
     tvPlanning.setItems(list);
    tvPlanning.setRowFactory(tv -> new TableRow<Planning>() {
    @Override
    protected void updateItem(Planning item, boolean empty) {
        super.updateItem(item, empty);
        
    }
});

      
    FilteredList<Planning> filteredData = new FilteredList<>(list, b -> true);
		
		tfRechercherPlqnnig.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Event -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Event.getDescription().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}else if(Event.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Planning> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tvPlanning.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tvPlanning.setItems(sortedData);
    }
    
    
    public void fnSpShow(){
        ServiceTache sr=new ServiceTache();
         ObservableList<Tache> list = FXCollections.observableArrayList(sr.afficher());;
    
     colNomTache.setCellValueFactory(new PropertyValueFactory<>("Nom"));       
     colDescriptionTache.setCellValueFactory(new PropertyValueFactory<>("description"));        
     colPlanningTache.setCellValueFactory(new PropertyValueFactory<>("planning_nom")); 
     colPrioriteTache.setCellValueFactory(new PropertyValueFactory<>("priorite")); 

        
     tvTache.setItems(list);
    
    tvTache.setRowFactory(tv -> new TableRow<Tache>() {
    @Override
    protected void updateItem(Tache item, boolean empty) {
        super.updateItem(item, empty);
        
    }
});
      
    FilteredList<Tache> filteredData = new FilteredList<>(list, b -> true);
		
		tfRechercherTache.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Event -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Event.getDescription().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}else if(Event.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}else if(Event.getPlanning_nom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Tache> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tvTache.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tvTache.setItems(sortedData);
        }




    @FXML
    private void fnSelectedPlanning(MouseEvent event) {
        Planning rec= tvPlanning.getSelectionModel().getSelectedItem();
        lbIdPlan.setText(rec.getId()+"");
        btnModifyPlan.setVisible(true);
        btnSupprimerPlan.setVisible(true);
    }




    @FXML
    private void fnSelectedTache(MouseEvent event) {
         Tache rec= tvTache.getSelectionModel().getSelectedItem();
        lbidTache.setText(rec.getId()+"");
    }




    @FXML
    private void fnMenuPlan(ActionEvent event) {
         pnPlan.toFront();
    }

    @FXML
    private void fnMenuTache(ActionEvent event) {
        pnTache.toFront();
    }



    @FXML
    private void fnSupprimerEvent(ActionEvent event) {
        ServicePlanning cs=new ServicePlanning();
        cs.supprimer(Integer.parseInt(lbIdPlan.getText()));
        fnEventShow();
        fnSpShow();
    }


    @FXML
    private void fnConfPlan(ActionEvent event) {
        ServicePlanning cs=new ServicePlanning();
        if(lbTitleAjoutPlanning.isVisible()){
            Planning c =new Planning();
            
            String ERROR_MSG="";
            if("".equals(tfNomPlan.getText() )|| tfNomPlan.getText().matches(".*\\d+.*")){
                ERROR_MSG="Le champs nom de dois pas pas étre null";
                System.out.println();
                System.out.println(ERROR_MSG);
            }
            if("".equals(tfDescPlan.getText())){
                ERROR_MSG="Le champs date debut de dois pas pas étre null ou inf a date aujoud'hui";
                System.out.println(ERROR_MSG);
            }
            if(tfDebutPlan.getValue()==null || tfDebutPlan.getValue().isBefore(LocalDate.now()) ){
                ERROR_MSG="Le champs date debut de dois pas pas étre null";
                System.out.println(ERROR_MSG);
            }
            if(tfFinPlan.getValue()==null || tfDebutPlan.getValue()==null || tfFinPlan.getValue().isBefore(tfDebutPlan.getValue())){
                ERROR_MSG="Le champs date fin de dois pas pas étre null ou avant la date debut";
                System.out.println(ERROR_MSG);
            }
            if(!"".equals(ERROR_MSG)){
            Stage window = (Stage)pnFormPlanning.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.ERROR;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText(ERROR_MSG);
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnFormPlanning.toFront();
        }}else{
                c.setNom(tfNomPlan.getText());
            c.setDescription(tfDescPlan.getText());
            c.setDate_debut(Date.valueOf(tfDebutPlan.getValue()));
            c.setDatefin(Date.valueOf(tfFinPlan.getValue()));
               cs.ajout(c); 
                Notifications.create()
              .title("Planning ")
              .text("a été ajouter avec succées")
              .showWarning();
               tfNomPlan.setText("");
            tfDescPlan.setText("");
            tfDebutPlan.setValue(null);
            tfFinPlan.setValue(null);
            pnPlan.toFront();
            Stage window = (Stage)pnPlan.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.INFORMATION;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText("plan Ajouté");
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnPlan.toFront();
        }
            fnEventShow();
            ObservableList<String> valuesList = FXCollections.observableArrayList();
        COM.HRSTORMDESKTOP.services.Planning.ServicePlanning sr=new ServicePlanning();
        for (Planning comp : sr.afficher()) {
            valuesList.add(comp.getNom());
        }
        tfPlanningTache.setItems(valuesList);
            }
            
            
        }else{
            Planning c =new Planning();
            
            String ERROR_MSG="";
            if("".equals(tfNomPlan.getText() )|| tfNomPlan.getText().matches(".*\\d+.*")){
                ERROR_MSG="Le champs nom de dois pas pas étre null";
                System.out.println();
                System.out.println(ERROR_MSG);
            }
            if("".equals(tfDescPlan.getText())){
                ERROR_MSG="Le champs description de dois pas pas étre null";
                System.out.println(ERROR_MSG);
            }
            if(tfDebutPlan.getValue()==null || tfDebutPlan.getValue().isBefore(LocalDate.now()) ){
                ERROR_MSG="Le champs date debut de dois pas pas étre null ou inf a date aujoud'hui";
                System.out.println(ERROR_MSG);
            }
            if(tfFinPlan.getValue()==null || tfDebutPlan.getValue()==null || tfFinPlan.getValue().isBefore(tfDebutPlan.getValue())){
                ERROR_MSG="Le champs date fin de dois pas pas étre null ou avant la date debut";
                System.out.println(ERROR_MSG);
            }
            if(!"".equals(ERROR_MSG)){
            Stage window = (Stage)pnFormPlanning.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.ERROR;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText(ERROR_MSG);
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnFormPlanning.toFront();
        }}else{
                c.setNom(tfNomPlan.getText());
            c.setDescription(tfDescPlan.getText());
            c.setDate_debut(Date.valueOf(tfDebutPlan.getValue()));
            c.setDatefin(Date.valueOf(tfFinPlan.getValue()));
            c.setId(Integer.parseInt(lbIdPlan.getText()));
               cs.modifier(c); 
               Notifications.create()
              .title("Planning ")
              .text("a été modifier avec succées");
               tfNomPlan.setText("");
            tfDescPlan.setText("");
            tfDebutPlan.setValue(null);
            tfFinPlan.setValue(null);
            pnPlan.toFront();
            Stage window = (Stage)pnPlan.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.INFORMATION;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText("plan modifié");
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnPlan.toFront();
        }
            fnEventShow();
            ObservableList<String> valuesList = FXCollections.observableArrayList();
        COM.HRSTORMDESKTOP.services.Planning.ServicePlanning sr=new ServicePlanning();
        for (Planning comp : sr.afficher()) {
            valuesList.add(comp.getNom());
        }
        tfPlanningTache.setItems(valuesList);
            }

        }
    }

    @FXML
    private void fnAjoutPlan(ActionEvent event) {
        pnFormPlanning.toFront();
        lbTitleAjoutPlanning.setVisible(true);
        lbTitleModifyPlanning.setVisible(false);
    }

    @FXML
    private void fnModifyPlan(ActionEvent event) {
         pnFormPlanning.toFront();
        lbTitleAjoutPlanning.setVisible(false);
        lbTitleModifyPlanning.setVisible(true);
        ServicePlanning cs=new ServicePlanning();
        Planning rec=cs.getById(Integer.parseInt(lbIdPlan.getText()));
        tfNomPlan.setText(rec.getNom());
        tfDescPlan.setText(rec.getDescription());
        tfDebutPlan.setValue(rec.getDate_debut().toLocalDate());
        tfFinPlan.setValue(rec.getDatefin().toLocalDate());
    }

    @FXML
    private void fnConfTache(ActionEvent event) {
        ServicePlanning cs=new ServicePlanning();
        ServiceTache st =new ServiceTache();
        System.out.println(lbTitleAjoutTache.isVisible());
        System.out.println(lbidTache.getText());
        if(lbTitleAjoutTache.isVisible()){
            Tache c =new Tache();
            
            
            
            String ERROR_MSG="";
            if("".equals(tfNomTache.getText())){
                ERROR_MSG="Le champs nom de dois pas pas étre null";
            }
            if("".equals(tfDescripTache.getText())){
                ERROR_MSG="Le champs description de dois pas pas étre null";
            }
            if(tfPlanningTache.getValue() == null ){
                ERROR_MSG="Le champs planning de dois pas pas étre null";
            }
            if(!"".equals(ERROR_MSG)){
            Stage window = (Stage)pnFormTache.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.INFORMATION;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText(ERROR_MSG);
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnFormTache.toFront();
        }}else{
                c.setNom(tfNomTache.getText());
            c.setDescription(tfDescripTache.getText());
            c.setPlanning(cs.getByNom(tfPlanningTache.getValue()));
            if(tfPrioriteTache.getValue()==1){
                c.setPriorite("low");
            }else if(tfPrioriteTache.getValue()==2){
                c.setPriorite("medium");
            }else{
                c.setPriorite("high");
            }
               st.ajout(c); 
               tfNomTache.setText("");
            tfDescripTache.setText("");
            tfPlanningTache.setValue(null);
            tfPrioriteTache.setValue(1.0);
            pnTache.toFront();
            Stage window = (Stage)pnTache.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.INFORMATION;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText("tache Ajouté");
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnTache.toFront();
        }
            
        fnSpShow();
            }
            
            
            
            
        }else{
             Tache c =new Tache();
            
            
            
            String ERROR_MSG="";
            if("".equals(tfNomTache.getText())){
                ERROR_MSG="Le champs nom de dois pas pas étre null";
            }
            if("".equals(tfDescripTache.getText())){
                ERROR_MSG="Le champs description de dois pas pas étre null";
            }
            if("".equals(tfPlanningTache.getValue())){
                ERROR_MSG="Le champs planning de dois pas pas étre null";
            }
            if(!"".equals(ERROR_MSG)){
            Stage window = (Stage)pnFormTache.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.ERROR;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText(ERROR_MSG);
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnFormTache.toFront();
        }}else{
                c.setNom(tfNomTache.getText());
            c.setDescription(tfDescripTache.getText());
            c.setPlanning(cs.getByNom(tfPlanningTache.getValue()));
            
                            c.setId(Integer.parseInt(lbidTache.getText()));
            if(tfPrioriteTache.getValue()==1){
                c.setPriorite("low");
            }else if(tfPrioriteTache.getValue()==2){
                c.setPriorite("medium");
            }else{
                c.setPriorite("high");
            }
               st.modifier(c); 
               Stage window = (Stage)pnPlan.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.INFORMATION;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText("tache modifié");
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnPlan.toFront();
        }
               tfNomTache.setText("");
            tfDescripTache.setText("");
            tfPlanningTache.setValue(null);
            tfPrioriteTache.setValue(1.0);
            pnTache.toFront();
            
        fnSpShow();
            }
        }
    }

    @FXML
    private void fnAjoutTache(ActionEvent event) {
         pnFormTache.toFront();
        lbTitleAjoutTache.setVisible(true);
        lbTitleModifyTache.setVisible(false);
    }

    @FXML
    private void fnModifyTache(ActionEvent event) {
        pnFormTache.toFront();
        lbTitleAjoutTache.setVisible(false);
        lbTitleModifyTache.setVisible(true);
        ServiceTache eq =new ServiceTache();
        Tache e=eq.getById(Integer.parseInt(lbidTache.getText()));
        tfNomTache.setText(e.getNom());
        tfDescripTache.setText(e.getDescription()+"");
        tfPlanningTache.setValue(e.getPlanning_nom());
    }

    @FXML
    private void fnSupprimerTache(ActionEvent event) {
        ServiceTache cs =new ServiceTache();
        cs.supprimer(Integer.parseInt(lbidTache.getText()));
        fnSpShow();
    }

    @FXML
    private void fnFilter (ActionEvent event) {
        ServicePlanning cs=new ServicePlanning();
        int i=0;
        if(tfDebutFilter.getValue()==null && tfFinFilter.getValue()==null){
            fnEventShow();
        }else if(tfDebutFilter.getValue()!=null && tfFinFilter.getValue()==null){
             ServicePlanning sr=new ServicePlanning();
         ObservableList<Planning> list = FXCollections.observableArrayList(sr.afficherBydate(Date.valueOf(tfDebutFilter.getValue()), null, 1));
    
     colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));       
     colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));        
     colDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));   
     colFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));   

        
     tvPlanning.setItems(list);
    tvPlanning.setRowFactory(tv -> new TableRow<Planning>() {
    @Override
    protected void updateItem(Planning item, boolean empty) {
        super.updateItem(item, empty);
        
    }
});
      
    FilteredList<Planning> filteredData = new FilteredList<>(list, b -> true);
		
		tfRechercherPlqnnig.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Event -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Event.getDescription().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}else if(Event.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Planning> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tvPlanning.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tvPlanning.setItems(sortedData);
        }else if(tfDebutFilter.getValue()==null && tfFinFilter.getValue()!=null){
             ServicePlanning sr=new ServicePlanning();
         ObservableList<Planning> list = FXCollections.observableArrayList(sr.afficherBydate( null,Date.valueOf(tfFinFilter.getValue()), 2));;
    
     colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));       
     colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));        
     colDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));   
     colFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));   

        
     tvPlanning.setItems(list);
    tvPlanning.setRowFactory(tv -> new TableRow<Planning>() {
    @Override
    protected void updateItem(Planning item, boolean empty) {
        super.updateItem(item, empty);
        
    }
});
      
    FilteredList<Planning> filteredData = new FilteredList<>(list, b -> true);
		
		tfRechercherPlqnnig.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Event -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Event.getDescription().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}else if(Event.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Planning> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tvPlanning.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tvPlanning.setItems(sortedData);
        }else if(tfFinFilter.getValue().isBefore(tfDebutFilter.getValue())){
            String ERROR_MSG="";
            ERROR_MSG="Le champs date fin de dois pas pas étre null ou avant la date debut";
            if(!"".equals(ERROR_MSG)){
            Stage window = (Stage)pnPlan.getScene().getWindow();
            Alert.AlertType type=Alert.AlertType.ERROR;
            Alert alert=new Alert(type,"");
            
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(window);
            
            alert.getDialogPane().setContentText(ERROR_MSG);
            alert.getDialogPane().setHeaderText("");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK) {
                
                pnPlan.toFront();
        }}
        }
        
        else{
             ServicePlanning sr=new ServicePlanning();
         ObservableList<Planning> list = FXCollections.observableArrayList(sr.afficherBydate(Date.valueOf(tfDebutFilter.getValue()), Date.valueOf(tfFinFilter.getValue()), 3));;
    
     colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));       
     colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));        
     colDebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));   
     colFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));   

        
     tvPlanning.setItems(list);
    tvPlanning.setRowFactory(tv -> new TableRow<Planning>() {
    @Override
    protected void updateItem(Planning item, boolean empty) {
        super.updateItem(item, empty);
        
    }
});
      
    FilteredList<Planning> filteredData = new FilteredList<>(list, b -> true);
		
		tfRechercherPlqnnig.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Event -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Event.getDescription().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}else if(Event.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Planning> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tvPlanning.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tvPlanning.setItems(sortedData);
        }
    }

    @FXML
    private void fnReset(ActionEvent event) {
        tfDebutFilter.setValue(null);
        tfFinFilter.setValue(null);
        tfDebutFilter.setPromptText("Debut");
        tfFinFilter.setPromptText("Fin");
        fnEventShow();
    }

    @FXML
    private void fnMenuAgenda(ActionEvent event) {
        PlanningAgenda.appointments().clear();
        pnAgenda.toFront();
        ServicePlanning sr=new ServicePlanning();
        List<Planning> plan = sr.afficher();

        for(int i=0; i<plan.size();i++) {
            LocalDateTime localDateTime = plan.get(i).getDate_debut().toLocalDate().atTime(8, 00);
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            Calendar calendarDebut = Calendar.getInstance();
            calendarDebut.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());

            Calendar calendarFin = Calendar.getInstance();
            PlanningAgenda.appointments() ;
            calendarFin.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());
            Agenda.AppointmentImplLocal app=new Agenda.AppointmentImplLocal();
            app.setStartLocalDateTime(plan.get(i).getDate_debut().toLocalDate().atTime(8, 00));
            app.setEndLocalDateTime(plan.get(i).getDatefin().toLocalDate().atTime(17, 00));
            app.setDescription(plan.get(i).getNom());
            PlanningAgenda.appointments().add(app);
           
        }
    }
    
    
}
