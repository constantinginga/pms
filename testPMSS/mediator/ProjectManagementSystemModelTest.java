/*
package mediator;

import model.Project;
import model.ProjectList;
import model.TeamMember;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectManagementSystemModelTest
{
  private ProjectManagementSystemModel pms;

  @BeforeEach void setUp()
  {
    pms = new ProjectManagementSystemModelManager();
  }

  @AfterEach void tearDown()
  {

  }

  @Test void addProject()
  {
    pms.addProject(new Project("Tests", "Started"));
    pms.getProjectList().getProject(0).getId();
    assertEquals("1", pms.getProjectList().getProject(0).getId());
  }

  @Test void idCheckForProject()
  {
    Project p1 = new Project("Project1", "Started");
    pms.addProject(p1);
    pms.addProject(new Project("Project2", "Started"));
    assertEquals("2", pms.getProjectList().getProject(1).getId());
    pms.removeProject("1");
    assertEquals(1, pms.getProjectList().getSize());
    assertEquals("2", pms.getProjectList().getProject(0).getId());
    pms.addProject(new Project("Project3", "Started"));
    assertEquals("3", pms.getProjectList().getProject(1).getId());
    assertEquals(2, pms.getProjectList().getSize());
  }

  @Test void removeProject()
  {
    Project p1 = new Project("Project1", "Started");
    pms.addProject(p1);
    pms.addProject(new Project("Project2", "Started"));
    assertEquals(2, pms.getProjectList().getSize());
    pms.removeProject("1");
    assertEquals(1, pms.getProjectList().getSize());
  }

  @Test void addTeamMember()
  {
    pms.addTeamMember(new TeamMember("Klaus1"));
    pms.addTeamMember(new TeamMember("Klaus2"));
    assertEquals(2, pms.getTeamMemberList().getSize());

  }

  @Test void removeTeamMember()
  {
    pms.addTeamMember(new TeamMember("Klaus1"));
    pms.addTeamMember(new TeamMember("Klaus2"));
    assertEquals(2, pms.getTeamMemberList().getSize());
    pms.removeTeamMember("1");
    assertEquals(1, pms.getTeamMemberList().getSize());
  }

  @Test void teamMemberIdCHECK(){
    pms.addTeamMember(new TeamMember("Klaus1"));
    pms.addTeamMember(new TeamMember("Klaus2"));
    assertEquals("2", pms.getTeamMemberList().getTeamMember(1).getId());
    pms.removeTeamMember("1");
    assertEquals("2", pms.getTeamMemberList().getTeamMember(0).getId());
    pms.addTeamMember(new TeamMember("Klaus2"));
    assertEquals("3", pms.getTeamMemberList().getTeamMember(1).getId());
  }
  @Test void addRequirement()
  {

  }

  @Test void removeRequirement()
  {
  }

  @Test void addTask()
  {
  }

  @Test void removeTask()
  {
  }

  @Test void findById()
  {
  }

  @Test void findByTitle()
  {
  }

  @Test void findByResponsiblePerson()
  {
  }

  @Test void getProject()
  {
  }

  @Test void getRequirement()
  {
  }

  @Test void getTask()
  {
  }

  @Test void setProjectCreator()
  {
  }

  @Test void setScrumMaster()
  {
  }

  @Test void setProductOwner()
  {
  }

  @Test void getTitleForTask()
  {
  }

  @Test void getTitleForProject()
  {
  }

  @Test void getUserStoryRequirement()
  {
  }

  @Test void setStatusForTask()
  {
  }

  @Test void setStatusForRequirement()
  {
  }

  @Test void setStatusForProject()
  {
  }

  @Test void getDeadlineForTask()
  {
  }

  @Test void getDeadlineForRequirement()
  {
  }

  @Test void setDeadlineForTask()
  {
  }

  @Test void setDeadlineForRequirement()
  {
  }

  @Test void getActualTimeForTask()
  {
  }

  @Test void getActualTimeForRequirement()
  {
  }

  @Test void setActualTimeForTask()
  {
  }

  @Test void getEstimatedTimeForTask()
  {
  }

  @Test void getEstimatedTimeForRequirement()
  {
  }

  @Test void setEstimatedTimeForTask()
  {
  }

  @Test void setEstimatedTimeForRequirement()
  {
  }

  @Test void getTeamMemberListForProject()
  {
  }

  @Test void getTeamMemberListForRequirement()
  {
  }

  @Test void getTeamMemberListForTask()
  {
  }

  @Test void getResponsiblePersonForTask()
  {
  }

  @Test void getResponsiblePersonForRequirement()
  {
  }

  @Test void setResponsiblePersonForTask()
  {
  }

  @Test void setResponsiblePersonForRequirement()
  {
  }

  @Test void getUserStory()
  {
  }

  @Test void getTaskList()
  {
  }

  @Test void getProjectList()
  {
  }

  @Test void getRequirementList()
  {
  }

  @Test void getName()
  {
  }

  @Test void setName()
  {
  }
}*/
