package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.Task;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.TaskListViewModel;
import view.viewModel.TaskViewModel;

public class TaskListViewController {
    @FXML private TableView<TaskViewModel> taskListTable;
    @FXML private TableColumn<TaskViewModel, String> idColumn;
    @FXML private TableColumn<TaskViewModel, String> titleColumn;
    @FXML private TableColumn<TaskViewModel, String> statusColumn;
    @FXML private TableColumn<TaskViewModel, String> responsiblePersonColumn;
    @FXML private TableColumn<TaskViewModel, String> deadlineColumn;
    @FXML private TableColumn<TaskViewModel, Number> estimatedTimeColumn;
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

}
