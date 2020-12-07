package view.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.TeamMemberViewModel;

import java.time.LocalDate;

public class AddRequirementViewController {
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;


    @FXML private TextField userStoryTextField;
    @FXML private TextField estimatedTimeTextField;
    @FXML private DatePicker deadLineDatePicker;
    @FXML private Label errorLabel;

    @FXML private ChoiceBox<String> resPersonChoiceBox = new ChoiceBox<>();
    @FXML private ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();
    @FXML private ChoiceBox<String> teamMemberChoiceBox = new ChoiceBox<>();

    public AddRequirementViewController(){}

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;

        resPersonChoiceBox.getItems().setAll(new TeamMember("Person1").getName(), new TeamMember("Person2").getName(),new TeamMember("person3").getName());
        teamMemberChoiceBox.getItems().setAll(new TeamMember("Person1").getName(), new TeamMember("Person2").getName(),new TeamMember("person3").getName());
        statusChoiceBox.getItems().setAll(GeneralTemplate.STATUS_NOT_STARTED,GeneralTemplate.STATUS_STARTED,GeneralTemplate.STATUS_ENDED,
                GeneralTemplate.STATUS_APPROVED,GeneralTemplate.STATUS_REJECTED);


    }

    public void reset(){
        errorLabel.setText("");
        userStoryTextField.setText("");
        estimatedTimeTextField.setText("");
        deadLineDatePicker.setValue(null);

        statusChoiceBox.setValue(GeneralTemplate.STATUS_NOT_STARTED);
        resPersonChoiceBox.valueProperty().set("");

    }

    public Region getRoot(){
        return root;
    }


    @FXML
    private void keyTyped(){
        if(userStoryTextField == null ){
            errorLabel.setText("Please Enter User Story");
        }
    }

    @FXML
    private void onEnter(Event event){
        if(event.getSource() == userStoryTextField){
            if(userStoryTextField.getText().equals("")){
                errorLabel.setText("User story Can't be empty");
                userStoryTextField.requestFocus();
            }else{
                resPersonChoiceBox.requestFocus();
                errorLabel.setText("");
            }
        }else if(event.getSource() == resPersonChoiceBox){
            if(resPersonChoiceBox.getValue().equals("")){
                resPersonChoiceBox.requestFocus();
            }else {
                teamMemberChoiceBox.requestFocus();

            }
        }else if(event.getSource() == teamMemberChoiceBox){
            estimatedTimeTextField.requestFocus();
        }else if(event.getSource() == estimatedTimeTextField){
            if (estimatedTimeTextField.getText().equals("")){

                estimatedTimeTextField.requestFocus();
            }
            try {
                Integer.parseInt(estimatedTimeTextField.getText());
                statusChoiceBox.requestFocus();
                errorLabel.setText("");
            }catch (Exception e){
                errorLabel.setText(e.getMessage());
                estimatedTimeTextField.requestFocus();
            }

        }else if(event.getSource() == statusChoiceBox){
            deadLineDatePicker.requestFocus();
        }
    }
    @FXML
    private void handleAddButton(){
        try{
            LocalDate datePickerValue = deadLineDatePicker.getValue();
            int estimatedTime = Integer.parseInt(estimatedTimeTextField.getText());
            Requirement requirement = new Requirement(userStoryTextField.getText(), new TeamMember(resPersonChoiceBox.getValue()),
                    statusChoiceBox.getValue(),estimatedTime ,new MyDate(datePickerValue.getDayOfMonth(), datePickerValue.getMonthValue(), datePickerValue.getYear())
            );
            model.addRequirement(requirement, state.getSelectedProjectID());
            viewHandler.openView("reqList");
        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }

    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("reqList");
    }

}
