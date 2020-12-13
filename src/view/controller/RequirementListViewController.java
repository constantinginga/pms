package view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.*;
import view.ViewHandler;
import view.ViewState;
import view.viewModel.*;

import java.util.ArrayList;
import java.util.Optional;

/**
 * RequirementListviewController class.
 */

public class RequirementListViewController {

    @FXML
    private TableView<RequirementViewModel> requirementListTable;
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
    private Text projectID;
    @FXML
    private Label errorLabel;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private ComboBox<String> projectCreatorChoiceBox;
    @FXML
    private ComboBox<String> productOwnerChoiceBox;
    @FXML
    private ComboBox<String> scrumMasterChoiceBox;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private TextField searchBarTextField;
    @FXML
    private Button editButton;
    @FXML
    private Button chooseTeamMemberButton;
    @FXML
    private ComboBox<String> chooseTeamMemberComboBox;
    @FXML
    private ListView listView;
    @FXML
    private Button cancelButton;
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private ViewState state;
    private RequirementListViewModel requirementListViewModel;
    private ObservableList<TeamMember> teamObs;
    private ArrayList<ComboBox<String>> comboBoxes;
    private boolean editButtonClicked;

    /**
     * Empty Constructs
     */
    public RequirementListViewController() {
    }

    /**
     * initializers
     *
     * @param viewHandler
     * @param root
     * @param model
     * @param state
     */
    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {

        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        teamObs = FXCollections.observableArrayList();
        this.requirementListViewModel = new RequirementListViewModel(model,
                state.getSelectedProjectID());
        update();
        this.editButtonClicked = false;
    }

    // store all ComboBoxes in ArrayList
    private void initComboBoxesArr() {
        comboBoxes = new ArrayList<>();
        comboBoxes.add(scrumMasterChoiceBox);
        comboBoxes.add(productOwnerChoiceBox);
        comboBoxes.add(projectCreatorChoiceBox);
        comboBoxes.add(chooseTeamMemberComboBox);
    }

    private void initFields() {
        errorLabel.setText("");
        chooseTeamMemberComboBox.setPromptText("Choose team member");
        searchBarTextField.setText("");

        projectTitleTextField
                .setText(model.getTitleForProject(state.getSelectedProjectID()));
        projectID.setText(model.getProject(state.getSelectedProjectID()).getId());

        if (statusChoiceBox.getItems().size() == 0) {
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_ENDED);
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
            statusChoiceBox.getItems().add(GeneralTemplate.STATUS_STARTED);
        }
        statusChoiceBox.getSelectionModel()
                .select(model.getProject(state.getSelectedProjectID()).getStatus());

