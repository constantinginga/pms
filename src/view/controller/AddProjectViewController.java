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
import model.*;
import view.ViewHandler;
import view.ViewState;

import java.util.ArrayList;

public class AddProjectViewController {
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
    private ComboBox<String> projectCreatorComboBox;
    @FXML
    private ComboBox<String> productOwnerComboBox;
    @FXML
    private ComboBox<String> scrumMasterComboBox;
    @FXML
    private ComboBox<String> teamMemberListComboBox;
    @FXML
    private ArrayList<ComboBox<String>> comboBoxes;

    public AddProjectViewController() {
    }

    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {
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
        projectCreatorComboBox.setPromptText("Select project creator");
        productOwnerComboBox.setPromptText("Select product owner");
        scrumMasterComboBox.setPromptText("Select scrum master");
        noteTextArea.setPromptText("Enter additional information");
    }

    // store all ComboBoxes in ArrayList
    private void initComboBoxesArr() {
        comboBoxes = new ArrayList<>();
        comboBoxes.add(projectCreatorComboBox);
        comboBoxes.add(productOwnerComboBox);
        comboBoxes.add(scrumMasterComboBox);
        comboBoxes.add(teamMemberListComboBox);
    }

    public void reset() {
        titleTextField.setText("");
        noteTextArea.setText("");
        errorLabel.setText("");
        resetComboBoxes();
        addComboBoxItems();
        addComboBoxListeners();
        initPlaceholders();
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

        // create new project with title from TextField and id from State
        Project newProject = new Project(titleTextField.getText(), GeneralTemplate.STATUS_NOT_STARTED);
        newProject.setId(state.getSelectedProjectID());

        // if note is different from previous value and is not empty
        if (noteTextArea.getText() != null && !noteTextArea.getText().equals(""))
            newProject.setNote(noteTextArea.getText());

        // add team members for current project
        for (TeamMember t : addedTeamMembers) {
            newProject.addAlreadyExistsTeamMember(t);
        }

        // set roles for project
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
