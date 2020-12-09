package view.viewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.ProjectManagementSystemModel;
import model.MyDate;
import model.Task;
import model.TeamMember;
import view.ViewState;

public class TaskListViewModel {
    private ObservableList<TaskViewModel> list;
    private ProjectManagementSystemModel model;
    private String selectedRequirement;
    private String selectedProject;

    public TaskListViewModel(ProjectManagementSystemModel model, String selectedProject, String selectedRequirement) {
        this.model = model;
        this.list = FXCollections.observableArrayList();
        this.selectedProject = selectedProject;
        this.selectedRequirement = selectedRequirement;
        update();
    }

    public ObservableList<TaskViewModel> getList() {
        return list;
    }

    public void update() {
        list.clear();
        for (int i = 0; i < model.getTaskList(selectedProject, selectedRequirement).getSize(); i++) {
            list.add(new TaskViewModel(model.getTask(i , selectedProject, selectedRequirement)));
        }
    }

    public void add(Task task) {
        list.add(new TaskViewModel(task));
    }

    public void remove(Task task) {
        if (task == null) return;
        for (int i = 0; i < list.size(); i++) {
            Task other = new Task(list.get(i).getTitleProperty(),
                    new TeamMember(list.get(i).getResponsiblePersonNameProperty()),
                    list.get(i).getEstimatedTimeProperty(),
                    new MyDate(list.get(i).getDayProperty(), list.get(i).getMonthProperty(), list.get(i).getYearProperty()));
            if (task.equals(other)) {
                list.remove(i);
                break;
            }
        }
    }
}
