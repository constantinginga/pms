package view.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
  private RequirementListViewController requirementListViewController;

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
    ErrorLabel.setText("");
    this.viewModel = new ProjectListViewModel(model);

    idColumn.setCellValueFactory(
        cellData -> cellData.getValue().idPropertyProperty());
    titleColumn.setCellValueFactory(
        cellData -> cellData.getValue().titlePropertyProperty());
    statusColumn.setCellValueFactory(
        cellData -> cellData.getValue().statusPropertyProperty());

    projectListTable.setItems(viewModel.getList());
    search();
  }

  public Region getRoot()
  {
    return root;
  }

  private void search()
  {

    FilteredList<ProjectViewModel> filteredList = new FilteredList<>(
        viewModel.getList(), b -> true);
    SearchBar.textProperty()
        .addListener(((observableValue, oldValue, newValue) -> {
          filteredList.setPredicate(project -> {
            if (newValue == null || newValue.isEmpty())
              return true;

            String lowerCaseFilter = newValue.toLowerCase();
            return project.getTitleProperty().toLowerCase()
                .contains(lowerCaseFilter) || project.getIdProperty()
                .toLowerCase().contains(lowerCaseFilter);
          });
        }));

    SortedList<ProjectViewModel> sortedList = new SortedList<>(filteredList);
    sortedList.comparatorProperty().bind(projectListTable.comparatorProperty());
    projectListTable.setItems(sortedList);
  }

  public void reset()
  {
    ErrorLabel.setText("");
    idColumn.setCellValueFactory(
        cellData -> cellData.getValue().idPropertyProperty());
    titleColumn.setCellValueFactory(
        cellData -> cellData.getValue().titlePropertyProperty());
    statusColumn.setCellValueFactory(
        cellData -> cellData.getValue().statusPropertyProperty());
    viewModel.update();

  }

  @FXML private void handleOpenProjectButton()
  {
    try
    {
      state.setSelectedProjectID(
          projectListTable.getSelectionModel().getSelectedItem()
              .getIdProperty());
      viewHandler.openView("reqList");
    }
    catch (Exception e)
    {
      ErrorLabel.setText("Choose project to open");
    }
  }

  @FXML private void handleAddProjectButton()
  {
    state.setSelectedProjectID(
        Integer.toString(projectListTable.getItems().size() + 1));
    viewHandler.openView("addProject");
  }

  @FXML private void handleEmployeeButton()
  {
    viewHandler.openView("teamView");
  }

  @FXML private void handleRemoveProjectButton()
  {
    ErrorLabel.setText("");
    try
    {
      ProjectViewModel selectedItem = projectListTable.getSelectionModel()
          .getSelectedItem();

      boolean remove = confirmation();
      if (remove)
      {
        String projectId = selectedItem.idPropertyProperty().get();
        model.removeProject(state.getSelectedProjectID());
        viewModel.remove(projectId);
        projectListTable.getItems().remove(selectedItem);
      }
    }
    catch (Exception e)
    {
      ErrorLabel.setText("Choose project to remove");
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
        "Removing project [" + selectedItem.getIdProperty() + "] "
            + selectedItem.getTitleProperty() + ": " + selectedItem
            .getStatusProperty());
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
}
