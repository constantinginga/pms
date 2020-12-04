package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class TaskListView
{
    private ViewHandler viewHandler;
    private Region root;

    public TaskListView(){}

    public void init(ViewHandler viewHandler, Region root){
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public Region getRoot(){
        return root;
    }

    @FXML
    private void handleBackButton(){
        viewHandler.openView("projectDetails");
    }

    @FXML
    private void handleTaskDetailsAndEditButton(){
        viewHandler.openView("taskDetails");
    }

    @FXML
    private void handleAddTaskButton(){
        viewHandler.openView("addTask");
    }
}
