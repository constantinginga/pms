package mediator;

import model.*;

public interface ProjectManagementSystemModel
{

  public abstract void addProject(Project project);
  public abstract  void setTitleForProject(String title, String ProjectID);
  public abstract void removeProject(Project project);
  public abstract void removeProject(String projectID);

  public abstract void addTeamMember(TeamMember teammember);

  TeamMemberList getTeamMemberList();

  void removeTeamMember(TeamMember teammember);
  public abstract void removeTeamMember(String ID);

  public abstract void addRequirement(Requirement requirement,
      String ProjectId);

  // get requirement by index
  public abstract Requirement getRequirement(int index, String ProjectId);

  //remove requirement by id
  public abstract void removeRequirement(String id, String ProjectId);

  public abstract void removeRequirement(Requirement requirement,
      String ProjectId);

  public abstract void addTask(Task task, String projectID,
      String requirementID);
  public abstract void removeTask(Task task, String projectID,
      String requirementID);

  public abstract GeneralTemplate findById(String id);

  public abstract GeneralTemplate findByTitle(String title);

  public abstract Requirement findByResponsiblePerson(TeamMember teamMember);
  public abstract Project getProject(String projectID);
  public abstract Requirement getRequirement(String requirementID,
      String ProjectId);
  public abstract Task getTask(String taskID, String projectID,
      String requirementID);
  public abstract Task getTask(int index, String projectId, String requirementId);
  public abstract void setProjectCreator(TeamMember teamMember,
      String projectID);
  public abstract void setScrumMaster(TeamMember teamMember, String projectID);
  public abstract void setProductOwner(TeamMember teamMember, String projectID);

  public abstract String getTitleForTask(String projectID, String requirementID,
      String TaskID);
  public abstract String getTitleForProject(String projectID);
  public abstract String getUserStoryRequirement(String projectID,
      String requirementID);

  public abstract void setStatusForTask(String status, String projectID,
      String requirementID, String taskID);
  public abstract void setStatusForRequirement(String status, String projectID,
      String requirementID);
  public abstract void setStatusForProject(String status, String projectID);

  public abstract MyDate getDeadlineForTask(String taskID, String projectID,
      String requirementID);
  public abstract MyDate getDeadlineForRequirement(String projectID,
      String requirementID);

  public abstract void setDeadlineForTask(String taskID, String projectID,
      String requirementID, MyDate deadline);
  public abstract void setDeadlineForRequirement(String projectID,
      String requirementID, MyDate deadline);

  public abstract int getActualTimeForTask(String taskID, String projectID,
      String requirementID);
  public abstract int getActualTimeForRequirement(String projectID,
      String requirementID);

  public abstract void setActualTimeForTask(String taskID, String projectID,
      String requirementID, int actualTime);

  public abstract int getEstimatedTimeForTask(String taskID, String projectID,
      String requirementID);
  public abstract int getEstimatedTimeForRequirement(String taskID,
      String projectID, String requirementID);

  public abstract void setEstimatedTimeForTask(String taskID, String projectID,
      String requirementID, int estimatedTime);
  public abstract void setEstimatedTimeForRequirement(String taskID,
      String projectID, String requirementID, int estimatedTime);

  public abstract TeamMemberList getTeamMemberListForProject(String projectID);
  public abstract TeamMemberList getTeamMemberListForRequirement(
      String projectID, String requirementID);
  public abstract TeamMemberList getTeamMemberListForTask(String projectID,
      String requirementID, String TaskID);
  public abstract TeamMember getResponsiblePersonForTask(String projectID,
      String requirementID, String taskID);
  public abstract TeamMember getResponsiblePersonForRequirement(
      String projectID, String requirementID);

  public abstract void setResponsiblePersonForTask(TeamMember teamMember,
      String projectID, String requirementID, String TaskID);
  public abstract void setResponsiblePersonForRequirement(TeamMember teamMember,
      String projectID, String requirementID);
  public abstract void addTeamMemberForTask(TeamMember teamMember,
      String projectID, String requirementID, String TaskID);
  public abstract void addTeamMemberForRequirement(TeamMember teamMember,
      String projectID, String requirementID, String TaskID);

  public abstract String getUserStory(String projectID, String requirementID);
  public abstract TaskList getTaskList(String projectID, String requirementID);
  public abstract ProjectList getProjectList();
  public abstract RequirementList getRequirementList(String projectID);
  public abstract String getName(String teamMemberID);

  public abstract void setName(String teamMemberID, String name);
  public abstract String getNote(String projectID);

}
