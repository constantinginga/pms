package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class AddProjectController {
    private ViewHandler viewHandler;
    private Region root;

    public AddProjectController(){}

    public void init(ViewHandler viewHandler, Region root){
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public Region getRoot(){
        return root;
    }

    @FXML
    private void handleAddProjectButton(){
        viewHandler.openView("projectDetails");
    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("mainWindow");
    }
}
