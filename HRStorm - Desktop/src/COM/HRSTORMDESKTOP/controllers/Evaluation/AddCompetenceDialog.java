/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.controllers.Evaluation;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import COM.HRSTORMDESKTOP.models.Evaluation.Competence;

public class AddCompetenceDialog extends Dialog<Competence> {

    private TextField nomField;
    private TextArea descriptionArea;

    public AddCompetenceDialog() {
        setTitle("Ajouter une compétence");

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> addCompetence());

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(e -> close());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        nomField = new TextField();
        nomField.setPromptText("Nom");

        descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionArea, 1, 1);

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(addButton, cancelButton);
        grid.add(buttons, 1, 2);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        buttons.getChildren().add(spacer);

        getDialogPane().setContent(grid);
    }

    private void addCompetence() {
        String nom = nomField.getText();
        String description = descriptionArea.getText();
        if (nom.isEmpty() || description.isEmpty()) {
            return;
        }
        Competence competence = new Competence(nom, description);
        setResult(competence);
        close();
    }

    public static Competence showAddCompetenceDialog() {
        AddCompetenceDialog dialog = new AddCompetenceDialog();
        dialog.showAndWait();
        return dialog.getResult();
    }

}