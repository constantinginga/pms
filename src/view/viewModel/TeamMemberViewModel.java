package view.viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.TeamMember;

public class TeamMemberViewModel {
    private StringProperty idProperty;
    private StringProperty nameProperty;

    public TeamMemberViewModel(TeamMember teamMember) {
        idProperty = new SimpleStringProperty(teamMember.getId());
        nameProperty = new SimpleStringProperty(teamMember.getName());
    }

    public String getIdProperty() {
        return idProperty.get();
    }

    public StringProperty idPropertyProperty() {
        return idProperty;
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public StringProperty namePropertyProperty() {
        return nameProperty;
    }
}
