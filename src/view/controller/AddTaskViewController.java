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
    private ChangeListener<String> teamMemberListComboBoxListener;
    private ChangeListener<String> responsiblePersonComboBoxListener;
    private ArrayList<TeamMember> addedTeamMembers;
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;
    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<String> responsiblePersonComboBox = new ComboBox<>();
    @FXML
    private ComboBox<String> teamMemberListComboBox = new ComboBox<>();
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
        addComboBoxListeners();
        addComboBoxItems();
    }

    public void reset() {
        titleTextField.setText("");
        estimatedTimeTextField.setText("");
        errorLabel.setText("");
        deadlineDatePicker.setValue(null);
        resetComboBoxes();
        addComboBoxItems();
        addComboBoxListeners();
    }

    public Region getRoot() {
        return root;
    }

    public void addComboBoxItems() {
        for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
            responsiblePersonComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
            teamMemberListComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
        }
    }

    public void addComboBoxListeners() {

        responsiblePersonComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                teamMemberListComboBox.getItems().remove(new_val);
                if (old_val != null) teamMemberListComboBox.getItems().add(old_val);
            }
        };

        teamMemberListComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                responsiblePersonComboBox.getItems().remove(new_val);
                if (old_val != null) responsiblePersonComboBox.getItems().add(old_val);
            }
        };

        responsiblePersonComboBox.getSelectionModel().selectedItemProperty().addListener(responsiblePersonComboBoxListener);
        teamMemberListComboBox.getSelectionModel().selectedItemProperty().addListener(teamMemberListComboBoxListener);
    }

    public void resetComboBoxes() {

        if (responsiblePersonComboBoxListener != null && teamMemberListComboBoxListener != null) {
            responsiblePersonComboBox.getSelectionModel().selectedItemProperty().removeListener(responsiblePersonComboBoxListener);
            teamMemberListComboBox.getSelectionModel().selectedItemProperty().removeListener(teamMemberListComboBoxListener);
        }

        responsiblePersonComboBox.getItems().clear();
        teamMemberListComboBox.getItems().clear();
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

        LocalDate deadlineLocalDate = deadlineDatePicker.getValue();
        MyDate deadline = new MyDate();
        try {
            deadline = new MyDate(deadlineLocalDate.getDayOfMonth(), deadlineLocalDate.getMonthValue(), deadlineLocalDate.getYear());
        } catch (Exception e) {
            errorLabel.setText("Invalid date");
        }

        if (deadline.isBefore(new MyDate())) {
            errorLabel.setText("Deadline must be after today");
            return;
        }

        TeamMember responsiblePerson = formatTeamMember(responsiblePersonComboBox.getValue());
        try {
            Task task = new Task(titleTextField.getText(), responsiblePerson, estimatedTime, deadline);
            task.setId(state.getSelectedTaskID());

            // add team members for current project
            for (TeamMember t : addedTeamMembers) {
                task.addTeamMember(t);
            }

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
        teamMemberListComboBox.getSelectionModel().clearSelection();
        teamMemberListComboBox.getItems().remove(member.toString());
        responsiblePersonComboBox.getItems().remove(member.toString());

        // inform user that team member was added
        errorLabel.setTextFill(Paint.valueOf("#19fc3f"));
        errorLabel.setText("Team member successfully added");

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
        String[] memberInfo = teamMemberString.split("\s");
        TeamMember member = new TeamMember(memberInfo[1]);
        member.setId(memberInfo[0]);
        return member;
    }
}
