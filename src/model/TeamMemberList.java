package model;

import java.util.ArrayList;

public class TeamMemberList {
    private ArrayList<TeamMember> teamMemberList;

    public TeamMemberList() {
        this.teamMemberList = new ArrayList<>();
    }

    public void add(TeamMember teamMember) {
        teamMemberList.add(teamMember);
    }

    public void remove(TeamMember teamMember) {
        teamMemberList.remove(teamMember);
    }

    public TeamMember findByName(String name) {
        for (TeamMember team : teamMemberList) {
            if (team.getName().equals(name)) return team;
        }

        return null;
    }

    public TeamMember findById(String Id) {
        for (TeamMember team : teamMemberList) {
            if (team.getId().equals(Id)) return team;
        }

        return null;
    }

    public TeamMember getTeamMember(int index) {
        return teamMemberList.get(index);
    }

    public int getTeamSize() {
        return teamMemberList.size();
    }

    public TeamMember getTeamMemberByName(String name) {
        for (TeamMember team : teamMemberList) {
            TeamMember temp = (TeamMember) team;
            if (temp.getName().equals(name)) return temp;
        }

        return null;
    }

    public TeamMember getTeamMemberById(String id) {
        for (TeamMember team : teamMemberList) {
            TeamMember temp = (TeamMember) team;
            if (temp.getId().equals(id)) return temp;
        }

        return null;
    }
}
