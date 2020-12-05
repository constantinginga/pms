package view.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;

public class AddTaskViewController {
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;

    public AddTaskViewController(){}

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
    }

    public void reset(){}

    public Region getRoot(){
        return root;
    }


    @FXML
    private void handleAddButton(){
        viewHandler.openView("taskList");
    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("taskList");
    }

}
