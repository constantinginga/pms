package view.viewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.ProjectManagementSystemModel;
import model.TeamMember;

public class TeamMemberListViewModel {
    private ObservableList<TeamMemberViewModel> list;
    private ProjectManagementSystemModel model;

    public TeamMemberListViewModel(ProjectManagementSystemModel model) {
        this.model = model;
        this.list = FXCollections.observableArrayList();
        update();
    }

    public ObservableList<TeamMemberViewModel> getList() {
        return list;
    }

    public void update() {
        list.clear();
        for (int i = 0; i < model.getTeamMemberList().getSize(); i++) {
            list.add(new TeamMemberViewModel(model.getTeamMemberList().getTeamMember(i)));
        }
    }

    public void add(TeamMember teamMember) {
        list.add(new TeamMemberViewModel(teamMember));
    }

    public void remove(TeamMember teamMember) {
        if (teamMember == null) return;
        for (int i = 0; i < list.size(); i++) {
            TeamMember other = new TeamMember(list.get(i).getNameProperty());
            other.setId(list.get(i).getIdProperty());
            if (teamMember.equals(other)) {
                list.remove(i);
                break;
            }
        }
    }

    public void changeMember(TeamMember teamMember) {
        if (teamMember == null) return;
        for (int i = 0; i < list.size(); i++) {
            TeamMember other = new TeamMember(list.get(i).getNameProperty());
            other.setId(list.get(i).getIdProperty());
            if (teamMember.equals(other)) {
                list.set(i, new TeamMemberViewModel(teamMember));
                break;
            }
        }
    }
}
