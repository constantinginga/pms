package model;

import java.util.ArrayList;

public class TeamMemberList {
    private ArrayList<TeamMember> teamMemberList;
    private int idIndex;

    public TeamMemberList() {
        this.teamMemberList = new ArrayList<>();
        this.idIndex = 0;
    }

    public int getSize() {
        return teamMemberList.size();
    }

    public void add(TeamMember teamMember) {
        teamMemberList.add(teamMember);
        this.idIndex++;
        generateId(teamMember);
    }

    public void remove(TeamMember teamMember) {
        teamMemberList.remove(teamMember);
        // get the index of last element in teamMemberList + 1
        //this.idIndex = Integer.parseInt(teamMemberList.get(teamMemberList.size() - 1).getId() + 1);
    }

    public TeamMember findByName(String name) {
        if (name == null || name.equals("")) throw new IllegalArgumentException("Name is invalid");
        for (TeamMember team : teamMemberList) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }

    public TeamMember findById(String Id)
    {
        {
            try
            {
                int idNum = Integer.parseInt(Id);
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Invalid ID");
            }
            for (TeamMember team : teamMemberList)
            {
                if (team.getId().equals(Id))
                    return team;
            }

            return null;
        }
    }

        public TeamMember getTeamMember(int index) {
            try {
                return teamMemberList.get(index);
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Index out of bounds");
            }
        }

        public int getTeamSize() {
            return teamMemberList.size();
        }

        public void generateId(TeamMember teamMember) {
            if (teamMember == null) throw new IllegalArgumentException("Invalid team member");
            teamMember.setId(String.valueOf(idIndex));
        }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TeamMemberList)) return false;

        TeamMemberList other = (TeamMemberList) obj;
        if (idIndex != other.idIndex || teamMemberList == null || teamMemberList.size() != other.getSize()) return false;
        for (int i = 0; i < teamMemberList.size(); i++) {
            if (!teamMemberList.get(i).equals(other.getTeamMember(i))) return false;
        }

        return true;
    }
}
