/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import entities.Competence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import services.CompetenceService;

/**
 *
 * @author conta
 */
public class ListCompetenceForm extends Form{
    
    CompetenceService cs = CompetenceService.getInstance();

    public ListCompetenceForm() {
        //custom
        this.setLayout(BoxLayout.y());
        this.setTitle("All Competences");
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new HomeForm().showBack();
        });
        this.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, (evt) -> {
            new AddCompetenceForm().showBack();
        });
         for (Competence c : cs.fetchTasks()) {
    // Create a container to hold the competence name and delete button
    Container competenceContainer = new Container(new BorderLayout());


    // Create an icon to represent the competence
    FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_CLASS, "Label", 4);
    Label iconLabel = new Label(icon);
    competenceContainer.add(BorderLayout.WEST, iconLabel);

    // Create a button to display the competence name and open the ShowCompetenceForm
    Button competenceButton = new Button(c.getNom());
    competenceButton.addActionListener((evt) -> {
        new ShowCompetenceForm(c).show();
    });
    competenceButton.getStyle().setFgColor(0x000000);
    competenceButton.getStyle().setAlignment(Component.CENTER);
    competenceContainer.add(BorderLayout.CENTER, competenceButton);

    // Create a button to delete the competence
    Button deleteButton = new Button("Supprimer");
    deleteButton.addPointerPressedListener((evt) -> {
                CompetenceService.getInstance().deleteCompetence(c.getId()) ;
                new ListCompetenceForm().show();
                
            });

        
   
        
        
this.getToolbar().addSearchCommand(e -> {
    String text = (String)e.getSource();
    List<Competence> competences = cs.fetchTasks();
    for (Competence t : competences) {
        // Filter the competences based on the search text
        if(!(text.length() == 0) && !c.getNom().toLowerCase().contains(text)) {
            // Hide the competence if it doesn't match the search text
            competenceContainer.setVisible(false);   
        } else {
            // Show the competence if it matches the search text
            competenceContainer.setHidden(!true);
        }
    }
    // Animate the layout to reflect the changes
    this.getContentPane().animateLayout(150);
});

        //widgets
      
        
    deleteButton.getStyle().setFgColor(0xFF0000);
    competenceContainer.add(BorderLayout.EAST, deleteButton);
         Button editButton = new Button("Modifier");
    editButton.addPointerPressedListener((evt) -> {
                CompetenceService.getInstance().modifierCompetence(c) ;
                new EditCompetenceForm(c).show();
                
            });
    this.add(competenceContainer);
}

    }

    
}
