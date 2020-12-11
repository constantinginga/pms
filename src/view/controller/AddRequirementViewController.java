package view.controller;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;


public class AddRequirementViewController {
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;

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
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private ComboBox<String> teamMemberComboBox;
    @FXML
    private Button addTamMembersButtton;
    private ArrayList<TeamMember> teamMembers;

    public AddRequirementViewController() {
    }

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        this.teamMembers = new ArrayList<>();
        errorLabel.setText("");

        addComboBox();
        ComboBoxListener();
        resPersonComboBox.setPromptText("Add Scrum master");
        teamMemberComboBox.setPromptText("Add Team member");


    }

    @FXML
    public void addTamMembersButtton() {
        if (teamMemberComboBox.getSelectionModel().getSelectedItem() != null) {
            TeamMember member = formatTeamMember(teamMemberComboBox.getSelectionModel().getSelectedItem());
            teamMembers.add(member);
            teamMemberComboBox.getSelectionModel().clearSelection();
            teamMemberComboBox.getItems().remove(member.toString());
            resPersonComboBox.getItems().remove(member.toString());
        }
    }

    public void reset() {
        resPersonComboBox.setPromptText("Add Scrum master");
        teamMemberComboBox.setPromptText("Add Team member");
        errorLabel.setText("");
        userStoryTextField.setText("");
        estimatedTimeTextField.setText("");
        deadLineDatePicker.setValue(null);
        resPersonComboBox.getItems().clear();
        resPersonComboBox.getSelectionModel().clearSelection();
        teamMemberComboBox.getItems().clear();
        teamMemberComboBox.getSelectionModel().clearSelection();
        addComboBox();
        ComboBoxListener();
    }


    private void addComboBox() {
        for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
            resPersonComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
            teamMemberComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
        }
    }

    private void ComboBoxListener() {
        resPersonComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldV, newValue) -> {
            if (newValue != null && !newValue.equals(oldV)) {
                teamMemberComboBox.getItems().remove(newValue);
            }
        });
    }

    public Region getRoot() {
        return root;
    }


    @FXML
    private void keyTyped() {
        if (userStoryTextField == null) {
            errorLabel.setText("Please Enter User Story");
        }
    }

    @FXML
    private void onEnter(Event event) {
        if (event.getSource() == userStoryTextField) {
            if (userStoryTextField.getText().equals("")) {
                errorLabel.setText("User story Can't be empty");
                userStoryTextField.requestFocus();
            } else {
                resPersonComboBox.requestFocus();
                errorLabel.setText("");
            }
        } else if (event.getSource() == estimatedTimeTextField) {
            if (estimatedTimeTextField.getText().equals("")) {
                estimatedTimeTextField.requestFocus();
            }
            try {
                Integer.parseInt(estimatedTimeTextField.getText());
                errorLabel.setText("");
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
                estimatedTimeTextField.requestFocus();
            }

        } else if (event.getSource() == estimatedTimeTextField) {
            deadLineDatePicker.requestFocus();
        }
    }

    @FXML
    private void handleAddButton() {
        try {
            LocalDate datePickerValue = deadLineDatePicker.getValue();
            int estimatedTime = Integer.parseInt(estimatedTimeTextField.getText());
            Requirement requirement = new Requirement(userStoryTextField.getText(), formatTeamMember(resPersonComboBox.getSelectionModel().getSelectedItem()), GeneralTemplate.STATUS_NOT_STARTED, estimatedTime, new MyDate(datePickerValue.getDayOfMonth(), datePickerValue.getMonthValue(), datePickerValue.getYear())
            );
            for (TeamMember t : teamMembers) {
                requirement.addAlreadyExistsTeamMember(t);
            }
            model.addRequirement(requirement, state.getSelectedProjectID());
            viewHandler.openView("reqList");

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
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
