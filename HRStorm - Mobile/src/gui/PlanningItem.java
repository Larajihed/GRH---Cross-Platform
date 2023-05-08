/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import entities.Planning;
import services.PlanningService;

/**
 *
 * @author ilyes
 */
    public class PlanningItem extends Container {
    private Label nameLabel;
    private Label descriptionLabel;
    private Label startDateLabel;
    private Label endDateLabel;
    private Button deleteButton;
    private Button editButton;
    private Button shareButton;
    private Button addTaskButton;

    // Constructor
    public PlanningItem(Planning planning) {
        // Set the Container's layout to BorderLayout
        super(new BorderLayout());

        // Create Labels for each Planning item property
        nameLabel = new Label(planning.getName());
        descriptionLabel = new Label(planning.getDescription());
        startDateLabel = new Label(planning.getStartDate().toString());
        endDateLabel = new Label(planning.getEndDate().toString());

        // Create Buttons for each action
        deleteButton = new Button(FontImage.MATERIAL_DELETE);
        editButton = new Button(FontImage.MATERIAL_EDIT);
        shareButton = new Button(FontImage.MATERIAL_SHARE);
        addTaskButton = new Button(FontImage.MATERIAL_ADD_TASK);

        // Add the Labels and Buttons to the Container's BorderLayout positions
        add(BorderLayout.NORTH, nameLabel);
        add(BorderLayout.CENTER, descriptionLabel);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(startDateLabel, endDateLabel));
        add(BorderLayout.WEST,BoxLayout.encloseY(deleteButton, editButton, shareButton, addTaskButton));
         //   FlowLayout.encloseRight(deleteButton, editButton, shareButton, addTaskButton)));

        // Set the Container's background color to white
        getStyle().setBgColor(0xffffff);

        // Add ActionListener to each Button to show another Form when clicked
        deleteButton.addActionListener(e -> {
          //  Form deleteForm = new Form("Delete Planning", new BorderLayout());
           // deleteForm.addComponent(BorderLayout.CENTER, new Label("Are you sure you want to delete this planning?"));
//            deleteForm.show();
PlanningService.getInstance().deletePlan(planning.getId());
new PlanningListForm().show();
        });
        
        editButton.addActionListener(e -> {
            new EditPlanForm(planning).show();
        });

        shareButton.addActionListener(e -> {
            Form shareForm = new Form("Share Planning", new BorderLayout());
            TextField emailField = new TextField("", "Email", 20, TextField.EMAILADDR);

// Add the email field to the form
    Container emailContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
    emailContainer.add(new Label("Recipient Email: "));
    emailContainer.add(emailField);
    shareForm.add(BorderLayout.NORTH, emailContainer);

// Create the share button
        Button shareButton = new Button("Share");
        shareButton.addActionListener(ev -> {
            // When the share button is clicked, get the email address from the input field and send the email
            String recipientEmail = emailField.getText();
            if (recipientEmail.isEmpty()) {
                Dialog.show("Error", "Recipient Email is required", "OK", null);
                return;
            }

            // TODO: implement code to send email with the Planning attached

            // Show a success message after the email is sent
            Dialog.show("Success", "Planning shared successfully", "OK", null);
        });
        shareForm.add(BorderLayout.SOUTH, shareButton);

        // Show the share form
        shareForm.show();
        });

        addTaskButton.addActionListener(e -> {
            new AddPlanForm().show();
        });
    }
    
    
}


