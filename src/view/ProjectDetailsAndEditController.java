package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class ProjectDetailsAndEditController {

    private ViewHandler viewHandler;
    private Region root;

    public ProjectDetailsAndEditController(){}

    public void init(ViewHandler viewHandler, Region root){
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public  Region getRoot(){
        return root;
    }

    @FXML
    private void handleOpenRequirementButton(){
        viewHandler.openView("requirementDetails");
    }

    @FXML
    private void handleBackButton(){
        viewHandler.openView("mainWindow");
    }

    @FXML
    private void handleAddRequirementButton(){
        viewHandler.openView("addRequirement");
    }
}
