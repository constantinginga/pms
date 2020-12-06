package view.viewModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Task;

public class TaskViewModel {
    private StringProperty titleProperty;
    private StringProperty idProperty;
    private StringProperty statusProperty;
    private StringProperty responsiblePersonNameProperty;
    private IntegerProperty estimatedTimeProperty;
    private StringProperty deadlineProperty;
    private IntegerProperty dayProperty;
    private IntegerProperty monthProperty;
    private IntegerProperty yearProperty;

    public TaskViewModel(Task task) {
        titleProperty = new SimpleStringProperty(task.getTitle());
        idProperty = new SimpleStringProperty(task.getId());
        statusProperty = new SimpleStringProperty(task.getStatus());
        responsiblePersonNameProperty = new SimpleStringProperty(task.getResponsiblePerson().getName());
        estimatedTimeProperty = new SimpleIntegerProperty(task.getEstimatedTime());
        deadlineProperty = new SimpleStringProperty(task.getDeadline().toString());
        dayProperty = new SimpleIntegerProperty(task.getDeadline().getDay());
        monthProperty = new SimpleIntegerProperty(task.getDeadline().getMonth());
        yearProperty = new SimpleIntegerProperty(task.getDeadline().getYear());
    }

    public String getStatusProperty() {
        return statusProperty.get();
    }

    public StringProperty statusPropertyProperty() {
        return statusProperty;
    }

    public String getIdProperty() {
        return idProperty.get();
    }

    public StringProperty idPropertyProperty() {
        return idProperty;
    }

    public String getTitleProperty() {
        return titleProperty.get();
    }

    public StringProperty titlePropertyProperty() {
        return titleProperty;
    }

    public String getResponsiblePersonNameProperty() {
        return responsiblePersonNameProperty.get();
    }

    public StringProperty responsiblePersonNamePropertyProperty() {
        return responsiblePersonNameProperty;
    }

    public int getEstimatedTimeProperty() {
        return estimatedTimeProperty.get();
    }

    public IntegerProperty estimatedTimePropertyProperty() {
        return estimatedTimeProperty;
    }

    public String getDeadlineProperty() {
        return deadlineProperty.get();
    }

    public StringProperty deadlinePropertyProperty() {
        return deadlineProperty;
    }

    public int getDayProperty() {
        return dayProperty.get();
    }

    public IntegerProperty dayPropertyProperty() {
        return dayProperty;
    }

    public int getMonthProperty() {
        return monthProperty.get();
    }

    public IntegerProperty monthPropertyProperty() {
        return monthProperty;
    }

    public int getYearProperty() {
        return yearProperty.get();
    }

    public IntegerProperty yearPropertyProperty() {
        return yearProperty;
    }
}
