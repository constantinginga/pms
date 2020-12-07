package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.Project;
import view.ViewHandler;
import view.ViewState;

public class AddProjectViewController
{
  private ViewHandler viewHandler;
  private Region root;
  private ProjectManagementSystemModel model;
  private ViewState state;
  @FXML private Text IDtext;
  @FXML private TextField TitleTextField;
  @FXML private TextField HoursWorkedTextField;

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
    TitleTextField.setText("");
    HoursWorkedTextField.setText("");
    IDtext.setText(model.getProjectList().generateId(new Project(title, status)));
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML public String IDtextField(){
    return IDtext.setText(model.getProjectList().generateId());
  }

  @FXML private void handleAddButton()
  {
    viewHandler.openView("mainWindow");
  }

  @FXML private void handleCancelButton()
  {
    viewHandler.openView("mainWindow");
  }

  public void ProjectCreatorChoiceBox()
  {
    try{
      model.getTeamMemberListForProject(state.getSelectedProjectID());
    }
    catch(Exception e){
    }
  }

  public void ProductOwnerChoiceBox(ActionEvent actionEvent)
  {
    try{
      model.getTeamMemberListForProject(state.getSelectedProjectID());
    }
    catch(Exception e){
    }
  }

  public void ScrumMasterChoiceBox(ActionEvent actionEvent)
  {
    try{
      model.getTeamMemberListForProject(state.getSelectedProjectID());
    }
    catch(Exception e){
    }
  }

  public void StatusChoiceBox()
  {

  }

  public void DeadlineDatePicker()
  {

  }
}
