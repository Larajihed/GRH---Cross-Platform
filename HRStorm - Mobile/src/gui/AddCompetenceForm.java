/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author conta
 */

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import entities.Competence;
import java.io.IOException;
import services.CompetenceService;


public class AddCompetenceForm extends Form {
    
    
    	//var
    CompetenceService cs = CompetenceService.getInstance();

    public AddCompetenceForm() {
        
 	//CUSTOM
        this.setLayout(BoxLayout.y());
        this.setTitle("Nouvelle Competence");
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
           
                new HomeForm().showBack();
            
        });

        //Widgets
       TextField nomField = new TextField("", "Nom de compétence");
    nomField.getStyle().setMarginUnit(Style.UNIT_TYPE_DIPS);
    nomField.getStyle().setPaddingUnit(Style.UNIT_TYPE_DIPS);
    nomField.getStyle().setPadding(5,5,5,5);
    nomField.getStyle().setBorder(Border.createLineBorder(1, 0xCCCCCC));
    nomField.getStyle().setFgColor(0x000000);

    TextField descriptionField = new TextField("", "Description de compétence");


    Button createButton = new Button("Créer");
    createButton.getStyle().setMarginUnit(Style.UNIT_TYPE_DIPS);
    createButton.getStyle().setPaddingUnit(Style.UNIT_TYPE_DIPS);
    createButton.getStyle().setPadding(5,5,5,5);
    createButton.getStyle().setBorder(Border.createLineBorder(1, 0xCCCCCC));



        //actions
        createButton.addActionListener((evt) -> {
               cs.ajoutCompetence(new Competence(nomField.getText(), descriptionField.getText() ));
                Dialog.show("Success", "Task Inserted successfully", "Got it", null);        
                //Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
        });

        //end
        this.addAll(nomField, descriptionField, createButton);

    }

   
}