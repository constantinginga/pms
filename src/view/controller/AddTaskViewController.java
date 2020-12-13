package view.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import mediator.ProjectManagementSystemModel;
import model.MyDate;
import model.Task;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddTaskViewController {
    private ArrayList<TeamMember> addedTeamMembers;
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;
    private ArrayList<ComboBox<String>> comboBoxes;
    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<String> responsiblePersonComboBox;
    @FXML
    private ComboBox<String> teamMemberListComboBox;
    @FXML
    private TextField estimatedTimeTextField;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private Label errorLabel;

    public AddTaskViewController() {
    }

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        this.addedTeamMembers = new ArrayList<>();
        initComboBoxesArr();
        initPlaceholders();
        addComboBoxListeners();
        addComboBoxItems();
    }

    // set placeholders
    private void initPlaceholders() {
        titleTextField.setPromptText("Enter title");
        responsiblePersonComboBox.setPromptText("Select responsible person");
        estimatedTimeTextField.setPromptText("Number of hours");
        deadlineDatePicker.setPromptText("Select date");
    }

    // store all ComboBoxes in ArrayList
    public void initComboBoxesArr() {
        comboBoxes = new ArrayList<>();
        comboBoxes.add(teamMemberListComboBox);
        comboBoxes.add(responsiblePersonComboBox);
    }

    public void reset() {
        titleTextField.setText("");
        estimatedTimeTextField.setText("");
        errorLabel.setText("");
        deadlineDatePicker.setValue(null);
        initPlaceholders();
        resetComboBoxes();
        addComboBoxItems();
        addComboBoxListeners();
    }

    public Region getRoot() {
        return root;
    }

    // fill all ComboBoxes with team members from model
    private void addComboBoxItems() {
        for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
            for (ComboBox<String> c : comboBoxes) {
                c.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
            }
        }
    }

    // prevents the selection of the same member for more than one ComboBox
    private ChangeListener<String> createListener(String comboBoxType) {

        // make copy of comboBoxes ArrayList
        ArrayList<ComboBox<String>> temp = new ArrayList<>(comboBoxes);

        // remove the ComboBox for which to create listener from list
        temp.removeIf(c -> c.getId().equals(comboBoxType));


        // return listener
        return (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            for (ComboBox<String> c : temp) {
                if (new_val != null && !new_val.equals(old_val)) {
                    // remove selected value from all other ComboBoxes
                    c.getItems().remove(new_val);
                    if (old_val != null) c.getItems().add(old_val);
                }
            }
        };
    }

    // add listeners to ComboBoxes
    private void addComboBoxListeners() {
        for (ComboBox<String> c : comboBoxes) {
            c.getSelectionModel().selectedItemProperty().addListener(createListener(c.getId()));
        }
    }

    // remove all ComboBox items
    private void resetComboBoxes() {
        for (ComboBox<String> c : comboBoxes) {
            c.getItems().clear();
        }
    }

    @FXML
    private void handleAddButton() {

        int estimatedTime = -1;

        if (titleTextField == null || titleTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter a title");
            return;
        }

        if (responsiblePersonComboBox.getValue() == null) {
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

        if (deadlineDatePicker.getValue() == null) {
            errorLabel.setText("Please select a date");
            return;
        }

        // convert deadlineDatePicker value to MyDate object
        LocalDate deadlineLocalDate = deadlineDatePicker.getValue();
        MyDate deadline = new MyDate();
        try {
            deadline = new MyDate(deadlineLocalDate.getDayOfMonth(), deadlineLocalDate.getMonthValue(), deadlineLocalDate.getYear());
        } catch (Exception e) {
            errorLabel.setText("Invalid date");
        }

        // prevent past deadline
        if (deadline.isBefore(new MyDate())) {
            errorLabel.setText("Deadline must be after today");
            return;
        }

        TeamMember responsiblePerson = formatTeamMember(responsiblePersonComboBox.getValue());
        try {
            Task task = new Task(titleTextField.getText(), responsiblePerson, estimatedTime, deadline);
            task.setId(state.getSelectedTaskID());

            // add team members for current task
            for (TeamMember t : addedTeamMembers) {
                task.addAlreadyExistsTeamMember(t);
            }

            // add task to model
            model.addTask(task, state.getSelectedProjectID(), state.getSelectedRequirementID());
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
        viewHandler.openView("taskList");
    }

    @FXML
    private void handleCancelButton() {
        viewHandler.openView("taskList");
    }

    @FXML
    public void handleAddTeamMemberButton() {
        if (teamMemberListComboBox.getSelectionModel().getSelectedItem() == null) return;

        TeamMember member = formatTeamMember(teamMemberListComboBox.getSelectionModel().getSelectedItem());

        // add member to model and remove from all ComboBoxes
        addedTeamMembers.add(member);
        for (ComboBox<String> c : comboBoxes) {
            if (c.getId().equals("teamMemberListComboBox")) c.getSelectionModel().clearSelection();
            c.getItems().remove(member.toString());
        }

        // inform user that team member was added
        errorLabel.setTextFill(Paint.valueOf("#218838"));
        errorLabel.setText(String.format("%s successfully added", member.toString()));

        // reset the label after 2s
        new Timeline(new KeyFrame(Duration.millis(2000), e -> {
            errorLabel.setText("");
            errorLabel.setTextFill(Paint.valueOf("#e81111"));
        })).play();
    }

    // return new team member from string
    private TeamMember formatTeamMember(String teamMemberString) {
        // format string
        teamMemberString = teamMemberString.replace("[", "");
        teamMemberString = teamMemberString.replace("]", "");
        String[] memberInfo = teamMemberString.split("\s", 2);
        TeamMember member = new TeamMember(memberInfo[1]);
        member.setId(memberInfo[0]);
        return member;
    }
}
