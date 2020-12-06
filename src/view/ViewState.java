package view;

public class ViewState
{
  private String selectedProjectID;
  private String selectedRequirementID;
  private  String selectedTaskID;
  public ViewState(){
    this.selectedProjectID = "";
    this.selectedRequirementID = "";
    this.selectedTaskID = "";
  }

  public String getSelectedProjectID()
  {
    return selectedProjectID;
  }

  public void setSelectedProjectID(String selectedProjectID)
  {
    this.selectedProjectID = selectedProjectID;
  }

  public String getSelectedRequirementID()
  {
    return selectedRequirementID;
  }

  public void setSelectedRequirementID(String selectedRequirementID)
  {
    this.selectedRequirementID = selectedRequirementID;
  }

  public String getSelectedTaskID()
  {
    return selectedTaskID;
  }

  public void setSelectedTaskID(String selectedTaskID)
  {
    this.selectedTaskID = selectedTaskID;
  }
}
