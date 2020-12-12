package view.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.GeneralTemplate;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.*;

import java.util.Optional;

/**
 *
 *RequirementListviewController class.
 *
 */

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
    @FXML private ChoiceBox<String> statusChoiceBox ;
    @FXML private ComboBox<String> projectCreatorChoiceBox;
    @FXML private ComboBox<String> productOwnerChoiceBox;
    @FXML private ComboBox<String> scrumMasterChoiceBox;
    @FXML private TextField noteTextField;
    @FXML private TextField searchBarTextField;
    @FXML private Button editButton;
    @FXML private Button addteamMember;
    @FXML private ComboBox<String> chooseTeamMemberComboBox;
    @FXML private ListView<String> listView;


    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;
    private RequirementListViewModel requirementListViewModel;

    private ObservableList<String> teamObs;

    /**
     *
     *Empty Constructs
     *
     */
    public RequirementListViewController()
    {
    }

    /**
     * initializers
     * @param viewHandler
     * @param root
     * @param model
     * @param state
     */
    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state){

        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        this.requirementListViewModel = new RequirementListViewModel(model,
                state.getSelectedProjectID());
        teamObs = FXCollections.observableArrayList();
        update();

    }

    //necessary to implement ProjectCreator, ScrumMaster and ProductOwner choiceBoxes
    //also Note

    /**
     * update methode
     *
     */
    public void update()
    {
        errorLabel.setText("");
        projectTitleTextField
                .setText(model.getTitleForProject(state.getSelectedProjectID()));
        projectID.setText(model.getProject(state.getSelectedProjectID()).getId());
        if (statusChoiceBox.getItems().size() == 0){
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_ENDED);
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_STARTED);
        }
        statusChoiceBox.getSelectionModel()
                .select(model.getProject(state.getSelectedProjectID()).getStatus());
        searchBarTextField.setText("");

        if (noteTextField.getText() != null){
            noteTextField.setText(model.getNote(state.getSelectedProjectID()));
        }


        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPropertyProperty());
        userStoryColumn.setCellValueFactory(
                cellData -> cellData.getValue().userStoryPropertyProperty());
        statusColumn.setCellValueFactory(
                cellData -> cellData.getValue().statusPropertyProperty());
        deadLineColumn.setCellValueFactory(
                cellData -> cellData.getValue().deadLinePropertyProperty());

        requirementListTable.setItems(requirementListViewModel.getReqList());

        search();

        chooseTeamMemberComboBox.setPromptText("Choose team member");

        addTeamsToComboBox();
        scrumMasterChoiceBox.setPromptText(model.getProject(state.getSelectedProjectID()).getScrumMaster().toString());
        productOwnerChoiceBox.setPromptText(model.getProject(state.getSelectedProjectID()).getProductOwner().toString());
        projectCreatorChoiceBox.setPromptText(model.getProject(state.getSelectedProjectID()).getProjectCreator().toString());
        updateObservableListForTeam();
        updateChooseTeamMemberComboBox();

    }


    private void updateObservableListForTeam(){
        teamObs.clear();
        for (int i = 0; i<model.getTeamMemberListForProject(state.getSelectedProjectID()).getSize(); i++){
            teamObs.add(model.getTeamMemberListForProject(state.getSelectedProjectID()).getTeamMember(i).toString());
        }
        listView.setItems(teamObs);

    }

    /**
     * update requirement observable list
     * set text to empty
     */


    public void reset() {
        requirementListViewModel.update();
        projectID.setText("");
        projectTitleTextField.setText("");
        editButton.setText("Edit");
        update();

    }

    /**
     *add developers to project and show it in list view.
     */
    public void addTeamMemberButton(){

        if(chooseTeamMemberComboBox.getSelectionModel().getSelectedItem() != null){
            teamObs.add(chooseTeamMemberComboBox.getSelectionModel().getSelectedItem());
            String s = chooseTeamMemberComboBox.getSelectionModel().getSelectedItem();
            chooseTeamMemberComboBox.getItems().remove(s);
            scrumMasterChoiceBox.getItems().remove(s);
            productOwnerChoiceBox.getItems().remove(s);
            projectCreatorChoiceBox.getItems().remove(s);
        }
        listView.setItems(teamObs);
    }

    private void updateChooseTeamMemberComboBox(){
        for (String s: listView.getItems()){
            chooseTeamMemberComboBox.getItems().remove(s);
            scrumMasterChoiceBox.getItems().remove(s);
            productOwnerChoiceBox.getItems().remove(s);
            projectCreatorChoiceBox.getItems().remove(s);
        }
    }

    /**
     * add team member to Combo box to chose from.
     */

    private void addTeamsToComboBox(){
        for (int i = 0; i<model.getTeamMemberListForProject(state.getSelectedProjectID()).getSize(); i++){
            projectCreatorChoiceBox.getItems().add(model.getTeamMemberListForProject(state.getSelectedProjectID()).getTeamMember(i).toString());
            productOwnerChoiceBox.getItems().add(model.getTeamMemberListForProject(state.getSelectedProjectID()).getTeamMember(i).toString());
            scrumMasterChoiceBox.getItems().add(model.getTeamMemberListForProject(state.getSelectedProjectID()).getTeamMember(i).toString());
            chooseTeamMemberComboBox.getItems().add(model.getTeamMemberListForProject(state.getSelectedProjectID()).getTeamMember(i).toString());

        }
    }

    /**
     *delete team member from the list view by pressing delete button
     * @param event
     */
    @FXML
    public void handleKeyPressed(KeyEvent event){
        String teamMember = listView.getSelectionModel().getSelectedItem();
        if (teamMember != null){
            if (event.getCode().equals(KeyCode.DELETE)){
                deleteTeamMember(teamMember);
                teamObs.remove(teamMember);
            }
        }
    }

    /**
     * delete team member dialog window, press ok to delete or cancel to back out.
     * @param team
     */
    public void deleteTeamMember(String team){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Team Member");
        alert.setHeaderText("Delete Team Member: "+ team);
        alert.setContentText("Are you sure? Press Ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            teamObs.remove(team);
            model.getTeamMemberListForProject(state.getSelectedProjectID()).remove(new TeamMember(team));
            chooseTeamMemberComboBox.getItems().add(team);
            scrumMasterChoiceBox.getItems().add(team);
            projectCreatorChoiceBox.getItems().add(team);
            productOwnerChoiceBox.getItems().add(team);

        }
    }

    /**
     * search method that find team member by id, userStory or status.
     */
    private void search(){

        FilteredList<RequirementViewModel> filteredList = new FilteredList<>(requirementListViewModel.getReqList(), b -> true);
        searchBarTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(requirement -> {
                if(newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();

                return requirement.getUserStoryProperty().toLowerCase().contains(lowerCaseFilter) ||
                        requirement.getIdProperty().toLowerCase().contains(lowerCaseFilter) ||
                        requirement.getStatusProperty().toLowerCase().contains(lowerCaseFilter);
            });
        }));

        SortedList<RequirementViewModel> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(requirementListTable.comparatorProperty());
        requirementListTable.setItems(sortedList);
    }


    /**
     *
     * @return  RequirementListView.fxml
     */

    public Region getRoot(){
        return root;
    }


    /**
     * open task requirement details and task list related to the requirement.
     */

    @FXML private void handleOpenRequirementButton(){
        RequirementViewModel selectedItem = requirementListTable.getSelectionModel()
                .getSelectedItem();
        state.setSelectedRequirementID(selectedItem.getIdProperty());
        viewHandler.openView("taskList");
    }

    /**
     * add new requirement to requirement list to related project.
     */


    @FXML private void handleAddRequirementButton(){
        viewHandler.openView("addReq");

    }

    /**
     * back to project list window.
     */


    @FXML private void handleBackButton(){
        state.setSelectedProjectID("");
        viewHandler.openView("mainWindow");
    }


    /**
     * remove requirement form related project.
     */


    @FXML private void handleRemoveRequirementButton()
    {
        try
        {
            RequirementViewModel selectedItem = requirementListTable
                    .getSelectionModel().getSelectedItem();
            boolean remove = confirmation();
            if (remove)
            {
                String reqID = selectedItem.getIdProperty();
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


    /**
     * confirmation dialog window to confirm removing requirement or back out.
     * @return true if ok button pressed or false if cancel button pressed.
     */


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
                "Remove requirement \n" + selectedItem.getIdProperty() + ":"
                        + selectedItem.getUserStoryProperty() + ".");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent() && (result.get() == ButtonType.OK));
    }

    /**
     * if edit button pressed change the button text to save button and
     *
     */


    public void handleEditButton() {
        if (editButton.getText().equals("Save")){
        if (noteTextField.getText() != null){
            model.getProject(state.getSelectedProjectID())
                    .setNote(noteTextField.getText());
        }
        model.getProject(state.getSelectedProjectID())
                .set(projectTitleTextField.getText());
        model.getProject(state.getSelectedProjectID())
                .setProductOwner(new TeamMember(productOwnerChoiceBox.getValue()));
        model.getProject(state.getSelectedProjectID())
                .setProjectCreator(new TeamMember(projectCreatorChoiceBox.getValue()));
        model.getProject(state.getSelectedProjectID())
                .setScrumMaster(new TeamMember(scrumMasterChoiceBox.getValue()));
        model.setStatusForProject(statusChoiceBox.getValue(), state.getSelectedProjectID());
    }

        if (editButton.getText().equals("Edit")){
            editButton.setText("Save");
            attributesDisability(false);
        } else {
            editButton.setText("Edit");
            attributesDisability(true);
        }
        if (projectTitleTextField.getText() == null)
        {
            errorLabel.setText("Title is empty");
            return;
        }

    }

    /**
     *
     *
     * @param disabled
     */

    public void attributesDisability(boolean disabled)
    {
        projectID.setDisable(disabled);
        productOwnerChoiceBox.setDisable(disabled);
        projectCreatorChoiceBox.setDisable(disabled);
        projectTitleTextField.setDisable(disabled);
        errorLabel.setText("");
        scrumMasterChoiceBox.setDisable(disabled);
        noteTextField.setDisable(disabled);
        statusChoiceBox.setDisable(disabled);
        listView.setDisable(disabled);
        chooseTeamMemberComboBox.setDisable(disabled);
        addteamMember.setDisable(disabled);
    }

    /**
     * back disable the field and not allowing you change it.
     */
    public void handleCancelButton()
    {
        attributesDisability(true);
        editButton.setText("Edit");
    }

}

