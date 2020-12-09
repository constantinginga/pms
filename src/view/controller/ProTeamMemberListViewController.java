package view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import mediator.ProjectManagementSystemModel;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.TeamMemberListViewModel;
import view.viewModel.TeamMemberViewModel;

public class ProTeamMemberListViewController
{
  @FXML private TableView<TeamMemberViewModel> proTeamMemberListTable;
  @FXML private Label errorLabel;
  @FXML private TableColumn<TeamMemberViewModel, String> idColumn;
  @FXML private TableColumn<TeamMemberViewModel, String> nameColumn;

  private TeamMemberListViewModel proTeamMemberListViewModel;
  private ProjectManagementSystemModel model;
  private Region root;
  private ViewState state;
  private ViewHandler viewHandler;

  public ProTeamMemberListViewController()
  {

  }

  public void init(ViewHandler viewHandler, Region root,
      ProjectManagementSystemModel model, ViewState state)
  {
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    this.state = state;
    this.proTeamMemberListViewModel = new TeamMemberListViewModel(model);
    idColumn.setCellValueFactory(
        cellData -> cellData.getValue().idPropertyProperty());
    nameColumn.setCellValueFactory(
        cellData -> cellData.getValue().namePropertyProperty());
    proTeamMemberListTable.setItems(proTeamMemberListViewModel.getList());
  }

  public void reset()
  {
    errorLabel.setText("");
    proTeamMemberListViewModel.update();
  }

  public Region getRoot()
  {
    return root;
  }

  @FXML private void handleSelectButton()
  {
    TeamMember other = new TeamMember(
        proTeamMemberListTable.getSelectionModel().getSelectedItem()
            .getNameProperty());
    other.setId(proTeamMemberListTable.getSelectionModel().getSelectedItem()
        .getIdProperty());
    model.getProject(state.getSelectedProjectID()).getMembers().add(other);
  }

  @FXML private void handleCancelButton()
  {
    viewHandler.openView("addProject");
  }
}
