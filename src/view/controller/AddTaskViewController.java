package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.MyDate;
import model.Task;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;

import java.time.LocalDate;

public class AddTaskViewController {
    @FXML
    private TextField titleTextField;
    @FXML
    private ChoiceBox responsiblePersonChoiceBox;
    @FXML
    private ChoiceBox teamMembersChoiceBox;
    @FXML
    private TextField estimatedTimeTextField;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private Label errorLabel;
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;

    public AddTaskViewController() {
    }

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;

        // this is just for testing
        responsiblePersonChoiceBox.getItems().add("Bob");
        responsiblePersonChoiceBox.getItems().add("Steve");
        responsiblePersonChoiceBox.getItems().add("John");
    }

    public void reset() {
        titleTextField.setText("");
        estimatedTimeTextField.setText("");
        errorLabel.setText("");
        deadlineDatePicker.setValue(null);
        responsiblePersonChoiceBox.valueProperty().set(null);
        teamMembersChoiceBox.valueProperty().set(null);
    }

    public Region getRoot() {
        return root;
    }


    @FXML
    private void handleAddButton() {

        int estimatedTime = -1;

        if (titleTextField == null || titleTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter a title");
            return;
        }

        if (responsiblePersonChoiceBox.getValue() == null) {
            errorLabel.setText("Please select a responsible person");
            return;
        }

        if (estimatedTimeTextField == null || estimatedTimeTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter an estimated time");
            return;
        } else {
            try {
                estimatedTime = Integer.parseInt(estimatedTimeTextField.getText());
            } catch (Exception e) {
                errorLabel.setText("Estimated time must be an integer");
            }
        }
        LocalDate deadlineLocalDate = deadlineDatePicker.getValue();
        MyDate deadline = new MyDate();
        try {
            deadline = new MyDate(deadlineLocalDate.getDayOfMonth(), deadlineLocalDate.getMonthValue(), deadlineLocalDate.getYear());
        } catch (Exception e) {
            errorLabel.setText("Please select a date");
        }

        if (deadline.isBefore(new MyDate())) {
            errorLabel.setText("Deadline must be after today");
            return;
        }
        Task task = new Task(titleTextField.getText(), (TeamMember)responsiblePersonChoiceBox.getValue(), estimatedTime, deadline);
        task.setId(state.getSelectedTaskID());
        model.addTask(task, state.getSelectedProjectID(), state.getSelectedRequirementID());
    }

    @FXML
    private void handleCancelButton() {
        viewHandler.openView("taskList");
    }

}
