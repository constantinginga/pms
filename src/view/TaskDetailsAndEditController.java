package view;

import javafx.scene.layout.Region;

public class TaskDetailsAndEditController {
    private ViewHandler viewHandler;
    private Region root;

    public TaskDetailsAndEditController(){}

    public void init(ViewHandler viewHandler, Region root){
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public Region getRoot(){
        return root;
    }


    public void handleCancelButton(){
        viewHandler.openView("requirementDetails");
    }
}
