package view.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;

public class TeamMemberListViewController {
    private ProjectManagementSystemModel model;
    private ViewHandler viewHandler;
    private Region root;
    private ViewState state;

    public TeamMemberListViewController(){

    }

    public void init(ViewHandler viewHandler, Region root,ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.state = state;
    }

    public void reset(){}

    public Region getRoot(){
        return root;
    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("mainWindow");
    }

}
