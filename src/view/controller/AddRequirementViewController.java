package view.controller;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.*;
import view.ViewHandler;
import view.ViewState;

import java.nio.charset.StandardCharsets;
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

    @FXML private ComboBox<String> resPersonComboBox ;
    @FXML private ChoiceBox<String> statusChoiceBox ;
    @FXML private ComboBox<String> teamMemberComboBox;
    @FXML private Button addTamMembersButtton;
    TeamMemberList teamMembers;

    public AddRequirementViewController(){}
    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        teamMembers = new TeamMemberList();
        errorLabel.setText("");

        statusChoiceBox.getItems().setAll(GeneralTemplate.STATUS_NOT_STARTED,GeneralTemplate.STATUS_STARTED,GeneralTemplate.STATUS_ENDED,
                GeneralTemplate.STATUS_APPROVED,GeneralTemplate.STATUS_REJECTED);

        addComboBox();
        ComboBoxListener();
        resPersonComboBox.setPromptText("Add Scrum master");
        teamMemberComboBox.setPromptText("Add Team member");


    }

    @FXML  public void addTamMembersButtton(){
        if(teamMemberComboBox.getSelectionModel().getSelectedItem() != null){
            teamMembers.add(new TeamMember(teamMemberComboBox.getSelectionModel().getSelectedItem()));
            String s = teamMemberComboBox.getSelectionModel().getSelectedItem();
            teamMemberComboBox.getItems().remove(s);
            resPersonComboBox.getItems().remove(s);
        }
    }

    public void reset(){
        resPersonComboBox.setPromptText("Add Scrum master");
        teamMemberComboBox.setPromptText("Add Team member");
        errorLabel.setText("");
        userStoryTextField.setText("");
        estimatedTimeTextField.setText("");
        deadLineDatePicker.setValue(null);
        statusChoiceBox.setValue(GeneralTemplate.STATUS_NOT_STARTED);
        resPersonComboBox.getItems().clear();
        resPersonComboBox.getSelectionModel().clearSelection();
        teamMemberComboBox.getItems().clear();
        teamMemberComboBox.getSelectionModel().clearSelection();
        addComboBox();
        ComboBoxListener();
    }




    private void addComboBox(){
        for (int i = 0; i<model.getTeamMemberList().getSize(); i++){
            resPersonComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
            teamMemberComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).getName());
        }
    }

    private void ComboBoxListener(){
        resPersonComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldV, newValue) ->{
            if(newValue !=null && !newValue.equals(oldV)){
                teamMemberComboBox.getItems().remove(newValue);
            }
        });
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
                resPersonComboBox.requestFocus();
                errorLabel.setText("");
            }
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
            Requirement requirement = new Requirement(userStoryTextField.getText(), new TeamMember(resPersonComboBox.getValue()),
                    statusChoiceBox.getValue(),estimatedTime ,new MyDate(datePickerValue.getDayOfMonth(), datePickerValue.getMonthValue(), datePickerValue.getYear())
            );
            model.addRequirement(requirement, state.getSelectedProjectID());
            viewHandler.openView("reqList");
            if(teamMembers.getSize() > 0){
                requirement.setMembers((teamMembers));
                for(int i = 0; i < teamMembers.getSize(); i++){
                    System.out.println(teamMembers.getTeamMember(i).getName());
                }
            }

        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }

    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("reqList");
    }

}
