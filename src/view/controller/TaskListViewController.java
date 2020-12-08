package view.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.TaskListViewModel;
import view.viewModel.TaskViewModel;

import java.time.LocalDate;
import java.util.Optional;

public class TaskListViewController
{
  @FXML private TableView<TaskViewModel> taskListTable;
  @FXML private TableColumn<TaskViewModel, String> idColumn;
  @FXML private TableColumn<TaskViewModel, String> titleColumn;
  @FXML private TableColumn<TaskViewModel, String> statusColumn;
  @FXML private TableColumn<TaskViewModel, String> responsiblePersonColumn;
  @FXML private TableColumn<TaskViewModel, String> deadlineColumn;
  @FXML private TableColumn<TaskViewModel, Number> estimatedTimeColumn;
  @FXML private TextField userStoryTextField;
  @FXML private Text idText;
  @FXML private ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();
  @FXML private ChoiceBox<String> responsiblePersonChoiceBox = new ChoiceBox<>();
  @FXML private DatePicker deadlineDatePicker;
  @FXML private TextField estimatedTimeTextField;
  @FXML private TextField actualTimeTextField;
  @FXML private TextField searchBarTextField;
  @FXML private Label errorLabel;
  @FXML private Button editButton;

  private ViewHandler viewHandler;
  private Region root;
  private ProjectManagementSystemModel model;
  private TaskListViewModel taskListViewModel;
  private ViewState state;

  public TaskListViewController()
  {
  }

