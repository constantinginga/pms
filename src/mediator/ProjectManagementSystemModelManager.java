package mediator;

import model.*;

import parser.XmlJsonParser;

import java.io.File;

public class ProjectManagementSystemModelManager
    implements ProjectManagementSystemModel
{

  private ProjectList projectList;
  private TeamMemberList teamMemberList;
  private ProjectManagementSystemModel projectManagementSystemModel;
  private ProjectManagementSystemFile fileSaving;
  private XmlJsonParser parser = new XmlJsonParser();

  public ProjectManagementSystemModelManager()
  {
    this.projectList = new ProjectList();
    this.teamMemberList = new TeamMemberList();
    this.fileSaving = new ProjectManagementSystemFile(projectList,
        teamMemberList);
    this.projectList = fileSaving.readProjects();
    this.teamMemberList = fileSaving.readTeamMemberList();
    teamMemberList.remove(teamMemberList.findByName("boblauching"));
  }

  @Override public Requirement getRequirement(int index, String ProjectId)
  {
    return projectList.getProject(ProjectId).getRequirement(index);
  }

  @Override public void addProject(Project project)
  {
    projectList.add(project);
    fileSaving.saveToFile(projectList);
  }

  @Override public void removeProject(Project project)
  {
    projectList.remove(project);
    fileSaving.saveToFile(projectList);
  }

  @Override public void removeProject(String projectID)
  {
    projectList.remove((Project) projectList.findById(projectID));
    fileSaving.saveToFile(projectList);
  }

  @Override public void addTeamMember(TeamMember teammember)
  {
    teamMemberList.add(teammember);
    fileSaving.saveToFile(teamMemberList);
  }

  @Override public TeamMemberList getTeamMemberList()
  {
    return teamMemberList;
  }

  @Override public void removeTeamMember(TeamMember teammember)
  {
    teamMemberList.remove(teammember);
    fileSaving.saveToFile(teamMemberList);
  }

  @Override public void removeTeamMember(String ID)
  {
    teamMemberList.remove(teamMemberList.findById(ID));
    fileSaving.saveToFile(teamMemberList);
  }

  @Override public void addRequirement(Requirement requirement,
      String ProjectId)
  {
    projectList.getProject(ProjectId).addRequirement(requirement);
    fileSaving.saveToFile(projectList);
  }

  @Override public void removeRequirement(Requirement requirement,
      String ProjectId)
  {
    projectList.getProject(ProjectId).removeRequirement(requirement);
    fileSaving.saveToFile(projectList);
  }

  @Override public void removeRequirement(String requirementID,
      String projectID)
  {
    projectList.getProject(projectID).removeRequirement(
        projectList.getProject(projectID).getRequirement(requirementID));
    fileSaving.saveToFile(projectList);
  }

  @Override public void addTask(Task task, String projectID,
      String requirementID)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .addTask(task);
    fileSaving.saveToFile(projectList);
  }

  @Override public void removeTask(Task task, String projectID,
      String requirementID)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .removeTask(task);
    fileSaving.saveToFile(projectList);
  }

  //*******************EDIT FOR LATER***************************************
  @Override public GeneralTemplate findById(String id)
  {
    return null;
  }

  @Override public GeneralTemplate findByTitle(String title)
  {
    return null;
  }

  @Override public Requirement findByResponsiblePerson(TeamMember teamMember)
  {
    return null;
  }

  //***********************************************************************
  @Override public Project getProject(String projectID)
  {
    return projectList.getProject(projectID);
  }

  @Override public Requirement getRequirement(String requirementID,
      String ProjectId)
  {
    return projectList.getProject(ProjectId).getRequirement(requirementID);
  }

  @Override public Task getTask(String taskID, String projectID,
      String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(taskID);
  }

  @Override public void setProjectCreator(TeamMember teamMember,
      String projectID)
  {
    projectList.getProject(projectID).setProjectCreator(teamMember);
    fileSaving.saveToFile(projectList);
  }

  @Override public void setScrumMaster(TeamMember teamMember, String projectID)
  {
    projectList.getProject(projectID).setScrumMaster(teamMember);
    fileSaving.saveToFile(projectList);
  }

  @Override public void setProductOwner(TeamMember teamMember, String projectID)
  {
    projectList.getProject(projectID).setProductOwner(teamMember);
    fileSaving.saveToFile(projectList);
  }

  @Override public String getTitleForTask(String projectID,
      String requirementID, String TaskID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(TaskID).getTitle();
  }

  @Override public String getTitleForProject(String projectID)
  {
    return projectList.getProject(projectID).getTitle();
  }

  @Override public String getUserStoryRequirement(String projectID,
      String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getUserStory();
  }

  @Override public void setStatusForTask(String status, String projectID,
      String requirementID, String taskID)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(taskID).setStatus(status);
    fileSaving.saveToFile(projectList);
  }

  @Override public void setStatusForRequirement(String status, String projectID,
      String requirementID)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .setStatusForRequirement(status);
    fileSaving.saveToFile(projectList);

  }

  @Override public void setStatusForProject(String status, String projectID)
  {
    projectList.getProject(projectID).setStatusForProject(status);
    fileSaving.saveToFile(projectList);
  }

  @Override public MyDate getDeadlineForTask(String taskID, String projectID,
      String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(taskID).getDeadline();
  }

  @Override public MyDate getDeadlineForRequirement(String projectID,
      String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getDeadline();
  }

  @Override public void setDeadlineForTask(String taskID, String projectID,
      String requirementID, MyDate deadline)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(taskID).setDeadline(deadline);
    fileSaving.saveToFile(projectList);
  }

  @Override public void setDeadlineForRequirement(String projectID,
      String requirementID, MyDate deadline)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .setDeadline(deadline);
    fileSaving.saveToFile(projectList);
  }

  @Override public int getActualTimeForTask(String taskID, String projectID,
      String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(taskID).getActualTime();
  }

  @Override public int getActualTimeForRequirement(String projectID,
      String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(projectID)
        .getTaskList().getActualTimeForAllTasks();
  }

  @Override public void setActualTimeForTask(String taskID, String projectID,
      String requirementID, int actualTime)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(taskID).setActualTime(actualTime);
    fileSaving.saveToFile(projectList);
  }

  @Override public int getEstimatedTimeForTask(String taskID, String projectID,
      String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(taskID).getEstimatedTime();
  }

  @Override public int getEstimatedTimeForRequirement(String taskID,
      String projectID, String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getEstimatedTime();

  }

  @Override public void setEstimatedTimeForTask(String taskID, String projectID,
      String requirementID, int estimatedTime)
  {
    projectList.getProject(projectID).getRequirement(requirementID).getTask(taskID).setEstimatedTime(estimatedTime);
    fileSaving.saveToFile(projectList);
  }

  @Override public void setEstimatedTimeForRequirement(String taskID,
      String projectID, String requirementID, int estimatedTime)
  {
    projectList.getProject(projectID).getRequirement(requirementID).setEstimatedTime(estimatedTime);
    fileSaving.saveToFile(projectList);
  }

  @Override public TeamMemberList getTeamMemberListForProject(String projectID)
  {
    return projectList.getProject(projectID).getMembers();
  }

  @Override public TeamMemberList getTeamMemberListForRequirement(
      String projectID, String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getMembers();
  }

  @Override public TeamMemberList getTeamMemberListForTask(String projectID,
      String requirementID, String TaskID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(TaskID).getMembers();

  }

  @Override public TeamMember getResponsiblePersonForTask(String projectID,
      String requirementID, String taskID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(taskID).getResponsiblePerson();

  }

  @Override public TeamMember getResponsiblePersonForRequirement(
      String projectID, String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getResponsiblePerson();

  }

  @Override public void setResponsiblePersonForTask(TeamMember teamMember,
      String projectID, String requirementID, String TaskID)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .getTask(TaskID).setResponsiblePerson(teamMember);
    fileSaving.saveToFile(projectList);


  }

  @Override public void setResponsiblePersonForRequirement(
      TeamMember teamMember, String projectID, String requirementID)
  {
    projectList.getProject(projectID).getRequirement(requirementID)
        .setResponsiblePerson(teamMember);
    fileSaving.saveToFile(projectList);


  }

  @Override public String getUserStory(String projectID, String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getUserStory();
  }

  @Override public TaskList getTaskList(String projectID, String requirementID)
  {
    return projectList.getProject(projectID).getRequirement(requirementID)
        .getTaskList();
  }

  @Override public ProjectList getProjectList()
  {
    return projectList;
  }

  @Override public RequirementList getRequirementList(String projectID)
  {
    return projectList.getProject(projectID).getRequirementList();
  }

  @Override public String getName(String teamMemberID)
  {
    return teamMemberList.findById(teamMemberID).getName();
  }

  @Override public void setName(String teamMemberID, String name)
  {
    teamMemberList.findById(teamMemberID).setName(name);
    fileSaving.saveToFile(projectList);


  }

}
