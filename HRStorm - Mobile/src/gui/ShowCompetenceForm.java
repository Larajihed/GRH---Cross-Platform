package gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import entities.Competence;

public class ShowCompetenceForm extends Form {

    private Competence competence;

    public ShowCompetenceForm(Competence competence) {
        this.competence = competence;

        //custom
        this.setLayout(BoxLayout.y());
        this.setTitle("Competence Details");
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new ListCompetenceForm().showBack();
        });

        //widgets
        Label nomLabel = new Label("Nom: " + competence.getNom());
        Label descriptionLabel = new Label("Description: " + competence.getDescription());
        Button editButton = new Button("Edit");
        editButton.addActionListener((evt) -> {
            new EditCompetenceForm(competence).show();
        });

        //end
        this.add(nomLabel);
        this.add(descriptionLabel);
        this.add(editButton);
    }

}

    
