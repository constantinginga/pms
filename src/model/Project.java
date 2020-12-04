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
  private RequirementList requirementList;

  public Project(String title, String status)
  {
    super(status);
    set(title);
    this.productOwner = null;
    this.scrumMaster = null;
    this.projectCreator = null;
    this.requirementList = new RequirementList();
  }

  public void setId(String id)
  {
    try
    {
      int idInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid id");
    }
    this.id = id;
  }

  public void set(String title)
  {
    if (title.equals("") || title == null || title.length() < 3)
    {
      throw new IllegalArgumentException("Invalid title");
    }
    this.title = title;
  }

  public void setNote()
  {
    try
    {
      int noteInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid id");
    }
    this.note = note;
  }

  public RequirementList getRequirementList()
  {
    return requirementList;
  }

  public void setRequirementList(RequirementList requirementList)
  {
    try
    {
      int reqInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid input");
    }
    this.requirementList = requirementList;
  }

  public void setProjectCreator(TeamMember teamMember)
  {
    try
    {
      int prCrInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid input");
    }
    projectCreator = teamMember;
  }

  public void setScrumMaster(TeamMember teamMember)
  {
    try
    {
      int scrMstrInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid input");
    }
    scrumMaster = teamMember;
  }

  public void setProductOwner(TeamMember teamMember)
  {
    try
    {
      int prodOwnInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid input");
    }
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
    }
  }

  public String getId()
  {
    return id;
  }

  public String getTitle()
  {
    return title;
  }
}
