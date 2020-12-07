package view.viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Project;

public class ProjectViewModel
{
  private StringProperty idProperty;
  private StringProperty titleProperty;
  private StringProperty statusProperty;

  public ProjectViewModel(Project project)
  {
    this.idProperty = new SimpleStringProperty(project.getId());
    this.titleProperty = new SimpleStringProperty(project.getTitle());
    this.statusProperty = new SimpleStringProperty(project.getStatus());
  }

  public String getIdProperty()
  {
    return idProperty.get();
  }

  public StringProperty idPropertyProperty()
  {
    return idProperty;
  }

  public String getTitleProperty()
  {
    return titleProperty.get();
  }

  public StringProperty titlePropertyProperty()
  {
    return titleProperty;
  }

  public String getStatusProperty()
  {
    return statusProperty.get();
  }

  public StringProperty statusPropertyProperty()
  {
    return statusProperty;
  }
}
