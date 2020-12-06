package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.Project;
import view.ViewHandler;
import view.ViewState;

public class MainWindowViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private ProjectManagementSystemModel model;
  private ViewState state;
  @FXML private Label ErrorLabel;
  @FXML private TextField SearchBar;

  public MainWindowViewController()
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

  public Region getRoot()
  {
    return root;
  }

  public void reset()
  {
    ErrorLabel.setText("");
  }

  @FXML public void handleOpenProjectButton()
  {
    try{
      viewHandler.openView("reqList");
    }
    catch(Exception e){
      ErrorLabel.setText("Choose project to open");
    }
  }

  @FXML public void handleAddProjectButton()
  {
        viewHandler.openView("addProject");
  }

  @FXML public void handleEmployeeButton()
  {
    viewHandler.openView("teamView");
  }

  @FXML public void handleRemoveProjectButton(Project project)
  {
    try
    {
      model.removeProject(project);
    }
    catch (Exception e)
    {
      ErrorLabel.setText("Choose a project to remove");
    }
  }
  @FXML public Project Searchbar(String ID){
      try{
        model.findById(ID);
      }
      catch(Exception e){
        ErrorLabel.setText("Project wasn't found");
      }
      return model.getProject(ID);
  }
  @FXML public Project Searchbar(String title){
    try{
      model.findByTitle(title);
    }
    catch(Exception e){
      ErrorLabel.setText("Project wasn't found");
    }
    return model.getProject(title);
  }

}
