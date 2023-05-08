/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import entities.Recrutment;
import services.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author usoum
 */
public class AjouterRecrutmentForm extends Form {
    public AjouterRecrutmentForm(){
        TextField titre = new TextField("","Titre");
        addStringValue("Titre",titre);
        TextField desc = new TextField("","Description");
        addStringValue("Description",desc);
        TextField nbr = new TextField("","Nombre de poste");
        addStringValue("Nombre de poste",nbr);
        TextField salaire = new TextField("","Salaire");
        addStringValue("Salaire",salaire);
        TextField type = new TextField("","Type");
        addStringValue("Type",type);
        
        Button btn = new Button("Ajouter");
        this.add(btn);
        Button rturn = new Button("<-- Retourner");
        this.add(rturn);
        rturn.addActionListener(e ->{
           ListRecrutmentForm l = new ListRecrutmentForm();
           l.show();
    });
        btn.addActionListener(e ->{
                        try {
                
                if(titre.getText().equals("") || desc.getText().equals("") || nbr.getText().equals("") || salaire.getText().equals("") ||type.getText().equals("") ) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    
                    
                    //njibo iduser men session (current user)
                    Recrutment r = new Recrutment(String.valueOf(titre.getText().toString()), String.valueOf(desc.getText().toString()), Integer.parseInt(nbr.getText()), Float.parseFloat(salaire.getText()), String.valueOf(type.getText().toString()));

                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    Service.getInstance().ajoutReclamation(r);
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    
                    //ba3d ajout net3adaw lel ListREclamationForm
                    new ListRecrutmentForm().show();
                    
                    
                    refreshTheme();//Actualisation
                            
                }
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
        });
        
    }
    
    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        
    }
}
