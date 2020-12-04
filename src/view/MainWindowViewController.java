package view;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class MainWindowViewController {
    private Region root;
    private ViewHandler viewHandler;

    public MainWindowViewController(){

    }

    public void init(ViewHandler viewHandler, Region root){
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public Region getRoot(){
        return root;
    }

    @FXML
    private void handleOpenProjectPressed(){
        viewHandler.openView("projectDetails");
    }

    @FXML
    private void handleAddProjectButton(){
        viewHandler.openView("addProject");
    }
}
