package view.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;

public class MainWindowViewController {
    private Region root;
    private ViewHandler viewHandler;
    private ProjectManagementSystemModel model;
    private ViewState state;

    public MainWindowViewController(){

    }

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
    private void handleOpenProjectButton(){
        viewHandler.openView("reqList");
    }

    @FXML
    private void handleAddProjectButton(){
        viewHandler.openView("addProject");
    }

    @FXML
    private void handleEmployeeButton(){
        viewHandler.openView("teamView");
    }

}
