/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author usoum
 */
public class menuForm extends Form {
    public menuForm(){
        super("",BoxLayout.y());
        Button rec = new Button("Compagne de recrutment");
        Button can = new Button("Candidature");
        
        rec.addActionListener(e->{
            ListRecrutmentForm rf = new ListRecrutmentForm();
            rf.show();
        });
        can.addActionListener(e->{
            ListCandidatForm cf = new ListCandidatForm();
            cf.show();
        });
        this.add(rec);
        this.add(can);
    }
}
