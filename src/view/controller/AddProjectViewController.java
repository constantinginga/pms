package view.controller;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;

import java.time.LocalDate;

public class AddProjectViewController {
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField noteTextField;
    @FXML
    private ChoiceBox<String> projectCreatorChoiceBox = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> productOwnerChoiceBox = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> scrumMasterChoiceBox = new ChoiceBox<>();

    public AddProjectViewController() {
    }

    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        setupChoiceBoxes();
    }

    public void reset() {
        titleTextField.setText("");
        projectCreatorChoiceBox.valueProperty().setValue(null);
        productOwnerChoiceBox.valueProperty().setValue(null);
        scrumMasterChoiceBox.valueProperty().setValue(null);
        noteTextField.setText("");
        errorLabel.setText("");
    }

    public Region getRoot() {
        return root;
    }

    private void setupChoiceBoxes() {
        for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
            projectCreatorChoiceBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
            productOwnerChoiceBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
            scrumMasterChoiceBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
        }

        // prevents the selection of the same member for more
        projectCreatorChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    if (!new_val.equals(old_val)) {
                        productOwnerChoiceBox.getItems().remove(new_val);
                        scrumMasterChoiceBox.getItems().remove(new_val);
                        if (old_val != null) {
                            productOwnerChoiceBox.getItems().add(old_val);
                            scrumMasterChoiceBox.getItems().add(old_val);
                        }
                    }
                });

        productOwnerChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    if (!new_val.equals(old_val)) {
                        projectCreatorChoiceBox.getItems().remove(new_val);
                        scrumMasterChoiceBox.getItems().remove(new_val);
                        if (old_val != null) {
                            projectCreatorChoiceBox.getItems().add(old_val);
                            scrumMasterChoiceBox.getItems().add(old_val);
                        }
                    }
                });

        scrumMasterChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    if (!new_val.equals(old_val)) {
                        projectCreatorChoiceBox.getItems().remove(new_val);
                        productOwnerChoiceBox.getItems().remove(new_val);
                        if (old_val != null) {
                            projectCreatorChoiceBox.getItems().add(old_val);
                            productOwnerChoiceBox.getItems().add(old_val);
                        }
                    }
                });
    }

    @FXML
    private void handleAddButton() {
        if (titleTextField == null || titleTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter a title");
            return;
        }

        if (projectCreatorChoiceBox.getValue() == null) {
            errorLabel.setText("Please select a project creator");
            return;
        }

        if (productOwnerChoiceBox == null) {
            errorLabel.setText("Please enter a product owner");
            return;
        }
        if (scrumMasterChoiceBox == null) {
            errorLabel.setText("Please enter a scrum master");
            return;
        }
        Project newProject = new Project(titleTextField.getText(), GeneralTemplate.STATUS_NOT_STARTED);
        newProject.setId(state.getSelectedTaskID());
        if (noteTextField.getText() != null) newProject.setNote(noteTextField.getText());
        model.addProject(newProject);
        viewHandler.openView("mainWindow");
    }

    @FXML
    private void handleCancelButton() {
        viewHandler.openView("mainWindow");
    }

    public void handleAddTeamMemberButton(ActionEvent actionEvent) {
    }
}
