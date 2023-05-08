/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;
import entities.Planning;
import services.PlanningService;
import java.util.Date;

/**
 *
 * @author ilyes
 */
public class EditPlanForm extends Form {

    private Planning planning;
    private TextField nameField;
    private TextField descriptionField;
    private Picker startDatePicker;
    private Picker endDatePicker;

    public EditPlanForm(Planning planning) {
        super("Edit Planning");
        this.planning = planning;

        // Create the input fields for the planning's information
        nameField = new TextField(planning.getName(), "Name", 20, TextField.ANY);
        descriptionField = new TextField(planning.getDescription(), "Description", 100, TextField.ANY);
        startDatePicker = new Picker();
        startDatePicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        startDatePicker.setDate(planning.getStartDate());
        endDatePicker = new Picker();
        endDatePicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        endDatePicker.setDate(planning.getEndDate());

        // Add the input fields to the form
        add(nameField);
        add(descriptionField);
        add(new Label("Start Date"));
        add(startDatePicker);
        add(new Label("End Date"));
        add(endDatePicker);

        // Add a button to submit the updated planning's information
        Button updateButton = new Button("Update");
        updateButton.addActionListener(e -> {
            // When the button is clicked, validate the input fields and update the planning in the server
            if (validateInputFields()) {
                planning.setName(nameField.getText());
                planning.setDescription(descriptionField.getText());
                planning.setStartDate((Date) startDatePicker.getValue());
                planning.setEndDate((Date) endDatePicker.getValue());
                boolean updated = PlanningService.getInstance().editPlan(planning);
                Dialog.show("Success", "Planning updated successfully", "OK", null);
                new PlanningListForm().show();
                if (updated) {
                    Dialog.show("Success", "Planning updated successfully", "OK", null);
                    // Close the form and go back to the planning list form
                   // this.close();
                    new PlanningListForm().show();
                } else {
                    Dialog.show("Error", "Failed to update planning", "OK", null);
                }
            }
        });
        add(updateButton);

        // Add a button to cancel the edit and go back to the planning list form
        Button cancelButton = new Button("Cancel");
        cancelButton.addActionListener(e -> {
         //   this.close();
            new PlanningListForm().show();
        });
        add(cancelButton);
    }

    private boolean validateInputFields() {
        String name = nameField.getText();
        Date startDate = (Date) startDatePicker.getValue();
        Date endDate = (Date) endDatePicker.getValue();

        if (name.isEmpty()) {
            Dialog.show("Error", "Name field is required", "OK", null);
            return false;
        }
        
        if (startDate == null) {
            Dialog.show("Error", "Start Date field is required", "OK", null);
            return false;
        }
        
        if (endDate == null) {
            Dialog.show("Error", "End Date field is required", "OK", null);
            return false;
        }
        
       
        
        return true;
    }
}
