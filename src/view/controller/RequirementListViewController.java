package view.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.GeneralTemplate;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.ProjectViewModel;
import view.viewModel.RequirementListViewModel;
import view.viewModel.RequirementViewModel;

import java.util.Optional;

public class RequirementListViewController
{

  @FXML private TableView<RequirementViewModel> requirementListTable;
  @FXML private TableColumn<RequirementViewModel, String> idColumn;
  @FXML private TableColumn<RequirementViewModel, String> userStoryColumn;
  @FXML private TableColumn<RequirementViewModel, String> statusColumn;
  @FXML private TableColumn<RequirementViewModel, String> deadLineColumn;
  @FXML private TextField projectTitleTextField;
  @FXML private Text projectID;
  @FXML private Label errorLabel;
  @FXML private ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();
  @FXML private ChoiceBox<String> projectCreatorChoiceBox = new ChoiceBox<>();
  @FXML private ChoiceBox<String> productOwnerChoiceBox = new ChoiceBox<>();
  @FXML private ChoiceBox<String> scrumMasterChoiceBox = new ChoiceBox<>();
  @FXML private TextField noteTextField;
  @FXML private TextField searchBarTextField;
  @FXML private Button editButton;

  private ViewHandler viewHandler;
  private Region root;
  private ProjectManagementSystemModel model;
  private ViewState state;
  private RequirementListViewModel requirementListViewModel;

  public RequirementListViewController()
  {
  }

  public void init(ViewHandler viewHandler, Region root,
      ProjectManagementSystemModel model, ViewState state)
  {
    this.viewHandler = viewHandler;
    this.root = root;
    this.model = model;
    this.state = state;
    this.editButton = new Button("edit");
    this.requirementListViewModel = new RequirementListViewModel(model,
        state.getSelectedProjectID());
    update();
  }

  //necessary to implement ProjectCreator, ScrumMaster and ProductOwner choiceBoxes
  //also Note
  public void update()
  {
    errorLabel.setText("");
    projectTitleTextField
        .setText(model.getTitleForProject(state.getSelectedProjectID()));
    projectID.setText(model.getProject(state.getSelectedProjectID()).getId());
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_ENDED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
    statusChoiceBox.getItems().add(GeneralTemplate.STATUS_STARTED);
    statusChoiceBox.getSelectionModel()
        .select(model.getProject(state.getSelectedProjectID()).getStatus());
    searchBarTextField.setText("");
    noteTextField.setText(model.getNote(state.getSelectedProjectID()));

    idColumn
        .setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
    userStoryColumn.setCellValueFactory(
        cellData -> cellData.getValue().getUserStoryProperty());
    statusColumn.setCellValueFactory(
        cellData -> cellData.getValue().getStatusProperty());
    deadLineColumn.setCellValueFactory(
        cellData -> cellData.getValue().getDeadLineProperty());

    requirementListTable.setItems(requirementListViewModel.getReqList());
    search();
  }

  private void search(){

    FilteredList<RequirementViewModel> filteredList = new FilteredList<>(requirementListViewModel.getReqList(), b -> true);
    searchBarTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
      filteredList.setPredicate(requirement -> {
        if(newValue == null || newValue.isEmpty()) return true;
        String lowerCaseFilter = newValue.toLowerCase();

        return requirement.getUserStoryProperty().get().toLowerCase().contains(lowerCaseFilter) ||
                requirement.getIdProperty().get().toLowerCase().contains(lowerCaseFilter) ||
                requirement.getStatusProperty().get().toLowerCase().contains(lowerCaseFilter);
      });
    }));

    SortedList<RequirementViewModel> sortedList = new SortedList<>(filteredList);
    sortedList.comparatorProperty().bind(requirementListTable.comparatorProperty());
    requirementListTable.setItems(sortedList);
  }

  public Region getRoot()
  {
    return root;
  }

  public void reset()
  {
    requirementListViewModel.update();
    projectID.setText("");
    projectTitleTextField.setText("");
    update();
  }

  @FXML private void handleOpenRequirementButton()
  {
    RequirementViewModel selectedItem = requirementListTable.getSelectionModel()
        .getSelectedItem();
    state.setSelectedRequirementID(selectedItem.getIdProperty().get());
    viewHandler.openView("taskList");
  }

  @FXML private void handleAddRequirementButton()
  {
    viewHandler.openView("addReq");
  }

  @FXML private void handleBackButton()
  {
    state.setSelectedProjectID("");
    viewHandler.openView("mainWindow");
  }

  @FXML private void handleRemoveRequirementButton()
  {
    try
    {
      RequirementViewModel selectedItem = requirementListTable
          .getSelectionModel().getSelectedItem();
      boolean remove = confirmation();
      if (remove)
      {
        String reqID = selectedItem.getIdProperty().get();
        model.removeRequirement(reqID, state.getSelectedProjectID());
        requirementListViewModel.remove(reqID);
        requirementListTable.getItems().remove(selectedItem);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private boolean confirmation()
  {
    int index = requirementListTable.getSelectionModel().getFocusedIndex();
    RequirementViewModel selectedItem = requirementListTable.getItems()
        .get(index);
    requirementListTable.getItems().get(index);
    if (index < 0 || index >= requirementListTable.getItems().size())
      return false;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation");
    alert.setHeaderText(
        "Remove requirement \n" + selectedItem.getIdProperty().get() + ":"
            + selectedItem.getUserStoryProperty().get() + ".");
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent() && (result.get() == ButtonType.OK));
  }

  public void handleEditButton()
  {
    attributesVisibility(true);
    editButton.setText("Save");
    if (projectTitleTextField.getText() == null)
    {
      errorLabel.setText("Title is empty");
      return;
    }
    model.getProject(state.getSelectedProjectID())
        .setNote(noteTextField.getText());
    model.getProject(state.getSelectedProjectID())
        .set(projectTitleTextField.getText());
    model.getProject(state.getSelectedProjectID())
        .setProductOwner(new TeamMember(productOwnerChoiceBox.getValue()));
    model.getProject(state.getSelectedProjectID())
        .setProjectCreator(new TeamMember(projectCreatorChoiceBox.getValue()));
    model.getProject(state.getSelectedProjectID())
        .setScrumMaster(new TeamMember(scrumMasterChoiceBox.getValue()));
    model.getProject(state.getSelectedProjectID())
        .setStatusForProject(statusChoiceBox.getValue());
  }

  public void attributesVisibility(boolean visible)
  {
    projectID.setVisible(false);
    productOwnerChoiceBox.setVisible(false);
    projectCreatorChoiceBox.setVisible(false);
    projectTitleTextField.setVisible(false);
    errorLabel.setText("");
    scrumMasterChoiceBox.setVisible(false);
    noteTextField.setVisible(false);
  }

  public void handleCancelButton()
  {
    init(viewHandler, root, model, state);
  }

  public void handleEditTeamMembersButton()
  {
    viewHandler.openView("proTeamMember");
  }
}
