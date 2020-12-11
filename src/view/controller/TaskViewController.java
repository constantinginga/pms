package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import mediator.ProjectManagementSystemModel;
import model.GeneralTemplate;
import model.MyDate;
import model.TeamMember;
import view.ViewHandler;
import view.ViewState;
import javafx.scene.control.TextField;
import view.viewModel.TeamMemberListViewModel;
import view.viewModel.TeamMemberViewModel;

import java.time.LocalDate;

public class TaskViewController
{
  @FXML private Text idText;
  @FXML private TextField titleTextField;
  @FXML private ComboBox<String> responsiblePersonComboBox = new ComboBox();
  @FXML private ComboBox<String> teamMembersComboBox = new ComboBox();
  @FXML private TextField estimatedTimeTextField;
  @FXML private TextField actualTimeTextField;
  @FXML private ComboBox<String> statusComboBox = new ComboBox();
  @FXML private DatePicker deadlineDatePicker;
  @FXML private Button editButton;
  @FXML private Label errorLabel;
  @FXML private Button chooseTeamMemberButton;
  private TeamMemberListViewModel teamMemberListViewModel;
  @FXML private ListView listView;
  private ObservableList<String> teamMemberList = FXCollections
      .observableArrayList();

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
    listView.setItems(teamMemberList);
    errorLabel.setText("");

    this.teamMemberListViewModel = new TeamMemberListViewModel(model);
    update();
  }

  public void update()
  {
    errorLabel.setText("");
    idText.setText(model
        .getTask(state.getSelectedTaskID(), state.getSelectedProjectID(),
            state.getSelectedRequirementID()).getId());

    responsiblePersonComboBox.setValue(model
        .getResponsiblePersonForTask(state.getSelectedProjectID(),
            state.getSelectedRequirementID(), state.getSelectedTaskID())
        .getName());

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
    if (teamMembersComboBox.getItems().size() == 0)
    {
      for (int i = 0; i < model.getTeamMemberList().getSize(); i++)
      {
        teamMembersComboBox.getItems().add(String.valueOf(model
            .getTeamMemberListForTask(state.getSelectedProjectID(),
                state.getSelectedRequirementID(), state.getSelectedTaskID())
            .getSize()));
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
    teamMemberListViewModel.update();
    editButton.setText("Edit");
    listView.setItems(teamMemberList);
    update();
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
  }

  @FXML private void handleCancelButton()
  {
    attributesDisability(true);
    editButton.setText("Edit");
    update();
  }

  public void handleEditButton()
  {
    if (editButton.getText().equals("Edit"))
    {
      editButton.setText("Save");
      attributesDisability(false);
    }
    else
    {
      editButton.setText("Edit");
      attributesDisability(true);
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
    teamMemberList.add(selected);
    listView.setItems(teamMemberList);
    model.getTeamMemberListForTask(state.getSelectedProjectID(),
        state.getSelectedRequirementID(), state.getSelectedTaskID())
        .add(formatTeamMember(selected));
    teamMembersComboBox.getSelectionModel().clearSelection();
    teamMembersComboBox.getItems().remove(selected);
    responsiblePersonComboBox.getItems().remove(selected);
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
