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
<<<<<<< HEAD
    try
    {
      int idInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid id");
    }
=======
>>>>>>> 0ca929e954d0d966d833495d6642ae9c78df3f62
    this.id = id;
  }

  public void set(String title)
  {
<<<<<<< HEAD
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
=======
    this.title = title;
  }

  public Requirement getRequirement(String ID)
  {
    for (int i = 0; i < requirementList.getSize(); i++)
    {
      if (requirementList.getRequirement(i).getId().equals(ID))
      {
        return requirementList.getRequirement(i);
      }
    }
    return null;
  }

  public void addRequirement(Requirement requirement)
  {
    requirementList.add(requirement);
  }

  public void removeRequirement(Requirement requirement)
  {
    for (int i = 0; i < requirementList.getSize(); i++)
    {
      if (requirementList.getRequirement(i).equals(requirement))
      {
        requirementList.remove(requirement);
      }
    }
  }

  public void setNote()
  {
>>>>>>> 0ca929e954d0d966d833495d6642ae9c78df3f62
    this.note = note;
  }

  public RequirementList getRequirementList()
  {
    return requirementList;
  }

  public void setRequirementList(RequirementList requirementList)
  {
<<<<<<< HEAD
    try
    {
      int reqInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid input");
    }
=======
>>>>>>> 0ca929e954d0d966d833495d6642ae9c78df3f62
    this.requirementList = requirementList;
  }

  public void setProjectCreator(TeamMember teamMember)
  {
<<<<<<< HEAD
    try
    {
      int prCrInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid input");
    }
=======
>>>>>>> 0ca929e954d0d966d833495d6642ae9c78df3f62
    projectCreator = teamMember;
  }

  public void setScrumMaster(TeamMember teamMember)
  {
<<<<<<< HEAD
    try
    {
      int scrMstrInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid input");
    }
=======
>>>>>>> 0ca929e954d0d966d833495d6642ae9c78df3f62
    scrumMaster = teamMember;
  }

  public void setProductOwner(TeamMember teamMember)
  {
<<<<<<< HEAD
    try
    {
      int prodOwnInt = Integer.parseInt(id);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Invalid input");
    }
=======
>>>>>>> 0ca929e954d0d966d833495d6642ae9c78df3f62
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

<<<<<<< HEAD
=======
  public void setStatusForProject(String Status)
  {
    setStatus(Status);
    if (Status.equals(STATUS_ENDED))
    {
      requirementList.FinishAllRequiremnts();
    }
  }

>>>>>>> 0ca929e954d0d966d833495d6642ae9c78df3f62
  public String getId()
  {
    return id;
  }

  public String getTitle()
  {
    return title;
  }
}
