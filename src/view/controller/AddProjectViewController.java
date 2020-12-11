package view.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;

import java.util.ArrayList;

public class AddProjectViewController {
    private ChangeListener<String> projectCreatorComboBoxListener;
    private ChangeListener<String> productOwnerComboBoxListener;
    private ChangeListener<String> scrumMasterComboBoxListener;
    private ChangeListener<String> teamMemberListComboBoxListener;
    private ArrayList<TeamMember> addedTeamMembers;
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private ComboBox<String> projectCreatorComboBox = new ComboBox<>();
    @FXML
    private ComboBox<String> productOwnerComboBox = new ComboBox<>();
    @FXML
    private ComboBox<String> scrumMasterComboBox = new ComboBox<>();
    @FXML
    private ComboBox<String> teamMemberListComboBox = new ComboBox<>();
    @FXML
    AnchorPane layout;

    public AddProjectViewController() {
    }

    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {
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
        noteTextArea.setText("");
        errorLabel.setText("");
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
            projectCreatorComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
            productOwnerComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
            scrumMasterComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
            teamMemberListComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
        }
    }

    // prevents the selection of the same member for more than one ComboBox
    private void addComboBoxListeners() {

        projectCreatorComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                productOwnerComboBox.getItems().remove(new_val);
                scrumMasterComboBox.getItems().remove(new_val);
                teamMemberListComboBox.getItems().remove(new_val);
                if (old_val != null) {
                    productOwnerComboBox.getItems().add(old_val);
                    scrumMasterComboBox.getItems().add(old_val);
                    teamMemberListComboBox.getItems().add(old_val);
                }
            }
        };

        productOwnerComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                projectCreatorComboBox.getItems().remove(new_val);
                scrumMasterComboBox.getItems().remove(new_val);
                teamMemberListComboBox.getItems().remove(new_val);
                if (old_val != null) {
                    projectCreatorComboBox.getItems().add(old_val);
                    scrumMasterComboBox.getItems().add(old_val);
                    teamMemberListComboBox.getItems().add(old_val);
                }
            }
        };

        scrumMasterComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                projectCreatorComboBox.getItems().remove(new_val);
                productOwnerComboBox.getItems().remove(new_val);
                teamMemberListComboBox.getItems().remove(new_val);
                if (old_val != null) {
                    projectCreatorComboBox.getItems().add(old_val);
                    productOwnerComboBox.getItems().add(old_val);
                    teamMemberListComboBox.getItems().add(old_val);
                }
            }
        };

        teamMemberListComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                projectCreatorComboBox.getItems().remove(new_val);
                productOwnerComboBox.getItems().remove(new_val);
                scrumMasterComboBox.getItems().remove(new_val);
                if (old_val != null) {
                    projectCreatorComboBox.getItems().add(old_val);
                    productOwnerComboBox.getItems().add(old_val);
                    scrumMasterComboBox.getItems().add(old_val);
                }
            }
        };

        // add listeners to ComboBoxes
        projectCreatorComboBox.getSelectionModel().selectedItemProperty().addListener(projectCreatorComboBoxListener);
        productOwnerComboBox.getSelectionModel().selectedItemProperty().addListener(productOwnerComboBoxListener);
        scrumMasterComboBox.getSelectionModel().selectedItemProperty().addListener(scrumMasterComboBoxListener);
        teamMemberListComboBox.getSelectionModel().selectedItemProperty().addListener(teamMemberListComboBoxListener);
    }

    private void resetComboBoxes() {
        // remove ComboBox listeners
        if (projectCreatorComboBoxListener != null &&
                productOwnerComboBoxListener != null &&
                scrumMasterComboBoxListener != null &&
                teamMemberListComboBoxListener != null) {
            projectCreatorComboBox.getSelectionModel().selectedItemProperty().removeListener(projectCreatorComboBoxListener);
            productOwnerComboBox.getSelectionModel().selectedItemProperty().removeListener(productOwnerComboBoxListener);
            scrumMasterComboBox.getSelectionModel().selectedItemProperty().removeListener(scrumMasterComboBoxListener);
            teamMemberListComboBox.getSelectionModel().selectedItemProperty().removeListener(teamMemberListComboBoxListener);
        }

        // remove all ComboBox items
        projectCreatorComboBox.getItems().clear();
        productOwnerComboBox.getItems().clear();
        scrumMasterComboBox.getItems().clear();
        teamMemberListComboBox.getItems().clear();
    }

    @FXML
    private void handleAddButton() {
        if (titleTextField == null || titleTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter a title");
            return;
        }

        if (projectCreatorComboBox.getValue() == null) {
            errorLabel.setText("Please select a project creator");
            return;
        }

        if (productOwnerComboBox == null) {
            errorLabel.setText("Please enter a product owner");
            return;
        }
        if (scrumMasterComboBox == null) {
            errorLabel.setText("Please enter a scrum master");
            return;
        }

        // create new project with values from TextFields
        Project newProject = new Project(titleTextField.getText(), GeneralTemplate.STATUS_NOT_STARTED);
        newProject.setId(state.getSelectedProjectID());

        // if note is different from previous value and is not empty
        if (noteTextArea.getText() != null && !noteTextArea.getText().equals(""))
            newProject.setNote(noteTextArea.getText());

        // add team members for current project
        for (TeamMember t : addedTeamMembers) {
            newProject.addAlreadyExistsTeamMember(t);
        }
         newProject.setProjectCreator(model.getTeamMemberList().findById(formatTeamMember(projectCreatorComboBox.getSelectionModel().getSelectedItem()).getId()));
        newProject.setScrumMaster(model.getTeamMemberList().findById(formatTeamMember(scrumMasterComboBox.getSelectionModel().getSelectedItem()).getId()));
        newProject.setProductOwner(model.getTeamMemberList().findById(formatTeamMember(productOwnerComboBox.getSelectionModel().getSelectedItem()).getId()));
        model.addProject(newProject);
        viewHandler.openView("mainWindow");
    }

    @FXML
    private void handleCancelButton() {
        viewHandler.openView("mainWindow");
    }

    @FXML
    public void handleAddTeamMemberButton() {
        if (teamMemberListComboBox.getSelectionModel().getSelectedItem() == null) return;

        TeamMember member = formatTeamMember(teamMemberListComboBox.getSelectionModel().getSelectedItem());

        // add member to model and remove from all ComboBoxes
        addedTeamMembers.add(member);
        teamMemberListComboBox.getSelectionModel().clearSelection();
        teamMemberListComboBox.getItems().remove(member.toString());
        projectCreatorComboBox.getItems().remove(member.toString());
        productOwnerComboBox.getItems().remove(member.toString());
        scrumMasterComboBox.getItems().remove(member.toString());

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
