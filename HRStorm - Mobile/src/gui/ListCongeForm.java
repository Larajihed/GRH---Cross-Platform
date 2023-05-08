/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import entities.Conge;
import services.ServiceConge;
import java.util.ArrayList;
import gui.HomeForm;
/**
 *
 * @author usoum
 */
public class ListCongeForm extends Form {
    public ListCongeForm(){
        super("",BoxLayout.y());
        Button ajouter = new Button("Ajouter Conge");
        ajouter.addActionListener(e->{
            /*AjouterCongeForm a = new AjouterCongeForm();
            a.show();
            */
        });

        
        
        
        
         this.setTitle("Congés");
        this.setLayout(BoxLayout.y());
           this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new HomeForm().showBack();
        });
        this.add(ajouter);
        
        String h ="Mes Conge";
        TextArea t1 = new TextArea(h);
        this.add(t1);
        /*ArrayList<Conge> list = ServiceConge.getInstance().affichageConge();
        for(Conge r : list){
        addButton((int) r.getId(),r.getDescription(),r.getCategorie(),r.getDebut_m(),r.getFin_m());
    }
        
        */

}
    private void addButton(int id,String description,String categorie,String debut_m, String fin_m) {
         
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        TextArea t = new TextArea("Categorie : "+ categorie +"\n"+"Description : "+description+"\n"+
               "date de debut : "+debut_m+"\n"+"date du fin : "+fin_m +"\n" );
      
       Button modifier = new Button("Modifier");
       modifier.addActionListener(e->{
           Conge r = new Conge(id, categorie, description, debut_m, fin_m);
           //new ModifierCongeForm(r).show();
       });
       
       Button supprimer = new Button("Supprimer");
       supprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(Dialog.show("Suppression","Vous voulez supprimer ce Conge ?","Annuler","Ok")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                /*
                if(ServiceConge.getInstance().deleteConge((int)id)) {
                    new ListCongeForm().show();
                }
*/
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
