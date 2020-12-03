package mediator;

import model.*;

public class ProjectManagementSystemModelManager
    implements ProjectManagementSystemModel
{

  private ProjectList projectList;

  private ProjectManagementSystemModel projectManagementSystemModel;

//  public ProjectManagementSystem()
//  {
//
//  }

  @Override public void add(Project project)
  {

  }

  @Override public void remove(Project project)
  {

  }

  @Override public void add(TeamMember teammember)
  {

  }

  @Override public void remove(TeamMember teammember)
  {

  }

  @Override public void add(Requirement requirement)
  {

  }

  @Override public void remove(Requirement requirment)
  {

  }

  @Override public void add(Task task)
  {

  }

  @Override public void remove(Task task)
  {

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
    return null;
  }

  @Override public Requirement getRequirement(int index)
  {
    return null;
  }

  @Override public Task getTask(int index)
  {
    return null;
  }

  @Override public void setProjectCreator(TeamMember teamMember)
  {

  }

  @Override public void setScrumMaster(TeamMember teamMember)
  {

  }

  @Override public void setProductOwner(TeamMember teamMember)
  {

  }

  @Override public String getId()
  {
    return null;
  }

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
