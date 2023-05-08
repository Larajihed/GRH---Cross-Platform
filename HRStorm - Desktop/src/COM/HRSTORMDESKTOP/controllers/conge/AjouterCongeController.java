/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.conge;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import COM.HRSTORMDESKTOP.models.Conge.Conge;
import COM.HRSTORMDESKTOP.models.user.User;
import COM.HRSTORMDESKTOP.services.conge.ServiceConge;

/**
 * FXML Controller class
 *
 * @author FGH
 */
public class AjouterCongeController implements Initializable {

    @FXML
    private TextField tf_categorie;
    @FXML
    private TextField tf_desc;
    @FXML
    private DatePicker dp_debut;
    @FXML
    private DatePicker dp_fin;
    @FXML
    private Button gen;
    @FXML
    private TextField QR_path;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void ajouterConge(ActionEvent event) {
        if (tf_categorie.getText().isEmpty()
                || tf_desc.getText().isEmpty()
                || dp_debut.getValue() == null
                || dp_fin.getValue() == null) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Contrôle de saisie");
            al.setContentText("Veuillez remplir tous les champs !!");
            al.show();

        } else {
            try {
                String categorie = tf_categorie.getText();
                String description = tf_desc.getText();
                LocalDate selectedDate = dp_debut.getValue();
                String debut = selectedDate.toString();

                LocalDate selectedDateF = dp_fin.getValue();
                String fin = selectedDateF.toString();

                int etat = 0;

                Conge conge = new Conge(categorie, description, etat, debut, fin);
                System.out.println(conge);
                ServiceConge serviceConge = new ServiceConge();
                serviceConge.insertOne(conge);

                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Congé ajouté");
                al.setContentText("Le congé est ajouté avec succès !!");
                al.show();

            } catch (SQLException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur SQL");
                al.setContentText(ex.getMessage());
                al.show();
            }
        }
    }

    /* 
       
@FXML
private void ajouterConge(ActionEvent event) {
    if (tf_categorie.getText().isEmpty() || tf_desc.getText().isEmpty() || dp_debut.getValue() == null || dp_fin.getValue() == null) {
        Alert al = new Alert(Alert.AlertType.WARNING);
        al.setTitle("Contrôle de saisie");
        al.setContentText("Veuillez remplir tous les champs !!");
        al.show();

    } else {
        String categorie = tf_categorie.getText();

        // Check for spelling mistakes in the input category
        CategorySpellChecker spellChecker = new CategorySpellChecker();
        
        List<String> suggestions = spellChecker.getSuggestions(categorie);
        if (!suggestions.contains(categorie.toLowerCase())) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Faute d'orthographe");
            al.setContentText("Il y a peut-être une faute d'orthographe dans la catégorie. Veuillez vérifier et réessayer. Suggestions : " + suggestions);
            al.show();
            return; // Stop execution if there's a spelling mistake
        }

        try {
            String description = tf_desc.getText();
            LocalDate selectedDate = dp_debut.getValue();
            String debut = selectedDate.toString();

            LocalDate selectedDateF = dp_fin.getValue();
            String fin = selectedDateF.toString();

            int etat = 0;

            Conge conge = new Conge(categorie, description, etat, debut, fin);
            System.out.println(conge);
            ServiceConge serviceConge = new ServiceConge();
            serviceConge.insertOne(conge);

            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Congé ajouté");
            al.setContentText("Le congé est ajouté avec succès !!");
            al.show();

        } catch (SQLException ex) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Erreur SQL");
            al.setContentText(ex.getMessage());
            al.show();
        }
    }
}


@FXML
private void restFields(ActionEvent event) {
    tf_categorie.setText("");
    tf_desc.setText("");

}

     */
    @FXML
    private void restFields(ActionEvent event) {
        tf_categorie.setText("");
        tf_desc.setText("");

    }

    private void AfficherListe(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/COM/HRSTORMDESKTOP/views/conge/AfficherCongeFXML.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Liste des Competences");
        st.setScene(scene);
        st.show();
    
    }
    @FXML
    private void gen(ActionEvent event) {
        try {
            // concatenate all attributes into a single string separated by a delimiter
            String qrText = tf_categorie.getText() + " | "
                    + tf_desc.getText() + " | "
                    + dp_debut.getValue() + " | "
                    + dp_fin.getValue() + " | ";
            
            System.out.println(qrText);

            // generate the QR code image
            ByteArrayOutputStream out = QRCode.from(qrText)
                    .to(ImageType.PNG)
                    .stream();

            // create the file name and path
            String fileName = tf_categorie.getText() + ".png";
            String filePath = "C:\\Users\\conta\\Desktop\\HRSTORMDESKTOP3\\qr\\" + fileName;

            // write the image data to a file
            FileOutputStream fout = new FileOutputStream(new File(filePath));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void read(ActionEvent event) {
        try {
            // read the QR code image file and create a binary bitmap
            InputStream barcodeInputStream = new FileInputStream(QR_path.getText());
            BufferedImage barcBufferedImage = ImageIO.read(barcodeInputStream);
            LuminanceSource source = new BufferedImageLuminanceSource(barcBufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // decode the QR code and split the resulting text into attributes
            Reader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);
            String qrText = result.getText();
            String[] attributes = qrText.split(" \\| ");

            // set the values of the GUI components based on the decoded attributes
            tf_categorie.setText(attributes[0]);
            tf_desc.setText(attributes[1]);
            dp_debut.setValue(LocalDate.parse(attributes[2]));
            dp_fin.setValue(LocalDate.parse(attributes[3]));
        } catch (Exception e) {
            // handle exceptions
        }
    }
}
