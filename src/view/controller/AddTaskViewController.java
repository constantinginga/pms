package view.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;

public class AddTaskViewController {
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;

    public AddTaskViewController(){}

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model){
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
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
