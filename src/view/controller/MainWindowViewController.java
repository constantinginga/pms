package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.Project;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.ProjectListViewModel;
import view.viewModel.ProjectViewModel;

import java.util.Optional;

public class MainWindowViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private ProjectManagementSystemModel model;
  private ViewState state;
  private ProjectListViewModel viewModel;
  @FXML private TableView<ProjectViewModel> projectListTable;
  @FXML private TableColumn<ProjectViewModel, String> idColumn;
  @FXML private TableColumn<ProjectViewModel, String> titleColumn;
  @FXML private TableColumn<ProjectViewModel, String> statusColumn;
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
    this.viewModel = new ProjectListViewModel(model,
        state.getSelectedProjectID());

    idColumn
        .setCellValueFactory(cellData -> cellData.getValue().idPropertyProperty());
    titleColumn.setCellValueFactory(
        cellData -> cellData.getValue().titlePropertyProperty());
    statusColumn.setCellValueFactory(
        cellData -> cellData.getValue().statusPropertyProperty());

    projectListTable.setItems(viewModel.getList());
  }

  public Region getRoot()
  {
    return root;
  }

  public void reset()
  {
    ErrorLabel.setText("");
    viewModel.update();
  }

  @FXML public void handleOpenProjectButton()
  {
    try
    {
      state.setSelectedProjectID(projectListTable.getSelectionModel().getSelectedItem().getIdProperty());
      viewHandler.openView("reqList");
    }
    catch (Exception e)
    {
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
    ErrorLabel.setText("");
    try
    {
      ProjectViewModel selectedItem = projectListTable.getSelectionModel()
          .getSelectedItem();

      boolean remove = confirmation();
      if (remove)
      {
        Project removeProject = new Project(selectedItem.getTitleProperty(),
            selectedItem.getStatusProperty());
        model.removeProject(removeProject);
        viewModel.remove(removeProject);
        projectListTable.getSelectionModel().clearSelection();
      }
    }
    catch (Exception e)
    {
      ErrorLabel.setText("Item not found: " + e.getMessage());
    }
  }

  private boolean confirmation()
  {
    int index = projectListTable.getSelectionModel().getSelectedIndex();
    ProjectViewModel selectedItem = projectListTable.getItems().get(index);
    if (index < 0 || index >= projectListTable.getItems().size())
    {
      return false;
    }
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(
        "Removing project {" + selectedItem.getIdProperty() + ": "
            + selectedItem.getTitleProperty() + ": " + selectedItem
            .getStatusProperty() + "} ");
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }

  @FXML public Project SearchbarByID()
  {
    try
    {
      model.findById(SearchBar.getText());
    }
    catch (Exception e)
    {
      ErrorLabel.setText("Project wasn't found");
    }
    return model.getProject(SearchBar.getText());
  }

  @FXML public Project SearchbarByTitle()
  {
    try
    {
      model.findByTitle(SearchBar.getText());
    }
    catch (Exception e)
    {
      ErrorLabel.setText("Project wasn't found");
    }
    return model.getProject(SearchBar.getText());
  }

}
