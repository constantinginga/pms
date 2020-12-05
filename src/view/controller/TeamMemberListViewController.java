package view.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;

public class TeamMemberListViewController {
    private ProjectManagementSystemModel model;
    private ViewHandler viewHandler;
    private Region root;

    public TeamMemberListViewController(){

    }

    public void init(ViewHandler viewHandler, Region root,ProjectManagementSystemModel model){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
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
