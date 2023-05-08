package gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import entities.Competence;
import entities.Poste;
import services.PosteService;

public class ListPosteForm extends Form {
    
    private PosteService posteService = PosteService.getInstance();

    public ListPosteForm() {
        this.setLayout(BoxLayout.y());
        this.setTitle("Postes");
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new HomeForm().showBack();
        });
        this.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, (evt) -> {
            new AddPosteForm().showBack();
        });

        for (Poste poste : posteService.fetchPostes()) {
            // Create a container to hold the poste name and delete button
            Container posteContainer = new Container(new BorderLayout());

            // Create an icon to represent the poste
            FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_WORK, "Label", 4);
            Label iconLabel = new Label(icon);
            posteContainer.add(BorderLayout.WEST, iconLabel);

            // Create a button to display the poste name and open the ShowPosteForm
            Button posteButton = new Button(poste.getNom());
            posteButton.addActionListener((evt) -> {
             //   new ShowPosteForm(poste).show();
            });
            posteButton.getStyle().setFgColor(0x000000);
            posteButton.getStyle().setAlignment(Component.CENTER);
            posteContainer.add(BorderLayout.CENTER, posteButton);

            // Create a button to delete the poste
            Button deleteButton = new Button("Supprimer");
            deleteButton.addPointerPressedListener((evt) -> {
                posteService.deletePoste(poste.getId());
                new ListPosteForm().show();
            });
            deleteButton.getStyle().setFgColor(0xFF0000);
            posteContainer.add(BorderLayout.EAST, deleteButton);

           
            this.add(posteContainer);
        }
    }

}
