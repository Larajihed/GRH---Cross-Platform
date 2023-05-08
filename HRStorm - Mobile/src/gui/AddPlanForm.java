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
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;
import entities.Planning;
import services.PlanningService;
import java.util.Date;

/**
 *
 * @author ilyes
 */
public class AddPlanForm extends Form {
    private TextField nameField;
    private TextArea descriptionField;
    private Picker startDatePicker;
    private Picker endDatePicker;

    public AddPlanForm() {
        super("Add Planning");

        // Create the input fields for the planning's information
        nameField = new TextField("", "Name", 20, TextField.ANY);
        descriptionField = new TextField("","description", 5, TextField.ANY);
        startDatePicker = new Picker();
        startDatePicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        endDatePicker = new Picker();
        endDatePicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);

        // Add the input fields to the form
        add(nameField);
        add(new Label("Description"));
        add(descriptionField);
        add(new Label("Start Date"));
        add(startDatePicker);
        add(new Label("End Date"));
        add(endDatePicker);

        // Add a button to submit the new planning's information
        Button addButton = new Button("Add");
        addButton.addActionListener(e -> {
            // When the button is clicked, validate the input fields and add the new planning to the server
            if (validateInputFields()) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                Date startDate = (Date) startDatePicker.getValue();
                Date endDate = (Date) endDatePicker.getValue();

                Planning planning = new Planning();
                planning.setName(name);
                planning.setDescription(description);
                planning.setStartDate(startDate);
                planning.setEndDate(endDate);

                boolean added = PlanningService.getInstance().addPlan(planning);
                if (added) {
                    Dialog.show("Success", "Planning added successfully", "OK", null);
                    // Clear the input fields
                    clearInputFields();
                    new PlanningListForm().show();
                } else {
                    Dialog.show("Error", "Failed to add planning", "OK", null);
                }
            }
        });
        add(addButton);

        // Add a button to return to the planning list form
        Button returnButton = new Button("Return");
        returnButton.addActionListener(e -> new PlanningListForm().show());
        add(returnButton);
    }

    private boolean validateInputFields() {
        String name = nameField.getText();
        String description = descriptionField.getText();
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

   //     if (startDate.compareTo(endDate) > 0) {
    //        Dialog.show("Error", "Start Date must be before End Date", "OK", null);
     //       return false;
      //  }

        return true;
    }

    private void clearInputFields() {
        nameField.setText("");
        descriptionField.setText("");
        startDatePicker.setDate(null);
        endDatePicker.setDate(null);
    }
}
