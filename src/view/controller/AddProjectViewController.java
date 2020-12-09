package view.controller;

import javafx.beans.value.ChangeListener;
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
    private ChangeListener<String> projectCreatorChoiceBoxListener;
    private ChangeListener<String> productOwnerChoiceBoxListener;
    private ChangeListener<String> scrumMasterChoiceBoxListener;
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

        addChoiceBoxListeners();
        addChoiceBoxItems();
    }

    public void reset() {
        titleTextField.setText("");
        noteTextField.setText("");
        errorLabel.setText("");
        removeChoiceBoxListeners();
        projectCreatorChoiceBox.getItems().clear();
        projectCreatorChoiceBox.getSelectionModel().clearSelection();
        productOwnerChoiceBox.getItems().clear();
        productOwnerChoiceBox.getSelectionModel().clearSelection();
        scrumMasterChoiceBox.getItems().clear();
        scrumMasterChoiceBox.getSelectionModel().clearSelection();
        addChoiceBoxItems();
        addChoiceBoxListeners();
    }

    public Region getRoot() {
        return root;
    }

    private void addChoiceBoxItems() {
        for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
            projectCreatorChoiceBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
            productOwnerChoiceBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
            scrumMasterChoiceBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
        }
    }

    private void addChoiceBoxListeners() {

        // prevents the selection of the same member for more than one ChoiceBox
        projectCreatorChoiceBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                productOwnerChoiceBox.getItems().remove(new_val);
                scrumMasterChoiceBox.getItems().remove(new_val);
                if (old_val != null) {
                    productOwnerChoiceBox.getItems().add(old_val);
                    scrumMasterChoiceBox.getItems().add(old_val);
                }
            }
        };

        productOwnerChoiceBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                projectCreatorChoiceBox.getItems().remove(new_val);
                scrumMasterChoiceBox.getItems().remove(new_val);
                if (old_val != null) {
                    projectCreatorChoiceBox.getItems().add(old_val);
                    scrumMasterChoiceBox.getItems().add(old_val);
                }
            }
        };

        scrumMasterChoiceBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                projectCreatorChoiceBox.getItems().remove(new_val);
                productOwnerChoiceBox.getItems().remove(new_val);
                if (old_val != null) {
                    projectCreatorChoiceBox.getItems().add(old_val);
                    productOwnerChoiceBox.getItems().add(old_val);
                }
            }
        };

        projectCreatorChoiceBox.getSelectionModel().selectedItemProperty().addListener(projectCreatorChoiceBoxListener);
        productOwnerChoiceBox.getSelectionModel().selectedItemProperty().addListener(productOwnerChoiceBoxListener);
        scrumMasterChoiceBox.getSelectionModel().selectedItemProperty().addListener(scrumMasterChoiceBoxListener);
    }

    private void removeChoiceBoxListeners() {
        if (projectCreatorChoiceBoxListener != null && productOwnerChoiceBoxListener != null && scrumMasterChoiceBoxListener != null) {
            projectCreatorChoiceBox.getSelectionModel().selectedItemProperty().removeListener(projectCreatorChoiceBoxListener);
            productOwnerChoiceBox.getSelectionModel().selectedItemProperty().removeListener(productOwnerChoiceBoxListener);
            scrumMasterChoiceBox.getSelectionModel().selectedItemProperty().removeListener(scrumMasterChoiceBoxListener);
        }
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
        if (noteTextField.getText() != null && !noteTextField.getText().equals("")) newProject.setNote(noteTextField.getText());
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
