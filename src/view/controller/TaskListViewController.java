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
    private ComboBox<String> statusComboBox = new ComboBox<>();
    @FXML
    private ComboBox<String> responsiblePersonComboBox = new ComboBox<>();
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private TextField estimatedTimeTextField;
    @FXML
    private TextField actualTimeTextField;
    @FXML
    private TextField searchBarTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button editButton;
    @FXML
    private Button chooseTeamMemberButton;
    private ViewHandler viewHandler;
    private Region root;
    private ProjectManagementSystemModel model;
    private TaskListViewModel taskListViewModel;
    private ViewState state;
    private ChangeListener<String> responsiblePersonComboBoxListener;
    private ChangeListener<String> chooseTeamMembersComboBoxListener;
    private ObservableList<String> teamMemberList;

    public TaskListViewController() {
    }

    //!!!still have to add responsiblePersonChoiceBox!!!
    //!!!add to handleCancelButton as well!!!
    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.root = root;
        this.model = model;
        this.state = state;
        this.teamMemberList = FXCollections.observableArrayList();
        this.taskListViewModel = new TaskListViewModel(model,
                state.getSelectedProjectID(),
                state.getSelectedRequirementID());
        update();
    }

    // updates attributes and table with last values and resets errorLabel
    private void update() {
        attributesDisability(true);
        errorLabel.setText("");
        userStoryTextField.setText(model
                .getUserStoryRequirement(state.getSelectedProjectID(),
                        state.getSelectedRequirementID()));
        idText.setText(model.getRequirement(state.getSelectedProjectID(),
                state.getSelectedRequirementID()).getId());
        estimatedTimeTextField.setText(String.valueOf(model
                .getEstimatedTimeForRequirement(state.getSelectedTaskID(),
                        state.getSelectedProjectID(), state.getSelectedRequirementID())));
        actualTimeTextField.setText(String.valueOf(model
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
        searchBarTextField.setText("");
        addComboBoxListeners();
        addComboBoxItems();
        search();
    }

    private void addComboBoxItems() {
        // update choose team member ComboBox
        if (chooseTeamMembersComboBox.getItems().size() == 0) {
            for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
                chooseTeamMembersComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
            }
            if (teamMemberList.size() != 0) chooseTeamMembersComboBox.getItems().removeAll(teamMemberList);
        }

        // update responsible person ComboBox
        if (responsiblePersonComboBox.getItems().size() == 0) {
            for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
                responsiblePersonComboBox.getItems().add(model.getTeamMemberList().getTeamMember(i).toString());
            }
            if (teamMemberList.size() != 0) responsiblePersonComboBox.getItems().removeAll(teamMemberList);
        }
    }

    private void addComboBoxListeners() {
        responsiblePersonComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                chooseTeamMembersComboBox.getItems().remove(new_val);
                if (old_val != null) chooseTeamMembersComboBox.getItems().add(old_val);
            }
        };

        chooseTeamMembersComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            if (new_val != null && !new_val.equals(old_val)) {
                responsiblePersonComboBox.getItems().remove(new_val);
                if (old_val != null) responsiblePersonComboBox.getItems().add(old_val);
            }
        };

        responsiblePersonComboBox.getSelectionModel().selectedItemProperty().addListener(responsiblePersonComboBoxListener);
        chooseTeamMembersComboBox.getSelectionModel().selectedItemProperty().addListener(chooseTeamMembersComboBoxListener);
    }

    private void resetComboBoxes() {
        if (responsiblePersonComboBoxListener != null && chooseTeamMembersComboBoxListener != null) {
            responsiblePersonComboBox.getSelectionModel().selectedItemProperty().removeListener(responsiblePersonComboBoxListener);
            chooseTeamMembersComboBox.getSelectionModel().selectedItemProperty().removeListener(chooseTeamMembersComboBoxListener);
        }

        responsiblePersonComboBox.getItems().clear();
        chooseTeamMembersComboBox.getItems().clear();
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
        // if save button has not been clicked, remove items from listview
        if (editButton.getText().equals("Save")) {
            if (teamMemberList.size() != 0) {
                String selected = chooseTeamMembersComboBox.getSelectionModel().getSelectedItem();
                if (selected != null) teamMemberList.remove(selected);
                for (int i = 0; i < teamMemberList.size(); i++) {
                    chooseTeamMembersComboBox.getItems().add(teamMemberList.get(i));
                    responsiblePersonComboBox.getItems().add(teamMemberList.get(i));
                    model.getTeamMemberListForRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID()).remove(formatTeamMember(teamMemberList.get(i)));
                }
            }
        }
        listView.setItems(teamMemberList);
        taskListViewModel.update();
        editButton.setText("Edit");
        resetComboBoxes();
        update();
    }


    // opens selected task
    @FXML
    private void handleOpenTaskButton() {
        state.setSelectedTaskID(
                taskListTable.getSelectionModel().getSelectedItem().getIdProperty());
        viewHandler.openView("taskView");
    }

    @FXML
    private void handleAddTaskButton() {
        // if list is empty, set id for next project to 1
        state.setSelectedTaskID(Integer.toString(taskListTable.getItems().size() + 1));
        viewHandler.openView("addTask");
    }

    @FXML
    private void handleBackButton() {
        viewHandler.openView("reqList");
    }

    //sets attributes to be editable
    public void attributesDisability(boolean disabled) {
        userStoryTextField.setDisable(disabled);
        idText.setDisable(disabled);
        statusComboBox.setDisable(disabled);
        responsiblePersonComboBox.setDisable(disabled);
        deadlineDatePicker.setDisable(disabled);
        estimatedTimeTextField.setDisable(disabled);
        actualTimeTextField.setDisable(disabled);
        listView.setDisable(disabled);
        chooseTeamMembersComboBox.setDisable(disabled);
        chooseTeamMemberButton.setDisable(disabled);
    }

    public void handleEditButton() {
        if (editButton.getText().equals("Edit")) {
            editButton.setText("Save");
            attributesDisability(false);
        } else {
            editButton.setText("Edit");
            attributesDisability(true);
        }
        if (userStoryTextField.getText() == null) {
            errorLabel.setText("User story is empty");
            return;
        }
        if (estimatedTimeTextField.getText() == null) {
            errorLabel.setText("Estimated time is empty");
            return;
        }
        if (actualTimeTextField.getText() == null) {
            errorLabel.setText("Actual time is empty");
            return;
        }
        model.getRequirement(state.getSelectedRequirementID(),
                state.getSelectedProjectID()).setTitle(userStoryTextField.getText());
        model.getRequirement(state.getSelectedRequirementID(),
                state.getSelectedProjectID())
                .setStatusForRequirement(statusComboBox.getValue());
        model.getRequirement(state.getSelectedRequirementID(),
                state.getSelectedProjectID()).setResponsiblePerson(
                new TeamMember(responsiblePersonComboBox.getValue()));
        model.getRequirement(state.getSelectedRequirementID(),
                state.getSelectedProjectID())
                .setDeadline(new MyDate(deadlineDatePicker.getValue()));
        model.getRequirement(state.getSelectedRequirementID(),
                state.getSelectedProjectID())
                .setEstimatedTime(Integer.parseInt(estimatedTimeTextField.getText()));
        model.getRequirement(state.getSelectedRequirementID(),
                state.getSelectedProjectID())
                .setActualTime(Integer.parseInt(actualTimeTextField.getText()));
    }

    //resets values for requirement's attributes to last values before editing
    public void handleCancelButton() {
        attributesDisability(true);
        editButton.setText("Edit");
        update();
    }

    //removes chosen task from taskList
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
                //taskListTable.getSelectionModel().clearSelection();
                taskListTable.getItems().remove(selectedItem);
            }
        } catch (Exception e) {
            errorLabel.setText("Choose task to remove");
        }
    }

    private boolean confirmation() {
        int index = taskListTable.getSelectionModel().getSelectedIndex();
        TaskViewModel selectedItem = taskListTable.getItems().get(index);
        if (index < 0 || index >= taskListTable.getItems().size()) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
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
        String selected = chooseTeamMembersComboBox.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Please select a team member");
            return;
        }
        teamMemberList.add(selected);
        listView.setItems(teamMemberList);
        model.getTeamMemberListForRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID()).add(formatTeamMember(selected));
        chooseTeamMembersComboBox.getSelectionModel().clearSelection();
        chooseTeamMembersComboBox.getItems().remove(selected);
        responsiblePersonComboBox.getItems().remove(selected);
    }

    @FXML
    public void handleKeyPressed(KeyEvent e) {
        String selected = chooseTeamMembersComboBox.getSelectionModel().getSelectedItem();
        // check if selected item is null
        if (selected == null) {
            errorLabel.setText("Please selected a team member");
            return;
        }
        // delete selected item from list and add back to the ComboBoxes when DELETE button is pressed
        if (e.getCode().equals(KeyCode.DELETE)) {
            teamMemberList.remove(selected);
            model.getTeamMemberListForRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID()).remove(formatTeamMember(selected));
            chooseTeamMembersComboBox.getItems().add(selected);
            responsiblePersonComboBox.getItems().add(selected);
        }
    }

    // return new team member from string
    private TeamMember formatTeamMember(String teamMemberString) {
        // format string
        teamMemberString = teamMemberString.replace("[", "");
        teamMemberString = teamMemberString.replace("]", "");
        String[] memberInfo = teamMemberString.split("\s");
        TeamMember member = new TeamMember(memberInfo[1]);
        member.setId(memberInfo[0]);
        return member;
    }
}
