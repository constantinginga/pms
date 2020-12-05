package view.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;

public class TaskListViewController {
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;

    public TaskListViewController(){}

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
    }

    public Region getRoot(){
        return root;
    }

    public void reset(){}

    @FXML
    private void handleOpenTaskButton(){
        viewHandler.openView("taskView");
    }

    @FXML
    private void handleAddTaskButton(){
        viewHandler.openView("addTask");
    }

    @FXML
    private void handleBackButton(){
        viewHandler.openView("reqList");
    }

}
