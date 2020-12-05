package mediator;

import model.Project;
import model.ProjectList;
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

  @Test void idCheck()
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
  }
}