package view.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import view.viewModel.TaskListViewModel;
import view.viewModel.TaskViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class TaskListViewController {
    @FXML
    private TableView<TaskViewModel> taskListTable;
    @FXML
    private TableColumn<TaskViewModel, String> idColumn;
    @FXML
    private TableColumn<TaskViewModel, String> titleColumn;
    @FXML
    private TableColumn<TaskViewModel, String> statusColumn;
    @FXML
    private TableColumn<TaskViewModel, String> responsiblePersonColumn;
    @FXML
    private TableColumn<TaskViewModel, String> deadlineColumn;
    @FXML
    private TableColumn<TaskViewModel, Number> estimatedTimeColumn;
    @FXML
    private ListView listView;
    @FXML
    private ComboBox<String> chooseTeamMembersComboBox;
    @FXML
    private TextField userStoryTextField;
    @FXML
    private Text idText;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private ComboBox<String> responsiblePersonComboBox;
    @FXML
    private ComboBox<String> functionalityComboBox;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private TextField estimatedTimeTextField;
    @FXML
    private Text actualTimeText;
    @FXML
    private TextField searchBarTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button chooseTeamMemberButton;
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private TaskListViewModel taskListViewModel;
    private ViewState state;
    private ObservableList<TeamMember> teamMemberList;
    private boolean editButtonClicked;
    private ArrayList<ComboBox<String>> comboBoxes;

    public TaskListViewController() {
    }


    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        this.teamMemberList = FXCollections.observableArrayList();
        this.taskListViewModel = new TaskListViewModel(model,
                state.getSelectedProjectID(), state.getSelectedRequirementID());
        update();
        this.editButtonClicked = false;
    }

    // store all ComboBoxes in ArrayList
    private void initComboBoxesArr() {
        comboBoxes = new ArrayList<>();
        comboBoxes.add(chooseTeamMembersComboBox);
        comboBoxes.add(responsiblePersonComboBox);
    }

    private void initFields() {
        errorLabel.setText("");
        searchBarTextField.setText("");
        chooseTeamMembersComboBox.setPromptText("Choose team member");

        if (functionalityComboBox.getItems().size() == 0) {
            functionalityComboBox.getItems().add("Functional");
            functionalityComboBox.getItems().add("Non-functional");
        }
        final String selectedFunc = (model.getRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID()).isFunctional()) ? "Functional" : "Non-functional";
        functionalityComboBox.getSelectionModel().select(selectedFunc);

        userStoryTextField.setText(model
                .getUserStoryRequirement(state.getSelectedProjectID(),
                        state.getSelectedRequirementID()));

        idText.setText(model.getRequirement(state.getSelectedProjectID(),
                state.getSelectedRequirementID()).getId());

        estimatedTimeTextField.setText(String.valueOf(model
                .getEstimatedTimeForRequirement(state.getSelectedTaskID(),
                        state.getSelectedProjectID(), state.getSelectedRequirementID())));

        actualTimeText.setText(String.valueOf(model
                .getActualTimeForRequirement(state.getSelectedProjectID(),
                        state.getSelectedRequirementID())));

        deadlineDatePicker.setValue(LocalDate.of(model
                .getDeadlineForRequirement(state.getSelectedProjectID(),
                        state.getSelectedRequirementID()).getYear(), model
                .getDeadlineForRequirement(state.getSelectedProjectID(),
                        state.getSelectedRequirementID()).getMonth(), model
                .getDeadlineForRequirement(state.getSelectedProjectID(),
                        state.getSelectedRequirementID()).getDay()));

        if (statusComboBox.getItems().size() == 0) {
            statusComboBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
            statusComboBox.getItems().add(GeneralTemplate.STATUS_ENDED);
            statusComboBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
            statusComboBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
            statusComboBox.getItems().add(GeneralTemplate.STATUS_STARTED);
        }

        statusComboBox.getSelectionModel().select(model
                .getRequirement(state.getSelectedProjectID(),
                        state.getSelectedRequirementID()).getStatus());
    }

    private void initTable() {
        idColumn.setCellValueFactory(
                cellData -> cellData.getValue().idPropertyProperty());
        titleColumn.setCellValueFactory(
                cellData -> cellData.getValue().titlePropertyProperty());
        statusColumn.setCellValueFactory(
                cellData -> cellData.getValue().statusPropertyProperty());
        responsiblePersonColumn.setCellValueFactory(cellData -> cellData.getValue()
                .responsiblePersonNamePropertyProperty());
        deadlineColumn.setCellValueFactory(
                cellData -> cellData.getValue().deadlinePropertyProperty());
        estimatedTimeColumn.setCellValueFactory(
                cellData -> cellData.getValue().estimatedTimePropertyProperty());
        taskListTable.setItems(taskListViewModel.getList());
    }

    // updates attributes and table with last values and resets errorLabel
    private void update() {
        attributesDisability(true);
        initComboBoxesArr();
        initFields();
        initTable();
        addListViewItems();
        addComboBoxListeners();
        addComboBoxItems();
        search();
    }

    private void addListViewItems() {
        TeamMemberList list = model.getTeamMemberListForRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID());
        // reload ObservableList from model if changes have not been saved
        if (list != null && (!editButtonClicked || editButton.getText().equals("Save") || list.isWasRemoved())) {
            teamMemberList.clear();
            for (int i = 0; i < list.getSize(); i++) {
                teamMemberList.add(list.getTeamMember(i));
            }
        }

        // if changes have been saved, reload model from ObservableList
        if (editButton.getText().equals("Edit") && editButtonClicked && list != null) {
            list.removeAll();
            for (TeamMember t : teamMemberList) {
                list.addAlreadyExists(t);
            }
        }

        listView.setItems(teamMemberList);
    }

    private void addComboBoxItems() {
        // update choose team member ComboBox
        chooseTeamMembersComboBox.getItems().clear();
        responsiblePersonComboBox.getItems().clear();

        TeamMemberList list = model.getTeamMemberListForProject(state.getSelectedProjectID());
        for (int i = 0; i < list.getSize(); i++) {
            for (ComboBox<String> c : comboBoxes) {
                c.getItems().add(list.getTeamMember(i).toString());
            }
        }

        // remove current team members from ComboBoxes
        if (teamMemberList.size() != 0) {
            for (TeamMember t : teamMemberList) {
                for (ComboBox<String> c : comboBoxes) {
                    c.getItems().remove(t.toString());
                }
            }
        }

        // set responsible person
        String responsiblePersonInfo = model.getResponsiblePersonForRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID()).toString();
        responsiblePersonComboBox.getSelectionModel().select(responsiblePersonInfo);
        chooseTeamMembersComboBox.getItems().remove(responsiblePersonInfo);
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

    private void search() {

        FilteredList<TaskViewModel> filteredList = new FilteredList<>(
                taskListViewModel.getList(), b -> true);
        searchBarTextField.textProperty()
                .addListener(((observableValue, oldValue, newValue) -> {
                    filteredList.setPredicate(requirement -> {
                        if (newValue == null || newValue.isEmpty())
                            return true;
                        String lowerCaseFilter = newValue.toLowerCase();

                        return requirement.getIdProperty().toLowerCase()
                                .contains(lowerCaseFilter) || requirement.getTitleProperty()
                                .toLowerCase().contains(lowerCaseFilter) || requirement
                                .getStatusProperty().toLowerCase().contains(lowerCaseFilter);
                    });
                }));
    }

    public Region getRoot() {
        return root;
    }

    // resets scene
    public void reset() {
        this.taskListViewModel = new TaskListViewModel(model,
                state.getSelectedProjectID(), state.getSelectedRequirementID());
        taskListViewModel.update();
        resetComboBoxes();
        update();
        editButton.setText("Edit");
    }

    @FXML
    private void handleOpenTaskButton() {
        // set state of task and try to open it
        try {
            state.setSelectedTaskID(
                    taskListTable.getSelectionModel().getSelectedItem().getIdProperty());
            viewHandler.openView("taskView");
        } catch (Exception e) {
            errorLabel.setText("Choose task to open");
        }
    }

    @FXML
    private void handleAddTaskButton() {
        // if list is empty, set id for next project to 1
        state.setSelectedTaskID(
                Integer.toString(taskListTable.getItems().size() + 1));
        viewHandler.openView("addTask");
    }

    @FXML
    private void handleBackButton() {
        viewHandler.openView("reqList");
    }

    //sets attributes to be editable
    public void attributesDisability(boolean disabled) {
        cancelButton.setDisable(disabled);
        userStoryTextField.setDisable(disabled);
        idText.setDisable(disabled);
        statusComboBox.setDisable(disabled);
        responsiblePersonComboBox.setDisable(disabled);
        deadlineDatePicker.setDisable(disabled);
        estimatedTimeTextField.setDisable(disabled);
        actualTimeText.setDisable(disabled);
        listView.setDisable(disabled);
        chooseTeamMembersComboBox.setDisable(disabled);
        chooseTeamMemberButton.setDisable(disabled);
        functionalityComboBox.setDisable(disabled);
    }

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

        if (userStoryTextField.getText() == null) {
            errorLabel.setText("User story is empty");
            return;
        }

        if (estimatedTimeTextField.getText() == null) {
            errorLabel.setText("Estimated time is empty");
            return;
        }

        if (actualTimeText.getText() == null) {
            errorLabel.setText("Actual time is empty");
            return;
        }

        // convert deadlineDatePicker value to MyDate object
        LocalDate deadlineLocalDate = deadlineDatePicker.getValue();
        MyDate deadline = new MyDate();
        try {
            deadline = new MyDate(deadlineLocalDate.getDayOfMonth(), deadlineLocalDate.getMonthValue(), deadlineLocalDate.getYear());
        } catch (Exception e) {
            errorLabel.setText("Invalid date");
        }

        // prevent past deadline
        if (deadline.isBefore(new MyDate())) {
            errorLabel.setText("Deadline must be after today");
            return;
        }

        setModelFromFields();
    }

    // change values in model to values from fields
    private void setModelFromFields() {
        Requirement req = model.getRequirement(state.getSelectedRequirementID(), state.getSelectedRequirementID());
        req.setTitle(userStoryTextField.getText());
        req.setStatusForRequirement(statusComboBox.getValue());
        req.setResponsiblePerson(formatTeamMember(responsiblePersonComboBox.getValue()));
        req.setDeadline(new MyDate(deadlineDatePicker.getValue()));
        req.setEstimatedTime(Integer.parseInt(estimatedTimeTextField.getText()));
        req.setActualTime(Integer.parseInt(actualTimeText.getText()));
        req.setFunctional(functionalityComboBox.getSelectionModel().getSelectedItem().equals("Functional"));
    }

    // resets values for requirement's attributes to last values before editing
    public void handleCancelButton() {
        reset();
    }

    // removes chosen task from taskList
    public void handleRemoveTaskButton() {
        errorLabel.setText("");
        try {
            TaskViewModel selectedItem = taskListTable.getSelectionModel()
                    .getSelectedItem();

            boolean remove = confirmation();
            if (remove) {
                Task removeTask = new Task(selectedItem.getTitleProperty(),
                        new TeamMember(selectedItem.getResponsiblePersonNameProperty()),
                        selectedItem.getEstimatedTimeProperty(),
                        new MyDate(selectedItem.getDayProperty(),
                                selectedItem.getMonthProperty(),
                                selectedItem.getYearProperty()));
                model.removeTask(removeTask, state.getSelectedProjectID(),
                        state.getSelectedRequirementID());
                taskListViewModel.remove(removeTask);
                taskListTable.getItems().remove(selectedItem);
            }
        } catch (Exception e) {
            errorLabel.setText("Select task to remove");
        }
    }

    private boolean confirmation() {
        int index = taskListTable.getSelectionModel().getSelectedIndex();
        TaskViewModel selectedItem = taskListTable.getItems().get(index);
        if (index >= taskListTable.getItems().size()) return false;

        // create alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");

        // set alert message
        alert.setHeaderText(
                "Removing task [" + selectedItem.getIdProperty() + "] " + selectedItem
                        .getTitleProperty() + ": " + selectedItem.getStatusProperty() + ": "
                        + selectedItem.getResponsiblePersonNameProperty() + ": "
                        + selectedItem.getDeadlineProperty() + ": " + selectedItem
                        .getEstimatedTimeProperty());
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }

    @FXML
    public void handleChooseTeamMemberButton() {
        String selected = chooseTeamMembersComboBox.getSelectionModel()
                .getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Please select a team member");
            return;
        }

        // add member to model and remove from all ComboBoxes
        teamMemberList.add(formatTeamMember(selected));
        for (ComboBox<String> c : comboBoxes) {
            if (c.getId().equals("chooseTeamMembersComboBox")) c.getSelectionModel().clearSelection();
            c.getItems().remove(selected);
        }
    }

    // delete TeamMember from ListView
    @FXML
    public void handleKeyPressed(KeyEvent e) {
        // check if selected item is null
        if (listView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Please selected a team member");
            return;
        }
        TeamMember selected = formatTeamMember(listView.getSelectionModel().getSelectedItem().toString());
        // delete selected item from list and add back to the ComboBoxes when DELETE button is pressed
        if (e.getCode().equals(KeyCode.DELETE)) {
            teamMemberList.remove(selected);
            if (editButtonClicked && editButton.getText().equals("Edit"))
                model.getTeamMemberList().addAlreadyExists(selected);
            for (ComboBox<String> c : comboBoxes) {
                c.getItems().add(selected.toString());
            }
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