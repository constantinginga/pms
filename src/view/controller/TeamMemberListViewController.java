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
        this.root = root;
        this.model = model;
        this.state = state;
        this.teamMemberListViewModel = new TeamMemberListViewModel(model);
        this.selected = null;
        nameTextField.setPromptText("Enter name");
        initTable();
    }

    private void initTable() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPropertyProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePropertyProperty());
        teamMemberListTable.setItems(teamMemberListViewModel.getList());
    }

    public void reset() {
        nameTextField.setText("");
        nameTextField.setPromptText("Enter name");
        nameTextField.requestFocus();
        errorLabel.setText("");
        teamMemberListViewModel.update();
        editButton.setText("Edit");
    }

    public Region getRoot() {
        return root;
    }

    private boolean confirmation() {
        int index = teamMemberListTable.getSelectionModel().getSelectedIndex();
        TeamMemberViewModel selectedItem = teamMemberListTable.getItems().get(index);

        if (index >= teamMemberListTable.getItems().size()) return false;
        // create alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        // set alert message
        alert.setHeaderText(
                "Removing team member [" + selectedItem.getIdProperty() + "] "
                        + selectedItem.getNameProperty());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }

    @FXML
    private void handleAddButton() {
        // check for empty name
        if (nameTextField.getText() == null || nameTextField.getText().equals("")) {
            errorLabel.setText("Please enter a name");
            return;
        }

        // prevent user from adding a new team member when he's already editing an existing one
        if (editButton.getText().equals("Save")) {
            errorLabel.setText("Please save your changes first");
            return;
        }

        // check for valid name
        TeamMember other;
        try {
            other = new TeamMember(nameTextField.getText());
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            return;
        }

        // determine the id of the newly created TeamMember according to table size
        int position = (teamMemberListTable.getItems().size() == 0) ? 0 : teamMemberListTable.getItems().size() - 1;
        other.setId(Integer.toString(position));
        // add new TeamMember to model and reset
        model.addTeamMember(other);
        reset();
    }


    @FXML
    private void handleRemoveButton() {
        errorLabel.setText("");

        if (editButton.getText().equals("Save")) {
            errorLabel.setText("Please save your changes first");
            return;
        }

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
                reset();
            }
        } catch (Exception e) {
            errorLabel.setText("Select team member to remove");
        }
    }

    @FXML
    private void handleEditButton() {
        errorLabel.setText("");
        if (editButton.getText().equals("Edit")) {
            // get selected TeamMember
            try {
                selected = teamMemberListTable.getSelectionModel().getSelectedItem();
                nameTextField.setText(selected.getNameProperty());
                editButton.setText("Save");
            } catch (Exception e) {
                errorLabel.setText("Please select a team member");
            }
        } else {
            // if nameTextField is left empty
            if (nameTextField.getText() == null || nameTextField.getText().equals("")) {
                errorLabel.setText("Please enter a new name");
                return;
            }

            // if no changes were made, reset
            if (nameTextField.getText().equals(selected.getNameProperty())) {
                reset();
                return;
            }

            // if name was changed, add to model and reset
            if (selected != null && !nameTextField.getText().equals(selected.getNameProperty())) {
                TeamMember selectedTeamMember = model.getTeamMemberList().findByName(selected.getNameProperty());
                try {
                    selectedTeamMember.setName(nameTextField.getText());
                } catch (IllegalArgumentException e) {
                    errorLabel.setText(e.getMessage());
                    return;
                }
                teamMemberListViewModel.changeMember(selectedTeamMember);
                reset();
            }
        }
    }

    @FXML
    private void handleBackButton() {
        viewHandler.openView("mainWindow");
    }
}
