/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import entities.Planning;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import services.PlanningService;

/**
 *
 * @author ilyes
 */
public class PlanningListForm extends Form {
    private ArrayList<Planning> plannings;
    // Constructor
    public PlanningListForm() {
        this.setTitle("Planning");
          this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new HomeForm().showBack();
        });
        this.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, (evt) -> {
            new AddPlanForm().showBack();
        });
        plannings = PlanningService.getInstance().getAllPlans();
        for (Planning planning : plannings){
            PlanningItem planningitem = new PlanningItem(planning);
             Container planningContainer = BoxLayout.encloseY(planningitem);
             add(planningContainer);
        }
                Button addButton1 = new Button("nouveau");

        addButton1.addActionListener(e -> {
            new AddPlanForm().show();
        });
        add(addButton1);   
              
        
    }
}

