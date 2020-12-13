package view.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class TaskViewController {
    @FXML
    private Text idText;
    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<String> responsiblePersonComboBox;
    @FXML
    private ComboBox<String> teamMembersComboBox;
    @FXML
    private TextField estimatedTimeTextField;
    @FXML
    private TextField actualTimeTextField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private Button editButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button chooseTeamMemberButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ListView listView;
    private ProjectManagementSystemModel model;
    private Region root;
    private ViewHandler viewHandler;
    private ViewState state;
    private ObservableList<TeamMember> teamMemberList;
    private ChangeListener<String> teamMemberListComboBoxListener;
    private ChangeListener<String> responsiblePersonComboBoxListener;
    private ArrayList<ComboBox<String>> comboBoxes;
    private boolean editButtonClicked;
    private boolean wasChanged;


    public TaskViewController() {

    }

    public void init(ViewHandler viewHandler, Region root,
                     ProjectManagementSystemModel model, ViewState state) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        this.state = state;
        this.teamMemberList = FXCollections.observableArrayList();
        update();
        this.editButtonClicked = false;
        this.wasChanged = false;
    }

    private void update() {
        attributesDisability(true);
        initComboBoxesArr();
        initFields();
        addListViewItems();
        addComboBoxListeners();
        addComboBoxItems();
    }

    private void initComboBoxesArr() {
        comboBoxes = new ArrayList<>();
        comboBoxes.add(teamMembersComboBox);
        comboBoxes.add(responsiblePersonComboBox);
    }

    private void initFields() {
        errorLabel.setText("");
        idText.setText(model
                .getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
                        state.getSelectedRequirementID()).getId());

        actualTimeTextField.setText(String.valueOf(model
                .getActualTimeForTask((state.getSelectedTaskID()),
                        state.getSelectedProjectID(), state.getSelectedRequirementID())));

        titleTextField.setText(model.getTitleForTask(state.getSelectedProjectID(),
                state.getSelectedRequirementID(), state.getSelectedTaskID()));

        estimatedTimeTextField.setText(String.valueOf(model
                .getEstimatedTimeForTask((state.getSelectedTaskID()),
                        state.getSelectedProjectID(), state.getSelectedRequirementID())));

        deadlineDatePicker.setValue(LocalDate.of(model
                .getDeadlineForTask(state.getSelectedTaskID(),
                        state.getSelectedProjectID(), state.getSelectedRequirementID())
                .getYear(), model.getDeadlineForTask(state.getSelectedTaskID(),
                state.getSelectedProjectID(), state.getSelectedRequirementID())
                .getMonth(), model.getDeadlineForTask(state.getSelectedTaskID(),
                state.getSelectedProjectID(), state.getSelectedRequirementID())
                .getDay()));

        if (statusComboBox.getItems().size() == 0) {
            statusComboBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
            statusComboBox.getItems().add(GeneralTemplate.STATUS_ENDED);
            statusComboBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
            statusComboBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
            statusComboBox.getItems().add(GeneralTemplate.STATUS_STARTED);
        }
        statusComboBox.getSelectionModel().select(model
                .getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
                        state.getSelectedRequirementID()).getStatus());
    }


    private void addListViewItems() {
        TeamMemberList list = model.getTeamMemberListForTask(state.getSelectedProjectID(), state.getSelectedRequirementID(), state.getSelectedTaskID());
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

    public Region getRoot() {
        return root;
    }

    public void reset() {
        resetComboBoxes();
        update();
        editButton.setText("Edit");
    }

    public void addComboBoxItems() {
        // update choose team member ComboBox
        teamMembersComboBox.getItems().clear();
        responsiblePersonComboBox.getItems().clear();

        TeamMemberList list = model.getTeamMemberListForRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID());
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
        teamMembersComboBox.getItems().remove(responsiblePersonInfo);
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

    public void attributesDisability(boolean disabled) {
        idText.setDisable(disabled);
        titleTextField.setDisable(disabled);
        responsiblePersonComboBox.setDisable(disabled);
        teamMembersComboBox.setDisable(disabled);
        estimatedTimeTextField.setDisable(disabled);
        actualTimeTextField.setDisable(disabled);
        statusComboBox.setDisable(disabled);
        deadlineDatePicker.setDisable(disabled);
        listView.setDisable(disabled);
        chooseTeamMemberButton.setDisable(disabled);
        cancelButton.setDisable(disabled);
    }

    @FXML
    private void handleCancelButton() {
        if (!wasChanged) {
            attributesDisability(true);
            editButton.setText("Edit");
            listView.getSelectionModel().clearSelection();
        } else reset();
    }

    public void handleEditButton() {
        if (!editButtonClicked)
            editButtonClicked = true;
        if (editButton.getText().equals("Edit")) {
            editButton.setText("Save");
            attributesDisability(false);
        } else {
            editButton.setText("Edit");
            attributesDisability(true);
            listView.getSelectionModel().clearSelection();
        }

        if (titleTextField.getText() == null) {
            errorLabel.setText("Title is empty");
            return;
        }
        if (responsiblePersonComboBox.getValue() == null) {
            errorLabel.setText("Responsible person is not chosen");
            return;
        }
        if (deadlineDatePicker.getValue() == null) {
            errorLabel.setText("Deadline is not chosen");
            return;
        }
        if (estimatedTimeTextField.getText() == null) {
            errorLabel.setText("Estimated time is empty");
            return;
        }

        // ensure actual time is an integer
        try {
            int actualTime = Integer.parseInt(actualTimeTextField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid actual time");
            return;
        }

        // ensure actual time is an integer
        try {
            int estimatedTime = Integer.parseInt(estimatedTimeTextField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid estimated time");
            return;
        }

        if (Integer.parseInt(actualTimeTextField.getText()) < 0) {
            errorLabel.setText("Actual time must be a positive number");
            return;
        }

        if (Integer.parseInt(estimatedTimeTextField.getText()) < 0) {
            errorLabel.setText("Estimated time must be a positive number");
            return;
        }

        // convert deadlineDatePicker value to MyDate object
        LocalDate deadlineLocalDate = deadlineDatePicker.getValue();
        MyDate deadline = new MyDate();
        try {
            deadline = new MyDate(deadlineLocalDate.getDayOfMonth(), deadlineLocalDate.getMonthValue(), deadlineLocalDate.getYear());
        } catch (Exception e) {
            errorLabel.setText("Invalid date");
            return;
        }

        // prevent past deadline
        if (deadline.isBefore(new MyDate())) {
            errorLabel.setText("Deadline must be after today");
            return;
        }

        setModelFromFields();
    }

    private void setModelFromFields() {
        Task task = model.getTask(state.getSelectedTaskID(), state.getSelectedProjectID(), state.getSelectedRequirementID());

        task.setActualTime(Integer.parseInt(actualTimeTextField.getText()));

        task.setEstimatedTime(Integer.parseInt(estimatedTimeTextField.getText()));

        task.setResponsiblePerson(formatTeamMember(String.valueOf(responsiblePersonComboBox.getValue())));

        task.setTitle(titleTextField.getText());

        task.setStatus(String.valueOf(statusComboBox.getValue()));

        task.setDeadline(new MyDate(deadlineDatePicker.getValue()));
    }

    @FXML
    private void handleBackButton() {
        viewHandler.openView("taskList");
    }

    @FXML
    private void handleChooseTeamMemberButton() {
        String selected = teamMembersComboBox.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Choose team member");
            return;
        }

        // add member to model and remove from all ComboBoxes
        teamMemberList.add(formatTeamMember(selected));
        for (ComboBox<String> c : comboBoxes) {
            if (c.getId().equals("chooseTeamMembersComboBox")) c.getSelectionModel().clearSelection();
            c.getItems().remove(selected);
        }
        wasChanged = true;
    }

    @FXML
    public void handleKeyPressed(KeyEvent e) {
        // check if selected item is null
        if (listView.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setText("Please select a team member");
            return;
        }

        if (e.getCode().equals(KeyCode.DELETE)) {
            deleteTeamMember(listView.getSelectionModel().getSelectedItem().toString());
            wasChanged = true;
        }
    }

    // delete selected item from list and add back to the ComboBoxes when DELETE button is pressed
    private void deleteTeamMember(String member) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Team Member");
        alert.setHeaderText("Delete Team Member: "+ member);
        alert.setContentText("Are you sure? Press Ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            teamMemberList.remove(formatTeamMember(member));
            if (editButtonClicked && editButton.getText().equals("Edit"))
                model.getTeamMemberList().addAlreadyExists(formatTeamMember(member));
            for (ComboBox<String> c : comboBoxes) {
                c.getItems().add(member);
            }
        }
    }

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
