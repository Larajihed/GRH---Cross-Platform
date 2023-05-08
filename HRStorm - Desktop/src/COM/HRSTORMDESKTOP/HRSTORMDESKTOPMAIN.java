/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 *
 * @author 21627
 */
public class HRSTORMDESKTOPMAIN extends Application {

    /**
     * @param args the command line arguments
     */
            public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("views/user/LandingPage.fxml"));
            
// Parent root=FXMLLoader.load(getClass().getResource("views/recrutement/CampagneRecrutement.fxml"));
            //Parent root=FXMLLoader.load(getClass().getResource("views/recrutement/ListCandidature.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("bootstrap.css");
            primaryStage.setTitle("HRSTORM");
            Image icon = new Image(getClass().getResourceAsStream("views/user/HR1.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(c);
            this.stage = primaryStage;
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
            public static Stage getStage() {
        return stage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
        // TODO code application logic here
    }
    
