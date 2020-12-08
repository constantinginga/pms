package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.TeamMemberListViewModel;
import view.viewModel.TeamMemberViewModel;



public class TeamMemberListViewController {
    @FXML private TextField nameTextField;
    @FXML private Label errorLabel;
    @FXML private TableView<TeamMemberViewModel> teamMemberListTable;
    @FXML private TableColumn<TeamMemberViewModel, String> idColumn;
    @FXML private TableColumn<TeamMemberViewModel, String> nameColumn;
    private TeamMemberListViewModel teamMemberListViewModel;
    private ProjectManagementSystemModel model;
    private ViewHandler viewHandler;
    private Region root;
    private ViewState state;

    public TeamMemberListViewController(){

    }

    public void init(ViewHandler viewHandler, Region root,ProjectManagementSystemModel model, ViewState state){
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.state = state;
        this.teamMemberListViewModel = new TeamMemberListViewModel(model);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPropertyProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePropertyProperty());
        teamMemberListTable.setItems(teamMemberListViewModel.getList());
    }

    public void reset(){
        nameTextField.setText("");
        errorLabel.setText("");
        teamMemberListViewModel.update();
    }

    public Region getRoot(){
        return root;
    }

    @FXML
    private void handleAddButton(){
        if (nameTextField.getText() == null || nameTextField.getText().equals("")){
            errorLabel.setText("Name cannot be empty");
            return;
        }
        TeamMember other = new TeamMember(nameTextField.getText());
        int position = (teamMemberListTable.getItems().size() == 0) ? 0 : teamMemberListTable.getItems().size() - 1;
        other.setId(Integer.toString(position));
        model.addTeamMember(other);
        errorLabel.setText("");
        nameTextField.setText("");
        teamMemberListViewModel.update();
    }


    @FXML
    private void handleRemoveButton(){
        errorLabel.setText("");
        try{
            TeamMemberViewModel selected = teamMemberListTable.getSelectionModel().getSelectedItem();
            TeamMember teamMember = new TeamMember(selected.getNameProperty());
            teamMember.setId(selected.getIdProperty());
            model.removeTeamMember(teamMember);
            teamMemberListViewModel.remove(teamMember);
            teamMemberListTable.getSelectionModel().clearSelection();
        } catch (Exception e){
            errorLabel.setText("Item not found: " + e.getMessage());
        }
    }

//    @FXML
//    private void handleEditButton(){
//        errorLabel.setText("");
//        try{
//            TeamMemberViewModel selected = teamMemberListTable.getSelectionModel().getSelectedItem();
//            nameTextField.setText(selected.getNameProperty());
//
//        }
//    }

    @FXML
    private void handleCancelButton(){
        viewHandler.openView("mainWindow");
    }

}
