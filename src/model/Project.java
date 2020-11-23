package model;

import java.util.ArrayList;

public class Project extends GeneralTemplate
{
  private String id;

  private String title;

  private String note;

  private TeamMember projectCreator;

  private TeamMember scrumMaster;

  private TeamMember productOwner;

  private TeamMemberList teamMembers;

  private RequirementList requirementList;

  private ProjectList projectList;

  public Project(String title, String status)
  {
    super(status);
    this.title = title;
  }

  public void setId(String id)
  {
    this.id= id;
  }

  public void set(String title)
  {
    this.title = title;
  }

  public void setNote()
  {
    this.note = note;
  }

  public void setProjectCreator(TeamMember teamMember)
  {
		projectCreator = teamMember;
  }

  public void setScrumMaster(TeamMember teamMember)
  {
		scrumMaster = teamMember;
  }

  public void setProductOwner(TeamMember teamMember)
  {
		productOwner = teamMember;
  }

  public void setPosition(String position, TeamMember teamMember)
  {
    switch (position.toLowerCase())
    {
      case "scrum master":
        scrumMaster = teamMember;
        break;
      case "project creator":
        projectCreator = teamMember;
				break;
      case "product owner":
        productOwner = teamMember;
				break;
    }
  }
  public String getId(){
  	return id;
	}
	public String getTitle(){
    return title;
  }

}
