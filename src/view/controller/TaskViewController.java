package view.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;

public class TaskViewController {
    private ProjectManagementSystemModel model;
    private Region root;
    private ViewHandler viewHandler;
    private ViewState state;

    public TaskViewController(){}

    public void init(ViewHandler viewHandler,  Region root, ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.state = state;
    }

    public Region getRoot(){
        return root;
    }

    public void reset(){

    }

    @FXML
    private void handleSaveButton(){
        viewHandler.openView("taskList");
    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("taskList");
    }
}
