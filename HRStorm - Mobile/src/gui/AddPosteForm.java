package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import entities.Competence;
import entities.Poste;
import services.CompetenceService;
import services.PosteService;

public class AddPosteForm extends Form {
    // Services
    PosteService posteService = PosteService.getInstance();
    CompetenceService competenceService = CompetenceService.getInstance();

    public AddPosteForm() {
        this.setLayout(BoxLayout.y());
        this.setTitle("Nouveau Poste");
 this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new HomeForm().show();
        });
        // Widgets
        TextField nomField = new TextField("", "Nom de poste");
        TextField missionsField = new TextField("", "Liste des missions");
        TextField descriptionField = new TextField("", "Description");
        TextField salaireMinField = new TextField("", "Salaire minimum");
        TextField salaireMaxField = new TextField("", "Salaire maximum");
        Button addCompetenceButton = new Button("Ajouter compétence");
        Button addPosteButton = new Button("Ajouter poste");

        Label competencesLabel = new Label("Compétences :");

        // Actions
        addCompetenceButton.addActionListener(evt -> {
            //new AddCompetenceForm(this).show();
        });

        addPosteButton.addActionListener(evt -> {
            try {
                // Create a new Poste object
                Poste poste = new Poste();
                poste.setNom(nomField.getText());
                poste.setMissions(missionsField.getText());
                poste.setDescription(descriptionField.getText());
                poste.setSalaireMin(Float.parseFloat(salaireMinField.getText()));
                poste.setSalaireMax(Float.parseFloat(salaireMaxField.getText()));

                // Add selected competences to the poste
                for (Competence competence : competenceService.fetchTasks()) {
                //   poste.setCompetences((Collection<Competence>) competence);
                }

                // Add the new poste
                posteService.addPoste(poste);

                // Show success message
                Dialog.show("Succès", "Le poste a été ajouté avec succès", "OK", null);
                // Go back to the previous form
                this.showBack();
            } catch (NumberFormatException e) {
                Dialog.show("Erreur", "Le salaire doit être un nombre", "OK", null);
            }
        });

        // Add widgets to the form
        this.add(nomField)
            .add(missionsField)
            .add(descriptionField)
            .add(salaireMinField)
            .add(salaireMaxField)
            .add(competencesLabel)
            .add(addCompetenceButton)
            .add(addPosteButton);
    }
}
