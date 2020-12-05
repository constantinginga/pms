package model;

import java.util.ArrayList;
import java.util.Objects;

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
    this.title = title;
    this.productOwner = null;
    this.scrumMaster = null;
    this.projectCreator = null;
    this.requirementList = new RequirementList();
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public void set(String title)
  {
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
    this.note = note;
  }

  public RequirementList getRequirementList()
  {
    return requirementList;
  }

  public void setRequirementList(RequirementList requirementList)
  {
    this.requirementList = requirementList;
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
    }
  }

  public void setStatusForProject(String Status)
  {
    setStatus(Status);
    if (Status.equals(STATUS_ENDED))
    {
      requirementList.FinishAllRequiremnts();
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

//  @Override public boolean equals(Object obj)
//  {
//    if (!(obj instanceof Project))
//      return false;
//
//    Project other = (Project) obj;
//    return super.equals(other) && id != null && title != null && note != null
//        && requirementList != null && id.equals(other.id) && title
//        .equals(other.title) && note.equals(other.note) && requirementList
//        .equals(other.requirementList);
//  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    Project project = (Project) o;
    return Objects.equals(id, project.id) && Objects
        .equals(title, project.title) && Objects.equals(note, project.note)
        && Objects.equals(projectCreator, project.projectCreator) && Objects
        .equals(scrumMaster, project.scrumMaster) && Objects
        .equals(productOwner, project.productOwner) && Objects
        .equals(requirementList, project.requirementList);
  }

  @Override public String toString()
  {
    return "Project{" + "id='" + id + '\'' + ", title='" + title + '\''
        + ", note='" + note + '\'' + ", projectCreator=" + projectCreator
        + ", scrumMaster=" + scrumMaster + ", productOwner=" + productOwner
        + ", requirementList=" + requirementList + '}';
  }
}
