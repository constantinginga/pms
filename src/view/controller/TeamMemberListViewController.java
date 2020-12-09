package view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.TeamMemberListViewModel;
import view.viewModel.TeamMemberViewModel;

import java.util.Optional;


public class TeamMemberListViewController {
    @FXML
    private TextField nameTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button editButton;
    @FXML
    private TableView<TeamMemberViewModel> teamMemberListTable;
    @FXML
    private TableColumn<TeamMemberViewModel, String> idColumn;
    @FXML
    private TableColumn<TeamMemberViewModel, String> nameColumn;
    private TeamMemberListViewModel teamMemberListViewModel;
    private TeamMemberViewModel selected;
    private ProjectManagementSystemModel model;
    private ViewHandler viewHandler;
    private Region root;
    private ViewState state;

    public TeamMemberListViewController() {

    }

    public void init(ViewHandler viewHandler, Region root, ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.state = state;

        this.teamMemberListViewModel = new TeamMemberListViewModel(model);
        this.selected = null;
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPropertyProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePropertyProperty());
        teamMemberListTable.setItems(teamMemberListViewModel.getList());
    }

    public void reset() {
        nameTextField.setText("");
        errorLabel.setText("");
        teamMemberListViewModel.update();
        editButton.setText("Edit");
    }

    public Region getRoot() {
        return root;
    }

    private boolean confirmation()
    {
        int index = teamMemberListTable.getSelectionModel().getSelectedIndex();
        TeamMemberViewModel selectedItem = teamMemberListTable.getItems()
                .get(index);
        if (index < 0 || index >= teamMemberListTable.getItems().size())
        {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(
                "Removing team member [" + selectedItem.getIdProperty() + "] "
                        + selectedItem.getNameProperty());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }

    @FXML
    private void handleAddButton() {
        if (nameTextField.getText() == null || nameTextField.getText().equals("")) {
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


    @FXML private void handleRemoveButton() {
        errorLabel.setText("");
        try {
            // selection of teamMember to remove
            TeamMemberViewModel selected = teamMemberListTable.getSelectionModel()
                    .getSelectedItem();
            TeamMember teamMember = new TeamMember(selected.getNameProperty());
            teamMember.setId(selected.getIdProperty());
            // removing from list
            boolean remove = confirmation();
            if (remove) {
                model.removeTeamMember(teamMember);
                teamMemberListViewModel.remove(teamMember);
                teamMemberListTable.getSelectionModel().clearSelection();
            }

        } catch (Exception e) {
            errorLabel.setText("Choose team member to remove");
        }
    }

        @FXML
    private void handleEditButton() {
        errorLabel.setText("");
        if (editButton.getText().equals("Edit")) {
            try {
                selected = teamMemberListTable.getSelectionModel().getSelectedItem();
                nameTextField.setText(selected.getNameProperty());
                editButton.setText("Save");
            } catch (Exception e) {
                errorLabel.setText("Please select a team member");
            }
        } else {
            if (nameTextField.getText() == null || nameTextField.getText().equals("")) {
                errorLabel.setText("Please enter a new name");
                return;
            }

            if (selected != null && !nameTextField.getText().equals(selected.getNameProperty())) {
                TeamMember selectedTeamMember = model.getTeamMemberList().findByName(selected.getNameProperty());
                selectedTeamMember.setName(nameTextField.getText());
                teamMemberListViewModel.changeMember(selectedTeamMember);
                teamMemberListViewModel.update();
                teamMemberListTable.getSelectionModel().clearSelection();
                nameTextField.clear();
                editButton.setText("Edit");
            }
        }
    }

    @FXML
    private void handleCancelButton() {
        viewHandler.openView("mainWindow");
    }

}
