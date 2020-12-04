package mediator;

import model.Project;
import model.TeamMember;
import model.Requirement;
import model.Task;
import model.GeneralTemplate;
import model.MyDate;
import model.TeamMemberList;
import model.TaskList;

public interface ProjectManagementSystemModel
{

  public abstract void addProject(Project project);

  public abstract void removeProject(Project project);

  public abstract void addTeamMember(TeamMember teammember);

  public abstract void removeTeamMember(TeamMember teammember);

  public abstract void addRequirement(Requirement requirement, Project project);
  public abstract void addRequirement(Requirement requirement,
      int indexOfProject);

  public abstract void removeRequirement(Requirement requirment,
      Project project);
  public abstract void removeRequirement(Requirement requirment,
      int indexOfProject);

  public abstract void addTask(Task task, Project project,
      Requirement requirement);
  public abstract void addTask(Task task, int indexOfProject,
      int indexOfRequirement);

  public abstract void removeTask(Task task, Project project,
      Requirement requirement);
  public abstract void removeTask(Task task, int indexOfProject,
      int indexOfRequirement);
  public abstract GeneralTemplate findById(String id);

  public abstract GeneralTemplate findByTitle(String title);

  public abstract Requirement findByResponsiblePerson(TeamMember teamMember);

  public abstract Project getProject(int index);
  public abstract Project getProjectFromList(Project project);

  public abstract Requirement getRequirement(int indexOfRequirement,
      int indexOfProject);
  public abstract Requirement getRequirementFromList(Requirement requirement,
      Project project)
  public abstract Task getTask(int indexOfTask, int indexOfRequirement,
      int indexOfProject);
  public abstract Task getTaskFromList(Task task, Requirement requirement,
      Project project);

  public abstract void setProjectCreator(TeamMember teamMember,
      int indexOfProject);
  public abstract void setProjectCreator(TeamMember teamMember,
      Project project);

  public abstract void setScrumMaster(TeamMember teamMember, int indexOfProject);
  public abstract void setScrumMaster(TeamMember teamMember,
      Project project);


  public abstract void setProductOwner(TeamMember teamMember,int indexOfProject);
  public abstract void setProductOwner(TeamMember teamMember,
      Project project);

  public abstract String getId();

  public abstract String getTitle();

  public abstract String getStatus();

  public abstract void setStatus(String status);

  public abstract MyDate getDeadline();

  public abstract void setDeadline(MyDate deadline);

  public abstract int getActualTime();

  public abstract void setActualTime(int actualTime);

  public abstract int getEstimatedTime();

  public abstract void setEstimatedTime(int estimatedTime);

  public abstract TeamMemberList getTeamMemberList();

  public abstract TeamMember getResponsiblePerson();

  public abstract void setResponsiblePerson(TeamMember teamMember);

  public abstract String getUserStory();

  public abstract TaskList getTaskList();

  public abstract String getName();

  public abstract void setName(String name);

}
