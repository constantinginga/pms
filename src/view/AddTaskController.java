package view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemFile;
import mediator.ProjectManagementSystemModel;

public class AddTaskController {
    @FXML private TextArea userStoryTextArea;
    @FXML private TextField estimateTextField;
    @FXML private TextField hoursWorkedTextField;
    @FXML private DatePicker deadlineDatePicker;
    @FXML private TextField idTextField;
    private ViewHandler viewHandler;
    private ProjectManagementSystemModel model;
    private Region root;

    public AddTaskController(){}

    public void init(ViewHandler viewHandler, ProjectManagementSystemModel model, Region root){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
    }

    public void reset() {
        userStoryTextArea.setText("");
        estimateTextField.setText("");
        hoursWorkedTextField.setText("");
        deadlineDatePicker.getEditor().clear();
        idTextField.setText("");
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
