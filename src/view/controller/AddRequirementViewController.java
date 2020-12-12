package view.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;

import java.time.LocalDate;
import java.util.ArrayList;


public class AddRequirementViewController {
    @FXML
    private TextField userStoryTextField;
    @FXML
    private TextField estimatedTimeTextField;
    @FXML
    private DatePicker deadLineDatePicker;
    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox<String> resPersonComboBox;
    @FXML
    private ComboBox<String> teamMemberComboBox;
    private ProjectManagementSystemModel model;
    private ViewHandler viewHandler;
    private Region root;
    private ViewState state;
    private ArrayList<TeamMember> teamMembers;
    private ArrayList<ComboBox<String>> comboBoxes;

    public AddRequirementViewController() {
    }

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        this.teamMembers = new ArrayList<>();
        initComboBoxesArr();
        initPlaceholders();
        addComboBox();
        comboBoxListener();
    }

    // store all ComboBoxes in ArrayList
    public void initComboBoxesArr() {
        comboBoxes = new ArrayList<>();
        comboBoxes.add(resPersonComboBox);
        comboBoxes.add(teamMemberComboBox);
    }

    private void initPlaceholders() {
        resPersonComboBox.setPromptText("Select responsible person");
        teamMemberComboBox.setPromptText("Select member");
        estimatedTimeTextField.setPromptText("Number of hours");
        deadLineDatePicker.setPromptText("Select date");
    }

    @FXML
    public void addTeamMembersButton() {
        if (teamMemberComboBox.getSelectionModel().getSelectedItem() != null) {

            TeamMember member = formatTeamMember(teamMemberComboBox.getSelectionModel().getSelectedItem());
            teamMembers.add(member);

            for (ComboBox<String> c : comboBoxes) {
                if (c.getId().equals("teamMemberComboBox")) c.getSelectionModel().clearSelection();
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
    }

    public void reset() {
        errorLabel.setText("");
        userStoryTextField.setText("");
        estimatedTimeTextField.setText("");
        deadLineDatePicker.setValue(null);
        initPlaceholders();
        resetComboBoxes();
        addComboBox();
        comboBoxListener();
    }

    // remove all ComboBox items
    private void resetComboBoxes() {
        for (ComboBox<String> c : comboBoxes) {
            c.getItems().clear();
        }
    }


    // fill all ComboBoxes with team members from model
    private void addComboBox() {
        // get TeamMemberList of current Project
        TeamMemberList list = model.getTeamMemberListForProject(state.getSelectedProjectID());

        for (int i = 0; i < list.getSize(); i++) {
            for (ComboBox<String> c : comboBoxes) {
                c.getItems().add(list.getTeamMember(i).toString());
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
    private void comboBoxListener() {
        for (ComboBox<String> c : comboBoxes) {
            c.getSelectionModel().selectedItemProperty().addListener(createListener(c.getId()));
        }
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void handleAddButton() {

        int estimatedTime = -1;

        if (userStoryTextField == null || userStoryTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter user story");
            return;
        }

        if (resPersonComboBox.getValue() == null) {
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

        if (deadLineDatePicker.getValue() == null) {
            errorLabel.setText("Please select a date");
            return;
        }

        // convert deadlineDatePicker value to MyDate object
        LocalDate deadlineLocalDate = deadLineDatePicker.getValue();
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

        try {
            Requirement requirement = new Requirement(userStoryTextField.getText(), formatTeamMember(resPersonComboBox.getSelectionModel().getSelectedItem()), GeneralTemplate.STATUS_NOT_STARTED, estimatedTime, deadline);

            // add team members for current requirement
            for (TeamMember t : teamMembers) {
                requirement.addAlreadyExistsTeamMember(t);
            }

            // add requirement to model
            model.addRequirement(requirement, state.getSelectedProjectID());

        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
            viewHandler.openView("reqList");
    }

    @FXML
    private void handleCancelButton() {
        viewHandler.openView("reqList");
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
