
package mediator;

import model.*;
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

  @Test void teamMemberIdCHECK()
  {
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
    Project p1 = new Project("Project1", "Started");
    pms.addTeamMember(new TeamMember("Klaus1"));
    pms.addProject(p1);
    pms.addProject(new Project("Project2", "Started"));
    pms.addRequirement(
        new Requirement("ja1", pms.getTeamMemberList().findById("1"), "Started",
            30, new MyDate(23, 12, 2021)), "1");
    assertEquals(1, pms.getProject("1").getRequirementList().getSize());
    assertEquals("ja1", pms.getProject("1").getRequirement("1").getUserStory());
    pms.addRequirement(
        new Requirement("ja2", pms.getTeamMemberList().findById("1"), "Started",
            30, new MyDate(23, 12, 2021)), "1");
    assertEquals(2, pms.getProject("1").getRequirementList().getSize());
    assertEquals("ja2", pms.getProject("1").getRequirement("2").getUserStory());
    pms.addRequirement(
        new Requirement("2ja1", pms.getTeamMemberList().findById("1"),
            "Started", 30, new MyDate(23, 12, 2021)), "2");
    assertEquals("2ja1", pms.getRequirement("1", "2").getUserStory());

  }

  @Test void removeRequirement()
  {
    Project p1 = new Project("Project1", "Started");
    pms.addTeamMember(new TeamMember("Klaus1"));
    pms.addProject(p1);
    pms.addProject(new Project("Project2", "Started"));
    pms.addRequirement(
        new Requirement("ja1", pms.getTeamMemberList().findById("1"), "Started",
            30, new MyDate(23, 12, 2021)), "1");
    pms.addRequirement(
        new Requirement("ja2", pms.getTeamMemberList().findById("1"), "Started",
            30, new MyDate(23, 12, 2021)), "1");
    pms.removeRequirement("1", "1");
    assertEquals(1, pms.getProject("1").getRequirementList().getSize());
    assertEquals("2",
        pms.getProject("1").getRequirementList().getRequirement(0).getId());
    pms.addRequirement(
        new Requirement("2ja1", pms.getTeamMemberList().findById("1"),
            "Started", 30, new MyDate(23, 12, 2021)), "2");
  }

  @Test void addTask()
  {
    Project p1 = new Project("Project1", "Started");
    pms.addTeamMember(new TeamMember("Klaus1"));
    pms.addProject(p1);
    pms.addProject(new Project("Project2", "Started"));
    pms.addRequirement(
        new Requirement("ja1", pms.getTeamMemberList().findById("1"), "Started",
            30, new MyDate(23, 12, 2021)), "1");
    pms.addRequirement(
        new Requirement("ja2", pms.getTeamMemberList().findById("1"), "Started",
            30, new MyDate(23, 12, 2021)), "1");
    pms.addTask(new Task("task1", pms.getTeamMemberList().findById("1"), 30,
        new MyDate(23, 12, 2021)), "1", "1");
    pms.addTask(new Task("task2", pms.getTeamMemberList().findById("1"), 30,
        new MyDate(23, 12, 2021)), "1", "1");
    assertEquals(2,
        pms.getProject("1").getRequirement("1").getTaskList().getSize());

  }

  @Test void removeTask()
  {
    Project p1 = new Project("Project1", "Started");
    pms.addTeamMember(new TeamMember("Klaus1"));
    pms.addProject(p1);
    pms.addProject(new Project("Project2", "Started"));
    pms.addRequirement(
        new Requirement("ja1", pms.getTeamMemberList().findById("1"), "Started",
            30, new MyDate(23, 12, 2021)), "1");
    pms.addRequirement(
        new Requirement("ja2", pms.getTeamMemberList().findById("1"), "Started",
            30, new MyDate(23, 12, 2021)), "1");
    pms.addTask(new Task("task1", pms.getTeamMemberList().findById("1"), 30,
        new MyDate(23, 12, 2021)), "1", "1");
    pms.addTask(new Task("task2", pms.getTeamMemberList().findById("1"), 30,
        new MyDate(23, 12, 2021)), "1", "1");
    pms.getProject("1").getRequirement("1").removeTask(pms.getTask("1","1","1"));
    assertEquals(1,
        pms.getProject("1").getRequirement("1").getTaskList().getSize());
    assertEquals("task2",pms.getProject("1").getRequirement("1").getTask("2").getTitle());
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
}

