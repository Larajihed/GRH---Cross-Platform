package gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import entities.Competence;
import services.CompetenceService;

public class EditCompetenceForm extends Form {

    private Competence competence;
    private TextField nomField;
    private TextField descriptionField;

    public EditCompetenceForm(Competence competence) {
        this.competence = competence;

        //custom
        this.setLayout(BoxLayout.y());
        this.setTitle("Edit Competence");
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new ShowCompetenceForm(competence).showBack();
        });

        //widgets
        nomField = new TextField(competence.getNom());
        descriptionField = new TextField(competence.getDescription());
        Button saveButton = new Button("Save");
        saveButton.addActionListener((evt) -> {
            saveChanges();
            new ShowCompetenceForm(competence).show();
        });

        //end
        this.add(new SpanLabel("Edit the fields you want to change:"));
        this.add(new SpanLabel("Nom:"));
        this.add(nomField);
        this.add(new SpanLabel("Description:"));
        this.add(descriptionField);
        this.add(saveButton);
    }

    private void saveChanges() {
        competence.setNom(nomField.getText());
        competence.setDescription(descriptionField.getText());
        CompetenceService.getInstance().modifierCompetence(competence);
    }
}
