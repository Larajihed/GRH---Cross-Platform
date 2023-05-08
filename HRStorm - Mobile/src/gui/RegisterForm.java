package gui;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import services.ServiceUtilisateur;

public class RegisterForm extends Form {

    private TextField emailField;
    private TextField passwordField;
    private TextField nomField;
    private TextField prenomField;
    private TextField nomsocieteField;

    public RegisterForm() {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));

        // create form elements
        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label emailLabel = new Label("Email:");
        emailField = new TextField("", "Enter your email address", 20, TextField.EMAILADDR);
        Label passwordLabel = new Label("Password:");
        passwordField = new TextField("", "Enter your password", 20, TextField.PASSWORD);
        Label nomLabel = new Label("Nom:");
        nomField = new TextField("", "Enter your last name", 20, TextField.ANY);
        Label prenomLabel = new Label("Prenom:");
        prenomField = new TextField("", "Enter your first name", 20, TextField.ANY);
        Label nomsocieteLabel = new Label("Nom Societe:");
        nomsocieteField = new TextField("", "Enter your company name", 20, TextField.ANY);
        Button registerButton = new Button("Register");

        // add action listener to register button
        registerButton.addActionListener((evt) -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String nomsociete = nomsocieteField.getText();

            // call service method to register user
            try {
                ServiceUtilisateur.getInstance().register(email, password, nom, prenom, nomsociete);
                ToastBar.showInfoMessage("Account created successfully");
            } catch (Exception e) {
                ToastBar.showErrorMessage("Error creating account");
            }

        });

        // add form elements to container
        center.add(emailLabel);
        center.add(emailField);
        center.add(passwordLabel);
        center.add(passwordField);
        center.add(nomLabel);
        center.add(nomField);
        center.add(prenomLabel);
        center.add(prenomField);
        center.add(nomsocieteLabel);
        center.add(nomsocieteField);
        center.add(registerButton);

        // add container to form
        add(BorderLayout.CENTER, center);
    }

    // getters for form fields
    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getNom() {
        return nomField.getText();
    }

    public String getPrenom() {
        return prenomField.getText();
    }

    public String getNomsociete() {
        return nomsocieteField.getText();
    }
}