  //still have to add responsiblePersonChoiceBox and deadlinePicker
  //add to handleCancelButton as well
  public void init(ViewHandler viewHandler, Region root,
      ProjectManagementSystemModel model, ViewState state)
  {
    this.viewHandler = viewHandler;
    this.root = root;
    this.model = model;
    this.state = state;
    this.editButton = new Button("Edit");
    errorLabel.setText("");
    attributesVisibility(false);
    userStoryTextField.setText(model
        .getUserStoryRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()));
    idText.setText(state.getSelectedRequirementID());
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_ENDED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_STARTED);
    statusChoiceBox.getSelectionModel().select(model
        .getRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getStatus());
    estimatedTimeTextField.setText(String.valueOf(model
        .getEstimatedTimeForRequirement(state.getSelectedTaskID(),
            state.getSelectedProjectID(), state.getSelectedTaskID())));
    actualTimeTextField.setText(String.valueOf(model
        .getActualTimeForRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID())));
    deadlineDatePicker.setValue(LocalDate.of(model
        .getDeadlineForRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getYear(), model
        .getDeadlineForRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getMonth(), model
        .getDeadlineForRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getDay()));
    searchBarTextField.setText("");

    this.taskListViewModel = new TaskListViewModel(model,
        state.getSelectedTaskID(), state.getSelectedProjectID(),
        state.getSelectedRequirementID());
    idColumn.setCellValueFactory(
        cellData -> cellData.getValue().idPropertyProperty());
    titleColumn.setCellValueFactory(
        cellData -> cellData.getValue().titlePropertyProperty());
    statusColumn.setCellValueFactory(
        cellData -> cellData.getValue().statusPropertyProperty());
    responsiblePersonColumn.setCellValueFactory(cellData -> cellData.getValue()
        .responsiblePersonNamePropertyProperty());
    deadlineColumn.setCellValueFactory(
        cellData -> cellData.getValue().deadlinePropertyProperty());
    estimatedTimeColumn.setCellValueFactory(
        cellData -> cellData.getValue().estimatedTimePropertyProperty());
    taskListTable.setItems(taskListViewModel.getList());
  }

  public Region getRoot()
  {
    return root;
  }

  public void reset()
  {
    taskListViewModel.update();
  }

  @FXML private void handleOpenTaskButton()
  {
    state.setSelectedTaskID(
        taskListTable.getSelectionModel().getSelectedItem().getIdProperty());
    viewHandler.openView("taskView");
  }

  @FXML private void handleAddTaskButton()
  {
    // get the id of last item in table
    int position = (taskListTable.getItems().size() == 0) ?
        0 :
        taskListTable.getItems().size() - 1;
    TaskViewModel lastRow = taskListTable.getItems().get(position);
    state.setSelectedTaskID(lastRow.getIdProperty());
    viewHandler.openView("addTask");
  }

  @FXML private void handleBackButton()
  {
    viewHandler.openView("reqList");
  }

  //sets attributes to be editable
  public void attributesVisibility(boolean visible)
  {
    userStoryTextField.setVisible(true);
    idText.setVisible(false);
    statusChoiceBox.setVisible(true);
    responsiblePersonChoiceBox.setVisible(true);
    deadlineDatePicker.setVisible(true);
    estimatedTimeColumn.setVisible(true);
    actualTimeTextField.setVisible(true);
  }

  public void handleEditButton()
  {
    attributesVisibility(true);
    editButton.setText("Save");
    if (userStoryTextField.getText() == null)
    {
      errorLabel.setText("User story is empty");
      return;
    }
    model.getRequirement(state.getSelectedRequirementID(),
        state.getSelectedProjectID()).setTitle(userStoryTextField.getText());
    model.getRequirement(state.getSelectedRequirementID(),
        state.getSelectedProjectID())
        .setStatusForRequirement(statusChoiceBox.getValue());
    model.getRequirement(state.getSelectedRequirementID(),
        state.getSelectedProjectID()).setResponsiblePerson(
        new TeamMember(responsiblePersonChoiceBox.getValue()));
    model.getRequirement(state.getSelectedRequirementID(),
        state.getSelectedProjectID())
        .setDeadline(new MyDate(deadlineDatePicker.getValue()));
    model.getRequirement(state.getSelectedRequirementID(),
        state.getSelectedProjectID())
        .setEstimatedTime(Integer.parseInt(estimatedTimeTextField.getText()));
    model.getRequirement(state.getSelectedRequirementID(),
        state.getSelectedProjectID())
        .setEstimatedTime(Integer.parseInt(actualTimeTextField.getText()));
  }

  //resets values for requirement's attributes to last values
  public void handleCancelButton()
  {
    userStoryTextField.setText(model
        .getUserStoryRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()));
    idText.setText(state.getSelectedRequirementID());
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_ENDED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_STARTED);
    statusChoiceBox.getSelectionModel().select(model
        .getRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getStatus());
    estimatedTimeTextField.setText(String.valueOf(model
        .getEstimatedTimeForRequirement(state.getSelectedTaskID(),
            state.getSelectedProjectID(), state.getSelectedTaskID())));
    actualTimeTextField.setText(String.valueOf(model
        .getActualTimeForRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID())));
    deadlineDatePicker.setValue(LocalDate.of(model
        .getDeadlineForRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getYear(), model
        .getDeadlineForRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getMonth(), model
        .getDeadlineForRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getDay()));
    errorLabel.setText("");
  }

  /*opens window with list of team members that can be assigned
  to or removed from requirement */
  public void editTeamMembersButton()
  {
    viewHandler.openView("proTeamMember");
  }

  //removes chosen task from taskList
  public void handleRemoveTaskButton()
  {
    errorLabel.setText("");
    try
    {
      TaskViewModel selectedItem = taskListTable.getSelectionModel()
          .getSelectedItem();

      boolean remove = confirmation();
      if (remove)
      {
        Task removeTask = new Task(selectedItem.getTitleProperty(),
            new TeamMember(selectedItem.getResponsiblePersonNameProperty()),
            selectedItem.getEstimatedTimeProperty(),
            new MyDate(selectedItem.getDayProperty(),
                selectedItem.getMonthProperty(),
                selectedItem.getYearProperty()));
        model.removeTask(removeTask, state.getSelectedProjectID(),
            state.getSelectedRequirementID());
        taskListViewModel.remove(removeTask);
        taskListTable.getSelectionModel().clearSelection();
      }
    }
    catch (Exception e)
    {
      errorLabel.setText("Choose task to remove");
    }
  }

  private boolean confirmation()
  {
    int index = taskListTable.getSelectionModel().getSelectedIndex();
    TaskViewModel selectedItem = taskListTable.getItems().get(index);
    if (index < 0 || index >= taskListTable.getItems().size())
    {
      return false;
    }
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(
        "Removing task {" + selectedItem.getIdProperty() + ": " + selectedItem
            .getTitleProperty() + ": " + selectedItem.getStatusProperty()
            + "} ");
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
}

