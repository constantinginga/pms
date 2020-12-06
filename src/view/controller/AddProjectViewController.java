package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;

public class AddProjectViewController
{
  private ViewHandler viewHandler;
  private Region root;
  private ProjectManagementSystemModel model;
  private ViewState state;
  @FXML private TextField IDtextField;
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

  //Should be option to get method generateID from model to
  // check last ID and add +1 to it to create new one
  public void reset()
  {
    TitleTextField.setText("");
    HoursWorkedTextField.setText("");
    IDtextField.setText(model.generateID());
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML public String IDtextField(){
    model.generateID();
  }

  @FXML private void handleAddButton()
  {
    viewHandler.openView("mainWindow");
  }

  @FXML private void handleCancelButton()
  {
    viewHandler.openView("mainWindow");
  }

}