        if (noteTextArea.getText() != null) {
            noteTextArea.setText(model.getNote(state.getSelectedProjectID()));
        }
    }

    private void initTable() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPropertyProperty());
        userStoryColumn.setCellValueFactory(
                cellData -> cellData.getValue().userStoryPropertyProperty());
        statusColumn.setCellValueFactory(
                cellData -> cellData.getValue().statusPropertyProperty());
        deadLineColumn.setCellValueFactory(
                cellData -> cellData.getValue().deadLinePropertyProperty());

        requirementListTable.setItems(requirementListViewModel.getReqList());
    }

    /**
     * update methode
     */
    public void update() {
        attributesDisability(true);
        initComboBoxesArr();
        initFields();
        initTable();
        addListViewItems();
        addComboBoxListeners();
        addComboBoxItems();
        search();
    }

    /**
     * update requirement observable list
     * set text to empty
     */


    public void reset() {
        this.requirementListViewModel = new RequirementListViewModel(model,
                state.getSelectedProjectID());
        requirementListViewModel.update();
        resetComboBoxes();
        update();
        editButton.setText("Edit");

    }

    /**
     * add team member to Combo box to chose from.
     */
    public void addComboBoxItems() {
        // update choose team member ComboBox
        for (ComboBox<String> c : comboBoxes) {
            c.getItems().clear();
        }

        TeamMemberList list = model.getTeamMemberList();
        for (int i = 0; i < list.getSize(); i++) {
            for (ComboBox<String> c : comboBoxes) {
                c.getItems().add(list.getTeamMember(i).toString());
            }
        }

        // remove current team members from ComboBoxes
        if (teamObs.size() != 0) {
            for (TeamMember t : teamObs) {
                for (ComboBox<String> c : comboBoxes) {
                    c.getItems().remove(t.toString());
                }
            }
        }

        // set ChoiceBoxes for roles
        for (ComboBox<String> c : comboBoxes) {
            setRoleComboBoxes(c.getId());
        }
    }

    private void setRoleComboBoxes(String comboBox) {
        String info = switch (comboBox) {
            case "projectCreatorChoiceBox" -> model.getProject(state.getSelectedProjectID()).getProjectCreator().toString();
            case "scrumMasterChoiceBox" -> model.getProject(state.getSelectedProjectID()).getScrumMaster().toString();
            case "productOwnerChoiceBox" -> model.getProject(state.getSelectedProjectID()).getProductOwner().toString();
            default -> "";
        };

        for (ComboBox<String> c : comboBoxes) {
            // skip for chooseTeamMemberComboBox (avoid bug of triggering default case of switch and adding empty string to all ComboBoxes)
            if (c.getId().equals("chooseTeamMemberComboBox")) continue;
            // add TeamMember to correct ChoiceBox
            if (c.getId().equals(comboBox)) c.getSelectionModel().select(info);
                // remove TeamMember from all other ChoiceBoxes
            else c.getItems().remove(info);
        }
    }

    /**
     * add developers to project and show it in list view.
     */
    private void addListViewItems() {
        TeamMemberList list = model.getTeamMemberListForProject(state.getSelectedProjectID());
        // reload ObservableList from model if changes have not been saved
        if (list != null && (!editButtonClicked || editButton.getText().equals("Save") || list.isWasRemoved())) {
            teamObs.clear();
            for (int i = 0; i < list.getSize(); i++) {
                teamObs.add(list.getTeamMember(i));
            }
        }

        // if changes have been saved, reload model from ObservableList
        if (editButton.getText().equals("Edit") && editButtonClicked && list != null) {
            list.removeAll();
            for (TeamMember t : teamObs) {
                list.addAlreadyExists(t);
            }
        }

        listView.setItems(teamObs);
    }

    // prevents the selection of the same member for more than one ComboBox
    private ChangeListener<String> createListener(String comboBoxType) {

        // make copy of comboBoxes ArrayList
        ArrayList<ComboBox<String>> temp = new ArrayList<>(comboBoxes);

        // remove the ComboBox for which to create listener from list
        temp.removeIf(c -> c.getId().equals(comboBoxType));


        // return listener
        return (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            for (ComboBox<String> c : temp) {
                if (new_val != null && !new_val.equals(old_val)) {
                    // remove selected value from all other ComboBoxes
                    c.getItems().remove(new_val);
                    if (old_val != null) c.getItems().add(old_val);
                }
            }
        };
    }

    // add listeners to ComboBoxes
    private void addComboBoxListeners() {
        for (ComboBox<String> c : comboBoxes) {
            c.getSelectionModel().selectedItemProperty().addListener(createListener(c.getId()));
        }
    }

    // remove all ComboBox items
    private void resetComboBoxes() {
        for (ComboBox<String> c : comboBoxes) {
            c.getItems().clear();
        }
    }

    /**
     * delete team member from the list view by pressing delete button
     *
     * @param event
     */
    @FXML
    public void handleKeyPressed(KeyEvent event) {
        // check if selected item is null
        if (listView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Please select a team member");
            return;
        }

        String teamMember = listView.getSelectionModel().getSelectedItem().toString();
        if (event.getCode().equals(KeyCode.DELETE)) deleteTeamMember(teamMember);
    }

    /**
     * delete team member dialog window, press ok to delete or cancel to back out.
     *
     * @param team
     */
    public void deleteTeamMember(String team) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Team Member");
        alert.setHeaderText("Delete Team Member: " + team);
        alert.setContentText("Are you sure? Press Ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            teamObs.remove(formatTeamMember(team));
            if (editButtonClicked && editButton.getText().equals("Edit"))
                model.getTeamMemberList().addAlreadyExists(formatTeamMember(team));
            for (ComboBox<String> c : comboBoxes) {
                c.getItems().add(team);
            }
        }
    }

    /**
     * search method that find team member by id, userStory or status.
     */
    private void search() {

        FilteredList<RequirementViewModel> filteredList = new FilteredList<>(requirementListViewModel.getReqList(), b -> true);
        searchBarTextField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(requirement -> {
                if (newValue == null || newValue.isEmpty()) return true;
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
     * @return RequirementListView.fxml
     */

    public Region getRoot() {
        return root;
    }


    /**
     * open task requirement details and task list related to the requirement.
     */

    @FXML
    private void handleOpenRequirementButton() {
        // set state of requirement and try to open it
        try {
            state.setSelectedRequirementID(requirementListTable.getSelectionModel()
                    .getSelectedItem().getIdProperty());
            viewHandler.openView("taskList");
        } catch (Exception e) {
            errorLabel.setText("Choose requirement to open");
        }
    }

    /**
     * add new requirement to requirement list to related project.
     */


    @FXML
    private void handleAddRequirementButton() {
        // determine the id of the newly created Requirement according to table size
        state.setSelectedRequirementID(
                Integer.toString(requirementListTable.getItems().size() + 1));
        viewHandler.openView("addReq");

    }

    /**
     * back to project list window.
     */


    @FXML
    private void handleBackButton() {
        state.setSelectedProjectID("");
        viewHandler.openView("mainWindow");
    }


    /**
     * remove requirement form related project.
     */


    @FXML
    private void handleRemoveRequirementButton() {
        try {
            RequirementViewModel selectedItem = requirementListTable
                    .getSelectionModel().getSelectedItem();
            boolean remove = confirmation();
            if (remove) {
                String reqID = selectedItem.getIdProperty();
                model.removeRequirement(reqID, state.getSelectedProjectID());
                requirementListViewModel.remove(reqID);
                requirementListTable.getItems().remove(selectedItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * confirmation dialog window to confirm removing requirement or back out.
     *
     * @return true if ok button pressed or false if cancel button pressed.
     */


    private boolean confirmation() {
        int index = requirementListTable.getSelectionModel().getFocusedIndex();
        RequirementViewModel selectedItem = requirementListTable.getItems()
                .get(index);
        requirementListTable.getItems().get(index);
        if (index >= requirementListTable.getItems().size())
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
     */


    public void handleEditButton() {
        if (!editButtonClicked) editButtonClicked = true;

        // if button text is "Edit", disable fields and change button text to "Save"
        if (editButton.getText().equals("Edit")) {
            editButton.setText("Save");
            attributesDisability(false);
        } else {
            editButton.setText("Edit");
            attributesDisability(true);
            listView.getSelectionModel().clearSelection();
        }

        if (projectTitleTextField.getText() == null) {
            errorLabel.setText("Title is empty");
            return;
        }

        setModelFromFields();
    }

    // change values in model from value in fields
    private void setModelFromFields() {
        Project project = model.getProject(state.getSelectedProjectID());

        project.setNote(noteTextArea.getText());
        project.set(projectTitleTextField.getText());
        project.setProductOwner(formatTeamMember(productOwnerChoiceBox.getValue()));
        project.setProjectCreator(formatTeamMember(projectCreatorChoiceBox.getValue()));
        project.setScrumMaster(formatTeamMember(scrumMasterChoiceBox.getValue()));
        model.setStatusForProject(statusChoiceBox.getValue(), state.getSelectedProjectID());
    }

    /**
     * @param disabled
     */

    public void attributesDisability(boolean disabled) {
        projectID.setDisable(disabled);
        productOwnerChoiceBox.setDisable(disabled);
        projectCreatorChoiceBox.setDisable(disabled);
        projectTitleTextField.setDisable(disabled);
        errorLabel.setText("");
        scrumMasterChoiceBox.setDisable(disabled);
        noteTextArea.setDisable(disabled);
        statusChoiceBox.setDisable(disabled);
        listView.setDisable(disabled);
        chooseTeamMemberComboBox.setDisable(disabled);
        chooseTeamMemberButton.setDisable(disabled);
        cancelButton.setDisable(disabled);
    }

    /**
     * back disable the field and not allowing you change it.
     */
    public void handleCancelButton() {
        reset();
    }

    @FXML
    private void handleChooseTeamMemberButton() {
        String selected = chooseTeamMemberComboBox.getSelectionModel()
                .getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Please select a team member");
            return;
        }

        // add member to model and remove from all ComboBoxes
        teamObs.add(formatTeamMember(selected));
        for (ComboBox<String> c : comboBoxes) {
            if (c.getId().equals("chooseTeamMemberComboBox")) c.getSelectionModel().clearSelection();
            c.getItems().remove(selected);
        }
    }

    // return new team member from string
    private TeamMember formatTeamMember(String teamMemberString) {
        // format string
        teamMemberString = teamMemberString.replace("[", "");
        teamMemberString = teamMemberString.replace("]", "");
        String[] memberInfo = teamMemberString.split("\s", 2);
        TeamMember member = new TeamMember(memberInfo[1]);
        member.setId(memberInfo[0]);
        return member;
    }
}

