package model;

import java.util.ArrayList;

public class TeamMemberList {
    private ArrayList<TeamMember> teamMembers;
    private int idIndex;
    private boolean wasRemoved;

    public TeamMemberList() {
        this.teamMembers = new ArrayList<>();
        this.wasRemoved = false;
        this.idIndex = 0;
    }

    public int getSize() {
        return teamMembers.size();
    }

    public boolean isWasRemoved() {
        return wasRemoved;
    }

    public void setWasRemoved(boolean wasRemoved) {
        this.wasRemoved = wasRemoved;
    }

    public void add(TeamMember teamMember) {
        teamMembers.add(teamMember);
        this.idIndex++;
        generateId(teamMember);
    }

    public void addAlreadyExists(TeamMember teamMember) {
        teamMembers.add(teamMember);
    }

    public void remove(TeamMember teamMember) {
        teamMembers.remove(teamMember);
        // get the index of last element in teamMemberList + 1
        //this.idIndex = Integer.parseInt(teamMemberList.get(teamMemberList.size() - 1).getId() + 1);
    }

    public void removeAll() {
        teamMembers.clear();
    }

    public TeamMember findByName(String name) {
        if (name == null || name.equals("")) throw new IllegalArgumentException("Name is invalid");
        for (TeamMember team : teamMembers) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }

    public TeamMember findById(String Id) {
        {
            try {
                int idNum = Integer.parseInt(Id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid ID");
            }
            for (TeamMember team : teamMembers) {
                if (team.getId().equals(Id))
                    return team;
            }

            return null;
        }
    }

    public TeamMember getTeamMember(int index) {
        try {
            return teamMembers.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    public int getTeamSize() {
        return teamMembers.size();
    }

    public void generateId(TeamMember teamMember) {
        if (teamMember == null) throw new IllegalArgumentException("Invalid team member");
        teamMember.setId(String.valueOf(idIndex));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TeamMemberList)) return false;

        TeamMemberList other = (TeamMemberList) obj;
        if (idIndex != other.idIndex || teamMembers == null || teamMembers.size() != other.getSize()) return false;
        for (int i = 0; i < teamMembers.size(); i++) {
            if (!teamMembers.get(i).equals(other.getTeamMember(i))) return false;
        }

        return true;
    }

  public void setIdIndex(int ID)
  {
      this.idIndex = ID;
  }

    public ArrayList<TeamMember> getTeamMembers()
    {
        return this.teamMembers;
    }

    public int getIdIndex()
    {
        return idIndex;
    }
}
