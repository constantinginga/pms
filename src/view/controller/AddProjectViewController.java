package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.MyDate;
import model.Project;
import model.Task;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;

import java.time.LocalDate;

public class AddProjectViewController
{
  private ViewHandler viewHandler;
  private Region root;
  private ProjectManagementSystemModel model;
  private ViewState state;
  private Label errorLabel;
  @FXML private TextField titleTextField;
  @FXML private ChoiceBox teamMembersChoiceBox;
  @FXML private ChoiceBox projectCreatorChoiceBox;
  @FXML private ChoiceBox productOwnerChoiceBox;
  @FXML private ChoiceBox scrumMasterChoiceBox;
  @FXML private ChoiceBox statusChoiceBox;

  public AddProjectViewController()
  {
  }

  public void init(ViewHandler viewHandler, Region root,
                   ProjectManagementSystemModel model, ViewState state)
  {
    this.viewHandler = viewHandler;
    this.root = root;
    this.model = model;
    this.state = state;

  }

  public void reset()
  {
    titleTextField.setText("");
    teamMembersChoiceBox.valueProperty().setValue(null);
    projectCreatorChoiceBox.valueProperty().setValue(null);
    productOwnerChoiceBox.valueProperty().setValue(null);
    scrumMasterChoiceBox.valueProperty().setValue(null);
    statusChoiceBox.valueProperty().setValue(null);
    errorLabel.setText("");
  }
  public Region getRoot()
  {
    return root;
  }

  @FXML private void handleAddButton()
  {
    int estimatedTime = -1;

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
    if (statusChoiceBox == null) {
      errorLabel.setText("Please enter a status");
      return;
    }
    Project newProject = new Project(titleTextField.getText(), statusChoiceBox.getAccessibleText());
    newProject.setId(state.getSelectedTaskID());
    model.addProject(newProject);
    viewHandler.openView("mainWindow");
  }

  @FXML private void handleCancelButton()
  {
    viewHandler.openView("mainWindow");
  }
}