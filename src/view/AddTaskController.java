package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class AddTaskController {
    private ViewHandler viewHandler;
    private Region root;

    public AddTaskController(){}

    public void init(ViewHandler viewHandler, Region root){
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public Region getRoot(){
        return root;
    }

    @FXML
    private void handleAddTaskButton(){
        viewHandler.openView("requirementDetails");
    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("requirementDetails");
    }
}
