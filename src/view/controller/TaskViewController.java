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
import model.GeneralTemplate;
import model.MyDate;
import model.TeamMember;
import model.TeamMemberList;
import view.ViewHandler;
import view.ViewState;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class TaskViewController
{
  @FXML private Text idText;
  @FXML private TextField titleTextField;
  @FXML private ComboBox<String> responsiblePersonComboBox;
  @FXML private ComboBox<String> teamMembersComboBox;
  @FXML private TextField estimatedTimeTextField;
  @FXML private TextField actualTimeTextField;
  @FXML private ComboBox<String> statusComboBox;
  @FXML private DatePicker deadlineDatePicker;
  @FXML private Button editButton;
  @FXML private Label errorLabel;
  @FXML private Button chooseTeamMemberButton;
  @FXML private Button cancelButton;
  @FXML private ListView listView;
  private ObservableList<TeamMember> teamMemberList;
  private ChangeListener<String> teamMemberListComboBoxListener;
  private ChangeListener<String> responsiblePersonComboBoxListener;
  private boolean editButtonClicked;

  private ProjectManagementSystemModel model;
  private Region root;
  private ViewHandler viewHandler;
  private ViewState state;

  public TaskViewController()
  {

  }

  public void init(ViewHandler viewHandler, Region root,
      ProjectManagementSystemModel model, ViewState state)
  {
    this.viewHandler = viewHandler;
    this.model = model;
    this.root = root;
    this.state = state;
    this.teamMemberList = FXCollections.observableArrayList();
    update();
    this.editButtonClicked = false;
  }

  private void update()
  {
    attributesDisability(true);
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

    if (statusComboBox.getItems().size() == 0)
    {
      statusComboBox.getItems().add(GeneralTemplate.STATUS_APPROVED);
      statusComboBox.getItems().add(GeneralTemplate.STATUS_ENDED);
      statusComboBox.getItems().add(GeneralTemplate.STATUS_NOT_STARTED);
      statusComboBox.getItems().add(GeneralTemplate.STATUS_REJECTED);
      statusComboBox.getItems().add(GeneralTemplate.STATUS_STARTED);
    }
    statusComboBox.getSelectionModel().select(model
        .getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getStatus());

    addListViewItems();
    addComboBoxListeners();
    addComboBoxItems();
  }


  private void addListViewItems() {
    TeamMemberList list = model.getTeamMemberListForRequirement(state.getSelectedProjectID(), state.getSelectedRequirementID());
    // reload ObservableList from model if changes have not been saved
    if (list != null && list.getSize() != 0 && (!editButtonClicked || editButton.getText().equals("Save"))) {
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

  public Region getRoot()
  {
    return root;
  }

  public void reset()
  {
    if (editButton.getText().equals("Save")){
      init(viewHandler, root, model, state);
    }
    editButton.setText("Edit");
    //resetComboBoxes();
    update();
  }

  public void addComboBoxItems()
  {
    // update choose team member ComboBox
    if (teamMembersComboBox.getItems().size() == 0)
    {
      for (int i = 0; i < model.getTeamMemberList().getSize(); i++)
      {
        teamMembersComboBox.getItems()
            .add(model.getTeamMemberList().getTeamMember(i).toString());
      }
      if (teamMemberList.size() != 0)
        teamMembersComboBox.getItems().removeAll(teamMemberList);
    }

    // update responsible person ComboBox
    if (responsiblePersonComboBox.getItems().size() == 0)
    {
      for (int i = 0; i < model.getTeamMemberList().getSize(); i++)
      {
        responsiblePersonComboBox.getItems()
            .add(model.getTeamMemberList().getTeamMember(i).toString());
      }
      responsiblePersonComboBox.getSelectionModel().select(model
          .getResponsiblePersonForTask(state.getSelectedProjectID(),
              state.getSelectedRequirementID(), state.getSelectedTaskID())
          .toString());
    }
  }

  public void addComboBoxListeners()
  {

    responsiblePersonComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
      if (new_val != null && !new_val.equals(old_val))
      {
        teamMembersComboBox.getItems().remove(new_val);
        if (old_val != null)
          teamMembersComboBox.getItems().add(old_val);
      }
    };

    teamMemberListComboBoxListener = (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
      if (new_val != null && !new_val.equals(old_val))
      {
        responsiblePersonComboBox.getItems().remove(new_val);
        if (old_val != null)
          responsiblePersonComboBox.getItems().add(old_val);
      }
    };

    responsiblePersonComboBox.getSelectionModel().selectedItemProperty()
        .addListener(responsiblePersonComboBoxListener);
    teamMembersComboBox.getSelectionModel().selectedItemProperty()
        .addListener(teamMemberListComboBoxListener);
  }

  public void resetComboBoxes()
  {

    if (responsiblePersonComboBoxListener != null
        && teamMemberListComboBoxListener != null)
    {
      responsiblePersonComboBox.getSelectionModel().selectedItemProperty()
          .removeListener(responsiblePersonComboBoxListener);
      teamMembersComboBox.getSelectionModel().selectedItemProperty()
          .removeListener(teamMemberListComboBoxListener);
    }

    responsiblePersonComboBox.getItems().clear();
    teamMembersComboBox.getItems().clear();
  }

  public void attributesDisability(boolean disabled)
  {
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

  @FXML private void handleCancelButton()
  {
    reset();
  }

  public void handleEditButton()
  {
    if (!editButtonClicked)
      editButtonClicked = true;
    if (editButton.getText().equals("Edit"))
    {
      editButton.setText("Save");
      attributesDisability(false);
    }
    else
    {
      editButton.setText("Edit");
      attributesDisability(true);
      listView.getSelectionModel().clearSelection();
    }
    if (titleTextField.getText() == null)
    {
      errorLabel.setText("Title is empty");
      return;
    }
    if (responsiblePersonComboBox.getValue() == null)
    {
      errorLabel.setText("Responsible person is not chosen");
      return;
    }
    if (deadlineDatePicker.getValue() == null)
    {
      errorLabel.setText("Deadline is not chosen");
      return;
    }
    if (estimatedTimeTextField.getText() == null)
    {
      errorLabel.setText("Estimated time is empty");
      return;
    }
    model.getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
        state.getSelectedRequirementID())
        .setActualTime(Integer.parseInt(actualTimeTextField.getText()));

    model.getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
        state.getSelectedRequirementID())
        .setEstimatedTime(Integer.parseInt(estimatedTimeTextField.getText()));

    model.getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
        state.getSelectedRequirementID()).setResponsiblePerson(
        new TeamMember(String.valueOf(responsiblePersonComboBox.getValue())));

    model.getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
        state.getSelectedRequirementID()).setTitle(titleTextField.getText());

    model.getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
        state.getSelectedRequirementID())
        .setStatus(String.valueOf(statusComboBox.getValue()));

    model.getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
        state.getSelectedRequirementID())
        .setDeadline(new MyDate(deadlineDatePicker.getValue()));
  }

  @FXML private void handleBackButton()
  {
    viewHandler.openView("taskList");
  }

  @FXML private void handleChooseTeamMemberButton()
  {
    String selected = teamMembersComboBox.getSelectionModel().getSelectedItem();
    if (selected == null)
    {
      errorLabel.setText("Choose team member");
      return;
    }
    teamMemberList.add(formatTeamMember(selected));
    teamMembersComboBox.getSelectionModel().clearSelection();
    teamMembersComboBox.getItems().remove(selected);
    responsiblePersonComboBox.getItems().remove(selected);
  }

  @FXML public void handleKeyPressed(KeyEvent e)
  {
    Object selected = listView.getSelectionModel().getSelectedItem();
    // check if selected item is null
    if (selected == null)
    {
      errorLabel.setText("Please select a team member");
      return;
    }
    // delete selected item from list and add back to the ComboBoxes when DELETE button is pressed
    if (e.getCode().equals(KeyCode.DELETE))
    {
      teamMemberList.remove(selected);
      model.getTeamMemberListForTask(state.getSelectedProjectID(),
          state.getSelectedRequirementID(), state.getSelectedTaskID()).remove(formatTeamMember(String.valueOf(selected)));
      teamMembersComboBox.getItems().add(String.valueOf(selected));
      responsiblePersonComboBox.getItems().add(String.valueOf(selected));
    }
  }

  private TeamMember formatTeamMember(String teamMemberString)
  {
    // format string
    teamMemberString = teamMemberString.replace("[", "");
    teamMemberString = teamMemberString.replace("]", "");
    String[] memberInfo = teamMemberString.split("\s");
    TeamMember member = new TeamMember(memberInfo[1]);
    member.setId(memberInfo[0]);
    return member;
  }
}
