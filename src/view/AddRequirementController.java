package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class AddRequirementController {
    private ViewHandler viewHandler;
    private Region root;


    public AddRequirementController(){}

    public void init(ViewHandler viewHandler, Region root){
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public Region getRoot(){
        return root;
    }

    @FXML
    private void handleAddRequirementButton(){
        viewHandler.openView("requirementDetails");
    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("projectDetails");
    }
}
