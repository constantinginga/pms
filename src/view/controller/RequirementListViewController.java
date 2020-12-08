package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.RequirementListViewModel;
import view.viewModel.RequirementViewModel;

import java.util.Optional;


public class RequirementListViewController {

    @FXML
    private TableView<RequirementViewModel>  requirementListTable;

    @FXML
    private TableColumn<RequirementViewModel, String> idColumn;

    @FXML
    private TableColumn<RequirementViewModel, String> userStoryColumn;

    @FXML
    private TableColumn<RequirementViewModel, String> statusColumn;

    @FXML
    private TableColumn<RequirementViewModel, String> deadLineColumn;


    @FXML
    private TextField projectTitleTextField;

    @FXML
    private TextField projectID;

    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;
    private RequirementListViewModel requirementListViewModel;


    public RequirementListViewController(){}

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        this.requirementListViewModel = new RequirementListViewModel(model, state.getSelectedProjectID());
        update();
    }

    public void update(){
        projectTitleTextField.setText(model.getTitleForProject(state.getSelectedProjectID()));
        projectID.setText(model.getProject(state.getSelectedProjectID()).getId());

        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        userStoryColumn.setCellValueFactory(cellData -> cellData.getValue().getUserStoryProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        deadLineColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadLineProperty());

        requirementListTable.setItems(requirementListViewModel.getReqList());
    }

    public  Region getRoot(){
        return root;
    }

    public void reset(){
        requirementListViewModel.update();
        projectID.setText("");
        projectTitleTextField.setText("");
    }

    @FXML
    private void handleOpenRequirementButton(){
        RequirementViewModel selectedItem = requirementListTable.getSelectionModel().getSelectedItem();
        state.setSelectedRequirementID(selectedItem.getIdProperty().get());
        viewHandler.openView("taskList");
    }

    @FXML
    private void handleAddRequirementButton(){
        viewHandler.openView("addReq");
    }

    @FXML
    private void handleBackButton(){
        state.setSelectedProjectID("");
        viewHandler.openView("mainWindow");
    }

    @FXML
    private void handleRemoveRequirementButton(){
        try{
            RequirementViewModel selectedItem = requirementListTable.getSelectionModel().getSelectedItem();
            boolean remove = confirmation();
            if (remove){
                String reqID = selectedItem.getIdProperty().get();
                model.removeRequirement(reqID, state.getSelectedProjectID());
                requirementListViewModel.remove(reqID);
                requirementListTable.getSelectionModel().clearSelection();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  boolean confirmation(){
        int index = requirementListTable.getSelectionModel().getFocusedIndex();
        RequirementViewModel selectedItem = requirementListTable.getItems().get(index);
        requirementListTable.getItems().get(index);
        if(index < 0 || index >= requirementListTable.getItems().size()) return false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Remove requirement \n"+selectedItem.getIdProperty().get()+":"+selectedItem.getUserStoryProperty().get()+".");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent() && (result.get() == ButtonType.OK));
    }


}
