/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import entities.Recrutment;
import services.Service;

/**
 *
 * @author usoum
 */
public class ModifierRecrutmentForm extends Form {
    public ModifierRecrutmentForm(Recrutment r){
        super("Newsfeed",BoxLayout.y()); 
        TextField Titre = new TextField(r.getTitre());
        TextField Description = new TextField(r.getDescription());
        TextField nbr = new TextField(Integer.toString(r.getNbrposte()));
        TextField salaire = new TextField(Float.toString(r.getSalaire()));
        TextField type = new TextField( r.getType());
        
        Button btnmodifier = new Button("Modifier Offre");
        btnmodifier.addActionListener(e ->{
            r.setDescription(Description.getText());
            r.setNbrposte(Integer.parseInt(nbr.getText()));
            r.setSalaire(Integer.parseInt(salaire.getText()));
            r.setTitre(Titre.getText());
            r.setType(type.getText());
            if(Service.getInstance().modifierReclamation(r)){
                new ListRecrutmentForm().show();
            }
            
        });
        
       Container cnt = new Container (new BoxLayout(BoxLayout.Y_AXIS));
       cnt.add(Titre);
       cnt.add(Description);
       cnt.add(nbr);
       cnt.add(salaire);
       cnt.add(type);
       add(cnt);
       add(btnmodifier);
        
    }
    
}
