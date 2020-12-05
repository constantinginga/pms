package view.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;


public class RequirementListViewController {

    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;


    public RequirementListViewController(){}

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;

    }

    public  Region getRoot(){
        return root;
    }

    public void reset(){

    }

    @FXML
    private void handleOpenRequirementButton(){
        viewHandler.openView("taskList");
    }

    @FXML
    private void handleAddRequirementButton(){
        viewHandler.openView("addReq");
    }

    @FXML
    private void handleBackButton(){
        viewHandler.openView("mainWindow");
    }


}
