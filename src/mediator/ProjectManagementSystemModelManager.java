package mediator;

import model.*;

public class ProjectManagementSystemModelManager
    implements ProjectManagementSystemModel
{

  private ProjectList projectList;
  private TeamMemberList teamMemberList;
  private ProjectManagementSystemModel projectManagementSystemModel;

  public ProjectManagementSystemModelManager()
  {
    this.projectList = new ProjectList();
    this.teamMemberList = new TeamMemberList();
  }

  @Override public void addProject(Project project)
  {
    projectList.add(project);
  }

  @Override public void removeProject(Project project)
  {
    projectList.remove(project);
  }

  @Override public void addTeamMember(TeamMember teammember)
  {
    teamMemberList.add(teammember);
  }

  @Override public void removeTeamMember(TeamMember teammember)
  {
    teamMemberList.remove(teammember);
  }

  @Override public void addRequirement(Requirement requirement, Project project)
  {
    projectList.getProject(projectList.getIndex(project)).getRequirementList()
        .add(requirement);
  }

  @Override public void addRequirement(Requirement requirement,
      int indexOfProject)
  {
    projectList.getProject(indexOfProject).getRequirementList()
        .add(requirement);
  }

  @Override public void removeRequirement(Requirement requirement,
      int indexOfProject)
  {
    projectList.getProject(indexOfProject).getRequirementList()
        .remove(requirement);
  }

  @Override public void removeRequirement(Requirement requirement,
      Project project)
  {
    projectList.getProject(projectList.getIndex(project)).getRequirementList()
        .remove(requirement);
  }

  @Override public void addTask(Task task, int indexOfProject,
      int indexOfRequirement)
  {
    projectList.getProject(indexOfProject).getRequirementList()
        .getRequirement(indexOfRequirement).getTaskList().add(task);
  }

  @Override public void addTask(Task task, Project project,
      Requirement requirement)
  {
    projectList.getProject(projectList.getIndex(project)).getRequirementList()
        .getRequirement(projectList.getProject(projectList.getIndex(project))
            .getRequirementList().getIndex(requirement)).getTaskList()
        .add(task);
  }

  @Override public void removeTask(Task task, int indexOfProject,
      int indexOfRequirement)
  {
    projectList.getProject(indexOfProject).getRequirementList()
        .getRequirement(indexOfRequirement).getTaskList().add(task);
  }

  @Override public void removeTask(Task task, Project project,
      Requirement requirement)
  {
    projectList.getProject(projectList.getIndex(project)).getRequirementList()
        .getRequirement(projectList.getProject(projectList.getIndex(project))
            .getRequirementList().getIndex(requirement)).getTaskList()
        .remove(task);
  }

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

  @Override public Project getProject(int index)
  {
    return projectList.getProject(index);
  }

  @Override public Requirement getRequirement(int indexOfRequirement,
      int indexOfProject)
  {
    return projectList.getProject(indexOfProject).getRequirementList()
        .getRequirement(indexOfProject);
  }

  @Override public Task getTask(int indexOffTask, int indexOfRequirement,
      int indexOfProject)
  {
    return projectList.getProject(indexOfProject).getRequirementList()
        .getRequirement(indexOfRequirement).getTaskList().getTask(indexOffTask);
  }

  @Override public void setProjectCreator(TeamMember teamMember,
      int indexOfProject)
  {
    projectList.getProject(indexOfProject).setProjectCreator(teamMember);
  }

  @Override public void setScrumMaster(TeamMember teamMember,
      int indexOfProject)
  {
    projectList.getProject(indexOfProject).setScrumMaster(teamMember);
  }

  @Override public void setProductOwner(TeamMember teamMember,
      int indexOfProject)
  {
    projectList.getProject(indexOfProject).setProductOwner(teamMember);
  }

  @Override public String getId(int indexOfProject)
  {
    return projectList.getProject(indexOfProject).getId();
  }

  @Override public String getId(int indexOfProject, int indexOfRequirement)
  {
    return projectList.getProject(indexOfProject).getRequirementList()
        .getRequirement(indexOfRequirement).getId();
  }

  //  @Override public String getId(int indexOfProject)
  //  {
  //    return projectList.getProject(indexOfProject).getId();j
  //  }
  @Override public String getTitle()
  {
    return null;
  }

  @Override public String getStatus()
  {
    return null;
  }

  @Override public void setStatus(String status)
  {

  }

  @Override public MyDate getDeadline()
  {
    return null;
  }

  @Override public void setDeadline(MyDate deadline)
  {

  }

  @Override public int getActualTime()
  {
    return 0;
  }

  @Override public void setActualTime(int actualTime)
  {

  }

  @Override public int getEstimatedTime()
  {
    return 0;
  }

  @Override public void setEstimatedTime(int estimatedTime)
  {

  }

  @Override public TeamMemberList getTeamMemberList()
  {
    return null;
  }

  @Override public TeamMember getResponsiblePerson()
  {
    return null;
  }

  @Override public void setResponsiblePerson(TeamMember teamMember)
  {

  }

  @Override public String getUserStory()
  {
    return null;
  }

  @Override public TaskList getTaskList()
  {
    return null;
  }

  @Override public String getName()
  {
    return null;
  }

  @Override public void setName(String name)
  {

  }
}