package view.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.RequirementViewModel;
import view.viewModel.TaskListViewModel;
import view.viewModel.TaskViewModel;
import view.viewModel.TeamMemberViewModel;

import java.beans.EventHandler;
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
  @FXML private TableView<TeamMemberViewModel> teamMembersTable;
  @FXML private TableColumn<TeamMemberViewModel, String> idColumnTeamMembers;
  @FXML private TableColumn<TeamMemberViewModel, String> nameColumnTeamMembers;
  @FXML private ComboBox<String> chooseTeamMembersComboBox;
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
  @FXML private Button chooseTeamMemberButton;

  private ViewHandler viewHandler;
  private Region root;
  private ProjectManagementSystemModel model;
  private TaskListViewModel taskListViewModel;
  private ViewState state;

  public TaskListViewController()
  {
  }

  //!!!still have to add responsiblePersonChoiceBox!!!
  //!!!add to handleCancelButton as well!!!
  public void init(ViewHandler viewHandler, Region root,
      ProjectManagementSystemModel model, ViewState state)
  {
    this.viewHandler = viewHandler;
    this.root = root;
    this.model = model;
    this.state = state;

    errorLabel.setText("");

    this.taskListViewModel = new TaskListViewModel(model,
       state.getSelectedProjectID(),
        state.getSelectedRequirementID());
    update();
  }
  // updates attributes and table with last values and resets errorLabel
  private void update()
  {
    errorLabel.setText("");
    userStoryTextField.setText(model
        .getUserStoryRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()));
    idText.setText(model.getRequirement(state.getSelectedProjectID(),
        state.getSelectedRequirementID()).getId());
    estimatedTimeTextField.setText(String.valueOf(model
        .getEstimatedTimeForRequirement(state.getSelectedTaskID(),
            state.getSelectedProjectID(), state.getSelectedRequirementID())));
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
    if (statusChoiceBox.getItems().size() == 0)
    {
      statusChoiceBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
      statusChoiceBox.getItems().add(GeneralTemplate.STATUS_ENDED);
      statusChoiceBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
      statusChoiceBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
      statusChoiceBox.getItems().add(GeneralTemplate.STATUS_STARTED);
    }
    statusChoiceBox.getSelectionModel().select(model
        .getRequirement(state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getStatus());

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
    if (chooseTeamMembersComboBox.getItems().size() == 0)
    {
      for (int i = 0; i < model.getTeamMemberList().getSize(); i++)
      {
        chooseTeamMembersComboBox.getItems()
            .add(model.getTeamMemberList().getTeamMember(i).getName());
      }
    }
    searchBarTextField.setText("");
    search();
  }

  private void search()
  {

    FilteredList<TaskViewModel> filteredList = new FilteredList<>(
        taskListViewModel.getList(), b -> true);
    searchBarTextField.textProperty()
        .addListener(((observableValue, oldValue, newValue) -> {
          filteredList.setPredicate(requirement -> {
            if (newValue == null || newValue.isEmpty())
              return true;
            String lowerCaseFilter = newValue.toLowerCase();

            return requirement.getIdProperty().toLowerCase()
                .contains(lowerCaseFilter) || requirement.getTitleProperty()
                .toLowerCase().contains(lowerCaseFilter) || requirement
                .getStatusProperty().toLowerCase().contains(lowerCaseFilter);
          });
        }));
  }

  public Region getRoot()
  {
    return root;
  }
  // resets scene
  public void reset()
  {
    taskListViewModel.update();
    editButton.setText("Edit");
    update();
  }



  // opens selected task
  @FXML private void handleOpenTaskButton()
  {
    state.setSelectedTaskID(
        taskListTable.getSelectionModel().getSelectedItem().getIdProperty());
    viewHandler.openView("taskView");
  }

  @FXML private void handleAddTaskButton()
  {
    // if list is empty, set id for next project to 1
    state.setSelectedTaskID(Integer.toString(taskListTable.getItems().size() + 1));
    viewHandler.openView("addTask");
  }

  @FXML private void handleBackButton()
  {
    viewHandler.openView("reqList");
  }

  //sets attributes to be editable
  public void attributesDisability(boolean disabled)
  {
    userStoryTextField.setDisable(disabled);
    idText.setDisable(disabled);
    statusChoiceBox.setDisable(disabled);
    responsiblePersonChoiceBox.setDisable(disabled);
    deadlineDatePicker.setDisable(disabled);
    estimatedTimeTextField.setDisable(disabled);
    actualTimeTextField.setDisable(disabled);
    teamMembersTable.setDisable(disabled);
    chooseTeamMembersComboBox.setDisable(disabled);
    chooseTeamMemberButton.setDisable(disabled);
  }

  public void handleEditButton()
  {
    if (editButton.getText().equals("Edit"))
    {
      editButton.setText("Save");
      attributesDisability(false);
    }
    else
    {
      editButton.setText("Edit");
      attributesDisability(true);
    }
    if (userStoryTextField.getText() == null)
    {
      errorLabel.setText("User story is empty");
      return;
    }
    if (estimatedTimeTextField.getText() == null)
    {
      errorLabel.setText("Estimated time is empty");
      return;
    }
    if (actualTimeTextField.getText() == null)
    {
      errorLabel.setText("Actual time is empty");
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
        .setActualTime(Integer.parseInt(actualTimeTextField.getText()));
  }

  //resets values for requirement's attributes to last values before editing
  public void handleCancelButton()
  {
    attributesDisability(true);
    editButton.setText("Edit");
    update();
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
        //taskListTable.getSelectionModel().clearSelection();
        taskListTable.getItems().remove(selectedItem);
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
            "Removing task [" + selectedItem.getIdProperty() + "] " + selectedItem
                    .getTitleProperty() + ": " + selectedItem.getStatusProperty() + ": "
                    + selectedItem.getResponsiblePersonNameProperty() + ": "
                    + selectedItem.getDeadlineProperty() + ": " + selectedItem
                    .getEstimatedTimeProperty());
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }

  public void handleChooseTeamMemberButton()
  {
  }
}

