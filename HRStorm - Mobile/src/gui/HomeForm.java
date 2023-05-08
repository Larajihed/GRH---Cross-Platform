package gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;

import com.codename1.ui.Form;

import com.codename1.ui.Component;

import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Font;
import com.codename1.ui.Display;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Container;
import java.io.IOException;



public class HomeForm extends Form {
public HomeForm()  {
 // create toolbar
        Toolbar toolbar = new Toolbar();
        setToolbar(toolbar);
        toolbar.addCommandToRightBar(new Command("Logout") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    // perform logout action here
                    new LoginForm().show();
                } catch (IOException ex) {
                }
            }
        });

    
        // set title of the form
        this.setLayout(BoxLayout.y());



// create container for profile icon and dashboard label
Container header = new Container(new BoxLayout(BoxLayout.X_AXIS));
Button profileBtn = new Button(FontImage.MATERIAL_PERSON);
profileBtn.addActionListener((evt) -> {
    new ProfileForm().show();
});
Label dashboardLabel = new Label("Dashboard");
Font font = Font.createTrueTypeFont("native:MainLight", "native:MainLight").derive(Display.getInstance().convertToPixels(6, true), Font.STYLE_BOLD);
dashboardLabel.getUnselectedStyle().setFont(font);
dashboardLabel.getUnselectedStyle().setFgColor(0x000000);
dashboardLabel.getUnselectedStyle().setAlignment(Component.CENTER);
header.add(profileBtn);
header.add(dashboardLabel);

    Button competenceBtn = new Button("Competences", FontImage.createMaterial(FontImage.MATERIAL_PEOPLE, "Button", 5));
    Button recrutementBtn = new Button("Gestion de recrutement", FontImage.createMaterial(FontImage.MATERIAL_WORK, "Button", 5));
    Button evalBtn = new Button("Evaluations", FontImage.createMaterial(FontImage.MATERIAL_WORK, "Button", 5));
    Button posteBtn = new Button("Fiches des postes", FontImage.createMaterial(FontImage.MATERIAL_DESCRIPTION, "Button", 5));
    Button planningBtn = new Button("Planning", FontImage.createMaterial(FontImage.MATERIAL_CALENDAR_TODAY, "Button", 5));
    Button congeBtn = new Button("Congés", FontImage.createMaterial(FontImage.MATERIAL_ADMIN_PANEL_SETTINGS, "Button", 5));
    Button budgetBtn = new Button("Budget", FontImage.createMaterial(FontImage.MATERIAL_BAR_CHART, "Button", 5));
    
    // add action to the button
    competenceBtn.addActionListener((evt) -> {
        new ListCompetenceForm().show();
    });
    congeBtn.addActionListener((evt) -> {
        new ListCongeForm().show();
    });
    budgetBtn.addActionListener((evt) -> {
        System.out.println("Budget Clicked !");
    });
    evalBtn.addActionListener((evt) -> {
        new ListEvaluationForm().show();
    });
    posteBtn.addActionListener((evt) -> {
        new ListPosteForm().show();
    });

    recrutementBtn.addActionListener((evt) -> {
        new ListRecrutmentForm().show();
    });
    planningBtn.addActionListener((evt) ->{
        new PlanningListForm().show();
    });
    this.addAll(
        header,
        competenceBtn,
        posteBtn,
        recrutementBtn,
        planningBtn,
        evalBtn,
        congeBtn,
        budgetBtn
    );
}

}
