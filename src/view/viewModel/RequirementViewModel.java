package view.viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Requirement;

public class RequirementViewModel {
    private StringProperty userStoryProperty;
    private StringProperty IdProperty;
    private StringProperty statusProperty;
    private StringProperty deadLineProperty;

    public RequirementViewModel(Requirement requirement) {
        userStoryProperty = new SimpleStringProperty(requirement.getUserStory());
        IdProperty = new SimpleStringProperty(requirement.getId());
        statusProperty = new SimpleStringProperty(requirement.getStatus());
        deadLineProperty = new SimpleStringProperty(requirement.getDeadline().toString());
    }

    public String getUserStoryProperty() {
        return userStoryProperty.get();
    }

    public StringProperty userStoryPropertyProperty() {
        return userStoryProperty;
    }

    public void setUserStoryProperty(String userStoryProperty) {
        this.userStoryProperty.set(userStoryProperty);
    }

    public String getIdProperty() {
        return IdProperty.get();
    }

    public StringProperty idPropertyProperty() {
        return IdProperty;
    }

    public void setIdProperty(String idProperty) {
        this.IdProperty.set(idProperty);
    }

    public String getStatusProperty() {
        return statusProperty.get();
    }

    public StringProperty statusPropertyProperty() {
        return statusProperty;
    }

    public void setStatusProperty(String statusProperty) {
        this.statusProperty.set(statusProperty);
    }

    public String getDeadLineProperty() {
        return deadLineProperty.get();
    }

    public StringProperty deadLinePropertyProperty() {
        return deadLineProperty;
    }

    public void setDeadLineProperty(String deadLineProperty) {
        this.deadLineProperty.set(deadLineProperty);
    }
}
