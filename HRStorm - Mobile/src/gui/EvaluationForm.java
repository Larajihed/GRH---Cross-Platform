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
public class EvaluationForm extends Form{
    
    
    //var

    public EvaluationForm() {
        
        //custom
        this.setLayout(BoxLayout.yCenter());
        this.setTitle("Home");
        
        //widgets
        Button addTaskBtn = new Button("Add Task");
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