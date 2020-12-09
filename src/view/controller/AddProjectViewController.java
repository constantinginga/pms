package view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;

public class AddProjectViewController {
    private ChangeListener<String> projectCreatorComboBoxListener;
    private ChangeListener<String> productOwnerComboBoxListener;
    private ChangeListener<String> scrumMasterComboBoxListener;
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

    public AddProjectViewController() {
    }

    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;

        addComboBoxListeners();
        addComboBoxItems();
    }

    public void reset() {
        titleTextField.setText("");
        noteTextArea.setText("");
        errorLabel.setText("");
        removeChoiceBoxListeners();
        projectCreatorComboBox.getItems().clear();
        projectCreatorComboBox.getSelectionModel().clearSelection();
        productOwnerComboBox.getItems().clear();
        productOwnerComboBox.getSelectionModel().clearSelection();
        scrumMasterComboBox.getItems().clear();
        scrumMasterComboBox.getSelectionModel().clearSelection();
        addComboBoxItems();
        addComboBoxListeners();
    }

    public Region getRoot() {
        return root;
    }

    private void addComboBoxItems() {
        for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
            projectCreatorComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
            productOwnerComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
            scrumMasterComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
        }
    }

    private void addComboBoxListeners() {

        // prevents the selection of the same member for more than one ComboBox
        projectCreatorComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                productOwnerComboBox.getItems().remove(new_val);
                scrumMasterComboBox.getItems().remove(new_val);
                if (old_val != null) {
                    productOwnerComboBox.getItems().add(old_val);
                    scrumMasterComboBox.getItems().add(old_val);
                }
            }
        };

        productOwnerComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                projectCreatorComboBox.getItems().remove(new_val);
                scrumMasterComboBox.getItems().remove(new_val);
                if (old_val != null) {
                    projectCreatorComboBox.getItems().add(old_val);
                    scrumMasterComboBox.getItems().add(old_val);
                }
            }
        };

        scrumMasterComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                projectCreatorComboBox.getItems().remove(new_val);
                productOwnerComboBox.getItems().remove(new_val);
                if (old_val != null) {
                    projectCreatorComboBox.getItems().add(old_val);
                    productOwnerComboBox.getItems().add(old_val);
                }
            }
        };

        projectCreatorComboBox.getSelectionModel().selectedItemProperty().addListener(projectCreatorComboBoxListener);
        productOwnerComboBox.getSelectionModel().selectedItemProperty().addListener(productOwnerComboBoxListener);
        scrumMasterComboBox.getSelectionModel().selectedItemProperty().addListener(scrumMasterComboBoxListener);
    }

    private void removeChoiceBoxListeners() {
        if (projectCreatorComboBoxListener != null && productOwnerComboBoxListener != null && scrumMasterComboBoxListener != null) {
            projectCreatorComboBox.getSelectionModel().selectedItemProperty().removeListener(projectCreatorComboBoxListener);
            productOwnerComboBox.getSelectionModel().selectedItemProperty().removeListener(productOwnerComboBoxListener);
            scrumMasterComboBox.getSelectionModel().selectedItemProperty().removeListener(scrumMasterComboBoxListener);
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
        Project newProject = new Project(titleTextField.getText(), GeneralTemplate.STATUS_NOT_STARTED);
        newProject.setId(state.getSelectedTaskID());
        if (noteTextArea.getText() != null && !noteTextArea.getText().equals("")) newProject.setNote(noteTextArea.getText());
        model.addProject(newProject);
        viewHandler.openView("mainWindow");
    }

    @FXML
    private void handleCancelButton() {
        viewHandler.openView("mainWindow");
    }

    public void handleAddTeamMemberButton() {

    }

}
