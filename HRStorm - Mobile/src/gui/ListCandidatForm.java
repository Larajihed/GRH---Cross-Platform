/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import entities.Candidat;
import entities.Recrutment;
import services.Service;
import java.util.ArrayList;

/**
 *
 * @author usoum
 */
public class ListCandidatForm extends Form{
    private int refus =0;
    private int att =0;
    private int acc =0;
    public ListCandidatForm(){
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Button ret = new Button("<-- Retourner");
        ret.addActionListener(e->{
            menuForm menu = new menuForm();
            menu.show();
        });
        
        
    ArrayList<Candidat> list = Service.getInstance().affichageCandidat();
     for(Candidat c : list){
        addButton(c.getId(),c.getEmail(),c.getEtat(),c.getLettre(),c.getNom(),c.getPrenom(),c.getTel());
        if(c.getEtat() == 0){
            att +=1;
        }
        if(c.getEtat() == 1){
            acc +=1;
        }
        if(c.getEtat() == 2){
            refus +=1;
        }
    }
     cnt.add(ret);
     Button stat = new Button("Statistique");
        stat.addActionListener(e->{
            statForm st = new statForm(acc, refus, att);
            st.show();
        });
      cnt.add(stat);
      this.add(cnt);
        
   }
    
    private void addButton(int id, String email, int etat, String lettre, String nom, String prenom, int tel) {
         
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        String e = "" ;
        if(etat == 0){
            e="En attente";
        }
        if(etat == 1){
            e="Accepté";
        }
        if(etat == 2){
            e = "Refusé";
        }
        
        TextArea t = new TextArea("Nom: "+nom+"\n"+"Prenom : "+prenom+"\n"+
               "tel : "+tel+"\n"+"Email : "+email +"\n" + "etat : "+e);
        
        Button accepter = new Button("Accepter");
        accepter.addActionListener(g->{
            if(Service.getInstance().AccepterCandidat(id)){
                ListCandidatForm li = new ListCandidatForm();
                li.show();
            }
        });
        Button refuser = new Button("Refuser");
        refuser.addActionListener(g->{
            if(Service.getInstance().RefuserCandidat(id)){
                ListCandidatForm li = new ListCandidatForm();
                li.show();
            }
        });
 
       
       
       
        Container cnt2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        if(etat == 0){
            cnt2.add(accepter);
            cnt2.add(refuser);
        }
        
        
       Button supprimer = new Button("Supprimer");
       supprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(Dialog.show("Suppression","Vous voulez supprimer ce Candidat ?","Annuler","Ok")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                if(Service.getInstance().deleteCandidat(id)) {
                    new ListCandidatForm().show();
                }
            }
           
        });
        
        cnt2.add(supprimer);
        
        cnt.add(t);
        cnt.add(cnt2);
        add(cnt);
    }
 }

    
