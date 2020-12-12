package mediator;

import model.Project;
import model.ProjectList;
import model.TeamMember;
import model.TeamMemberList;
import parser.XmlJsonParser;

import java.io.File;

public class ProjectManagementSystemFile
{
  private XmlJsonParser parser = new XmlJsonParser();
  private String fileName;
  private File fileForTeamMembers;
  private File fileForProjects;
  private String fileNameForProjects;
  private String fileNameForTeamMembers;

  public ProjectManagementSystemFile(ProjectList projectList,
      TeamMemberList teamMemberList)
  {
//    fileNameForTeamMembers = "pmsTeamMembers.xml";
//    fileNameForProjects = "pmsFiles.xml";
  }

  public void saveToFile(ProjectList projectList)
  {
//    try
//    {
//      fileForProjects = parser.toXml(projectList, fileNameForProjects);
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//    }
  }

  public void saveToFile(TeamMemberList teamMemberList)
  {
//    try
//    {
//      fileForTeamMembers = parser.toXml(teamMemberList, fileNameForTeamMembers);
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//    }
  }

  public TeamMemberList readTeamMemberList()
  {
//    TeamMemberList teamMemberList = new TeamMemberList();
//    try
//    {
//      teamMemberList = parser.fromXml(fileNameForTeamMembers, TeamMemberList.class);
//      return teamMemberList;
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//    }
//    return teamMemberList;
    return null;

  }

  public ProjectList readProjects()
  {
//    ProjectList projectList = new ProjectList();
//    try
//    {
//      projectList = parser.fromXml(fileNameForProjects, ProjectList.class);
//      return projectList;
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//    }
//    return projectList;
    return null;
  }
}
