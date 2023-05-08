/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.Recrutement;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ListRecrutmentCondFXMLController implements Initializable {
    
    private Connection cnx = DBConnector.getInstance().getCnx();

    @FXML
    private TableView<Recrutement> listRec;
    @FXML
    private TableColumn<Recrutement, String> titre;
    @FXML
    private TableColumn<Recrutement, String> description;
    @FXML
    private TableColumn<Recrutement, String> nbPoste;
    @FXML
    private TableColumn<Recrutement, String> salaire;
    @FXML
    private TableColumn<Recrutement, String> type;
    @FXML
    private TableColumn<Recrutement, String> action;

    ObservableList<Recrutement>  RecrutementList;
               @FXML
    private ImageView backButton;
    
    Recrutement rv = null ;
    private static int idRecrutement ;
    @FXML
    private Pagination table_pagi;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
    }    
    
    private void loadDate() {
        RecrutementList = FXCollections.observableArrayList();
        try {
            String req = "SELECT * FROM recrutement";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while (rs.next()) {
                Recrutement rv = new Recrutement();
            
                rv.setId(rs.getInt("id"));
                rv.setTitre(rs.getString("titre"));
                rv.setDescription(rs.getString("description"));
                rv.setNbrposte(rs.getInt("nbrposte"));
                rv.setSalaire((float) rs.getDouble("salaire"));
                rv.setType(rs.getString("type"));

                RecrutementList.add(rv);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        nbPoste.setCellValueFactory(new PropertyValueFactory<>("nbrposte"));
        salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        listRec.setItems(RecrutementList);
        
        Callback<TableColumn<Recrutement, String>, TableCell<Recrutement, String>> cellFoctory = (TableColumn<Recrutement, String> param) -> {

            final TableCell<Recrutement, String> cell = new TableCell<Recrutement, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button Postuler = new Button("Postuler");

                        Postuler.setStyle(
                            "-fx-background-color: #2196f3;"+
                            "-fx-font-family: Tahoma;"+
                            "-fx-font-size: 15px;"+
                            "-fx-text-fill: #FFF;"+
                            "-fx-background-radius: 20px;"
                        );
                        
                        Postuler.setOnAction((ActionEvent event) -> {
                            try {
                                rv = listRec.getSelectionModel().getSelectedItem();
                                idRecrutement = rv.getId();
                                Parent parent = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/Recrutement/AjoutCondidatFXML.fxml"));
                                Scene scene = new Scene(parent);
                                Stage stage = new Stage();
                                stage.setTitle("Postuler");
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            } catch (IOException ex) {
                                System.err.println(ex.getMessage());
                            }
                        });
                       

                        HBox managebtn = new HBox(Postuler);
                        managebtn.setStyle("-fx-alignment:center");

                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        action.setCellFactory(cellFoctory);
        listRec.setItems(RecrutementList);
        
        int itemsPerPage = 5; // number of rows per page
        table_pagi.setPageCount((int) Math.ceil((double) RecrutementList.size() / itemsPerPage));
        table_pagi.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * itemsPerPage;
            int toIndex = Math.min(fromIndex + itemsPerPage, RecrutementList.size());
            listRec.setItems(FXCollections.observableArrayList(RecrutementList.subList(fromIndex, toIndex)));
            return listRec;
        });
    }
      @FXML
void redirectToDash(MouseEvent event) {
                Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/LandingPage.fxml"));
            Scene c = new Scene(root);
                        Stage stage = (Stage) backButton.getScene().getWindow();
             stage.setScene(c);
           
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }}
    
    
    public static int getIdRecrutement() {
        return idRecrutement;
    }

    public static void setIdRecrutement(int idRecrutement) {
        ListRecrutmentCondFXMLController.idRecrutement = idRecrutement;
    }
    
}
