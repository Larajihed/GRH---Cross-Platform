
package gui;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entities.Competence;
import java.util.List;
import services.CompetenceService;

public class StatsForm extends Form {
    
    private final CompetenceService competenceService = CompetenceService.getInstance();

    public StatsForm() {
        // Fetch the Competence data
        List<Competence> competences = competenceService.fetchTasks();

        // Create UI components to display the data
        Label title = new Label("Competences");
        title.getAllStyles().setAlignment(ComponentGroup.CENTER);
        title.getAllStyles().setPaddingTop(10);
        title.getAllStyles().setPaddingBottom(10);

        Label count = new Label(String.valueOf(competences.size()));
        count.getAllStyles().setAlignment(ComponentGroup.CENTER);
        count.getAllStyles().setPaddingTop(10);
        count.getAllStyles().setPaddingBottom(10);
        count.getAllStyles().setFont(Font.createTrueTypeFont("native:MainLight", 50f));

        Button refreshButton = new Button("Refresh");
        refreshButton.addActionListener(e -> refreshData());

        Container content = BoxLayout.encloseY(title, count, refreshButton);
        content.getAllStyles().setAlignment(ComponentGroup.CENTER);
        //content.getAllStyles().setPadding(Component.ALL, 20);

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, content);
        this.setTitle("Competence Dashboard");
    }

    private void refreshData() {
        // Refresh the data and update the UI
        List<Competence> competences = competenceService.fetchTasks();
        Label count = (Label) this.getContentPane().getComponentAt(0, 1);
        count.setText(String.valueOf(competences.size()));
        count.getParent().animateLayout(200);
    }
}
