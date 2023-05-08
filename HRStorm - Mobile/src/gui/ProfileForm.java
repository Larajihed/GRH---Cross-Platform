package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entities.User;
import java.io.IOException;

public class ProfileForm extends Form {

    private Resources theme = UIManager.initFirstTheme("/theme");

    public ProfileForm() {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        User currentUser = SessionManager.getUser();

        // create form elements
        Label emailLabel = new Label("Email: " + currentUser.getEmail());
        Label nomLabel = new Label("Nom: " + currentUser.getNom());
        Label prenomLabel = new Label("Prenom: " + currentUser.getPrenom());
        //Label nomsocieteLabel = new Label("Nom de société: " + currentUser.getNomsociete());

        // customize form elements style
        Style labelStyle = UIManager.getInstance().getComponentStyle("Label");
       

        // create and customize back button
        Button backButton = new Button("Back");
        Style backButtonStyle = UIManager.getInstance().getComponentStyle("TitleCommand");
        backButtonStyle.setFont(theme.getFont("Font Awesome 5 Free Solid").derive(Display.getInstance().convertToPixels(4, true), Font.STYLE_PLAIN));
        backButtonStyle.setFgColor(0x000000);
        backButton.setUIID("TitleCommand");
       

        // add action to the back button
        backButton.addActionListener((evt) -> {
        
                new HomeForm().showBack();
            
        });

        // create and customize toolbar
        

        // create container for form elements
        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.add(emailLabel);
        center.add(nomLabel);
        center.add(prenomLabel);
        //center.add(nomsocieteLabel);
        center.setScrollableY(true);

        // add container to form
        add(BorderLayout.CENTER, center);
    }
}
