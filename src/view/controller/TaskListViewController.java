package view.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.GeneralTemplate;
import model.MyDate;
import model.Task;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.TaskListViewModel;
import view.viewModel.TaskViewModel;

import java.time.LocalDate;

public class TaskListViewController {
    @FXML private TableView<TaskViewModel> taskListTable;
    @FXML private TableColumn<TaskViewModel, String> idColumn;
    @FXML private TableColumn<TaskViewModel, String> titleColumn;
    @FXML private TableColumn<TaskViewModel, String> statusColumn;
    @FXML private TableColumn<TaskViewModel, String> responsiblePersonColumn;
    @FXML private TableColumn<TaskViewModel, String> deadlineColumn;
    @FXML private TableColumn<TaskViewModel, Number> estimatedTimeColumn;
    @FXML private TextField userStoryTextField;
    @FXML private Text idText;
    @FXML private ChoiceBox<String> statusChoiceBox= new ChoiceBox<>();
    @FXML private ChoiceBox<String> responsiblePersonChoiceBox= new ChoiceBox<>();
    @FXML private DatePicker deadlinePicker;
    @FXML private TextField estimatedTimeTextField;
    @FXML private TextField actualTimeTextField;
    @FXML private TextField searchBarTextField;
    @FXML private Label errorLabel;

    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private TaskListViewModel taskListViewModel;
    private ViewState state;

    public TaskListViewController(){}

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        errorLabel.setText("");
        userStoryTextField.setText(model.getUserStoryRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID()));
        idText.setText(state.getSelectedRequirementID());
        statusChoiceBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
        statusChoiceBox.getItems().add(GeneralTemplate.STATUS_ENDED);
        statusChoiceBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
        statusChoiceBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
        statusChoiceBox.getItems().add(GeneralTemplate.STATUS_STARTED);
        statusChoiceBox.getSelectionModel().select(model.getRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID()).getStatus());
        estimatedTimeTextField.setText(String.valueOf(model.getEstimatedTimeForRequirement(state.getSelectedTaskID(), state.getSelectedProjectID(), state.getSelectedTaskID())));
        actualTimeTextField.setText(String.valueOf(model.getActualTimeForRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID())));
        searchBarTextField.setText("");

        this.taskListViewModel = new TaskListViewModel(model, state.getSelectedTaskID(), state.getSelectedProjectID(), state.getSelectedRequirementID());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPropertyProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titlePropertyProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusPropertyProperty());
        responsiblePersonColumn.setCellValueFactory(cellData -> cellData.getValue().responsiblePersonNamePropertyProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().deadlinePropertyProperty());
        estimatedTimeColumn.setCellValueFactory(cellData -> cellData.getValue().estimatedTimePropertyProperty());
        taskListTable.setItems(taskListViewModel.getList());
    }

    public Region getRoot(){
        return root;
    }

    public void reset(){
        taskListViewModel.update();
    }

    @FXML
    private void handleOpenTaskButton(){
        state.setSelectedTaskID(taskListTable.getSelectionModel().getSelectedItem().getIdProperty());
        viewHandler.openView("taskView");
    }

    @FXML
    private void handleAddTaskButton(){
        // get the id of last item in table
        int position = (taskListTable.getItems().size() == 0) ? 0 : taskListTable.getItems().size() - 1;
        TaskViewModel lastRow = taskListTable.getItems().get(position);
        state.setSelectedTaskID(lastRow.getIdProperty());
        viewHandler.openView("addTask");
    }

    @FXML
    private void handleBackButton(){
        viewHandler.openView("reqList");
    }

    public void handleEditButton(ActionEvent actionEvent)
    {
    }

    public void handleCancelButton(ActionEvent actionEvent)
    {
    }

    public void editTeamMembersButton(ActionEvent actionEvent)
    {
    }

    public void handleRemoveTaskButton(ActionEvent actionEvent)
    {
    }
}
