package view.viewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.ProjectManagementSystemModel;
import model.Project;

public class ProjectListViewModel {
    private ObservableList<ProjectViewModel> list;
    private ProjectManagementSystemModel model;

    public ProjectListViewModel(ProjectManagementSystemModel model) {
        this.model = model;
        this.list = FXCollections.observableArrayList();
        update();
    }

    public ObservableList<ProjectViewModel> getList() {
        return list;
    }

    public void update() {
        list.clear();
        for (int i = 0; i < model.getProjectList().getSize(); i++) {
            list.add(new ProjectViewModel(model.getProjectList().getProject(i)));
        }
    }

    public void add(Project project) {
        list.add(new ProjectViewModel(project));
    }

    public void remove(String projectId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdProperty().equals(projectId)) {
                list.remove(i);
                break;
            }
        }
    }
}
