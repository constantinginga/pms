package view.viewModel;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.ProjectManagementSystemModel;
import model.Project;
import model.ProjectList;

public class ProjectListViewModel
{
  private ObservableList<ProjectViewModel> list;
  private ProjectManagementSystemModel model;

  public ProjectListViewModel(ProjectManagementSystemModel model)
  {
    this.model = model;
    this.list = FXCollections.observableArrayList();
  }

  public ObservableList<ProjectViewModel> getList()
  {
    return list;
  }

  public void update()
  {
    list.clear();
    for (int i = 0; i < model.getProjectList().getSize(); i++)
    {
      list.add(new ProjectViewModel(model.getProject(String.valueOf(i))));
    }
  }

  public void add(Project project)
  {
    list.add(new ProjectViewModel((project)));
  }

  public void remove(Project project)
  {
    for (int i = 0; i < list.size(); i++)
    {
      if (list.get(i).getIdProperty().equals(project.getId()) && list.get(i)
          .getTitleProperty().equals(project.getTitle())  && list.get(i).getStatusProperty().equals(project.getStatus())) {
      list.remove(i);
      break;
    }
    }
  }
}
