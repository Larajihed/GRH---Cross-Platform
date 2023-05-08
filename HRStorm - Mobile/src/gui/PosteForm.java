/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author conta
 */

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author khaledguedria
 */
public class PosteForm extends Form{
    
    
    //var

    public PosteForm() {
        
        //custom
        this.setLayout(BoxLayout.yCenter());
        this.setTitle("Nouvelle Poste");
        
        //widgets
        Button addTaskBtn = new Button("Create");
        Button showTaskBtn = new Button("Show Task");
        
        //actions
        addTaskBtn.addActionListener((evt) -> {
           
            
            
        });
        //..
        showTaskBtn.addActionListener((evt) -> {
           
            
        });
        
        //end
        this.addAll(addTaskBtn, showTaskBtn);
        
    }
    
    
    
}