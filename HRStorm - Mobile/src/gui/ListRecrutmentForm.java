/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Recrutment;
import services.Service;
import java.util.ArrayList;

/**
 *
 * @author usoum
 */
public class ListRecrutmentForm extends Form {
    public ListRecrutmentForm(){
         
        this.setTitle("Recrutement");
        this.setLayout(BoxLayout.y());
           this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new HomeForm().showBack();
        });
        Button ajouter = new Button("Ajouter Recrutment");
        ajouter.addActionListener(e->{
            AjouterRecrutmentForm a = new AjouterRecrutmentForm();
            a.show();
        });
        this.add(ajouter);
        
        String h ="Campagne de Recrutment";
        
        TextArea t1 = new TextArea(h);
Style blackStyle = t1.getAllStyles();
blackStyle.setFgColor(0x000000);
Font font = Font.createTrueTypeFont("native:MainLight", "native:MainLight")
        .derive(Display.getInstance().convertToPixels(3), Font.STYLE_PLAIN, (byte) 10);
blackStyle.setFont(font);

        this.add(t1);
        ArrayList<Recrutment> list = Service.getInstance().affichageReclamations();
        for(Recrutment r : list){
        addButton(r.getId(),r.getDescription(),r.getNbrposte(),r.getSalaire(),r.getTitre(),r.getType());
    }
}
    private void addButton(int id,String description,int nbrposte,float salaire,String titre, String type) {    
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        TextArea t = new TextArea("Titre : "+titre+"\n"+"Description : "+description+"\n"+
               "Nombre De Poste : "+nbrposte+"\n"+"Salaire : "+salaire +"\n" + "Type : "+type);
        Style blackStyle = t.getAllStyles();
        blackStyle.setFgColor(0x000000);
        Font font = Font.createTrueTypeFont("native:MainLight", "native:MainLight")
        .derive(Display.getInstance().convertToPixels(3), Font.STYLE_PLAIN, (byte) 10);
        blackStyle.setFont(font);
       Button modifier = new Button("Modifier");
       modifier.addActionListener(e->{
           Recrutment r = new Recrutment(id, titre, description, nbrposte, salaire, type);
           new ModifierRecrutmentForm(r).show();
       });
       
       Button supprimer = new Button("Supprimer");
       supprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(Dialog.show("Suppression","Vous voulez supprimer ce Recrutment ?","Annuler","Ok")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                if(Service.getInstance().deleteRecrutment(id)) {
                    new ListRecrutmentForm().show();
                }
            }
           
        });
       
        Container cnt2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        cnt2.add(modifier);
        cnt2.add(supprimer);
        cnt.add(t);
        cnt.add(cnt2);
        add(cnt);
    }
    
}
