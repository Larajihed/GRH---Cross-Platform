package gui;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entities.Evaluation;
import java.util.List;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.util.ArrayList;

import services.EvaluationService;
public class ListEvaluationForm extends Form {
    
    private List<Evaluation> evaluations;
    
    public ListEvaluationForm() {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        
        evaluations = EvaluationService.getInstance().fetchEvaluations();
        System.out.println(evaluations);
        // create form elements
        Label titleLabel = new Label("Evaluations");
        titleLabel.getAllStyles().setAlignment(Component.CENTER);
        
        List<EvaluationComponent> evaluationComponents = new ArrayList<>();
        for (Evaluation evaluation : evaluations) {
            EvaluationComponent evaluationComponent = new EvaluationComponent(evaluation);
            evaluationComponents.add(evaluationComponent);
        }
        Container evaluationsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        for (EvaluationComponent evaluationComponent : evaluationComponents) {
            evaluationsContainer.add(evaluationComponent);
        }
        evaluationsContainer.setScrollableY(true);
        
        // add elements to the form
        add(BorderLayout.NORTH, titleLabel);
        add(BorderLayout.CENTER, evaluationsContainer);
    }
    
    private class EvaluationComponent extends Container {
        
        public EvaluationComponent(Evaluation evaluation) {
            super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
            
            // create form elements
            Label commentaireLabel = new Label("Commentaire: " + evaluation.getCommentaire());
            Label experienceLabel = new Label("Experience: " + evaluation.getExperience());
            Label levelLabel = new Label("Level: " + evaluation.getLevel());
            Button viewButton = new Button("View");
            
            // customize form elements style
            Style labelStyle = UIManager.getInstance().getComponentStyle("Label");

            
            // add action to the view button
            viewButton.addActionListener(evt -> {
                Dialog.show("Evaluation Details", "Commentaire: " + evaluation.getCommentaire() +
                        "\nExperience: " + evaluation.getExperience() +
                        "\nLevel: " + evaluation.getLevel(), "OK", null);
            });
            
            // add elements to the container
            add(BorderLayout.WEST, viewButton);
            Container detailsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            detailsContainer.add(commentaireLabel);
            detailsContainer.add(experienceLabel);
            detailsContainer.add(levelLabel);
            add(BorderLayout.CENTER, detailsContainer);
        }
    }
}
