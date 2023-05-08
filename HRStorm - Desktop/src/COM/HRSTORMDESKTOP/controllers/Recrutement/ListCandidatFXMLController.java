/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.Recrutement;

import COM.HRSTORMDESKTOP.models.Recrutement.Candidat;
import COM.HRSTORMDESKTOP.models.Recrutement.Recrutement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author usoum
 */
public class ListCandidatFXMLController implements Initializable {
    
    private Connection cnx = DBConnector.getInstance().getCnx();

    @FXML
    private TableView<Candidat> listCond;
    @FXML
    private TableColumn<Candidat, String> nom;
    @FXML
    private TableColumn<Candidat, String> prenom;
    @FXML
    private TableColumn<Candidat, String> dateN;
    @FXML
    private TableColumn<Candidat, String> tel;
    @FXML
    private TableColumn<Candidat, String> email;
    @FXML
    private TableColumn<Candidat, String> etat;
    @FXML
    private TableColumn<Candidat, String> action;
                @FXML
    private ImageView backButton;
    
    ObservableList<Candidat>  CandidatList;
    
    Candidat rv = null ;
    @FXML
    private AnchorPane list_cond_form;
    @FXML
    private AnchorPane stat_form;
    @FXML
    private PieChart condi_chart;
    @FXML
    private Button list_btn;
    @FXML
    private Button stat_btn;
    private int nbracc=0;
    private int nbrrefu=0;
    private int nbratt=0;
    @FXML
    private TextField recherche;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
        ghraphe();
        listCandSearch();
    }    
    
    private void loadDate() {
        CandidatList = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM candidat";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while (rs.next()) {
                Candidat rv = new Candidat();
            
                rv.setId(rs.getInt("id"));
                rv.setNom(rs.getString("nom"));
                rv.setPrenom(rs.getString("prenom"));
                rv.setDatenaissance(rs.getDate("datenaissance"));
                rv.setTel(rs.getInt("tel"));
                rv.setEmail(rs.getString("email"));
                rv.setEtat(rs.getInt("etat"));
                
                int IntEtat = rs.getInt("etat");
                
                String e = "" ;
                if(IntEtat == 0){
                    e="En attente";
                    nbratt = nbratt+1;
                }
                if(IntEtat == 1){
                    e="Accepté";
                    nbracc = nbracc+1;
                }
                if(IntEtat == 2){
                    e = "Refusé";
                    nbrrefu = nbrrefu+1;
                }
                rv.setNometat(e);
                
                System.out.println("----------------"+e);
 
                
                CandidatList.add(rv);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateN.setCellValueFactory(new PropertyValueFactory<>("datenaissance"));
        etat.setCellValueFactory(new PropertyValueFactory<>("nometat"));
        
        listCond.setItems(CandidatList);
        
        Callback<TableColumn<Candidat, String>, TableCell<Candidat, String>> cellFoctory = (TableColumn<Candidat, String> param) -> {

            final TableCell<Candidat, String> cell = new TableCell<Candidat, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button Accepter = new Button("Accepter");
                        Button Refuser = new Button("Refuser");

                        Accepter.setStyle(
                            "-fx-background-color: #2196f3;"+
                            "-fx-font-family: Tahoma;"+
                            "-fx-font-size: 15px;"+
                            "-fx-text-fill: #FFF;"+
                            "-fx-background-radius: 20px;"
                        );
                        Refuser.setStyle(
                            "-fx-background-color: #2196f3;"+
                            "-fx-font-family: Tahoma;"+
                            "-fx-font-size: 15px;"+
                            "-fx-text-fill: #FFF;"+
                            "-fx-background-radius: 20px;"
                        );
                        
                        Accepter.setOnAction((ActionEvent event) -> {
                            try {
                                rv = listCond.getSelectionModel().getSelectedItem();
                                String req = "UPDATE candidat SET etat = 1 WHERE id ="+rv.getId();
                                PreparedStatement pst = cnx.prepareStatement(req);
                                System.out.println("Condidat activé");
                                pst.executeUpdate();
                                loadDate();
                            } catch (SQLException ex) {
                                System.err.println(ex.getMessage());
                            }
                        });
                        Refuser.setOnAction((ActionEvent event) -> {
                            try {
                                rv = listCond.getSelectionModel().getSelectedItem();
                                String req = "UPDATE candidat SET etat = 2 WHERE id ="+rv.getId();
                                PreparedStatement pst = cnx.prepareStatement(req);
                                System.out.println("Condidat refusé");
                                pst.executeUpdate();
                                loadDate();
                            } catch (SQLException ex) {
                                System.err.println(ex.getMessage());
                            }
                        });
                       

                        HBox managebtn = new HBox(Accepter,Refuser);
                        managebtn.setStyle("-fx-alignment:center");

                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        action.setCellFactory(cellFoctory);
        listCond.setItems(CandidatList);
    }
    @FXML
    public void switchform(ActionEvent event){
        if(event.getSource()==stat_btn){
            list_cond_form.setVisible(false);
            stat_form.setVisible(true);
        }
        else if(event.getSource()==list_btn){
            list_cond_form.setVisible(true);
            stat_form.setVisible(false);
        }
    }
      @FXML
void redirectToDash(MouseEvent event) {
                Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Recrutement/ResponsableFXML.fxml"));
            Scene c = new Scene(root);
                        Stage stage = (Stage) backButton.getScene().getWindow();
             stage.setScene(c);
           
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }}
    public void ghraphe(){
        ObservableList<PieChart.Data> chart = FXCollections.observableArrayList();
        chart.add(new PieChart.Data("Accepté",nbracc));
        chart.add(new PieChart.Data("Refusé",nbrrefu));
        chart.add(new PieChart.Data("En attente",nbratt));
        condi_chart.setData(chart);
        
    }
    
    public void listCandSearch() {

        FilteredList<Candidat> filter = new FilteredList<>(CandidatList, e -> true);

        recherche.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateCandidatData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateCandidatData.getNom().toLowerCase().startsWith(searchKey)) {
                    return true;
                } 
                else {
                    return false;
                }
            });
        });

        SortedList<Candidat> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(listCond.comparatorProperty());
        listCond.setItems(sortList);
    }
    
}
